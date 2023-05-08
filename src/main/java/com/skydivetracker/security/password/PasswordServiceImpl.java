package com.skydivetracker.security.password;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PasswordServiceImpl implements PasswordService {

  @Override
  public String encode(String plainTextPassword) {
    return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
  }

  @Override
  public boolean isMatchingPassword(String plainTextPassword, String encodedPassword) {
    return BCrypt.checkpw(plainTextPassword, encodedPassword);
  }

}
