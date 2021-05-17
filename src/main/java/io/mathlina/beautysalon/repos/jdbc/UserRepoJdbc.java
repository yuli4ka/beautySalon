package io.mathlina.beautysalon.repos.jdbc;

import io.mathlina.beautysalon.domain.Role;
import io.mathlina.beautysalon.domain.User;
import io.mathlina.beautysalon.repos.UserRepo;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

@Qualifier("userRepoJdbc")
public interface UserRepoJdbc extends CrudRepository<User, Long>, UserRepo {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    Optional<User> findByActivationCode(String code);

    List<User> findAllByRoleContaining(Role role, Pageable pageable);

    User save(User user);
}
