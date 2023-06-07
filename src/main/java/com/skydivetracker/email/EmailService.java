package com.skydivetracker.email;

import com.skydivetracker.email.models.EmailData;
import com.skydivetracker.skydivers.models.Skydiver;

import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import java.util.Map;

public interface EmailService {
  MimeMessage send(MimeMessage message);

  MimeMessage assembleMessage(EmailData emailData) throws MessagingException;

  MimeBodyPart createMessage(String templateName, String type,
                             Map<String, Object> contextVariables) throws MessagingException;

  Map<String, Object> createVerificationContextMap(Skydiver skydiver);

  MimeMessage createVerificationMail(Skydiver skydiver) throws MessagingException;
}
