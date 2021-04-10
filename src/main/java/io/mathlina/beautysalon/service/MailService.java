package io.mathlina.beautysalon.service;

import io.mathlina.beautysalon.domain.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

  @Value("${spring.mail.username}")
  private String username;

  private final JavaMailSender mailSender;

  public MailService(JavaMailSender mailSender) {
    this.mailSender = mailSender;
  }

  public void send(String emailTo, String subject, String message) {
    SimpleMailMessage mailMessage = new SimpleMailMessage();

    mailMessage.setFrom(username);
    mailMessage.setTo(emailTo);
    mailMessage.setSubject(subject);
    mailMessage.setText(message);

    mailSender.send(mailMessage);
  }

  public void sendActivationCode(User user) {
    String message = String.format(
        "Hello, %s \n" + "Welcome to my beauty salon Chamomile!. "
            + "Please, visit next link to activate your profile: "
            + "http://localhost:8090/activate/%s",
        user.getUsername(),
        user.getActivationCode()
    );

    send(user.getEmail(), "activation.code", message);
  }

}
