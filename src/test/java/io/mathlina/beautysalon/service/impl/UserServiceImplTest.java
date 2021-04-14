package io.mathlina.beautysalon.service.impl;

import static org.junit.jupiter.api.Assertions.*;

import io.mathlina.beautysalon.domain.User;
import io.mathlina.beautysalon.exception.UserNotFoundByActivationCode;
import io.mathlina.beautysalon.repos.UserRepo;
import io.mathlina.beautysalon.service.CommentService;
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

  @Mock
  UserRepo userRepo;

  @InjectMocks
  UserServiceImpl userService;

  @Test
  void loadUserByUsernameShouldReturnExistingUser() {
    String username = "username";
    User expected = User.builder().username(username).build();

    Mockito.when(userRepo.findByUsername(username)).thenReturn(Optional.of(expected));

    User actual = userService.loadUserByUsername(username);

    assertEquals(expected, actual);

    Mockito.verify(userRepo).findByUsername(username);
    Mockito.verifyNoMoreInteractions(userRepo);
  }

  @Test
  void loadUserByUsernameShouldThrowExceptionIfUserNotFound() {
    String username = "username";

    Mockito.when(userRepo.findByUsername(username)).thenReturn(Optional.empty());

    assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername(username));

    Mockito.verify(userRepo).findByUsername(username);
    Mockito.verifyNoMoreInteractions(userRepo);
  }

  @Test
  void addUser() {
  }

  @Test
  void updateUser() {
  }

  @Test
  void activateUserShouldUpdateUserActiveStatus() {
    String username = "username";
    String activationCode = "activationCode";
    User user = User.builder().username(username).activationCode(activationCode).build();
    User activeUser = User.builder().username(username).activationCode(null).active(true).build();

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