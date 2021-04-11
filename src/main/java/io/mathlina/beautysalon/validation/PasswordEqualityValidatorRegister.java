package io.mathlina.beautysalon.validation;

import io.mathlina.beautysalon.dto.UserRegistrationDto;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class PasswordEqualityValidatorRegister implements Validator {

  @Override
  public boolean supports(Class<?> aClass) {
    return UserRegistrationDto.class.equals(aClass);
  }

  @Override
  public void validate(Object o, Errors errors) {
    UserRegistrationDto userDTO = (UserRegistrationDto) o;
    if (!userDTO.getPassword().equals(userDTO.getPasswordConfirm())) {
      errors.rejectValue("passwordConfirm", "passwords.not.equal", "Passwords not equal");
    }
  }
}
