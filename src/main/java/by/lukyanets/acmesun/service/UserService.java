package by.lukyanets.acmesun.service;

import by.lukyanets.acmesun.dto.UserDto;
import by.lukyanets.acmesun.dto.UserRegistrationDto;
import by.lukyanets.acmesun.entity.UserEntity;

public interface UserService {
    UserEntity registerNewAccount(UserRegistrationDto userDto);

    void deleteAccount(UserDto userDto);

    void addAdminRole(UserDto userDto);

    void deleteAdminRole(UserDto userDto);

    void blockUser(UserDto userDto);

    void unblockUser(UserDto userDto);
}
