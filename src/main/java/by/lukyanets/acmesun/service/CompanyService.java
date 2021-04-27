package by.lukyanets.acmesun.service;

import by.lukyanets.acmesun.dto.company.BonusDto;
import by.lukyanets.acmesun.dto.company.CompanyDto;
import by.lukyanets.acmesun.entity.CompanyEntity;

public interface CompanyService {
    CompanyEntity createNewCompany(CompanyDto companyDto);
}
