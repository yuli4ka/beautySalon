package io.mathlina.beautysalon.exeption;

public class UsernameIsAlreadyTaken extends RuntimeException {

  public UsernameIsAlreadyTaken(String message) {
    super(message);
  }

}
