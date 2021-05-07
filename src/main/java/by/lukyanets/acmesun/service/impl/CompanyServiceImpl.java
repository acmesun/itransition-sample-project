package by.lukyanets.acmesun.service.impl;

import by.lukyanets.acmesun.dto.company.BonusDto;
import by.lukyanets.acmesun.dto.company.CompanyDto;
import by.lukyanets.acmesun.dto.company.CompanyDtoAllInfo;
import by.lukyanets.acmesun.dto.company.CompanyDtoToList;
import by.lukyanets.acmesun.dto.image.ImageDto;
import by.lukyanets.acmesun.entity.*;
import by.lukyanets.acmesun.repository.BuyBonusRepository;
import by.lukyanets.acmesun.repository.CompanyRepository;
import by.lukyanets.acmesun.repository.UserRepository;
import by.lukyanets.acmesun.service.CompanyService;
import com.cloudinary.Cloudinary;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityExistsException;
import java.util.*;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepository companyRepo;
    private final BuyBonusRepository bbonusRepo;
    private final UserRepository userRepo;
    private final CurrentUserService userService;
    private final Cloudinary cloudinary;

    @Override
    public CompanyEntity createNewCompany(CompanyDto companyDto, MultipartFile[] images) {
        if (companyRepo.existsByCompanyName(companyDto.getCompanyName())) {
            throw new EntityExistsException(companyDto.getCompanyName() + " already exist.");
        }
        var companyEntity = getDataFromDto(companyDto);
        companyEntity.setOwner(findOwner());
        companyEntity.setImageList(Arrays.stream(images).map(this::processImage).collect(toList()));
        return companyRepo.save(companyEntity);
    }

    @Override
    public List<CompanyDtoToList> listOfAllCompanies() {
        return companyRepo.findAllByOrderByCompanyNameAsc()
                .stream()
                .map(entity -> new CompanyDtoToList(
                        entity.getId(),
                        entity.getCompanyName(),
                        entity.getSubject(),
                        entity.getTargetAmount(),
                        entity.getExpirationDate(),
                        entity.getOwner().getName())).collect(toList());
    }

    @Override
    public List<CompanyDtoToList> listOfCompaniesBySubject(Subject subject) {
        return companyRepo.findAllBySubjectOrderByCompanyNameAsc(subject)
                .stream()
                .map(entity -> new CompanyDtoToList(
                        entity.getId(),
                        entity.getCompanyName(),
                        entity.getSubject(),
                        entity.getTargetAmount(),
                        entity.getExpirationDate(),
                        entity.getOwner().getName())).collect(toList());

    }

    @Override
    public List<CompanyDtoAllInfo> companyInfoByOwner(String email) {
        var companyEntitiesByOwner = companyRepo.findAllByOwnerEmail(email);
        List<CompanyDtoAllInfo> companyDtoAllInfos = new ArrayList<>();
        for (var companyEntity : companyEntitiesByOwner) {
            companyDtoAllInfos.add(fromCompanyEntityToCompanyDtoAllInfo(companyEntity));
        }
        return companyDtoAllInfos;
    }

    @Override
    public void deleteCompanyByName(String name) {
        companyRepo.delete(companyRepo.findCompanyEntityByCompanyName(name));
    }


    private CompanyDtoAllInfo fromCompanyEntityToCompanyDtoAllInfo(CompanyEntity companyEntity) {
        var companyDtoAllInfo = new CompanyDtoAllInfo();
        companyDtoAllInfo.setId(companyEntity.getId());
        companyDtoAllInfo.setCompanyName(companyEntity.getCompanyName());
        companyDtoAllInfo.setCompanyDescription(companyEntity.getCompanyDescription());
        companyDtoAllInfo.setSubject(companyEntity.getSubject());
        var bonusList = companyEntity.getBonusList();
        List<BonusDto> bonusDtos = new ArrayList<>(bonusList.size());
        Integer currentAmount = 0;
        for (BonusEntity bonusEntity : bonusList) {
            BonusDto bonusDto = new BonusDto();
            bonusDto.setBonusName(bonusEntity.getBonusName());
            bonusDto.setAmount(bonusEntity.getAmount());
            bonusDto.setDescription(bonusEntity.getDescription());
            Set<BuyBonusEntity> boughtBonuses = bonusEntity.getBoughtBonuses();
            for (BuyBonusEntity buyBonusEntity : boughtBonuses) {
                Integer quantity = buyBonusEntity.getQuantity();
                Integer amount = buyBonusEntity.getBonus().getAmount();
                Integer sum = quantity * amount;
                currentAmount = currentAmount + sum;
            }
            bonusDtos.add(bonusDto);
        }
        companyDtoAllInfo.setCurrentAmount(currentAmount);
        companyDtoAllInfo.setBonusList(bonusDtos);
        companyDtoAllInfo.setTargetAmount(companyEntity.getTargetAmount());
        companyDtoAllInfo.setExpirationDate(companyEntity.getExpirationDate());
        companyDtoAllInfo.setOwner(companyEntity.getOwner().getEmail());
        var imageList = companyEntity.getImageList();
        List<ImageDto> imageDtos = new ArrayList<>(imageList.size());
        for (var imageEntity : imageList) {
            ImageDto imageDto = new ImageDto();
            imageDto.setTitle(imageEntity.getTitle());
            imageDto.setUrl(imageEntity.getUrl());
            imageDtos.add(imageDto);
        }
        companyDtoAllInfo.setImageList(imageDtos);
        return companyDtoAllInfo;
    }

    @Override
    public CompanyDtoAllInfo companyInfoByName(String name) {
        var companyEntityByName = companyRepo.findCompanyEntityByCompanyName(name);
        return fromCompanyEntityToCompanyDtoAllInfo(companyEntityByName);
    }

    private UserEntity findOwner() {
        return userRepo.findUserEntityByEmail(userService.getCurrentUser().getEmail())
                .orElseThrow();
    }

    @SneakyThrows
    private ImageEntity processImage(MultipartFile image) {
        Map<?, ?> uploadResult = cloudinary.uploader().upload(image.getBytes(), new HashMap<Long, Object>());
        return new ImageEntity(
                image.getOriginalFilename(),
                (String) uploadResult.get("url")
        );
    }

    private CompanyEntity getDataFromDto(CompanyDto companyDto) {
        var companyEntity = new CompanyEntity();
        companyEntity.setCompanyName(companyDto.getCompanyName());
        companyEntity.setSubject(companyDto.getSubject());
        companyEntity.setCompanyDescription(companyDto.getCompanyDescription());
        companyEntity.setTargetAmount(companyDto.getTargetAmount());
        companyEntity.setExpirationDate(companyDto.getExpirationDate());
        companyEntity.setBonusList(companyDto.getBonusList().stream().map(this::getDataFromDto).collect(toList()));
        return companyEntity;
    }

    private BonusEntity getDataFromDto(BonusDto bonusDto) {
        var bonusEntity = new BonusEntity();
        bonusEntity.setBonusName(bonusDto.getBonusName());
        bonusEntity.setDescription(bonusDto.getDescription());
        bonusEntity.setAmount(bonusDto.getAmount());
        return bonusEntity;
    }

}
