package io.mathlina.beautysalon.validation;

import io.mathlina.beautysalon.dto.UserRegistrationDto;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class PasswordEqualityValidator implements Validator {

  @Override
  public boolean supports(Class<?> aClass) {
    return UserRegistrationDto.class.equals(aClass);
  }

  //TODO: text constant
  @Override
  public void validate(Object o, Errors errors) {
    UserRegistrationDto userDTO = (UserRegistrationDto) o;
    if (!userDTO.getPassword().equals(userDTO.getPasswordConfirm())) {
      errors.rejectValue(null, "passwordsError", "Passwords not equal");
    }
  }
}
