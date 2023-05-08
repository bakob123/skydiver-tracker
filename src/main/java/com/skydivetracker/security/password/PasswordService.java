package com.skydivetracker.security.password;

public interface PasswordService {

  String encode(String plainTextPassword);

  boolean isMatchingPassword(String plainTextPassword, String encodedPassword);

}
