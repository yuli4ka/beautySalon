package io.mathlina.beautysalon.service;

import io.mathlina.beautysalon.model.UserModel;

public interface MailService {

  void sendActivationCode(UserModel user);

}
