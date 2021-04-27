package by.lukyanets.acmesun.service.impl;

import by.lukyanets.acmesun.dto.company.BonusDto;
import by.lukyanets.acmesun.dto.company.CompanyDto;
import by.lukyanets.acmesun.entity.BonusEntity;
import by.lukyanets.acmesun.entity.CompanyEntity;
import by.lukyanets.acmesun.entity.UserEntity;
import by.lukyanets.acmesun.repository.CompanyRepository;
import by.lukyanets.acmesun.repository.UserRepository;
import by.lukyanets.acmesun.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepository repository;
    private final UserRepository userRepo;

    @Override
    public CompanyEntity createNewCompany(CompanyDto companyDto) {
        if (repository.existsByCompanyName(companyDto.getCompanyName())) {
            throw new EntityExistsException(companyDto.getCompanyName() + " already exist.");
        }
        var companyEntity = new CompanyEntity();
        companyEntity.setCompanyName(companyDto.getCompanyName());
        companyEntity.setSubject(companyDto.getSubject());
        companyEntity.setCompanyDescription(companyDto.getCompanyDescription());
        companyEntity.setTargetAmount(companyDto.getTargetAmount());
        companyEntity.setExpirationDate(companyDto.getExpirationDate());
        UserEntity owner = userRepo.findUserEntityByEmail(
                SecurityContextHolder.getContext().getAuthentication().getName()
        ).orElseThrow();
        companyEntity.setOwner(owner);
        var bonusDtoList = companyDto.getBonusList();
        var bonusEntities = bonusDtoList.stream()
                .map(this::mapBonusDtoToEntity)
                .collect(toList());
        companyEntity.setBonusList(bonusEntities);

        return repository.save(companyEntity);
    }

    private BonusEntity mapBonusDtoToEntity(BonusDto bonusDto) {
        var bonusEntity = new BonusEntity();
        bonusEntity.setBonusName(bonusDto.getBonusName());
        bonusEntity.setDescription(bonusDto.getDescription());
        bonusEntity.setAmount(bonusDto.getAmount());
        return bonusEntity;
    }

}
