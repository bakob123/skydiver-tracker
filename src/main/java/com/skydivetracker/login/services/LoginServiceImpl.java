package com.skydivetracker.login.services;

import com.skydivetracker.errorhandling.exceptions.IncorrectPasswordException;
import com.skydivetracker.errorhandling.exceptions.InvalidUserDataException;
import com.skydivetracker.errorhandling.exceptions.SkydiverNotFoundException;
import com.skydivetracker.login.models.LoginDTO;
import com.skydivetracker.login.models.LoginResponseDTO;
import com.skydivetracker.security.config.JwtTokenUtil;
import com.skydivetracker.security.password.PasswordService;
import com.skydivetracker.skydivers.models.Skydiver;
import com.skydivetracker.skydivers.services.SkydiverService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Getter
@Setter
public class LoginServiceImpl implements LoginService {

  private SkydiverService skydiverService;
  private PasswordService passwordService;
  private JwtTokenUtil jwtTokenUtil;

  @Override
  public LoginResponseDTO login(LoginDTO loginDTO) {
    Skydiver skydiver;
    try {
      skydiver = skydiverService.getByUsername(loginDTO.getUsername());
      if (!passwordService.isMatchingPassword(loginDTO.getPassword(), skydiver.getPassword())) {
        throw new IncorrectPasswordException();
      }
    } catch (SkydiverNotFoundException | IncorrectPasswordException e) {
      throw new InvalidUserDataException();
    }
    return new LoginResponseDTO(jwtTokenUtil.createJwtToken(jwtTokenUtil.createDefaultClaims(skydiver)));
  }

}
