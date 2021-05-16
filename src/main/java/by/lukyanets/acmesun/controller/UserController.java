package by.lukyanets.acmesun.controller;

import by.lukyanets.acmesun.dto.campaign.BuyBonusDto;
import by.lukyanets.acmesun.repository.UserRepository;
import by.lukyanets.acmesun.service.BuyBonusService;
import by.lukyanets.acmesun.service.CampaignService;
import by.lukyanets.acmesun.service.impl.CampaignSubscriptionService;
import by.lukyanets.acmesun.service.impl.CurrentUserService;
import by.lukyanets.acmesun.service.impl.UserDetailsByEmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final CampaignService campaignService;
    private final CurrentUserService userService;
    private final BuyBonusService bonusService;
    private final CampaignSubscriptionService subscriptionService;


    @GetMapping
    public ModelAndView displayUserPage() {
        return new ModelAndView("user", "user", userService.getCurrentUser().getName());
    }

    @GetMapping("/mysubscriptions")
    public ModelAndView displayMySubscriptions() {
        var currentEmail = userService.getCurrentUser().getEmail();
        return new ModelAndView("mysubscriptions", "campaigns", subscriptionService.findAllSubscriptions(currentEmail));
    }

    @GetMapping("/mycampaigns")
    public ModelAndView displayMyCampaigns() {
        var currentEmail = userService.getCurrentUser().getEmail();
        return new ModelAndView("mycampaigns", "campaigns", campaignService.campaignInfoByOwner(currentEmail));
    }

    @GetMapping("/mybonuses")
    public ModelAndView displayMyBonuses() {
        var currentEmail = userService.getCurrentUser().getEmail();
        List<BuyBonusDto> allBBDtosByUser = bonusService.findAllBBDtosByUser(currentEmail);
        return new ModelAndView("mybonuses", "bonuses", allBBDtosByUser);
    }

    @PostMapping("/delete")
    public ModelAndView deleteCampaign(@RequestParam("name") String name) {
        if (campaignService.isCampaignHasBoughtBonuses(name)) {
            return new ModelAndView("redirect:/user/notdeletecampaign");
        } else {
            campaignService.deleteCampaignByName(name);
            return displayUserPage();
        }
    }

    @PostMapping("/subscribe")
    public ModelAndView subscribe(@RequestParam("name") String name) {
        subscriptionService.subscribe(name);
        return displayMySubscriptions();
    }

    @PostMapping("/toAdmin")
    public String toAdmin() {
        return "redirect:/admin";
    }

    @PostMapping("/toCampaignReg")
    public String toCampaignReg() {
        return "redirect:/campaignreg";
    }

    @PostMapping("/toAllCampaigns")
    public String toAllCampaigns() {
        return "redirect:/campaigns";
    }

    @PostMapping("/toMyPage")
    public String toMyPage() {
        return "redirect:/user";
    }

    @GetMapping("/notadmin")
    public String notAdmin() {
        return "notadmin";
    }

    @GetMapping("/notdeletecampaign")
    public String notDeleteCampaign() {
        return "notdeletecampaign";
    }

    @GetMapping("/campaignexists")
    public String campaignAlreadyExists() {
        return "campaignexists";
    }
}
