package io.mathlina.beautysalon.service;

import io.mathlina.beautysalon.domain.Service;
import org.springframework.data.domain.Page;

public interface MyServiceService {

  Page<Service> findAllPaginated(int currentPage, int pageSize);

}
