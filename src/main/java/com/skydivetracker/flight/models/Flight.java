package com.skydivetracker.flight.models;

import com.skydivetracker.airport.models.Airport;
import com.skydivetracker.skydivers.models.Skydiver;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Table(name = "flights")
public class Flight {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @NotNull
  private Long estimatedTakeoff;
  @NotNull
  private Long actualTakeoff;
  @ManyToOne
  private Airport airport;
  @ManyToMany
  @JoinTable
      (
          name = "flights_skydivers",
          joinColumns = {@JoinColumn(name = "flight_id")},
          inverseJoinColumns = {@JoinColumn(name = "skydiver_id")}
      )
  private List<Skydiver> skydivers;

}
