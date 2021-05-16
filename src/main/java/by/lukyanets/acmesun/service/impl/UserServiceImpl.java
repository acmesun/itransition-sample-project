package by.lukyanets.acmesun.service.impl;

import by.lukyanets.acmesun.dto.user.UserAdminDto;
import by.lukyanets.acmesun.dto.user.UserRegistrationDto;
import by.lukyanets.acmesun.entity.RegistrationSource;
import by.lukyanets.acmesun.entity.UserEntity;
import by.lukyanets.acmesun.repository.CampaignRepository;
import by.lukyanets.acmesun.repository.UserRepository;
import by.lukyanets.acmesun.service.UserService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import static by.lukyanets.acmesun.entity.Roles.CLIENT;
import static java.util.stream.Collectors.toList;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepo;
    private final CampaignRepository campaignRepo;
    private final AuthenticationProvider provider;

    public UserServiceImpl(PasswordEncoder passwordEncoder,
                           UserRepository userRepository,
                           CampaignRepository campaignRepo,
                           @Qualifier("thirdParty") AuthenticationProvider provider) {
        this.passwordEncoder = passwordEncoder;
        this.userRepo = userRepository;
        this.campaignRepo = campaignRepo;
        this.provider = provider;
    }

    @Override
    public UserEntity registerNewAccount(UserRegistrationDto userDto) {
        if (userRepo.existsByEmail(userDto.getEmail())) {
            throw new IllegalArgumentException("There is an account with that email address " + userDto.getEmail());
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setActivity(true);
        userEntity.setRole(CLIENT);
        userEntity.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userEntity.setEmail(userDto.getEmail());
        userEntity.setName(userDto.getName());
        userEntity.setSource(RegistrationSource.APP);
        return userRepo.save(userEntity);
    }

    @Override
    public UserEntity thirdPartyLogin(String name, String email, RegistrationSource source) {
        UserEntity user = userRepo.findUserEntityByEmail(email).orElse(null);
        if (user != null && user.getSource() != source) {
            throw new IllegalArgumentException("User is allowed to login only via " + user.getSource());
        }
        if (user == null) {
            UserEntity userEntity = new UserEntity();
            userEntity.setActivity(true);
            userEntity.setRole(CLIENT);
            userEntity.setEmail(email);
            userEntity.setName(name);
            userEntity.setSource(source);
            user = userRepo.save(userEntity);
        }
        var auth = provider.authenticate(new UsernamePasswordAuthenticationToken(email, email));
        SecurityContextHolder.getContext().setAuthentication(auth);
        return user;
    }

    @Override
    public void deleteAccount(String email) {
        userRepo.findUserEntityByEmail(email).ifPresent(userRepo::delete);
    }

    @Override
    public void updateUser(String email, String newRole, Boolean newActivity) {
        userRepo.findUserEntityByEmail(email)
                .map(userEntity -> {
                    if (newRole != null) {
                        userEntity.setRole(newRole);
                    }
                    if (newActivity != null) {
                        userEntity.setActivity(newActivity);
                    }
                    return userEntity;
                }).ifPresent(userRepo::save);
    }

    @Override
    public List<UserAdminDto> listOfAllUsers() {
        return userRepo.findAllByOrderByNameAsc().stream()
                .map(entity -> new UserAdminDto(entity.getId(), entity.getName(), entity.getEmail(), entity.getRole(), entity.isActivity()))
                .collect(toList());
    }

    @Override
    public boolean isUserHasCampaigns(String email) {
        return campaignRepo.findAllByOwnerEmail(email).size() >= 1;
    }
}
