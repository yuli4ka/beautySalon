package io.mathlina.beautysalon.service;

import io.mathlina.beautysalon.domain.User;
import io.mathlina.beautysalon.repos.UserRepo;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

  private final UserRepo userRepo;

  @Autowired
  public UserService(UserRepo userRepo) {
    this.userRepo = userRepo;
  }


  @Override
  public UserDetails loadUserByUsername(String s) {
    Optional<User> user = userRepo.findByUsername(s);
    return user.orElseThrow(() -> new UsernameNotFoundException("User not exist!"));
  }
}
