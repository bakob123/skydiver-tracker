package com.skydivetracker.errorhandling.exceptions;

public class InvalidUserDataException extends RuntimeException {

  public static final String MESSAGE = "Incorrect username or password.";

  public InvalidUserDataException() {
    super(MESSAGE);
  }

}
