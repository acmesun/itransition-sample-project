package by.lukyanets.acmesun.controller;

import by.lukyanets.acmesun.dto.company.CompanyDto;
import by.lukyanets.acmesun.repository.CompanyRepository;
import by.lukyanets.acmesun.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@RequiredArgsConstructor
@Controller
@RequestMapping("/companyreg")
public class CompanyRegistrationController {
    private final CompanyService service;

    @GetMapping
    public ModelAndView displayCompanyRegistrationPage() {
        return new ModelAndView("companyreg", "company", new CompanyDto());
    }

    @PostMapping
    public void companyRegistration(@ModelAttribute("company") CompanyDto companyDto) {
        service.createNewCompany(companyDto);
    }

}
