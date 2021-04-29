package by.lukyanets.acmesun.service;

import by.lukyanets.acmesun.dto.company.CompanyDto;
import by.lukyanets.acmesun.dto.image.ImageDto;
import by.lukyanets.acmesun.entity.CompanyEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CompanyService {
    CompanyEntity createNewCompany(CompanyDto companyDto, MultipartFile[] images) throws IOException;


}
