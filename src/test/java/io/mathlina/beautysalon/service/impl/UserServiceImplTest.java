package io.mathlina.beautysalon.service.impl;

import io.mathlina.beautysalon.dto.UserRegistrationDto;
import io.mathlina.beautysalon.exception.EmailIsAlreadyTaken;
import io.mathlina.beautysalon.exception.UserNotFoundByActivationCode;
import io.mathlina.beautysalon.exception.UsernameIsAlreadyTaken;
import io.mathlina.beautysalon.model.UserModel;
import io.mathlina.beautysalon.repos.UserRepository;
import io.mathlina.beautysalon.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = UserService.class)
@ExtendWith({SpringExtension.class})
class UserServiceImplTest {

  private static final String USERNAME = "username";
  private static final String EMAIL = "email";
  private static final String PASSWORD = "password";

  @Mock
  UserRepository userRepository;

  @InjectMocks
  UserServiceImpl userService;

  @Test
  void loadUserByUsernameShouldReturnExistingUser() {
    UserModel expected = UserModel.builder().username(USERNAME).build();

    Mockito.when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.of(expected));

    UserModel actual = userService.loadUserByUsername(USERNAME);

    assertEquals(expected, actual);

    Mockito.verify(userRepository).findByUsername(USERNAME);
    Mockito.verifyNoMoreInteractions(userRepository);
  }

  @Test
  void loadUserByUsernameShouldThrowExceptionIfUserNotFound() {
    Mockito.when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.empty());

    assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername(USERNAME));

    Mockito.verify(userRepository).findByUsername(USERNAME);
    Mockito.verifyNoMoreInteractions(userRepository);
  }

  @Test
  void addUserShouldThrowExceptionWhenUserFoundByUsername() {
    UserRegistrationDto registrationDto = UserRegistrationDto.builder().username(USERNAME).build();
    UserModel user = UserModel.builder().username(USERNAME).build();

    Mockito.when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.of(user));

    assertThrows(UsernameIsAlreadyTaken.class, () -> userService.addUser(registrationDto));

    Mockito.verify(userRepository).findByUsername(USERNAME);
    Mockito.verifyNoMoreInteractions(userRepository);
  }

  @Test
  void addUserShouldThrowExceptionWhenUserFoundByEmail() {
    UserRegistrationDto registrationDto = UserRegistrationDto.builder()
        .username(USERNAME)
        .email(EMAIL)
        .password(PASSWORD)
        .build();
    UserModel user = UserModel.builder().email(EMAIL).build();

    Mockito.when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.empty());
    Mockito.when(userRepository.findByEmail(EMAIL)).thenReturn(Optional.of(user));

    assertThrows(EmailIsAlreadyTaken.class, () -> userService.addUser(registrationDto));

    Mockito.verify(userRepository).findByUsername(USERNAME);
    Mockito.verify(userRepository).findByEmail(EMAIL);
    Mockito.verifyNoMoreInteractions(userRepository);
  }

  //TODO replace UUID with using interface to test

  @Test
  void activateUserShouldUpdateUserActiveStatus() {
    String activationCode = "activationCode";
    UserModel user = UserModel.builder().username(USERNAME).activationCode(activationCode).build();
    UserModel activeUser = UserModel.builder().username(USERNAME).activationCode(null).active(true).build();

    Mockito.when(userRepository.findByActivationCode(activationCode)).thenReturn(Optional.of(user));

    userService.activateUser(activationCode);

    Mockito.verify(userRepository).findByActivationCode(activationCode);
    Mockito.verify(userRepository).save(activeUser);
    Mockito.verifyNoMoreInteractions(userRepository);
  }

  @Test
  void activateUserShouldThrowExceptionWhenActivationCodeDoesNotExist() {
    String activationCode = "activationCode";

    assertThrows(UserNotFoundByActivationCode.class,
        () -> userService.activateUser(activationCode));

    Mockito.verify(userRepository).findByActivationCode(activationCode);
    Mockito.verifyNoMoreInteractions(userRepository);
  }
}