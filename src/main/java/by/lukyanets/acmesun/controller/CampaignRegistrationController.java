package by.lukyanets.acmesun.controller;

import by.lukyanets.acmesun.dto.campaign.CampaignDto;
import by.lukyanets.acmesun.service.CampaignService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@RequiredArgsConstructor
@Controller
@RequestMapping("/campaignreg")
public class CampaignRegistrationController {
    private final CampaignService service;

    @GetMapping
    public ModelAndView displayCampaignRegistrationPage() {
        return new ModelAndView("campaignreg", "campaign", new CampaignDto());
    }

    @PostMapping
    public void campaignRegistration(
            @ModelAttribute("campaign") CampaignDto campaignDto,
            @RequestParam("images") MultipartFile[] images
    ) throws IOException {
        service.createNewCampaign(campaignDto, images);
    }

    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public String submit(@RequestParam("file") MultipartFile file, ModelMap modelMap) {
        modelMap.addAttribute("file", file);

        return "fileUploadView";
    }
}
