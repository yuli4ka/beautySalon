package io.mathlina.beautysalon.repos;

import io.mathlina.beautysalon.domain.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface MyServiceRepository {

    Page<Service> findAll(Pageable pageable);

    List<Service> findAll();
}
