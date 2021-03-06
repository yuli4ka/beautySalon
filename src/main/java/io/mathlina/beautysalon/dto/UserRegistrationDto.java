package io.mathlina.beautysalon.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationDto {

  @Pattern(regexp = "^[A-Za-z0-9_]{6,30}$", message = "{username.error}")
  String username;

  @Pattern(regexp = "^\\p{L}{0,30}$", message = "{name.error}")
  private String name;

  @Pattern(regexp = "^\\p{L}{0,30}$", message = "{surname.error}")
  private String surname;

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
