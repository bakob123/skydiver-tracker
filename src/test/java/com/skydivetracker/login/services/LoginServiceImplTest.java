package com.skydivetracker.login.services;

import com.skydivetracker.TestFactory;
import com.skydivetracker.errorhandling.exceptions.IncorrectPasswordException;
import com.skydivetracker.errorhandling.exceptions.InvalidUserDataException;
import com.skydivetracker.errorhandling.exceptions.SkydiverNotFoundException;
import com.skydivetracker.login.models.LoginDTO;
import com.skydivetracker.login.models.LoginResponseDTO;
import com.skydivetracker.security.config.JwtTokenUtil;
import com.skydivetracker.security.password.PasswordService;
import com.skydivetracker.skydivers.models.Skydiver;
import com.skydivetracker.skydivers.services.SkydiverService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.when;
@RunWith(MockitoJUnitRunner.class)
public class LoginServiceImplTest {

  @InjectMocks
  private LoginServiceImpl loginService;
  @Mock
  private SkydiverService mockSkyDiverService;
  @Mock
  private PasswordService mockPasswordService;
  @Mock
  private JwtTokenUtil mockJwtTokenUtil;
  private LoginDTO loginDTO;
  private Skydiver user;

  @Test(expected = InvalidUserDataException.class)
  public void login_should_throwInvalidUserDataException_when_userIsNotFound() {
    loginDTO = new LoginDTO("nonExistingUsername", "password");
    when(mockSkyDiverService.getByUsername(loginDTO.getUsername())).thenThrow(SkydiverNotFoundException.class);
    loginService.login(loginDTO);
  }

  @Test(expected = InvalidUserDataException.class)
  public void login_should_throwInvalidUserDataException_when_passwordIsIncorrect() {
    loginDTO = new LoginDTO("default", "incorrectPassword");
    user = TestFactory.createDefaultSkydiver();
    when(mockSkyDiverService.getByUsername(loginDTO.getUsername())).thenReturn(user);
    when(mockPasswordService.isMatchingPassword(loginDTO.getPassword(), user.getPassword()))
        .thenThrow(IncorrectPasswordException.class);

    loginService.login(loginDTO);
  }

  @Test
  public void login_should_returnCorrectLoginResponseDTO() {
    loginDTO = new LoginDTO("default", "password");
    user = TestFactory.createDefaultSkydiver();
    when(mockSkyDiverService.getByUsername(loginDTO.getUsername())).thenReturn(user);
    when(mockPasswordService.isMatchingPassword(loginDTO.getPassword(), user.getPassword())).thenReturn(true);
    when(mockJwtTokenUtil.createJwtToken(any())).thenReturn("123456789");
    LoginResponseDTO expected = new LoginResponseDTO("123456789");

    assertEquals(expected, loginService.login(loginDTO));
  }

}