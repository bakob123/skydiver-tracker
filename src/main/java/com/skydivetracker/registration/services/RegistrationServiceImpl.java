package com.skydivetracker.registration.services;

import com.skydivetracker.email.EmailService;
import com.skydivetracker.errorhandling.exceptions.AlreadyTakenException;
import com.skydivetracker.registration.models.dtos.RegistrationDTO;
import com.skydivetracker.registration.models.dtos.RegistrationResponseDTO;
import com.skydivetracker.security.password.PasswordService;
import com.skydivetracker.skydivers.models.Skydiver;
import com.skydivetracker.skydivers.repositories.SkydiverRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.ArrayList;
import java.util.UUID;

@Service
@AllArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {

  private SkydiverRepository skydiverRepository;
  private PasswordService passwordService;
  private EmailService emailService;

  @Override
  public RegistrationResponseDTO register(RegistrationDTO registrationDTO) throws AlreadyTakenException, MessagingException {
    validate(registrationDTO);
    Skydiver skydiver = skydiverRepository.save(convertToSkydiver(registrationDTO));
    emailService.send(emailService.createVerificationMail(skydiver));
    return new RegistrationResponseDTO(skydiver.getUsername(), skydiver.getEmail());
  }

  @Override
  public void validate(RegistrationDTO registrationDTO) throws AlreadyTakenException {
    if (skydiverRepository.findByUsername(registrationDTO.getUsername()).isPresent()) {
      throw new AlreadyTakenException("Username is already in use.");
    }
    if (skydiverRepository.findByEmail(registrationDTO.getEmail()).isPresent()) {
      throw new AlreadyTakenException("Email is already in use.");
    }
  }

  public Skydiver convertToSkydiver(RegistrationDTO registrationDTO) {
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
            false,
            UUID.randomUUID()
        );
  }


}
