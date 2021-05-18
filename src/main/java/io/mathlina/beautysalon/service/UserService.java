package io.mathlina.beautysalon.service;

import io.mathlina.beautysalon.dto.UserProfileDto;
import io.mathlina.beautysalon.dto.UserRegistrationDto;
import io.mathlina.beautysalon.model.UserModel;

public interface UserService {

  UserModel loadUserByUsername(String s);

  void addUser(UserRegistrationDto userDTO);

  void updateUser(UserProfileDto userDTO, String oldPassword);

  void activateUser(String code);

}
