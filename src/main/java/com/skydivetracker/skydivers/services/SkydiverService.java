package com.skydivetracker.skydivers.services;

import com.skydivetracker.errorhandling.exceptions.SkydiverNotFoundException;
import com.skydivetracker.skydivers.models.Skydiver;

public interface SkydiverService {

  Skydiver getByUsername(String username) throws SkydiverNotFoundException;

  Skydiver getByEmail(String email) throws SkydiverNotFoundException;

}
