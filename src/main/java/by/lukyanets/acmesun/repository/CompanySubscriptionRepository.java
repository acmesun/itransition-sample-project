package by.lukyanets.acmesun.repository;

import by.lukyanets.acmesun.entity.CompanySubscriptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CompanySubscriptionRepository extends JpaRepository<CompanySubscriptionEntity, Long> {
    List<CompanySubscriptionEntity> findAllByUserEmail(String userEmail);

    boolean existsCompanySubscriptionEntitiesByUser_Email(String userEmail);

    Optional<CompanySubscriptionEntity> findByUser_Email_AndCompanyEntity_CompanyName(
            String userEmail,
            String companyName
    );
}
