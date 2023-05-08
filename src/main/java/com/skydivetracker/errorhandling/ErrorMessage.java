package com.skydivetracker.errorhandling;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ErrorMessage {

  private String status;
  private String message;

  public ErrorMessage(String message) {
    this.status = "error";
    this.message = message;
  }

}
