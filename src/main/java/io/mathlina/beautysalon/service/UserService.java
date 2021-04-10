package io.mathlina.beautysalon.service;

import io.mathlina.beautysalon.dto.UserRegistrationDto;
import io.mathlina.beautysalon.domain.Role;
import io.mathlina.beautysalon.domain.User;
import io.mathlina.beautysalon.repos.UserRepo;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

  private final UserRepo userRepo;

  @Autowired
  public UserService(UserRepo userRepo) {
    this.userRepo = userRepo;
  }


  @Override
  public UserDetails loadUserByUsername(String s) {
    Optional<User> user = userRepo.findByUsername(s);
    return user.orElseThrow(() -> new UsernameNotFoundException("User not exist!"));
  }

  public boolean addUser(UserRegistrationDto userDTO) {
//    return false;
    User user = User.builder()
        .username(userDTO.getUsername())
        .password(userDTO.getPassword())
        .email(userDTO.getEmail())
        .active(false)
        .role(Collections.singleton(Role.CLIENT))
        .activationCode(UUID.randomUUID().toString())
        .build();

    //TODO password encode
    try {
      userRepo.save(user);
      //TODO: custom exception or optional
    } catch (Exception e) {
      return false;
    }

    //TODO send message for activation

    return true;
  }
}
