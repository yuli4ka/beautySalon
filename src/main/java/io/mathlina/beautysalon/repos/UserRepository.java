package io.mathlina.beautysalon.repos;

import io.mathlina.beautysalon.model.UserModel;

import java.util.Optional;

public interface UserRepository {

    Optional<UserModel> findByUsername(String username);

    Optional<UserModel> findByEmail(String email);

    Optional<UserModel> findByActivationCode(String code);

    void save(UserModel user);
}
