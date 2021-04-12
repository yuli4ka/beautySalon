package io.mathlina.beautysalon.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class UserProfileDto {

  String username;

  @Pattern(regexp = "^\\p{L}{0,30}$", message = "{name.error}")
  private String name;

  @Pattern(regexp = "^\\p{L}{0,30}$", message = "{surname.error}")
  private String surname;

  @Size(max=255, message = "{the.maximum.length.of.the.field.is.255.characters}")
  String password;

  @Size(max=255, message = "{the.maximum.length.of.the.field.is.255.characters}")
  String passwordConfirm;

  @NotBlank(message = "{this.field.is.mandatory}")
  @Size(max = 255, message = "{the.maximum.length.of.the.field.is.255.characters}")
  @Email(message = "{email.is.not.valid}")
  String email;

}
