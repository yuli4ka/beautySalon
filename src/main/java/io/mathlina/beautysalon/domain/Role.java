package io.mathlina.beautysalon.domain;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
  CLIENT, ADMIN, MASTER;

  @Override
  public String getAuthority() {
    return name();
  }
}
