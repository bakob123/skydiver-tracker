package com.skydivetracker.skydivers.models;

import com.skydivetracker.flight.models.Flight;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@Table(name = "skydivers")
public class Skydiver implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @NotBlank
  @Column(unique = true, length = 50)
  private String username;
  @NotBlank
  private String firstName;
  @NotBlank
  private String lastName;
  @NotBlank
  @Column(unique = true)
  private String email;
  @NotBlank
  private String password;
  @NotNull
  private Integer estimatedJumps;
  @NotNull
  private Long estimatedStay;
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
  @Column(name = "verification")
  private UUID verificationToken;

  public Skydiver() {
    this.flights = new ArrayList<>();
    this.verificationToken = UUID.randomUUID();
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return null;
  }

  @Override
  public boolean isAccountNonExpired() {
    return false;
  }

  @Override
  public boolean isAccountNonLocked() {
    return false;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return false;
  }

  @Override
  public boolean isEnabled() {
    return false;
  }

}
