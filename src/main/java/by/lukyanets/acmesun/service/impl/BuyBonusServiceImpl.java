package by.lukyanets.acmesun.service.impl;

import by.lukyanets.acmesun.dto.company.BuyBonusDto;
import by.lukyanets.acmesun.entity.BuyBonusEntity;
import by.lukyanets.acmesun.entity.UserEntity;
import by.lukyanets.acmesun.repository.BuyBonusRepository;
import by.lukyanets.acmesun.repository.CompanyRepository;
import by.lukyanets.acmesun.service.BuyBonusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BuyBonusServiceImpl implements BuyBonusService {
    private final BuyBonusRepository buyBonusRepo;
    private final CompanyRepository companyRepository;
    private final CurrentUserService userService;


    @Override
    public List<BuyBonusDto> findAllBBDtosByUser(String userEmail) {
        var bbList = buyBonusRepo.findBuyBonusEntitiesByUserEmail(userEmail);
        List<BuyBonusDto> bbDtos = new ArrayList<>();
        for (var bbEntity : bbList) {
            BuyBonusDto bbDto = new BuyBonusDto();
            bbDto.setBonusName(bbEntity.getBonus().getBonusName());
            bbDto.setBonusDescription(bbEntity.getBonus().getDescription());
            bbDto.setCompanyName(bbEntity.getBonus().getCompany().getCompanyName());
            bbDto.setQuantity(bbEntity.getQuantity());
            bbDto.setInvestmentAmount(bbEntity.getBonus().getAmount() * bbEntity.getQuantity());
            bbDtos.add(bbDto);
        }
        return bbDtos;
    }

    @Override
    public void saveBoughtBonus(String bonusName, String companyName) {
        var currentUser = userService.getCurrentUser();
        var currentBoughtBonus = buyBonusRepo.findByBonusBonusNameAndBonusCompanyCompanyNameAndUserId(
                bonusName, companyName, currentUser.getId()
        ).orElseGet(() -> createNewBuyBonus(bonusName, companyName, currentUser));
        currentBoughtBonus.setQuantity(currentBoughtBonus.getQuantity() + 1);
        buyBonusRepo.save(currentBoughtBonus);
    }

    private BuyBonusEntity createNewBuyBonus(String bonusName, String companyName, UserEntity currentUser) {
        var company = companyRepository.findCompanyEntityByCompanyName(companyName);
        var bonus = company.getBonusList().stream()
                .filter(it -> it.getBonusName().equals(bonusName))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Bonus " + bonusName + " not found!"));
        return new BuyBonusEntity(bonus, currentUser);
    }

}
