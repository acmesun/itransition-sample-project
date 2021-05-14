package by.lukyanets.acmesun.repository;

import by.lukyanets.acmesun.entity.CampaignEntity;
import by.lukyanets.acmesun.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CampaignRepository extends JpaRepository<CampaignEntity, Long> {
    boolean existsByCampaignName(String companyName);

    List<CampaignEntity> findAllByOrderByCampaignNameAsc();

    List<CampaignEntity> findAllBySubjectOrderByCampaignNameAsc(Subject subject);

    CampaignEntity findCampaignEntityByCampaignName(String name);

    List<CampaignEntity> findAllByOwnerEmail(String email);

}
