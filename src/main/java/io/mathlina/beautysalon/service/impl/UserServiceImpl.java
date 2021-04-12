package io.mathlina.beautysalon.service.impl;

import io.mathlina.beautysalon.domain.Role;
import io.mathlina.beautysalon.domain.User;
import io.mathlina.beautysalon.dto.MasterDto;
import io.mathlina.beautysalon.dto.UserProfileDto;
import io.mathlina.beautysalon.dto.UserRegistrationDto;
import io.mathlina.beautysalon.exception.CannotSaveUserToDatabase;
import io.mathlina.beautysalon.exception.EmailIsAlreadyTaken;
import io.mathlina.beautysalon.exception.UserNotFound;
import io.mathlina.beautysalon.exception.UserNotFoundByActivationCode;
import io.mathlina.beautysalon.exception.UsernameIsAlreadyTaken;
import io.mathlina.beautysalon.exception.WrongPassword;
import io.mathlina.beautysalon.repos.UserRepo;
import io.mathlina.beautysalon.service.MailService;
import io.mathlina.beautysalon.service.UserService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

//TODO: log
@Service
public class UserServiceImpl implements UserService {

  private final UserRepo userRepo;
  private final PasswordEncoder passwordEncoder;
  private final MailService mailService;

  @Autowired
  public UserServiceImpl(UserRepo userRepo,
      PasswordEncoder passwordEncoder, MailService mailService) {
    this.userRepo = userRepo;
    this.passwordEncoder = passwordEncoder;
    this.mailService = mailService;
  }

  public User loadUserByUsername(String s) {
    return userRepo.findByUsername(s)
        .orElseThrow(() -> new UsernameNotFoundException("User not exist!"));
  }

  public void addUser(UserRegistrationDto userDTO) {
    userRepo.findByUsername(userDTO.getUsername())
        .ifPresent(s -> {throw new UsernameIsAlreadyTaken("Username is already taken");});

    userRepo.findByEmail(userDTO.getEmail())
        .ifPresent(s -> {throw new EmailIsAlreadyTaken("Email is already taken");});

//    TODO: mapper
    User user = User.builder()
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
      userRepo.save(user);
    } catch (Exception e) {
      throw new CannotSaveUserToDatabase("Cannot save user to database");
    }

    mailService.sendActivationCode(user);
  }

  public void updateUser(UserProfileDto userDTO, String oldPassword) {
    User user = userRepo.findByUsername(userDTO.getUsername())
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

//  @Override
//  public Page<MasterDto> findPaginatedMasters(Pageable pageable) {
//    List<User> masters = userRepo.findAllByRoleContaining(Role.MASTER, pageable);
//
//    List<MasterDto> masterDtoList = new ArrayList<>();
//
//
//
//    return masterDtoPage;
//  }
//
//  public MasterDto convertUserToMasterDto(User user) {
//    MasterDto dto = new MasterDto();
//
//
//
//    return dto;
//  }

}
