package by.lukyanets.acmesun.service.impl;

import by.lukyanets.acmesun.dto.company.BuyBonusDto;
import by.lukyanets.acmesun.repository.BuyBonusRepository;
import by.lukyanets.acmesun.repository.CompanyRepository;
import by.lukyanets.acmesun.service.BuyBonusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BuyBonusServiceImpl implements BuyBonusService {
    private final BuyBonusRepository buyBonusRepo;
    private final CompanyRepository companyRepository;
    private final CurrentUserService userService;


    @Override
    public List<BuyBonusDto> findAllBBDtosByUser(String userName) {
        return null;
    }
}
