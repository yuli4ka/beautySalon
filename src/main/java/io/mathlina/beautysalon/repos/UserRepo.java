package io.mathlina.beautysalon.repos;

import io.mathlina.beautysalon.domain.User;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {

  Page<User> findAll(Pageable pageable);

  Optional<User> findByUsername(String username);

  Optional<User> findByActivationCode(String code);
}
