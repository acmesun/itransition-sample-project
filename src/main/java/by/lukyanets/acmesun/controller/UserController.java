package by.lukyanets.acmesun.controller;

import by.lukyanets.acmesun.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final CompanyService service;

    @GetMapping
    public ModelAndView displayUserPage() {
        String currentEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        return new ModelAndView("user", "companies", service.companyInfoByOwner(currentEmail));
    }

    @PostMapping("/delete")
    public ModelAndView deleteCompany(@RequestParam("name") String name) {
        service.deleteCompanyByName(name);
        return displayUserPage();
    }
}
