package com.skydivetracker.registration.services;

import com.skydivetracker.errorhandling.exceptions.AlreadyTakenException;
import com.skydivetracker.registration.models.dtos.RegistrationDTO;
import com.skydivetracker.registration.models.dtos.RegistrationResponseDTO;

import javax.mail.MessagingException;

public interface RegistrationService {

  RegistrationResponseDTO register(RegistrationDTO registrationDTO) throws AlreadyTakenException, MessagingException;

  void validate(RegistrationDTO registrationDTO) throws AlreadyTakenException;
}
