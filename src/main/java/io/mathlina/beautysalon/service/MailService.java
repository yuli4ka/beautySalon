package io.mathlina.beautysalon.service;

import io.mathlina.beautysalon.domain.User;

public interface MailService {

  void sendActivationCode(User user);

}
