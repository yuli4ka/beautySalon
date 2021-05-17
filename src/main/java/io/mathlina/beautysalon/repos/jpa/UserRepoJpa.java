package io.mathlina.beautysalon.repos.jpa;

import io.mathlina.beautysalon.domain.Role;
import io.mathlina.beautysalon.domain.User;
import io.mathlina.beautysalon.repos.UserRepo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepoJpa extends JpaRepository<User, Long>, UserRepo {

  Optional<User> findByUsername(String username);

  Optional<User> findByEmail(String email);

  Optional<User> findByActivationCode(String code);

  List<User> findAllByRoleContaining(Role role, Pageable pageable);

}
