package io.mathlina.beautysalon.service;

import io.mathlina.beautysalon.domain.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MyServiceService {

  Page<Service> findAllPaginated(Pageable pageable);

}
