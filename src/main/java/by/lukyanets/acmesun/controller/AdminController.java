package by.lukyanets.acmesun.controller;

import by.lukyanets.acmesun.service.UserService;
import by.lukyanets.acmesun.service.impl.CurrentUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final UserService service;
    private final CurrentUserService userService;

    @GetMapping
    public ModelAndView displayAdminForm() {
        return new ModelAndView("admin", "users", service.listOfAllUsers());
    }

    @PostMapping("/update")
    public ModelAndView updateUser(
            @RequestParam("email") String email,
            @RequestParam(value = "role", required = false) String newRole,
            @RequestParam(value = "activity", required = false) Boolean newActivity) {
        service.updateUser(email, newRole, newActivity);
        return isCurrent(email) ? new ModelAndView("redirect:/logout") : displayAdminForm();
    }

    @PostMapping("/delete")
    public ModelAndView deleteUser(@RequestParam("email") String email) {
        service.deleteAccount(email);
        return isCurrent(email) ? new ModelAndView("redirect:/logout") : displayAdminForm();
    }

    private boolean isCurrent(String email) {
        return userService.getCurrentUser().getName().equals(email);

    }
}