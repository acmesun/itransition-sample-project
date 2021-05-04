package by.lukyanets.acmesun.controller;

import by.lukyanets.acmesun.entity.Subject;
import by.lukyanets.acmesun.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/companies")
@RequiredArgsConstructor
public class CompaniesController {
    private final CompanyService service;

    @GetMapping
    public ModelAndView displayAllCompaniesView() {
        return new ModelAndView("companies", "companies", service.listOfAllCompanies());
    }

    @GetMapping("/type")
    public ModelAndView displayCompaniesViewByType(
            @RequestParam(value = "subject") Subject subject) {
        return new ModelAndView("companies", "companies", service.listOfCompaniesBySubject((subject)));
    }



}

