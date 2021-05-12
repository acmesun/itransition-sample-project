package by.lukyanets.acmesun.controller;

import by.lukyanets.acmesun.dto.company.CompanyDto;
import by.lukyanets.acmesun.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

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
    public void companyRegistration(
            @ModelAttribute("company") CompanyDto companyDto,
            @RequestParam("images") MultipartFile[] images
    ) throws IOException {
        service.createNewCompany(companyDto, images);
    }

    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public String submit(@RequestParam("file") MultipartFile file, ModelMap modelMap) {
        modelMap.addAttribute("file", file);

        return "fileUploadView";
    }
}
