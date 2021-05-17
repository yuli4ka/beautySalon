package io.mathlina.beautysalon.repos.jpa;

import io.mathlina.beautysalon.domain.Timetable;
import io.mathlina.beautysalon.repos.TimetableRepo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimetableRepoJpa extends JpaRepository<Timetable, Long>, TimetableRepo {

}
