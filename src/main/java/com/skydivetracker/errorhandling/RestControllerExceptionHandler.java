package com.skydivetracker.errorhandling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class RestControllerExceptionHandler {

  private final List<String> commonErrorCodes = new ArrayList<String>() {{
    add("NotNull");
    add("NotBlank");
    add("NotEmpty");
  }};

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<ErrorMessage> handleMissingRequestBody() {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorMessage("Request body is empty."));
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorMessage> handleMissingFields(MethodArgumentNotValidException e) {
    List<FieldError> allErrors = e.getFieldErrors();
    List<FieldError> missingFieldsList = allErrors.stream()
        .filter(error -> commonErrorCodes.contains(error.getCode()))
        .collect(Collectors.toList());
    String message = allErrors.get(0).getDefaultMessage();
    if (!missingFieldsList.isEmpty()) {
      message = getMissingFieldsMessage(missingFieldsList);
    }
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorMessage(message));
  }

  public String getMissingFieldsMessage(List<FieldError> fieldErrors) {
    if (fieldErrors == null || fieldErrors.isEmpty()) return "";

    String suffix;
    String missingFields = fieldErrors.stream()
        .map(FieldError::getField)
        .collect(Collectors.joining(", "));
    if (fieldErrors.size() == 1) {
      suffix = " is required.";
    } else {
      suffix = " are required.";
      int lastDelimiter = missingFields.lastIndexOf(",");
      StringBuilder sb = new StringBuilder(missingFields);
      sb.replace(lastDelimiter, lastDelimiter + 1, " and");
      missingFields = sb.toString();
    }
    String capitalizedLetter = missingFields.substring(0, 1).toUpperCase();
    String remainingLetters = missingFields.substring(1);
    return capitalizedLetter + remainingLetters + suffix;
  }



}
