package com.skydivetracker.login.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skydivetracker.errorhandling.exceptions.InvalidUserDataException;
import com.skydivetracker.login.models.LoginDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class LoginControllerIT {

  private final MediaType contentType = MediaType.APPLICATION_JSON;
  @Autowired
  private MockMvc mockMvc;
  private ObjectMapper mapper;
  private LoginDTO loginDTO;

  @Before
  public void setup() {
    mapper = new ObjectMapper();
  }

  @Test
  public void login_should_returnCorrectResponse_when_usernameIsIncorrect() throws Exception {
    loginDTO = new LoginDTO("nonExistingUsername", "password");
    mockMvc.perform(post("/login")
            .contentType(contentType)
            .content(mapper.writeValueAsString(loginDTO)))
        .andExpect(content().contentTypeCompatibleWith(contentType))
        .andExpect(status().isUnauthorized())
        .andExpect(jsonPath("$.status", is("error")))
        .andExpect(jsonPath("$.message", is(InvalidUserDataException.MESSAGE)));
  }

  @Test
  public void login_should_returnCorrectResponse_when_passwordIsIncorrect() throws Exception {
    loginDTO = new LoginDTO("default", "incorrectPassword");
    mockMvc.perform(post("/login")
            .contentType(contentType)
            .content(mapper.writeValueAsString(loginDTO)))
        .andExpect(content().contentTypeCompatibleWith(contentType))
        .andExpect(status().isUnauthorized())
        .andExpect(jsonPath("$.status", is("error")))
        .andExpect(jsonPath("$.message", is(InvalidUserDataException.MESSAGE)));
  }

  @Test
  public void login_should_returnCorrectResponse_when_loginDataIsCorrect() throws Exception {
    loginDTO = new LoginDTO("default", "password");
    mockMvc.perform(post("/login")
            .contentType(contentType)
            .content(mapper.writeValueAsString(loginDTO)))
        .andExpect(content().contentTypeCompatibleWith(contentType))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status", is("ok")))
        .andExpect(jsonPath("$.token").isString())
        .andExpect(jsonPath("$.token").isNotEmpty());
  }

}