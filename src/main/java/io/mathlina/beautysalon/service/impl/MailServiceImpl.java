package io.mathlina.beautysalon.service.impl;

import io.mathlina.beautysalon.model.UserModel;
import io.mathlina.beautysalon.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MailServiceImpl implements MailService {

  @Value("${spring.mail.username}")
  private String username;

  @Value("${server.port}")
  private String port;

  //TODO: get @Value host from application.properties
  //TODO: get @Value salon name from application.properties

  private final JavaMailSender mailSender;

  private void send(String emailTo, String subject, String message) {
    SimpleMailMessage mailMessage = new SimpleMailMessage();

    mailMessage.setFrom(username);
    mailMessage.setTo(emailTo);
    mailMessage.setSubject(subject);
    mailMessage.setText(message);

    mailSender.send(mailMessage);
  }

  //TODO: send thymeleaf template
  public void sendActivationCode(UserModel user) {
    StringBuilder builder = new StringBuilder();
    builder
        .append("Hello, ")
        .append(user.getUsername())
        .append("!\n. Welcome to my beauty salon Chamomile! ")
        .append("Please, visit next link to activate your profile:\n")
        .append("http://localhost:")
        .append(port)
        .append("/activate/")
        .append(user.getActivationCode());

    send(user.getEmail(), "activation.code", builder.toString());
  }

}
