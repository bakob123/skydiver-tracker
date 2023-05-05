package com.skydivetracker.skydivers.models;

import com.skydivetracker.flight.models.Flight;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Table(name = "skydivers")
public class Skydiver {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @NotBlank
  @Column(unique = true, length = 50)
  private String username;
  @NotBlank
  @Column(unique = true)
  private String email;
  @NotNull
  private Integer estimatedJumps;
  @NotNull
  private Date estimatedStay;
  @NotNull
  private Integer preferredHeight;
  @ManyToMany
  @JoinTable
      (
          name = "flights_skydivers",
          joinColumns = {@JoinColumn(name = "skydiver_id")},
          inverseJoinColumns = {@JoinColumn(name = "flight_id")}
      )
  private List<Flight> flights;
  private boolean available;
  private boolean admin;

}
