package io.mathlina.beautysalon.service;

import io.mathlina.beautysalon.domain.Role;
import io.mathlina.beautysalon.domain.User;
import io.mathlina.beautysalon.dto.UserRegistrationDto;
import io.mathlina.beautysalon.exception.CannotSaveUserToDatabase;
import io.mathlina.beautysalon.exception.EmailIsAlreadyTaken;
import io.mathlina.beautysalon.exception.UsernameIsAlreadyTaken;
import io.mathlina.beautysalon.repos.UserRepo;
import java.util.Collections;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

//TODO: log
@Service
public class UserService implements UserDetailsService {

  private final UserRepo userRepo;
  private final PasswordEncoder passwordEncoder;

  @Autowired
  public UserService(UserRepo userRepo,
      PasswordEncoder passwordEncoder) {
    this.userRepo = userRepo;
    this.passwordEncoder = passwordEncoder;
  }


  @Override
  public UserDetails loadUserByUsername(String s) {
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

    //TODO send message for activation

  }

}
