package by.lukyanets.acmesun.controller;

import by.lukyanets.acmesun.dto.UserDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/home")
public class HomeController {

    @GetMapping
    public ModelAndView displayHomePage(){
        return new ModelAndView("home", "user", new UserDto());
    }

    @PostMapping("/toAdmin")
    public String toAdmin() {
        return "redirect:/admin";
    }
}
