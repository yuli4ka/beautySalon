package io.mathlina.beautysalon.service;

import io.mathlina.beautysalon.domain.Role;
import io.mathlina.beautysalon.domain.User;
import io.mathlina.beautysalon.dto.UserProfileDto;
import io.mathlina.beautysalon.dto.UserRegistrationDto;
import io.mathlina.beautysalon.exception.CannotSaveUserToDatabase;
import io.mathlina.beautysalon.exception.EmailIsAlreadyTaken;
import io.mathlina.beautysalon.exception.UserNotFound;
import io.mathlina.beautysalon.exception.UserNotFoundByActivationCode;
import io.mathlina.beautysalon.exception.UsernameIsAlreadyTaken;
import io.mathlina.beautysalon.exception.WrongPassword;
import io.mathlina.beautysalon.repos.UserRepo;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

//TODO: log
@Service
public class UserService implements UserDetailsService {

  private final UserRepo userRepo;
  private final PasswordEncoder passwordEncoder;
  private final MailService mailService;

  @Autowired
  public UserService(UserRepo userRepo,
      PasswordEncoder passwordEncoder, MailService mailService) {
    this.userRepo = userRepo;
    this.passwordEncoder = passwordEncoder;
    this.mailService = mailService;
  }

  @Override
  public User loadUserByUsername(String s) {
    return userRepo.findByUsername(s)
        .orElseThrow(() -> new UsernameNotFoundException("User not exist!"));
  }

  public void addUser(UserRegistrationDto userDTO) {
    userRepo.findByUsername(userDTO.getUsername())
        .ifPresent(s -> {throw new UsernameIsAlreadyTaken("Username is already taken");});

    userRepo.findByEmail(userDTO.getEmail())
        .ifPresent(s -> {throw new EmailIsAlreadyTaken("Email is already taken");});

    User user = User.builder()
        .username(userDTO.getUsername())
        .password(passwordEncoder.encode(userDTO.getPassword()))
        .email(userDTO.getEmail())
        .active(false)
        .role(Collections.singleton(Role.CLIENT))
        .activationCode(UUID.randomUUID().toString())
        .build();

    try {
      userRepo.save(user);
    } catch (Exception e) {
      throw new CannotSaveUserToDatabase("Cannot save user to database");
    }

    mailService.sendActivationCode(user);
  }

  public void updateUser(UserProfileDto userDTO, String oldPassword) {
    User user = userRepo.findByUsername(userDTO.getUsername())
        .orElseThrow(() -> new UserNotFound("User not found"));

    boolean changed = false;

    if (!oldPassword.isEmpty()) {
      if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
        throw new WrongPassword("Wrong old password");
      }
      user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
      changed = true;
    }

    if (!user.getEmail().equals(userDTO.getEmail())) {
      userRepo.findByEmail(userDTO.getEmail())
          .ifPresent(s -> {throw new EmailIsAlreadyTaken("Email is already taken");});
      user.setEmail(userDTO.getEmail());
      user.setActive(false);
      user.setActivationCode(UUID.randomUUID().toString());
      mailService.sendActivationCode(user);
      changed = true;
    }

    if (changed) {
      try {
        userRepo.save(user);
      } catch (Exception e) {
        throw new CannotSaveUserToDatabase("Cannot save user to database");
      }
    }
  }

  public void activateUser(String code) {
    User user = userRepo.findByActivationCode(code)
        .orElseThrow(() -> new UserNotFoundByActivationCode("User not found by activation code"));

    user.setActivationCode(null);
    user.setActive(true);

    userRepo.save(user);
  }

  public List<User> findAllMasters() {
    return userRepo.findAllByRoleContaining(Role.MASTER);
  }

}
