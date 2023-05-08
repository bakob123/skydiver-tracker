package com.skydivetracker.errorhandling.exceptions;

public class SkydiverNotFoundException extends RuntimeException {

  public static final String MESSAGE = "Skydiver not found.";

  public SkydiverNotFoundException() {
    super(MESSAGE);
  }

}
