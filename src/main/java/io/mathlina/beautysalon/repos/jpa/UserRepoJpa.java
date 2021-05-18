package io.mathlina.beautysalon.repos.jpa;

import io.mathlina.beautysalon.domain.User;
import io.mathlina.beautysalon.repos.UserRepo;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@Qualifier("userRepoJpa")
@Primary
public interface UserRepoJpa extends JpaRepository<User, Long>, UserRepo {

  Optional<User> findByUsername(String username);

  Optional<User> findByEmail(String email);

  Optional<User> findByActivationCode(String code);

}
