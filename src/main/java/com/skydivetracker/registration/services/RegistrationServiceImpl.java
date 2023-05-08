package com.skydivetracker.registration.services;

import com.skydivetracker.errorhandling.exceptions.AlreadyTakenException;
import com.skydivetracker.registration.models.dtos.RegistrationDTO;
import com.skydivetracker.registration.models.dtos.RegistrationResponseDTO;
import com.skydivetracker.security.password.PasswordService;
import com.skydivetracker.skydivers.models.Skydiver;
import com.skydivetracker.skydivers.repositories.SkydiverRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@AllArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {

  private SkydiverRepository skydiverRepository;
  private PasswordService passwordService;

  @Override
  public RegistrationResponseDTO register(RegistrationDTO registrationDTO) {
    validate(registrationDTO);
    Skydiver skydiver = skydiverRepository.save(convertToSkydiver(registrationDTO));
    return new RegistrationResponseDTO(skydiver.getUsername());
  } //TODO TEST

  private Skydiver convertToSkydiver(RegistrationDTO registrationDTO) {
    return new Skydiver
        (
            null,
            registrationDTO.getUsername(),
            registrationDTO.getFirstName(),
            registrationDTO.getLastName(),
            registrationDTO.getEmail(),
            passwordService.encode(registrationDTO.getPassword()),
            0,
            0L,
            0,
            new ArrayList<>(),
            false,
            false
        );
  }

  private void validate(RegistrationDTO registrationDTO) {
    if (skydiverRepository.findByUsername(registrationDTO.getUsername()).isPresent()) {
      throw new AlreadyTakenException("Username is already in use.");
    }
    if (skydiverRepository.findByEmail(registrationDTO.getEmail()).isPresent()) {
      throw new AlreadyTakenException("Email is already in use.");
    }
  }

}
