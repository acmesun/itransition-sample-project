package by.lukyanets.acmesun.repository;

import by.lukyanets.acmesun.entity.BonusEntity;
import by.lukyanets.acmesun.entity.CompanyEntity;
import by.lukyanets.acmesun.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompanyRepository extends JpaRepository<CompanyEntity, Long> {
    boolean existsByCompanyName(String companyName);

    List<CompanyEntity> findAllByOrderByCompanyNameAsc();

    List<CompanyEntity> findAllBySubjectOrderByCompanyNameAsc(Subject subject);

    CompanyEntity findCompanyEntityByCompanyName(String name);

    List<CompanyEntity> findAllByOwnerEmail(String email);


}
