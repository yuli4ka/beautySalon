package io.mathlina.beautysalon.exception;

public class EmailIsAlreadyTaken extends RuntimeException {

  public EmailIsAlreadyTaken(String message) {
    super(message);
  }

}
