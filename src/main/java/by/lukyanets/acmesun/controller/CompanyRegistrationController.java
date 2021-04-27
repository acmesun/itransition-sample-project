package by.lukyanets.acmesun.controller;

import by.lukyanets.acmesun.dto.company.CompanyDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/companyreg")
public class CompanyRegistrationController {

    @GetMapping
    public ModelAndView displayCompanyRegistrationPage() {
        return new ModelAndView("companyreg", "company", new CompanyDto());
    }

}
