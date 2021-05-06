package by.lukyanets.acmesun.service.impl;

import by.lukyanets.acmesun.entity.UserEntity;
import by.lukyanets.acmesun.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrentUserService {
    private final UserRepository userRepo;

    public UserEntity getCurrentUser() {
        var email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepo.findUserEntityByEmail(email)
                .orElseThrow(() -> new IllegalStateException("Current user not found!"));
    }
}
