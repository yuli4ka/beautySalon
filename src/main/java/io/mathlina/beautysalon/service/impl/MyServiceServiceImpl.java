package io.mathlina.beautysalon.service.impl;

import io.mathlina.beautysalon.domain.Service;
import io.mathlina.beautysalon.repos.MyServiceRepo;
import io.mathlina.beautysalon.service.MyServiceService;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@org.springframework.stereotype.Service
public class MyServiceServiceImpl implements MyServiceService {

  private final MyServiceRepo myServiceRepo;

  @Autowired
  public MyServiceServiceImpl(MyServiceRepo myServiceRepo) {
    this.myServiceRepo = myServiceRepo;
  }

  public Page<Service> findAllPaginated(Pageable pageable) {
    List<Service> services = myServiceRepo.findAll();

    int pageSize = pageable.getPageSize();
    int currentPage = pageable.getPageNumber();
    int startIndex = currentPage * pageSize;

    List<Service> list;

    if (services.size() < startIndex) {
      list = Collections.emptyList();
    } else {
      int lastIndex = Math.min(startIndex + pageSize, services.size());
      list = services.subList(startIndex, lastIndex);
    }

    return new PageImpl<>(list, PageRequest.of(currentPage, pageSize), services.size());
  }

}
