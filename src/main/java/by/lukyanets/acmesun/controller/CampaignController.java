package by.lukyanets.acmesun.controller;

import by.lukyanets.acmesun.service.CampaignService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
@RequestMapping("/campaign")
public class CampaignController {
    private final CampaignService service;

    @GetMapping("/name")
    public ModelAndView displayCampaignByName(
            @RequestParam(value = "text") String name) {
        return new ModelAndView("campaign", "campaign", service.campaignInfoByName(name));
    }


}
