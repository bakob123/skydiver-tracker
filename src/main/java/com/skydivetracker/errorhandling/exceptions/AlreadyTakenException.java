package com.skydivetracker.errorhandling.exceptions;

public class AlreadyTakenException extends RuntimeException {

  public AlreadyTakenException(String message) {
    super(message);
  }

}
