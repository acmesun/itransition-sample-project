package by.lukyanets.acmesun.service.impl;

import by.lukyanets.acmesun.entity.RegistrationSource;
import by.lukyanets.acmesun.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserDetailsByEmailService implements UserDetailsService {
    private final UserRepository repo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var userEntity = repo.findUserEntityByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("There is no user with such email " + email));

        return new org.springframework.security.core.userdetails.User(
                userEntity.getEmail(),
                // If registration source is third-party then user does not have password and does not need one.
                userEntity.getSource() == RegistrationSource.APP ? userEntity.getPassword() : userEntity.getEmail(),
                userEntity.isActivity(),
                true,
                true,
                true,
                Set.of(new SimpleGrantedAuthority(userEntity.getRole()))
        );
    }
}
