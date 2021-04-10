package io.mathlina.beautysalon.exeption;

public class CannotSaveUserToDatabase extends RuntimeException {

  public CannotSaveUserToDatabase(String message) {
    super(message);
  }

}
