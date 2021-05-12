package by.lukyanets.acmesun.service;

import by.lukyanets.acmesun.dto.company.CompanyDto;
import by.lukyanets.acmesun.dto.company.CompanyDtoAllInfo;
import by.lukyanets.acmesun.dto.company.CompanyDtoToList;
import by.lukyanets.acmesun.entity.CompanyEntity;
import by.lukyanets.acmesun.entity.Subject;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CompanyService {
    CompanyEntity createNewCompany(CompanyDto companyDto, MultipartFile[] images) throws IOException;

    List<CompanyDtoToList> listOfAllCompanies();

    List<CompanyDtoToList> listOfCompaniesBySubject(Subject subject);

    CompanyDtoAllInfo companyInfoByName(String name);

    List<CompanyDtoAllInfo> companyInfoByOwner(String name);

    void deleteCompanyByName(String name);

    boolean isCompanyHasBoughtBonuses(String name);

}
