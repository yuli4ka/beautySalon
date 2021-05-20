package io.mathlina.beautysalon.repos;

import io.mathlina.beautysalon.domain.RequestContext;
import io.mathlina.beautysalon.model.UserModel;
import io.mathlina.beautysalon.repos.jdbc.JdbcUserRepository;
import io.mathlina.beautysalon.repos.jpa.JpaUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@RequiredArgsConstructor
@Repository
@Primary
public class UserRepositoryStrategy implements UserRepository {

    private final RequestContext requestContext;
    private final JdbcUserRepository jdbcUserRepository;
    private final JpaUserRepository jpaUserRepository;

    @Override
    public Optional<UserModel> findByUsername(String username) {
        String switcher = requestContext.getRepositorySwitch();
        if ("jdbc".equals(switcher)) {
            return jdbcUserRepository.findByUsername(username);
        } else {
            return jpaUserRepository.findByUsername(username);
        }
    }

    @Override
    public Optional<UserModel> findByEmail(String email) {
        String switcher = requestContext.getRepositorySwitch();
        if ("jdbc".equals(switcher)) {
            return jdbcUserRepository.findByEmail(email);
        } else {
            return jpaUserRepository.findByEmail(email);
        }
    }

    @Override
    public Optional<UserModel> findByActivationCode(String code) {
        String switcher = requestContext.getRepositorySwitch();
        if ("jdbc".equals(switcher)) {
            return jdbcUserRepository.findByActivationCode(code);
        } else {
            return jpaUserRepository.findByActivationCode(code);
        }
    }

    @Override
    public void save(UserModel user) {
        String switcher = requestContext.getRepositorySwitch();
        if ("jdbc".equals(switcher)) {
            jdbcUserRepository.save(user);
        } else {
            jpaUserRepository.save(user);
        }
    }
}
