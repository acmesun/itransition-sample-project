package by.lukyanets.acmesun.controller;

import by.lukyanets.acmesun.dto.user.UserLoginDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {
    private final AuthenticationProvider provider;

    @GetMapping
    public ModelAndView displayLogin(
            @RequestParam(value = "message", required = false) String message,
            @RequestParam(value = "error", required = false) String error
    ) {
        return new ModelAndView("login", "user", new UserLoginDto(message, error, null, null));
    }

    @PostMapping
    public String login(@ModelAttribute("user") UserLoginDto dto) {
        var authenticate = provider.authenticate(new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        return "redirect:/user";
    }

    @ExceptionHandler(value = {UsernameNotFoundException.class, BadCredentialsException.class})
    public ModelAndView processInvalidCredentials(Exception ignored) {
        return displayLogin(null, "Invalid credentials");
    }
}
