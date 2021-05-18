package io.mathlina.beautysalon.model;

import io.mathlina.beautysalon.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserModel {

    private Long id;

    private String username;

    private String name;

    private String surname;

    private String password;

    private boolean active;

    private String email;

    private String activationCode;

    private Set<Role> role;
}
