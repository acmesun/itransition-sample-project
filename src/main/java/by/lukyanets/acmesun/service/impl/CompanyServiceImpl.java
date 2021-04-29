package by.lukyanets.acmesun.service.impl;

import by.lukyanets.acmesun.dto.company.BonusDto;
import by.lukyanets.acmesun.dto.company.CompanyDto;
import by.lukyanets.acmesun.dto.image.ImageDto;
import by.lukyanets.acmesun.entity.BonusEntity;
import by.lukyanets.acmesun.entity.CompanyEntity;
import by.lukyanets.acmesun.entity.ImageEntity;
import by.lukyanets.acmesun.entity.UserEntity;
import by.lukyanets.acmesun.repository.CompanyRepository;
import by.lukyanets.acmesun.repository.UserRepository;
import by.lukyanets.acmesun.service.CompanyService;
import com.cloudinary.Cloudinary;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityExistsException;
import java.io.IOException;
import java.util.ArrayList;
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
    public CompanyEntity createNewCompany(CompanyDto companyDto, MultipartFile[] images) throws IOException {
        if (repository.existsByCompanyName(companyDto.getCompanyName())) {
            throw new EntityExistsException(companyDto.getCompanyName() + " already exist.");
        }
        var companyEntity = getCompanyEntity(companyDto);
        UserEntity owner = userRepo.findUserEntityByEmail(
                SecurityContextHolder.getContext().getAuthentication().getName()
        ).orElseThrow();
        companyEntity.setOwner(owner);
        var bonusDtoList = companyDto.getBonusList();
        var bonusEntities = bonusDtoList.stream()
                .map(this::mapBonusDtoToEntity)
                .collect(toList());
        companyEntity.setBonusList(bonusEntities);
        addImagesToCompanyDto(images, companyDto);
        var imageList = companyDto.getImageList();
        List<ImageEntity> imageEntities = new ArrayList<>();
        for (ImageDto imageDto : imageList) {
            var imageEntity = new ImageEntity();
            imageEntity.setTitle(imageDto.getTitle());
            imageEntities.add(imageEntity);
        }
        companyEntity.setImageList(imageEntities);
        return repository.save(companyEntity);
    }


    private void addImagesToCompanyDto(MultipartFile[] images, CompanyDto companyDto) throws IOException {
        List<Map<?, ?>> results = new ArrayList<>(images.length);
        List<ImageDto> imageDtos = new ArrayList<>();
        for (MultipartFile multipartFile : images) {
            results.add(cloudinary.uploader().upload(multipartFile.getBytes(), new HashMap<Long, Object>()));
            var imageDto = new ImageDto();
            var name = multipartFile.getOriginalFilename();
            imageDto.setTitle(name);
            imageDtos.add(imageDto);
        }
        companyDto.setImageList(imageDtos);
    }

    private CompanyEntity getCompanyEntity(CompanyDto companyDto) {
        var companyEntity = new CompanyEntity();
        companyEntity.setCompanyName(companyDto.getCompanyName());
        companyEntity.setSubject(companyDto.getSubject());
        companyEntity.setCompanyDescription(companyDto.getCompanyDescription());
        companyEntity.setTargetAmount(companyDto.getTargetAmount());
        companyEntity.setExpirationDate(companyDto.getExpirationDate());

        return companyEntity;
    }

    private BonusEntity mapBonusDtoToEntity(BonusDto bonusDto) {
        var bonusEntity = new BonusEntity();
        bonusEntity.setBonusName(bonusDto.getBonusName());
        bonusEntity.setDescription(bonusDto.getDescription());
        bonusEntity.setAmount(bonusDto.getAmount());
        return bonusEntity;
    }

}
