package com.skydivetracker.registration.services;

import com.skydivetracker.TestFactory;
import com.skydivetracker.errorhandling.exceptions.AlreadyTakenException;
import com.skydivetracker.registration.models.dtos.RegistrationDTO;
import com.skydivetracker.registration.models.dtos.RegistrationResponseDTO;
import com.skydivetracker.security.password.PasswordService;
import com.skydivetracker.skydivers.models.Skydiver;
import com.skydivetracker.skydivers.repositories.SkydiverRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RegistrationServiceImplTest {

  @Spy
  @InjectMocks
  private RegistrationServiceImpl registrationService;
  @Mock
  private SkydiverRepository mockSkydiverRepo;
  @Mock
  private PasswordService mockPasswordService;
  private RegistrationDTO registrationDTO;
  private Skydiver defaultSkydiver;

  @Before
  public void setup() {
    registrationDTO = TestFactory.createDefaultRegistrationDTO();
    defaultSkydiver = TestFactory.createDefaultSkydiver();
  }

  @Test(expected = AlreadyTakenException.class)
  public void register_should_throwAlreadyTakenException_when_usernameIsTaken() {
    doThrow(AlreadyTakenException.class).when(registrationService).validate(registrationDTO);
    registrationService.register(registrationDTO);
  }

  @Test
  public void register_should_saveUser() {
    doNothing().when(registrationService).validate(registrationDTO);
    doReturn(defaultSkydiver).when(registrationService).convertToSkydiver(registrationDTO);
    when(mockSkydiverRepo.save(defaultSkydiver)).thenReturn(defaultSkydiver);
    RegistrationResponseDTO expected = new RegistrationResponseDTO("default", "default@email.com");

    assertEquals(expected, registrationService.register(registrationDTO));
    verify(mockSkydiverRepo, times(1)).save(defaultSkydiver);
  }

  @Test(expected = AlreadyTakenException.class)
  public void validate_should_throwAlreadyTakenException_when_usernameIsTaken() {
    when(mockSkydiverRepo.findByUsername("default")).thenReturn(Optional.of(defaultSkydiver));
    registrationService.validate(registrationDTO);
  }

  @Test(expected = AlreadyTakenException.class)
  public void validate_should_throwAlreadyTakenException_when_emailIsTaken() {
    when(mockSkydiverRepo.findByEmail("default@email.com")).thenReturn(Optional.of(defaultSkydiver));
    registrationService.validate(registrationDTO);
  }

  @Test
  public void convertToSkydiver_should_returnCorrectSkydiver() {
    when(mockPasswordService.encode(anyString())).thenReturn("password");
    assertEquals(defaultSkydiver, registrationService.convertToSkydiver(registrationDTO));
  }

}