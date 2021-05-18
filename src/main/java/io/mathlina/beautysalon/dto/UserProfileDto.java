package io.mathlina.beautysalon.dto;

import io.mathlina.beautysalon.model.UserModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileDto {

  public UserProfileDto(UserModel user) {
    this.username = user.getUsername();
    this.email = user.getEmail();
    this.name = user.getName();
    this.surname = user.getSurname();
  }

  String username;

  @Pattern(regexp = "^\\p{L}{0,30}$", message = "{name.error}")
  private String name;

  @Pattern(regexp = "^\\p{L}{0,30}$", message = "{surname.error}")
  private String surname;

  @Size(max = 255, message = "{the.maximum.length.of.the.field.is.255.characters}")
  String password;

  @Size(max = 255, message = "{the.maximum.length.of.the.field.is.255.characters}")
  String passwordConfirm;

  @NotBlank(message = "{this.field.is.mandatory}")
  @Size(max = 255, message = "{the.maximum.length.of.the.field.is.255.characters}")
  @Email(message = "{email.is.not.valid}")
  String email;

}
