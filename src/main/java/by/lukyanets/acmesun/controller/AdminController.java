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
        return new ModelAndView("admin", "user", service.allUsers());
    }

    @PostMapping
    public ModelAndView adminDeleteUser(@ModelAttribute("user") UserDto dto) {
        service.deleteAccount(dto);
        return displayAdminForm();
    }

    @PostMapping
    public ModelAndView adminBlockUser(@ModelAttribute("user") UserDto dto) {
       service.blockUser(dto);
       return displayAdminForm();
    }

    @PostMapping
    public ModelAndView adminUnblockUser(@ModelAttribute("user") UserDto dto){
        service.unblockUser(dto);
        return displayAdminForm();
    }

    @PostMapping
    public ModelAndView addAdminRole(@ModelAttribute("user") UserDto dto) {
        service.addAdminRole(dto);
        return displayAdminForm();
    }

    @PostMapping
    public ModelAndView deleteAdminRole(@ModelAttribute("user") UserDto dto) {
        service.deleteAdminRole(dto);
        return displayAdminForm();
    }

}
