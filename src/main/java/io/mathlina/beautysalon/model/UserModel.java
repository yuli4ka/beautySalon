package io.mathlina.beautysalon.model;

import io.mathlina.beautysalon.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class UserModel {

    private Long id;

    private String username;

    private String name;

    private String surname;

    private String password;

    private Boolean active;

    private String email;

    private String activationCode;

    private Set<Role> role;
}
