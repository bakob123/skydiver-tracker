package com.skydivetracker.registration.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegistrationResponseDTO {

  private String username;
  private String email;

}
