package by.lukyanets.acmesun.service.impl;

import by.lukyanets.acmesun.dto.user.UserAdminDto;
import by.lukyanets.acmesun.dto.user.UserRegistrationDto;
import by.lukyanets.acmesun.dto.user.UserRegistrationTwitterDto;
import by.lukyanets.acmesun.entity.UserEntity;
import by.lukyanets.acmesun.repository.CampaignRepository;
import by.lukyanets.acmesun.repository.UserRepository;
import by.lukyanets.acmesun.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import static by.lukyanets.acmesun.entity.Roles.CLIENT;
import static java.util.stream.Collectors.toList;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final CampaignRepository campaignRepo;
    private final CurrentUserService service;

    @Override
    public UserEntity registerNewAccount(UserRegistrationDto userDto) {
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new IllegalArgumentException("There is an account with that email address " + userDto.getEmail());
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setActivity(true);
        userEntity.setRole(CLIENT);
        userEntity.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userEntity.setEmail(userDto.getEmail());
        userEntity.setName(userDto.getName());
        return userRepository.save(userEntity);
    }

    public UserEntity registerNewAccount(UserRegistrationTwitterDto userDto) {
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new IllegalArgumentException("There is an account with that email address " + userDto.getEmail());
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setActivity(true);
        userEntity.setRole(CLIENT);
        userEntity.setEmail(userDto.getEmail());
        userEntity.setName(userDto.getName());
        return userRepository.save(userEntity);
    }

    @Override
    public void deleteAccount(String email) {
        userRepository.findUserEntityByEmail(email).ifPresent(userRepository::delete);
    }

    @Override
    public void updateUser(String email, String newRole, Boolean newActivity) {
        userRepository.findUserEntityByEmail(email)
                .map(userEntity -> {
                    if (newRole != null) {
                        userEntity.setRole(newRole);
                    }
                    if (newActivity != null) {
                        userEntity.setActivity(newActivity);
                    }
                    return userEntity;
                }).ifPresent(userRepository::save);
    }

    @Override
    public List<UserAdminDto> listOfAllUsers() {
        return userRepository.findAllByOrderByNameAsc().stream()
                .map(entity -> new UserAdminDto(entity.getId(), entity.getName(), entity.getEmail(), entity.getRole(), entity.isActivity()))
                .collect(toList());
    }

    @Override
    public boolean isUserHasCampaigns(String email) {
        return campaignRepo.findAllByOwnerEmail(email).size() >= 1;
    }
}
