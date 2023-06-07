package com.skydivetracker.email.models;

import com.skydivetracker.skydivers.models.Skydiver;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.mail.internet.MimeBodyPart;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class EmailData {

  private Skydiver skydiver;
  private List<MimeBodyPart> bodyParts;
  private String subject;

  public EmailData() {
    this.bodyParts = new ArrayList<>();
  }

}
