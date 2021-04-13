package io.mathlina.beautysalon.service.impl;

import io.mathlina.beautysalon.domain.Timetable;
import io.mathlina.beautysalon.repos.TimetableRepo;
import io.mathlina.beautysalon.service.AppointmentService;
import org.springframework.stereotype.Service;

@Service
public class AppointmentServiceImpl implements AppointmentService {

  private final TimetableRepo timetableRepo;

  public AppointmentServiceImpl(TimetableRepo timetableRepo) {
    this.timetableRepo = timetableRepo;
  }

  @Override
  public void appoint(Timetable timetable) {
    timetableRepo.save(timetable);
  }
}
