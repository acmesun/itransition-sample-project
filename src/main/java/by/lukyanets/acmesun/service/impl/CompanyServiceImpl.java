package by.lukyanets.acmesun.service.impl;

import by.lukyanets.acmesun.dto.company.CompanyDto;
import by.lukyanets.acmesun.entity.CompanyEntity;
import by.lukyanets.acmesun.repository.CompanyRepository;
import by.lukyanets.acmesun.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepository repository;

    @Override
    public CompanyEntity createNewCompany(CompanyDto companyDto) {
        if (repository.existsByCompanyName(companyDto.getCompanyName())) {
            throw new EntityExistsException(companyDto.getCompanyName() + " already exist.");
        }
        var companyEntity = new CompanyEntity();
        companyEntity.setCompanyName(companyDto.getCompanyName());
        companyDto.setCompanyDescription(companyDto.getCompanyDescription());
        companyDto.setSubject(companyDto.getSubject());
        companyDto.setTargetAmount(companyDto.getTargetAmount());
        companyDto.setBonusList(companyDto.getBonusList());
        companyDto.setExpirationDate(companyDto.getExpirationDate());
        return repository.save(companyEntity);
    }

}
