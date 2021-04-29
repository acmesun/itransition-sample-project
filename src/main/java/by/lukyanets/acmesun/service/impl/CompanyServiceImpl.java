package by.lukyanets.acmesun.service.impl;

import by.lukyanets.acmesun.dto.company.BonusDto;
import by.lukyanets.acmesun.dto.company.CompanyDto;
import by.lukyanets.acmesun.dto.company.CompanyDtoToList;
import by.lukyanets.acmesun.entity.*;
import by.lukyanets.acmesun.repository.CompanyRepository;
import by.lukyanets.acmesun.repository.UserRepository;
import by.lukyanets.acmesun.service.CompanyService;
import com.cloudinary.Cloudinary;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityExistsException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepository repository;
    private final UserRepository userRepo;
    private final Cloudinary cloudinary;

    @Override
    public CompanyEntity createNewCompany(CompanyDto companyDto, MultipartFile[] images) {
        if (repository.existsByCompanyName(companyDto.getCompanyName())) {
            throw new EntityExistsException(companyDto.getCompanyName() + " already exist.");
        }
        var companyEntity = getDataFromDto(companyDto);
        companyEntity.setOwner(findOwner());
        companyEntity.setImageList(Arrays.stream(images).map(this::processImage).collect(toList()));
        return repository.save(companyEntity);
    }

    @Override
    public List<CompanyDtoToList> listOfAllCompanies() {
        return repository.findAllByOrderByCompanyNameAsc()
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
        return repository.findAllBySubjectOrderByCompanyNameAsc(subject)
                .stream()
                .map(entity -> new CompanyDtoToList(
                        entity.getId(),
                        entity.getCompanyName(),
                        entity.getSubject(),
                        entity.getTargetAmount(),
                        entity.getExpirationDate(),
                        entity.getOwner().getName())).collect(toList());

    }

    private UserEntity findOwner() {
        return userRepo.findUserEntityByEmail(SecurityContextHolder.getContext().getAuthentication().getName())
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
