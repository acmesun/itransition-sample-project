package by.lukyanets.acmesun.controller;

import by.lukyanets.acmesun.service.CompanyService;
import by.lukyanets.acmesun.service.impl.CompanySubscriptionService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
@RequestMapping("/company")
public class CompanyController {
    private final CompanyService service;

    @GetMapping("/name")
    public ModelAndView displayCompanyByName(
            @RequestParam(value = "text") String name) {
        return new ModelAndView("company", "company", service.companyInfoByName(name));
    }


}
