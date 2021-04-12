package io.mathlina.beautysalon.service;

import io.mathlina.beautysalon.domain.User;
import io.mathlina.beautysalon.dto.UserProfileDto;
import io.mathlina.beautysalon.dto.UserRegistrationDto;
import java.util.List;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {

  User loadUserByUsername(String s);

  void addUser(UserRegistrationDto userDTO);

  void updateUser(UserProfileDto userDTO, String oldPassword);

  void activateUser(String code);

  List<User> findAllMasters();

}
