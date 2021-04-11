package io.mathlina.beautysalon.validation;

import io.mathlina.beautysalon.dto.UserProfileDto;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class PasswordEqualityValidatorProfile implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return UserProfileDto.class.equals(clazz);
  }

  @Override
  public void validate(Object o, Errors errors) {
    UserProfileDto userDTO = (UserProfileDto) o;
    if (!userDTO.getPassword().equals(userDTO.getPasswordConfirm())) {
      errors.rejectValue("passwordConfirm", "passwords.not.equal", "Passwords not equal");
    }
  }
}
