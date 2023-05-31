package com.skydivetracker;

import com.skydivetracker.registration.models.dtos.RegistrationDTO;
import com.skydivetracker.skydivers.models.Skydiver;

import java.util.ArrayList;

public class TestFactory {

  public static RegistrationDTO createDefaultRegistrationDTO() {
    return new RegistrationDTO
        (
            "default",
            "Default",
            "User",
            "default@email.com",
            "password"
        );
  }

  public static Skydiver createDefaultSkydiver() {
    return new Skydiver
        (
            null,
            "default",
            "Default",
            "User",
            "default@email.com",
            "password",
            0,
            0L,
            0,
            new ArrayList<>(),
            false,
            false
        );
  }

}
