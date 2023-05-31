package com.skydivetracker.login.services;

import com.skydivetracker.login.models.LoginDTO;
import com.skydivetracker.login.models.LoginResponseDTO;

public interface LoginService {

  LoginResponseDTO login(LoginDTO loginDTO);

}
