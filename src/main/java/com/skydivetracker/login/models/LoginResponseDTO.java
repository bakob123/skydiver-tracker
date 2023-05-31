package com.skydivetracker.login.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponseDTO {

  private String status;
  private String token;

  public LoginResponseDTO(String token) {
    this.status = "ok";
    this.token = token;
  }

}
