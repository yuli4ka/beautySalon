package io.mathlina.beautysalon.service.impl;

import io.mathlina.beautysalon.domain.Timetable;
import io.mathlina.beautysalon.repos.TimetableRepository;
import io.mathlina.beautysalon.service.AppointmentService;
import org.springframework.stereotype.Service;

@Service
public class AppointmentServiceImpl implements AppointmentService {

  private final TimetableRepository timetableRepository;

  public AppointmentServiceImpl(TimetableRepository timetableRepository) {
    this.timetableRepository = timetableRepository;
  }

  @Override
  public void appoint(Timetable timetable) {
    timetableRepository.save(timetable);
  }
}
