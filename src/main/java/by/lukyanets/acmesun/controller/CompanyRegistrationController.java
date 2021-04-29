package by.lukyanets.acmesun.controller;

import by.lukyanets.acmesun.config.CloudinaryConfig;
import by.lukyanets.acmesun.dto.company.CompanyDto;
import by.lukyanets.acmesun.repository.CompanyRepository;
import by.lukyanets.acmesun.repository.UserRepository;
import by.lukyanets.acmesun.service.CompanyService;
import com.cloudinary.Cloudinary;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public String submit(@RequestParam("file") MultipartFile file, ModelMap modelMap) throws IOException {
        modelMap.addAttribute("file", file);

        return "fileUploadView";
    }
}
