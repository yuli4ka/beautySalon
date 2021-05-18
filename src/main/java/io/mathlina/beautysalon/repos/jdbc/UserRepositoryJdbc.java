package io.mathlina.beautysalon.repos.jdbc;

import io.mathlina.beautysalon.model.UserModel;
import io.mathlina.beautysalon.repos.UserRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

// TODO: deal with role and active

@Repository
@Qualifier("userRepoJdbc")
public class UserRepositoryJdbc implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserRepositoryJdbc(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    static class UseRowMapper implements RowMapper<UserModel> {
        @Override
        public UserModel mapRow(ResultSet rs, int i) throws SQLException {
            return UserModel.builder()
                    .id(rs.getLong("id"))
                    .username(rs.getString("username"))
                    .name(rs.getString("name"))
                    .surname(rs.getString("surname"))
                    .password(rs.getString("password"))
                    .email(rs.getString("email"))
                    .activationCode(rs.getString("activation_code"))
                    .build();
        }
    }

    @Override
    public Optional<UserModel> findByUsername(String username) {
        return Optional.ofNullable(jdbcTemplate.queryForObject(
                "select * from usr where username = ?",
                new UseRowMapper(),
                username));
    }

    @Override
    public Optional<UserModel> findByEmail(String email) {
        return Optional.ofNullable(jdbcTemplate.queryForObject(
                "select * from usr where email = ?",
                new UseRowMapper(),
                email));
    }

    @Override
    public Optional<UserModel> findByActivationCode(String code) {
        return Optional.ofNullable(jdbcTemplate.queryForObject(
                "select * from usr where activation_code = ?",
                new UseRowMapper(),
                code));
    }

    @Override
    public void save(UserModel user) {
        jdbcTemplate.update(
                "insert into usr (activation_code, email, password, username, name, surname) " +
                        "values (?, ?, ?, ?, ?, ?)",
                user.getActivationCode(),
                user.getEmail(),
                user.getPassword(),
                user.getUsername(),
                user.getName(),
                user.getSurname()
        );
    }
}
