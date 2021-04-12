package io.mathlina.beautysalon.service;

import io.mathlina.beautysalon.domain.User;
import io.mathlina.beautysalon.dto.MasterDto;
import io.mathlina.beautysalon.dto.UserProfileDto;
import io.mathlina.beautysalon.dto.UserRegistrationDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

  User loadUserByUsername(String s);

  void addUser(UserRegistrationDto userDTO);

  void updateUser(UserProfileDto userDTO, String oldPassword);

  void activateUser(String code);

//  Page<MasterDto> findPaginatedMasters(Pageable pageable);
}
