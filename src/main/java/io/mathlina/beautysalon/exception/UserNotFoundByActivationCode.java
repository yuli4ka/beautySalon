package io.mathlina.beautysalon.exception;

public class UserNotFoundByActivationCode extends RuntimeException {

  public UserNotFoundByActivationCode(String message) {
    super(message);
  }

}
