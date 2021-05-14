package by.lukyanets.acmesun.service;

import by.lukyanets.acmesun.dto.campaign.BuyBonusDto;

import java.util.List;

public interface BuyBonusService {

    List<BuyBonusDto> findAllBBDtosByUser(String userName);

    void saveBoughtBonus(String bonusName, String campaignName);
}
