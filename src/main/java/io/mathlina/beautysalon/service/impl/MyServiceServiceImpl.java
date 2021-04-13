package io.mathlina.beautysalon.service.impl;

import io.mathlina.beautysalon.domain.Service;
import io.mathlina.beautysalon.dto.MasterDto;
import io.mathlina.beautysalon.dto.ServiceDto;
import io.mathlina.beautysalon.repos.MyServiceRepo;
import io.mathlina.beautysalon.service.MyServiceService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@org.springframework.stereotype.Service
public class MyServiceServiceImpl implements MyServiceService {

  private final MyServiceRepo myServiceRepo;

  @Autowired
  public MyServiceServiceImpl(MyServiceRepo myServiceRepo) {
    this.myServiceRepo = myServiceRepo;
  }

  public Page<ServiceDto> findAll(Pageable pageable) {
    return myServiceRepo.findAll(pageable)
        .map(service -> new ServiceDto(service, LocaleContextHolder.getLocale().toString()));
  }

  @Override
  public List<MasterDto> findServiceMasters(Service service) {
    return service.getMasters().stream()
        .map(MasterDto::new)
        .collect(Collectors.toList());
  }

}
