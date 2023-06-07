package com.skydivetracker.registration.controllers;

import com.skydivetracker.errorhandling.ErrorMessage;
import com.skydivetracker.errorhandling.exceptions.AlreadyTakenException;
import com.skydivetracker.registration.models.dtos.RegistrationDTO;
import com.skydivetracker.registration.services.RegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.validation.Valid;

@RestController
@AllArgsConstructor
public class RegistrationController {

  private RegistrationService registrationService;

  @PostMapping("/register")
  public ResponseEntity<?> register(@Valid @RequestBody RegistrationDTO registrationDTO) {
    try {
      return ResponseEntity.status(HttpStatus.CREATED).body(registrationService.register(registrationDTO));
    } catch (AlreadyTakenException e) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorMessage(e.getMessage()));
    } catch (MessagingException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorMessage(e.getMessage()));
    }
  }

}
