package com.skydivetracker.registration.services;

import com.skydivetracker.registration.models.dtos.RegistrationDTO;
import com.skydivetracker.registration.models.dtos.RegistrationResponseDTO;

public interface RegistrationService {

  RegistrationResponseDTO register(RegistrationDTO registrationDTO);

}
