package io.mathlina.beautysalon.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

@Data
@NoArgsConstructor
public class UserRegistrationDto {

  //TODO: own annotation for validation
  //TODO: text constants
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  Long id;

  @Pattern(regexp = "^[A-Za-z0-9_]{6,30}$", message = "Username error")
  String username;

  @NotBlank(message = "Password is mandatory")
  @Size(max=255, message = "Password is too long")
  String password;

  @NotBlank(message = "Password is mandatory")
  @Size(max=255, message = "Password is too long")
  String passwordConfirm;

  @NotBlank(message = "Email is mandatory")
  @Size(max = 255, message = "Email is too long")
  @Email(message = "Email is not valid")
  String email;

}
