package io.mathlina.beautysalon.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import io.mathlina.beautysalon.domain.User;
import io.mathlina.beautysalon.dto.UserRegistrationDto;
import io.mathlina.beautysalon.exception.EmailIsAlreadyTaken;
import io.mathlina.beautysalon.exception.UserNotFoundByActivationCode;
import io.mathlina.beautysalon.exception.UsernameIsAlreadyTaken;
import io.mathlina.beautysalon.repos.UserRepo;
import io.mathlina.beautysalon.service.UserService;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest(classes = UserService.class)
@ExtendWith({SpringExtension.class})
class UserServiceImplTest {

  private static final String USERNAME = "username";
  private static final String EMAIL = "email";
  private static final String PASSWORD = "password";

  @Mock
  UserRepo userRepo;

  @InjectMocks
  UserServiceImpl userService;

  @Test
  void loadUserByUsernameShouldReturnExistingUser() {
    User expected = User.builder().username(USERNAME).build();

    Mockito.when(userRepo.findByUsername(USERNAME)).thenReturn(Optional.of(expected));

    User actual = userService.loadUserByUsername(USERNAME);

    assertEquals(expected, actual);

    Mockito.verify(userRepo).findByUsername(USERNAME);
    Mockito.verifyNoMoreInteractions(userRepo);
  }

  @Test
  void loadUserByUsernameShouldThrowExceptionIfUserNotFound() {
    Mockito.when(userRepo.findByUsername(USERNAME)).thenReturn(Optional.empty());

    assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername(USERNAME));

    Mockito.verify(userRepo).findByUsername(USERNAME);
    Mockito.verifyNoMoreInteractions(userRepo);
  }

  @Test
  void addUserShouldThrowExceptionWhenUserFoundByUsername() {
    UserRegistrationDto registrationDto = UserRegistrationDto.builder().username(USERNAME).build();
    User user = User.builder().username(USERNAME).build();

    Mockito.when(userRepo.findByUsername(USERNAME)).thenReturn(Optional.of(user));

    assertThrows(UsernameIsAlreadyTaken.class, () -> userService.addUser(registrationDto));

    Mockito.verify(userRepo).findByUsername(USERNAME);
    Mockito.verifyNoMoreInteractions(userRepo);
  }

  @Test
  void addUserShouldThrowExceptionWhenUserFoundByEmail() {
    UserRegistrationDto registrationDto = UserRegistrationDto.builder()
        .username(USERNAME)
        .email(EMAIL)
        .password(PASSWORD)
        .build();
    User user = User.builder().email(EMAIL).build();

    Mockito.when(userRepo.findByUsername(USERNAME)).thenReturn(Optional.empty());
    Mockito.when(userRepo.findByEmail(EMAIL)).thenReturn(Optional.of(user));

    assertThrows(EmailIsAlreadyTaken.class, () -> userService.addUser(registrationDto));

    Mockito.verify(userRepo).findByUsername(USERNAME);
    Mockito.verify(userRepo).findByEmail(EMAIL);
    Mockito.verifyNoMoreInteractions(userRepo);
  }

  //TODO replace UUID with using interface to test

  @Test
  void activateUserShouldUpdateUserActiveStatus() {
    String activationCode = "activationCode";
    User user = User.builder().username(USERNAME).activationCode(activationCode).build();
    User activeUser = User.builder().username(USERNAME).activationCode(null).active(true).build();

    Mockito.when(userRepo.findByActivationCode(activationCode)).thenReturn(Optional.of(user));

    userService.activateUser(activationCode);

    Mockito.verify(userRepo).findByActivationCode(activationCode);
    Mockito.verify(userRepo).save(activeUser);
    Mockito.verifyNoMoreInteractions(userRepo);
  }

  @Test
  void activateUserShouldThrowExceptionWhenActivationCodeDoesNotExist() {
    String activationCode = "activationCode";

    assertThrows(UserNotFoundByActivationCode.class,
        () -> userService.activateUser(activationCode));

    Mockito.verify(userRepo).findByActivationCode(activationCode);
    Mockito.verifyNoMoreInteractions(userRepo);
  }
}