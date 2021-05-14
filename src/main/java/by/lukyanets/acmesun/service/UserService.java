package by.lukyanets.acmesun.service;

import by.lukyanets.acmesun.dto.user.UserAdminDto;
import by.lukyanets.acmesun.dto.user.UserRegistrationDto;
import by.lukyanets.acmesun.dto.user.UserRegistrationTwitterDto;
import by.lukyanets.acmesun.entity.UserEntity;

import java.util.List;

public interface UserService {
    UserEntity registerNewAccount(UserRegistrationDto userDto);

    UserEntity registerNewAccount(UserRegistrationTwitterDto userDto);

    void deleteAccount(String email);

    void updateUser(String email, String newRole, Boolean newActivity);

    List<UserAdminDto> listOfAllUsers();

    boolean isUserHasCampaigns(String email);



}
