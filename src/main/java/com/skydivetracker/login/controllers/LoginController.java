package com.skydivetracker.login.controllers;

import com.skydivetracker.errorhandling.ErrorMessage;
import com.skydivetracker.errorhandling.exceptions.InvalidUserDataException;
import com.skydivetracker.login.models.LoginDTO;
import com.skydivetracker.login.services.LoginService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
public class LoginController {

  private LoginService loginService;

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody @Valid LoginDTO loginDTO) { //TODO test
    try {
      return ResponseEntity.status(HttpStatus.OK).body(loginService.login(loginDTO));
    } catch (InvalidUserDataException e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorMessage(e.getMessage()));
    }
  }

}
