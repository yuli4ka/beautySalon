package io.mathlina.beautysalon.repos.jpa;

import io.mathlina.beautysalon.domain.User;
import io.mathlina.beautysalon.model.UserModel;
import io.mathlina.beautysalon.model.mapper.Mapper;
import io.mathlina.beautysalon.repos.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class JpaUserRepository implements UserRepository {

    private final Mapper mapper;

    @PersistenceContext
    private EntityManager entityManager;

    //TODO: check if there is result
    @Override
    public Optional<UserModel> findByUsername(String username) {
        Query query = this.entityManager.createQuery(
                "SELECT user FROM User user " +
                        "WHERE user.username = :username");
        query.setParameter("username", username);
        User user = (User) query.getSingleResult();

        return Optional.ofNullable(mapper.map(user, UserModel.class));
    }

    //TODO: check if there is result
    @Override
    public Optional<UserModel> findByEmail(String email) {
        Query query = this.entityManager.createQuery(
                "SELECT user FROM User user " +
                        "WHERE user.email = :email");
        query.setParameter("email", email);
        User user = (User) query.getSingleResult();

        return Optional.ofNullable(mapper.map(user, UserModel.class));
    }

    //TODO: check if there is result
    @Override
    public Optional<UserModel> findByActivationCode(String code) {
        Query query = this.entityManager.createQuery(
                "SELECT user FROM User user " +
                        "WHERE user.activationCode = :code");
        query.setParameter("code", code);
        User user = (User) query.getSingleResult();

        return Optional.ofNullable(mapper.map(user, UserModel.class));
    }

    @Override
    public void save(UserModel userModel) {
        User user = mapper.map(userModel, User.class);
        if (user.getId() == null) {
            this.entityManager.persist(user);
        } else {
            this.entityManager.merge(user);
        }
    }

}
