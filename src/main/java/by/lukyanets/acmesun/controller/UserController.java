package by.lukyanets.acmesun.controller;

import by.lukyanets.acmesun.service.BuyBonusService;
import by.lukyanets.acmesun.service.CompanyService;
import by.lukyanets.acmesun.service.impl.CurrentUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final CompanyService service;
    private final CurrentUserService userService;
    private final BuyBonusService bonusService;

    @GetMapping
    public ModelAndView displayUserPage() {
        var currentEmail = userService.getCurrentUser().getEmail();
        return new ModelAndView("user",
                Map.of(
                        "bonuses", bonusService.findAllBBDtosByUser(currentEmail),
                        "companies", service.companyInfoByOwner(currentEmail))
        );
    }

    @PostMapping("/delete")
    public ModelAndView deleteCompany(@RequestParam("name") String name) {
        service.deleteCompanyByName(name);
        return displayUserPage();
    }

    @PostMapping("/toAdmin")
    public String toAdmin() {
        return "redirect:/admin";
    }

    @PostMapping("/toCompanyReg")
    public String toCompanyReg() {
        return "redirect:/companyreg";
    }

    @PostMapping("/toAllCompanies")
    public String toAllCompanies() {
        return "redirect:/companies";
    }

    @PostMapping("/toMyPage")
    public String toMyPage() {
        return "redirect:/user";
    }

    @GetMapping("/notadmin")
    public String notAdmin() {
        return "notadmin";
    }
}
