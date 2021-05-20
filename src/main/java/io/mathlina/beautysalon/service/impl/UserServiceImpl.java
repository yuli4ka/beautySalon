package io.mathlina.beautysalon.service.impl;

import io.mathlina.beautysalon.domain.Role;
import io.mathlina.beautysalon.dto.UserProfileDto;
import io.mathlina.beautysalon.dto.UserRegistrationDto;
import io.mathlina.beautysalon.exception.*;
import io.mathlina.beautysalon.model.UserModel;
import io.mathlina.beautysalon.repos.UserRepository;
import io.mathlina.beautysalon.service.MailService;
import io.mathlina.beautysalon.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.UUID;

//TODO: log
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder, MailService mailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.mailService = mailService;
    }

    public UserModel loadUserByUsername(String s) {
        return userRepository.findByUsername(s)
                .orElseThrow(() -> new UsernameNotFoundException("User not exist!"));
    }

    public void addUser(UserRegistrationDto userDTO) {
        userRepository.findByUsername(userDTO.getUsername())
                .ifPresent(s -> {
                    throw new UsernameIsAlreadyTaken("Username is already taken");
                });

        userRepository.findByEmail(userDTO.getEmail())
                .ifPresent(s -> {
                    throw new EmailIsAlreadyTaken("Email is already taken");
                });

//    TODO: mapper
        UserModel user = UserModel.builder()
                .username(userDTO.getUsername())
                .name(userDTO.getName())
                .surname(userDTO.getSurname())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .email(userDTO.getEmail())
                .active(false)
                .role(Collections.singleton(Role.CLIENT))
                .activationCode(UUID.randomUUID().toString())
                .build();

        try {
            userRepository.save(user);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CannotSaveUserToDatabase("Cannot save user to database");
        }

        mailService.sendActivationCode(user);
    }

    public void updateUser(UserProfileDto userDTO, String oldPassword) {
        UserModel user = userRepository.findByUsername(userDTO.getUsername())
                .orElseThrow(() -> new UserNotFound("User not found"));

        //TODO: refactor (or delete ifs except email)
        boolean changed = false;

        if (!user.getName().equals(userDTO.getName())) {
            user.setName(userDTO.getName());
            changed = true;
        }

        if (!user.getSurname().equals(userDTO.getSurname())) {
            user.setSurname(userDTO.getSurname());
            changed = true;
        }

        if (!oldPassword.isEmpty()) {
            if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
                throw new WrongPassword("Wrong old password");
            }
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            changed = true;
        }

        if (!user.getEmail().equals(userDTO.getEmail())) {
            userRepository.findByEmail(userDTO.getEmail())
                    .ifPresent(s -> {
                        throw new EmailIsAlreadyTaken("Email is already taken");
                    });
            user.setEmail(userDTO.getEmail());
            user.setActive(false);
            user.setActivationCode(UUID.randomUUID().toString());
            mailService.sendActivationCode(user);
            changed = true;
        }

        if (changed) {
            try {
                userRepository.save(user);
            } catch (Exception e) {
                throw new CannotSaveUserToDatabase("Cannot save user to database");
            }
        }
    }

    public void activateUser(String code) {
        UserModel user = userRepository.findByActivationCode(code)
                .orElseThrow(() -> new UserNotFoundByActivationCode("User not found by activation code"));

        user.setActivationCode(null);
        user.setActive(true);

        userRepository.save(user);
    }

}
