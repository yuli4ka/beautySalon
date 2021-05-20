package io.mathlina.beautysalon.repos.jpa;

import io.mathlina.beautysalon.domain.Timetable;
import io.mathlina.beautysalon.model.TimetableModel;
import io.mathlina.beautysalon.model.mapper.Mapper;
import io.mathlina.beautysalon.repos.TimetableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Transactional
@RequiredArgsConstructor
@Repository
public class TimetableRepositoryJpa implements TimetableRepository {

    private final Mapper mapper;

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
