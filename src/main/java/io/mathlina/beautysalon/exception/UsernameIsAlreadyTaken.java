package io.mathlina.beautysalon.exception;

public class UsernameIsAlreadyTaken extends RuntimeException {

  public UsernameIsAlreadyTaken(String message) {
    super(message);
  }

}
