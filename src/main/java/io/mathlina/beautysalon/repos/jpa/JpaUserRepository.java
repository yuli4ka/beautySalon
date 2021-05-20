package io.mathlina.beautysalon.repos.jpa;

import io.mathlina.beautysalon.domain.User;
import io.mathlina.beautysalon.model.UserModel;
import io.mathlina.beautysalon.model.mapper.Mapper;
import io.mathlina.beautysalon.repos.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Optional;

@Transactional
@RequiredArgsConstructor
@Repository
public class JpaUserRepository implements UserRepository {

    private final Mapper mapper;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<UserModel> findByUsername(String username) {
        Query query = this.entityManager.createQuery(
                "SELECT user FROM User user " +
                        "WHERE user.username = :username");
        query.setParameter("username", username);

        if (query.getResultList().isEmpty()) {
            return Optional.empty();
        }
        User user = (User) query.getSingleResult();
        return Optional.of(mapper.map(user, UserModel.class));
    }

    @Override
    public Optional<UserModel> findByEmail(String email) {
        Query query = this.entityManager.createQuery(
                "SELECT user FROM User user " +
                        "WHERE user.email = :email");
        query.setParameter("email", email);

        if (query.getResultList().isEmpty()) {
            return Optional.empty();
        }
        User user = (User) query.getSingleResult();
        return Optional.ofNullable(mapper.map(user, UserModel.class));
    }

    @Override
    public Optional<UserModel> findByActivationCode(String code) {
        Query query = this.entityManager.createQuery(
                "SELECT user FROM User user " +
                        "WHERE user.activationCode = :code");
        query.setParameter("code", code);

        if (query.getResultList().isEmpty()) {
            return Optional.empty();
        }
        User user = (User) query.getSingleResult();
        return Optional.ofNullable(mapper.map(user, UserModel.class));
    }

    //TODO: check role saving
    //TODO: make work
    //Duplicate entry '37' for key 'usr.PRIMARY'
    //org.springframework.dao.DataIntegrityViolationException: could not execute statement; SQL [n/a]; constraint [usr.PRIMARY]; nested exception is org.hibernate.exception.ConstraintViolationException: could not execute statement
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
