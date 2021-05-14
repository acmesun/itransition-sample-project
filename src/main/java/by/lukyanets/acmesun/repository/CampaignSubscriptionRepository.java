package by.lukyanets.acmesun.repository;

import by.lukyanets.acmesun.entity.CampaignSubscriptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CampaignSubscriptionRepository extends JpaRepository<CampaignSubscriptionEntity, Long> {
    List<CampaignSubscriptionEntity> findAllByUserEmail(String userEmail);

    boolean existsCampaignSubscriptionEntitiesByUser_Email(String userEmail);

    Optional<CampaignSubscriptionEntity> findByUser_Email_AndCampaignEntity_CampaignName(
            String userEmail,
            String campaignName
    );
}
