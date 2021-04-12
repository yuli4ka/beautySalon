package io.mathlina.beautysalon.repos;

import io.mathlina.beautysalon.domain.Role;
import io.mathlina.beautysalon.domain.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {

  Optional<User> findByUsername(String username);

  Optional<User> findByEmail(String email);

  Optional<User> findByActivationCode(String code);

  List<User> findAllByRoleContaining(Role role, Pageable pageable);

}
