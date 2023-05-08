package com.skydivetracker.registration.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
public class RegistrationDTO {

  @NotBlank
  @Size(message = "Username can not be longer than 50 characters.", max = 50)
  private String username;
  @NotBlank
  private String firstName;
  @NotBlank
  private String lastName;
  @NotBlank
  @Email(message = "Please provide a valid email address.", regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")
  private String email;
  @NotBlank
  @Size(message = "Password must be at least 8 characters.", min = 8)
  private String password;

}
