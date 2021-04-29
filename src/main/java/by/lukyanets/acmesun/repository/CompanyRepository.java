package by.lukyanets.acmesun.repository;

import by.lukyanets.acmesun.entity.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompanyRepository extends JpaRepository<CompanyEntity, Long> {
    boolean existsByCompanyName(String companyName);

    List<CompanyEntity> findAllByOrderByCompanyNameAsc();

}
