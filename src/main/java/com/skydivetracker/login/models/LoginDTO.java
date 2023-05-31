package com.skydivetracker.login.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class LoginDTO {

  @NotBlank
  private String username;
  @NotBlank
  private String password;

}
