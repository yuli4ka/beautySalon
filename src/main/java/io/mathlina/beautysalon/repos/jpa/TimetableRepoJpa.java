package io.mathlina.beautysalon.repos.jpa;

import io.mathlina.beautysalon.domain.Timetable;
import io.mathlina.beautysalon.repos.TimetableRepo;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;

@Qualifier("timetableRepoJpa")
@Primary
public interface TimetableRepoJpa extends JpaRepository<Timetable, Long>, TimetableRepo {

}
