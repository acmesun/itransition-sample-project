package by.lukyanets.acmesun.controller;

import by.lukyanets.acmesun.dto.user.UserRegistrationDto;
import by.lukyanets.acmesun.repository.UserRepository;
import by.lukyanets.acmesun.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/registration")
@RequiredArgsConstructor
public class RegistrationController {
    private final UserService service;
    private final UserRepository repo;

    @GetMapping
    public ModelAndView displayRegistrationForm(
            @RequestParam(value = "message", required = false) String message,
            @RequestParam(value = "error", required = false) String error) {
        return new ModelAndView("registration", "user", new UserRegistrationDto(null, null, null, error, message));
    }

    @PostMapping
    public String registration(@ModelAttribute("user") UserRegistrationDto dto) {
        if (repo.findUserEntityByEmail(dto.getEmail()).isPresent()){
            return "redirect:/login?error=This email already exists.";
        } else service.registerNewAccount(dto);
        return "redirect:/login?message=Registration successfully completed!";
    }

}
