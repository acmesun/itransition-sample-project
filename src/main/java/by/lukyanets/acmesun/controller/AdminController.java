package by.lukyanets.acmesun.controller;

import by.lukyanets.acmesun.service.UserService;
import by.lukyanets.acmesun.service.impl.CurrentUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;
    private final CurrentUserService currentUserService;

    @GetMapping
    public ModelAndView displayAdminForm() {
        return new ModelAndView("admin", "users", userService.listOfAllUsers());
    }

    @PostMapping("/update")
    public ModelAndView updateUser(
            @RequestParam("email") String email,
            @RequestParam(value = "role", required = false) String newRole,
            @RequestParam(value = "activity", required = false) Boolean newActivity) {
        userService.updateUser(email, newRole, newActivity);
        return isCurrent(email) ? new ModelAndView("redirect:/logout") : displayAdminForm();
    }

    @PostMapping("/delete")
    public ModelAndView deleteUser(@RequestParam("email") String email) {
        if (userService.isUserHasCampaigns(email)) {
            return new ModelAndView("redirect:/admin/notdelete");
        } else {
            userService.deleteAccount(email);
            return isCurrent(email) ? new ModelAndView("redirect:/logout") : displayAdminForm();
        }
    }

    @GetMapping("/notdelete")
    public String notDelete() {
        return "notdelete";
    }

    private boolean isCurrent(String email) {
        return currentUserService.getCurrentUser().getName().equals(email);

    }
}