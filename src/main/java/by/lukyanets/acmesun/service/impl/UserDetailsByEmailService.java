package by.lukyanets.acmesun.service.impl;

import by.lukyanets.acmesun.entity.UserEntity;
import by.lukyanets.acmesun.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class UserDetailsByEmailService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var userEntity = userRepository.findUserEntityByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("There is no user with such email " + email));

        return new org.springframework.security.core.userdetails.User(
                userEntity.getName(),
                userEntity.getPassword(),
                userEntity.isActivity(),
                true,
                true,
                true,
                Set.of(new SimpleGrantedAuthority(userEntity.getRole()))
        );
    }
}
