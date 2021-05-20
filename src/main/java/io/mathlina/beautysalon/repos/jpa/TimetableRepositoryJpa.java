package io.mathlina.beautysalon.repos.jpa;

import io.mathlina.beautysalon.domain.Timetable;
import io.mathlina.beautysalon.model.TimetableModel;
import io.mathlina.beautysalon.model.mapper.Mapper;
import io.mathlina.beautysalon.repos.TimetableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class TimetableRepositoryJpa implements TimetableRepository {

    @Autowired
    private Mapper mapper;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(TimetableModel timetableModel) {
        Timetable timetable = mapper.map(timetableModel, Timetable.class);
        if (timetable.getId() == null) {
            this.entityManager.persist(timetable);
        } else {
            this.entityManager.merge(timetable);
        }
    }
}
