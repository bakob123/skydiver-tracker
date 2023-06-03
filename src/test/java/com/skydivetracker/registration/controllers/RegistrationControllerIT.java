package com.skydivetracker.registration.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skydivetracker.TestFactory;
import com.skydivetracker.errorhandling.exceptions.AlreadyTakenException;
import com.skydivetracker.registration.models.dtos.RegistrationDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class RegistrationControllerIT {

  private final MediaType contentType = MediaType.APPLICATION_JSON;
  @Autowired
  private MockMvc mockMvc;
  private ObjectMapper mapper;
  private RegistrationDTO registrationDTO;

  @Before
  public void setup() {
    mapper = new ObjectMapper();
  }

  @Test
  public void register_should_returnCorrectResponse() throws Exception {
    registrationDTO = new RegistrationDTO
        (
            "New User",
            "New",
            "User",
            "newuser@email.com",
            "password"
        );
    mockMvc.perform(post("/register")
            .contentType(contentType)
            .content(mapper.writeValueAsString(registrationDTO)))
        .andExpect(content().contentTypeCompatibleWith(contentType))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.username", is("New User")))
        .andExpect(jsonPath("$.email", is("newuser@email.com")));
  }

  @Test
  public void register_should_returnCorrectResponse_when_usernameIsTaken() throws Exception {
    registrationDTO = TestFactory.createDefaultRegistrationDTO();
    mockMvc.perform(post("/register")
            .contentType(contentType)
            .content(mapper.writeValueAsString(registrationDTO)))
        .andExpect(content().contentTypeCompatibleWith(contentType))
        .andExpect(status().isConflict())
        .andExpect(jsonPath("$.status", is("error")))
        .andExpect(jsonPath("$.message", is("Username is already in use.")));
  }

  @Test
  public void register_should_returnCorrectResponse_when_emailIsTaken() throws Exception {
    registrationDTO = TestFactory.createDefaultRegistrationDTO();
    registrationDTO.setUsername("availableUsername");
    mockMvc.perform(post("/register")
            .contentType(contentType)
            .content(mapper.writeValueAsString(registrationDTO)))
        .andExpect(content().contentTypeCompatibleWith(contentType))
        .andExpect(status().isConflict())
        .andExpect(jsonPath("$.status", is("error")))
        .andExpect(jsonPath("$.message", is("Email is already in use.")));
  }

}