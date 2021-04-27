package by.lukyanets.acmesun.controller;

import by.lukyanets.acmesun.dto.company.CompanyDto;
import by.lukyanets.acmesun.entity.BonusEntity;
import by.lukyanets.acmesun.entity.CompanyEntity;
import by.lukyanets.acmesun.entity.Subject;
import by.lukyanets.acmesun.repository.CompanyRepository;
import by.lukyanets.acmesun.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Date;

@RequiredArgsConstructor
@Controller
@RequestMapping("/companyreg")
public class CompanyRegistrationController {
    private final CompanyService service;
    private final CompanyRepository repo; //TODO: REMOVE

    @GetMapping
    public ModelAndView displayCompanyRegistrationPage() {
        return new ModelAndView("companyreg", "company", new CompanyDto());
    }

    @PostMapping
    public void companyRegistration(@ModelAttribute("company") CompanyDto companyDto) {
        service.createNewCompany(companyDto);
    }

    @GetMapping("/load")
    public void testLoad() {
        CompanyEntity entity = repo.findAll().get(0);
    }

    @GetMapping("/save")
    public void testSave() {
        CompanyEntity entity = new CompanyEntity();
        entity.setCompanyName("Test");
        entity.setCompanyDescription("Test");
        entity.setExpirationDate(new Date());
        entity.setTargetAmount(666);
        entity.setSubject(Subject.EDUCATION);
        entity.setBonusList(new ArrayList<>());

        BonusEntity bEntity = new BonusEntity();
        bEntity.setAmount(1);
        bEntity.setBonusName("First");
        bEntity.setDescription("1");

        BonusEntity bEntity2 = new BonusEntity();
        bEntity2.setAmount(3);
        bEntity2.setBonusName("Second");
        bEntity2.setDescription("2");
        entity.getBonusList().add(bEntity);
        entity.getBonusList().add(bEntity2);
        repo.save(entity);
    }

}
