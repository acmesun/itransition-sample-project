package by.lukyanets.acmesun.service.impl;

import by.lukyanets.acmesun.dto.UserAdminDto;
import by.lukyanets.acmesun.dto.UserRegistrationDto;
import by.lukyanets.acmesun.entity.UserEntity;
import by.lukyanets.acmesun.repository.UserRepository;
import by.lukyanets.acmesun.service.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.transaction.Transactional;
import java.util.List;

import static by.lukyanets.acmesun.entity.Roles.CLIENT;
import static java.util.stream.Collectors.toList;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository repo;

    @Override
    public UserEntity registerNewAccount(UserRegistrationDto userDto) {
        if (repo.existsByEmail(userDto.getEmail())) {
            throw new IllegalArgumentException("There is an account with that email address " + userDto.getEmail());
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setActivity(true);
        userEntity.setRole(CLIENT);
        userEntity.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userEntity.setEmail(userDto.getEmail());
        userEntity.setName(userDto.getName());
        return repo.save(userEntity);
    }

    @Override
    public void deleteAccount(String email) {
        repo.findUserEntityByEmail(email).ifPresent(repo::delete);
    }

    @Override
    public void updateUser(String email, String newRole, Boolean newActivity) {
        repo.findUserEntityByEmail(email)
                .map(userEntity -> {
                    if (newRole != null) {
                        userEntity.setRole(newRole);
                    }
                    if (newActivity != null) {
                        userEntity.setActivity(newActivity);
                    }
                    return userEntity;
                }).ifPresent(repo::save);
    }

    @Override
    public List<UserAdminDto> listOfAllUsers() {
        return repo.findAllByOrderByNameAsc().stream()
                .map(entity -> new UserAdminDto(entity.getId(), entity.getName(), entity.getEmail(), entity.getRole(), entity.isActivity()))
                .collect(toList());
    }


}
