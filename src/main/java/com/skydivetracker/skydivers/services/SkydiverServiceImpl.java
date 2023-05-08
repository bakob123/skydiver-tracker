package com.skydivetracker.skydivers.services;

import com.skydivetracker.errorhandling.exceptions.SkydiverNotFoundException;
import com.skydivetracker.skydivers.models.Skydiver;
import com.skydivetracker.skydivers.repositories.SkydiverRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SkydiverServiceImpl implements SkydiverService {

  private SkydiverRepository skydiverRepository;

  @Override
  public Skydiver getByUsername(String username) throws SkydiverNotFoundException {
    return skydiverRepository.findByUsername(username).orElseThrow(SkydiverNotFoundException::new);
  }

  @Override
  public Skydiver getByEmail(String email) throws SkydiverNotFoundException {
    return skydiverRepository.findByEmail(email).orElseThrow(SkydiverNotFoundException::new);
  }

}
