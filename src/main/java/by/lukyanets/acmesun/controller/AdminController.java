package by.lukyanets.acmesun.controller;

import by.lukyanets.acmesun.dto.UserDto;
import by.lukyanets.acmesun.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final UserService service;


    @GetMapping
    public ModelAndView displayAdminForm() {
        return new ModelAndView("admin", "users", service.listOfAllUsers());
    }

    @PostMapping("/delete")
    public ModelAndView adminDeleteUser(@ModelAttribute("user") UserDto dto) {
        service.deleteAccount(dto);
        return displayAdminForm();
    }

    @PostMapping("/block")
    public ModelAndView adminBlockUser(@ModelAttribute("user") UserDto dto) {
        service.blockUser(dto);
        return displayAdminForm();
    }

    @PostMapping("/unblock")
    public ModelAndView adminUnblockUser(@ModelAttribute("user") UserDto dto) {
        service.unblockUser(dto);
        return displayAdminForm();
    }

    @PostMapping("/addRole")
    public ModelAndView addAdminRole(@ModelAttribute("user") UserDto dto) {
        service.addAdminRole(dto);
        return displayAdminForm();
    }

    @PostMapping("/deleteRole")
    public ModelAndView deleteAdminRole(@ModelAttribute("user") UserDto dto) {
        service.deleteAdminRole(dto);
        return displayAdminForm();
    }

}
