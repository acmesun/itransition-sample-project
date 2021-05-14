package by.lukyanets.acmesun.controller;

import by.lukyanets.acmesun.service.BuyBonusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/bonus")
@RequiredArgsConstructor
public class BonusController {
    private final BuyBonusService buyBonusService;

    @PostMapping("/buy")
    public String buyBonus(
            @RequestParam("campaign") String campaignName,
            @RequestParam("bonus") String bonusName
    ) {
        buyBonusService.saveBoughtBonus(bonusName, campaignName);
        return "redirect:/user";
    }

}
