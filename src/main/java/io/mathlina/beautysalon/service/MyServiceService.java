package io.mathlina.beautysalon.service;

import io.mathlina.beautysalon.domain.Service;
import io.mathlina.beautysalon.dto.MasterDto;
import io.mathlina.beautysalon.dto.ServiceDto;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MyServiceService {

  Page<ServiceDto> findAll(Pageable pageable);

  List<MasterDto> findServiceMasters(Service service);
}
