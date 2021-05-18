package io.mathlina.beautysalon.repos;

import io.mathlina.beautysalon.domain.Role;
import io.mathlina.beautysalon.domain.User;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    Optional<User> findByActivationCode(String code);

    User save(User user);
}
