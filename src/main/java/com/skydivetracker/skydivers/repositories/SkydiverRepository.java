package com.skydivetracker.skydivers.repositories;

import com.skydivetracker.skydivers.models.Skydiver;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SkydiverRepository extends CrudRepository<Skydiver, Long> {

  Optional<Skydiver> findByUsername(String username);

  Optional<Skydiver> findByEmail(String email);

}
