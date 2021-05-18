package io.mathlina.beautysalon.repos.jdbc;

import io.mathlina.beautysalon.domain.Role;
import io.mathlina.beautysalon.model.UserModel;
import io.mathlina.beautysalon.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

// TODO: deal with role on finds

@Repository
@Qualifier("userRepoJdbc")
public class JdbcUserRepositoryImpl implements UserRepository {

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedJdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        namedJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    static class UseRowMapper implements RowMapper<UserModel> {
        @Override
        public UserModel mapRow(ResultSet rs, int i) throws SQLException {
            return UserModel.builder()
                    .id(rs.getLong("user_id"))
                    .username(rs.getString("username"))
                    .name(rs.getString("name"))
                    .surname(rs.getString("surname"))
                    .password(rs.getString("password"))
                    .active(rs.getBoolean("active"))
                    .email(rs.getString("email"))
                    .activationCode(rs.getString("activation_code"))
                    .build();
        }
    }

    @Override
    public Optional<UserModel> findByUsername(String username) {
        List<UserModel> userModels = jdbcTemplate.query(
                "select * from usr where username = ?",
                new UseRowMapper(),
                username);

        if (userModels.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(userModels.get(0));
        }
    }

    @Override
    public Optional<UserModel> findByEmail(String email) {
        List<UserModel> userModels = jdbcTemplate.query(
                "select * from usr where email = ?",
                new UseRowMapper(),
                email);

        if (userModels.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(userModels.get(0));
        }
    }

    @Override
    public Optional<UserModel> findByActivationCode(String code) {
        List<UserModel> userModels = jdbcTemplate.query(
                "select * from usr where activation_code = ?",
                new UseRowMapper(),
                code);

        if (userModels.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(userModels.get(0));
        }
    }

    @Override
    public void save(UserModel user) {
        jdbcTemplate.update(
                "insert into usr (activation_code, email, password, username, name, surname, active) " +
                        "values (?, ?, ?, ?, ?, ?, ?)",
                user.getActivationCode(),
                user.getEmail(),
                user.getPassword(),
                user.getUsername(),
                user.getName(),
                user.getSurname(),
                user.getActive()
        );
        UserModel userModel = findByUsername(user.getUsername()).get();
        Long id = userModel.getId();
        for (Role role : user.getRole()) {
            jdbcTemplate.update("insert into user_role (user_id, role) " +
                            "values (?, ?)",
                    id,
                    role.toString()
            );
        }
    }
}
