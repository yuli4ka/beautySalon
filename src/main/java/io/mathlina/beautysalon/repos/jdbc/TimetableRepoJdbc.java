package io.mathlina.beautysalon.repos.jdbc;

import io.mathlina.beautysalon.domain.Timetable;
import io.mathlina.beautysalon.repos.TimetableRepo;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.CrudRepository;

@Qualifier("timetableRepoJdbc")
public interface TimetableRepoJdbc extends CrudRepository<Timetable, Long>, TimetableRepo {

    Timetable save(Timetable timetable);
}
