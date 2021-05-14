package by.lukyanets.acmesun.service.impl;

import by.lukyanets.acmesun.dto.campaign.CampaignSubscriptionDto;
import by.lukyanets.acmesun.entity.CampaignSubscriptionEntity;
import by.lukyanets.acmesun.entity.UserEntity;
import by.lukyanets.acmesun.repository.CampaignRepository;
import by.lukyanets.acmesun.repository.CampaignSubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CampaignSubscriptionService {
    private final CurrentUserService currentUserService;
    private final CampaignSubscriptionRepository subscriptionRepo;
    private final CampaignRepository campaignRepo;

    public void subscribe(String campaignName) {
        var currentUser = currentUserService.getCurrentUser();
        var sub = subscriptionRepo.findByUser_Email_AndCampaignEntity_CampaignName(currentUser.getEmail(), campaignName)
                .orElseGet(() -> createNewSubscribe(campaignName, currentUser));
        subscriptionRepo.save(sub);
    }

    public List<CampaignSubscriptionDto> findAllSubscriptions(String email) {
        List<CampaignSubscriptionEntity> subscriptions = subscriptionRepo.findAllByUserEmail(email);
        List<CampaignSubscriptionDto> dtos = new ArrayList<>(subscriptions.size());
        for (CampaignSubscriptionEntity entity : subscriptions) {
            CampaignSubscriptionDto dto = new CampaignSubscriptionDto();
            dto.setCampaignName(entity.getCampaignEntity().getCampaignName());
            dto.setTargetAmount(entity.getCampaignEntity().getTargetAmount());
            dto.setExpirationDate(entity.getCampaignEntity().getExpirationDate());
            dtos.add(dto);
        }
        return dtos;
    }

    private CampaignSubscriptionEntity createNewSubscribe(String campaignName, UserEntity currentUser) {
        return new CampaignSubscriptionEntity(currentUser, campaignRepo.findCampaignEntityByCampaignName(campaignName));
    }
}
