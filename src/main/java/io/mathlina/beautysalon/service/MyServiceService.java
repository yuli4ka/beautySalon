package io.mathlina.beautysalon.service;

import io.mathlina.beautysalon.dto.ServiceDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MyServiceService {

  Page<ServiceDto> findAll(Pageable pageable);

}
