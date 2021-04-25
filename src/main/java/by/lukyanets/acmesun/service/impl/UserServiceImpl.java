package by.lukyanets.acmesun.service.impl;

import by.lukyanets.acmesun.dto.UserAdminDto;
import by.lukyanets.acmesun.dto.UserDto;
import by.lukyanets.acmesun.dto.UserRegistrationDto;
import by.lukyanets.acmesun.entity.UserEntity;
import by.lukyanets.acmesun.repository.UserRepository;
import by.lukyanets.acmesun.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

import static by.lukyanets.acmesun.entity.Roles.ADMIN;
import static by.lukyanets.acmesun.entity.Roles.CLIENT;
import static java.util.stream.Collectors.toList;

@Service
@Transactional
@AllArgsConstructor
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
    public void deleteAccount(UserDto userDto) {
        repo.delete(getUserEntity(userDto));
    }

    @Override
    public void addAdminRole(UserDto userDto) {
        var user = getUserEntity(userDto);
        user.setRole(ADMIN);
        repo.save(user);
    }

    @Override
    public void deleteAdminRole(UserDto userDto) {
        var user = getUserEntity(userDto);
        user.setRole(CLIENT);
        repo.save(user);
    }

    @Override
    public void blockUser(UserDto userDto) {
        var user = getUserEntity(userDto);
        user.setActivity(false);
        repo.save(user);
    }

    @Override
    public void unblockUser(UserDto userDto) {
        var user = getUserEntity(userDto);
        user.setActivity(true);
        repo.save(user);
    }

    @Override
    public List<UserAdminDto> listOfAllUsers() {
        return repo.findAllByOrderByNameAsc().stream()
                .map(entity -> new UserAdminDto(entity.getId(), entity.getName(), entity.getEmail(), entity.getRole(), entity.isActivity()))
                .collect(toList());
    }

    private UserEntity getUserEntity(UserDto userDto) {
        return repo.findUserEntityByEmail(userDto.getEmail())
                .orElseThrow(() -> new EntityNotFoundException("User with email " + userDto.getEmail() + " not found!"));
    }


}
