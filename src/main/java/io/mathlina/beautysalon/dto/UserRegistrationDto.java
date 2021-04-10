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

@Data
@NoArgsConstructor
public class UserRegistrationDto {

  //TODO: own annotation for validation
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  Long id;

  @Pattern(regexp = "^[A-Za-z0-9_]{6,30}$", message = "{username.error}")
  String username;

  @NotBlank(message = "{this.field.is.mandatory}")
  @Size(max=255, message = "{the.maximum.length.of.the.field.is.255.characters}")
  String password;

  @NotBlank(message = "{this.field.is.mandatory}")
  @Size(max=255, message = "{the.maximum.length.of.the.field.is.255.characters}")
  String passwordConfirm;

  @NotBlank(message = "{this.field.is.mandatory}")
  @Size(max = 255, message = "{the.maximum.length.of.the.field.is.255.characters}")
  @Email(message = "{email.is.not.valid}")
  String email;

}
