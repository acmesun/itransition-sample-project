package by.lukyanets.acmesun.controller;

import by.lukyanets.acmesun.entity.Subject;
import by.lukyanets.acmesun.service.CampaignService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/campaigns")
@RequiredArgsConstructor
public class CampaignsController {
    private final CampaignService service;

    @GetMapping
    public ModelAndView displayAllCampaignsView() {
        return new ModelAndView("campaigns", "campaigns", service.listOfAllCampaigns());
    }

    @GetMapping("/type")
    public ModelAndView displayCampaignsViewByType(
            @RequestParam(value = "subject") Subject subject) {
        return new ModelAndView("campaigns", "campaigns", service.listOfCampaignsBySubject((subject)));
    }
}

