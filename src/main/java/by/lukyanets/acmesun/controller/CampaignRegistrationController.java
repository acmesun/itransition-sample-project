package by.lukyanets.acmesun.controller;

import by.lukyanets.acmesun.dto.campaign.CampaignDto;
import by.lukyanets.acmesun.repository.CampaignRepository;
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
    private final CampaignRepository repo;

    @GetMapping
    public ModelAndView displayCampaignRegistrationPage() {
        return new ModelAndView("campaignreg", "campaign", new CampaignDto());
    }

    @PostMapping
    public String campaignRegistration(
            @ModelAttribute("campaign") CampaignDto campaignDto,
            @RequestParam("images") MultipartFile[] images
    ) throws IOException {
        if (!repo.existsByCampaignName(campaignDto.getCampaignName())) {
            service.createNewCampaign(campaignDto, images);
            return "redirect:/user/mycampaigns";
        } else {
            return "redirect:/user/campaignexists";
        }
    }

    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public String submit(@RequestParam("file") MultipartFile file, ModelMap modelMap) {
        modelMap.addAttribute("file", file);

        return "fileUploadView";
    }

    @GetMapping("/campaignexists")
    public String campaignAlreadyExists() {
        return "campaignexists";
    }
}
