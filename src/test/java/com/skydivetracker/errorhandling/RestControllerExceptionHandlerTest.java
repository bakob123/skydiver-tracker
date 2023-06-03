package com.skydivetracker.errorhandling;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RestControllerExceptionHandlerTest {

  @Spy
  private RestControllerExceptionHandler restControllerExceptionHandler;
  private List<FieldError> fieldErrors;
  private String expectedMessage;
  private String receivedMessage;
  private HttpStatus expectedStatus;
  private HttpStatus receivedStatus;
  private ResponseEntity<ErrorMessage> response;
  @Mock
  private FieldError fieldError;
  @Mock
  private FieldError fieldErrorTwo;
  @Mock
  private FieldError fieldErrorThree;
  @Mock
  private MethodArgumentNotValidException methodArgumentNotValidException;

  @Before
  public void setup() {
    fieldErrors = new ArrayList<>();
    fieldErrors.add(fieldError);
  }

  @Test
  public void handleMissingFields_should_returnCorrectMessage_when_singleFieldIsMissing() {
    when(methodArgumentNotValidException.getFieldErrors()).thenReturn(fieldErrors);
    when(fieldError.getCode()).thenReturn("NotBlank");
    doReturn("Username is required.")
        .when(restControllerExceptionHandler).getMissingFieldsMessage(fieldErrors);

    response = restControllerExceptionHandler.handleMissingFields(methodArgumentNotValidException);
    expectedMessage = "Username is required.";
    receivedMessage = response.getBody().getMessage();
    expectedStatus = HttpStatus.BAD_REQUEST;
    receivedStatus = response.getStatusCode();

    assertEquals(expectedMessage, receivedMessage);
    assertEquals(expectedStatus, receivedStatus);
  }

  @Test
  public void handleMissingFields_should_returnCorrectMessage_when_twoFieldsAreMissing() {
    fieldErrors.add(fieldErrorTwo);
    when(methodArgumentNotValidException.getFieldErrors()).thenReturn(fieldErrors);
    when(fieldError.getCode()).thenReturn("NotBlank");
    when(fieldErrorTwo.getCode()).thenReturn("NotBlank");
    doReturn("Username and password are required.")
        .when(restControllerExceptionHandler).getMissingFieldsMessage(fieldErrors);

    response = restControllerExceptionHandler.handleMissingFields(methodArgumentNotValidException);
    expectedMessage = "Username and password are required.";
    receivedMessage = response.getBody().getMessage();
    expectedStatus = HttpStatus.BAD_REQUEST;
    receivedStatus = response.getStatusCode();

    assertEquals(expectedMessage, receivedMessage);
    assertEquals(expectedStatus, receivedStatus);
  }

  @Test
  public void handleMissingFields_should_returnCorrectMessage_when_multipleFieldsAreMissing() {
    fieldErrors.add(fieldErrorTwo);
    fieldErrors.add(fieldErrorThree);
    when(methodArgumentNotValidException.getFieldErrors()).thenReturn(fieldErrors);
    when(fieldError.getCode()).thenReturn("NotBlank");
    when(fieldErrorTwo.getCode()).thenReturn("NotBlank");
    when(fieldErrorThree.getCode()).thenReturn("NotBlank");
    doReturn("Username, password and email are required.")
        .when(restControllerExceptionHandler).getMissingFieldsMessage(fieldErrors);

    response = restControllerExceptionHandler.handleMissingFields(methodArgumentNotValidException);
    expectedMessage = "Username, password and email are required.";
    receivedMessage = response.getBody().getMessage();
    expectedStatus = HttpStatus.BAD_REQUEST;
    receivedStatus = response.getStatusCode();

    assertEquals(expectedMessage, receivedMessage);
    assertEquals(expectedStatus, receivedStatus);
  }

  @Test
  public void handleMissingFields_should_returnCorrectMessage_when_sizeConstraintIsViolated() {
    when(methodArgumentNotValidException.getFieldErrors()).thenReturn(fieldErrors);
    when(fieldError.getCode()).thenReturn("Size");
    when(fieldError.getDefaultMessage()).thenReturn("Password must be at least 8 characters.");

    response = restControllerExceptionHandler.handleMissingFields(methodArgumentNotValidException);
    expectedMessage = "Password must be at least 8 characters.";
    receivedMessage = response.getBody().getMessage();
    expectedStatus = HttpStatus.BAD_REQUEST;
    receivedStatus = response.getStatusCode();

    assertEquals(expectedMessage, receivedMessage);
    assertEquals(expectedStatus, receivedStatus);
  }

  @Test
  public void handleMissingRequestBody_should_returnCorrectMessage() {
    response = restControllerExceptionHandler.handleMissingRequestBody();
    expectedMessage = "Request body is empty.";
    receivedMessage = response.getBody().getMessage();
    expectedStatus = HttpStatus.BAD_REQUEST;
    receivedStatus = response.getStatusCode();

    assertEquals(expectedMessage, receivedMessage);
    assertEquals(expectedStatus, receivedStatus);
  }

}