package io.mathlina.beautysalon.service.impl;

import io.mathlina.beautysalon.model.TimetableModel;
import io.mathlina.beautysalon.repos.TimetableRepository;
import io.mathlina.beautysalon.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AppointmentServiceImpl implements AppointmentService {

  private final TimetableRepository timetableRepository;

  @Override
  public void appoint(TimetableModel timetableModel) {
    timetableRepository.save(timetableModel);
  }
}
