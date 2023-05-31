package com.skydivetracker.errorhandling.exceptions;

public class IncorrectPasswordException extends RuntimeException {

  public static final String MESSAGE = "Invorrect password.";

  public IncorrectPasswordException() {
    super(MESSAGE);
  }

}
