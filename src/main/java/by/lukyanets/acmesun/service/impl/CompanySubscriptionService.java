package by.lukyanets.acmesun.service.impl;

import by.lukyanets.acmesun.dto.company.CompanySubscriptionDto;
import by.lukyanets.acmesun.entity.CompanySubscriptionEntity;
import by.lukyanets.acmesun.entity.UserEntity;
import by.lukyanets.acmesun.repository.CompanyRepository;
import by.lukyanets.acmesun.repository.CompanySubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CompanySubscriptionService {
    private final CurrentUserService currentUserService;
    private final CompanySubscriptionRepository subscriptionRepo;
    private final CompanyRepository companyRepo;

    public void subscribe(String companyName) {
        var currentUser = currentUserService.getCurrentUser();
        var sub = subscriptionRepo.findByUser_Email_AndCompanyEntity_CompanyName(currentUser.getEmail(), companyName)
                .orElseGet(() -> createNewSubscribe(companyName, currentUser));
        subscriptionRepo.save(sub);
    }

    public List<CompanySubscriptionDto> findAllSubscriptions(String email) {
        List<CompanySubscriptionEntity> subscriptions = subscriptionRepo.findAllByUserEmail(email);
        List<CompanySubscriptionDto> dtos = new ArrayList<>(subscriptions.size());
        for (CompanySubscriptionEntity entity : subscriptions) {
            CompanySubscriptionDto dto = new CompanySubscriptionDto();
            dto.setCompanyName(entity.getCompanyEntity().getCompanyName());
            dto.setTargetAmount(entity.getCompanyEntity().getTargetAmount());
            dto.setExpirationDate(entity.getCompanyEntity().getExpirationDate());
            dtos.add(dto);
        }
        return dtos;
    }

    private CompanySubscriptionEntity createNewSubscribe(String companyName, UserEntity currentUser) {
        return new CompanySubscriptionEntity(currentUser, companyRepo.findCompanyEntityByCompanyName(companyName));
    }
}
