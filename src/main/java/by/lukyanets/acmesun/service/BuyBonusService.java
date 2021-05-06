package by.lukyanets.acmesun.service;

import by.lukyanets.acmesun.dto.company.BuyBonusDto;

import java.util.List;

public interface BuyBonusService {

    List<BuyBonusDto> findAllBBDtosByUser(String userName);
}
