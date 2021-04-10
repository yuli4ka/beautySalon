package io.mathlina.beautysalon.exeption;

public class EmailIsAlreadyTaken extends RuntimeException {

  public EmailIsAlreadyTaken(String message) {
    super(message);
  }

}
