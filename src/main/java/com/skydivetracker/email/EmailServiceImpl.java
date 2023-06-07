package com.skydivetracker.email;

import com.skydivetracker.email.models.EmailData;
import com.skydivetracker.skydivers.models.Skydiver;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class EmailServiceImpl implements EmailService {

  @Autowired
  private SpringTemplateEngine templateEngine;
  @Autowired
  private JavaMailSender javaMailSender;
  @Value("${spring.mail.username}")
  private String sender;
  private String host;
  @Value("${server.port}")
  private String port;
  @Value("${server.protocol}")
  private String protocol;

  public EmailServiceImpl() {
    host = InetAddress.getLoopbackAddress().getHostAddress();
  }

  @Override

  public MimeMessage send(MimeMessage message) { //TODO: exception handling, TEST
    javaMailSender.send(message);
    return message;
  }

  @Override
  public MimeMessage assembleMessage(EmailData emailData) throws MessagingException { //TODO: test
    MimeMessage message = javaMailSender.createMimeMessage();
    MimeMessageHelper helper = generateHelper(message);
    helper.setFrom(sender);
    helper.setTo(emailData.getSkydiver().getEmail());
    helper.setSubject(emailData.getSubject());
    Multipart multipartContent = new MimeMultipart("alternative");
    addBodyParts(multipartContent, emailData.getBodyParts());
    message.setContent(multipartContent);
    return message;
  }

  @Override
  public MimeBodyPart createMessage(String templateName, String type,
                                    Map<String, Object> contextVariables) throws MessagingException { //TODO: test
    Context context = new Context();
    context.setVariables(contextVariables);
    String text = templateEngine.process(templateName, context);
    MimeBodyPart bodyPart = new MimeBodyPart();
    bodyPart.setContent(text, type);
    return bodyPart;
  }

  @Override
  public Map<String, Object> createVerificationContextMap(Skydiver skydiver) {
    return new HashMap<String, Object>() {{
      put("name", String.format("%s %s", skydiver.getFirstName(), skydiver.getLastName()));
      put("protocol", protocol);
      put("host", host);
      put("port", port);
      put("token", skydiver.getVerificationToken());
    }};
  }

  @Override
  public MimeMessage createVerificationMail(Skydiver skydiver) throws MessagingException { //TODO: fix template conversion to plain text
    Map<String, Object> contextMap = createVerificationContextMap(skydiver);
    MimeBodyPart plainText = createMessage("verification.html", "text/plain", contextMap);
    MimeBodyPart htmlText = createMessage("verification.html", "text/html", contextMap);
    EmailData emailData = new EmailData
        (
            skydiver,
            Arrays.asList(plainText, htmlText),
            "Complete your registration!"
        );
    return assembleMessage(emailData);
  }

  private MimeMessageHelper generateHelper(MimeMessage message) throws MessagingException {
    if (message == null) return null;
    return new MimeMessageHelper
        (
            message,
            MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
            StandardCharsets.UTF_8.name()
        );
  }

  private void addBodyParts(Multipart multipart, List<MimeBodyPart> bodyParts) throws MessagingException {
    for (MimeBodyPart bodyPart : bodyParts) {
      multipart.addBodyPart(bodyPart);
    }
  }

}
