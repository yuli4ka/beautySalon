package io.mathlina.beautysalon.repos;

import io.mathlina.beautysalon.domain.Service;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyServiceRepo extends JpaRepository<Service, Long> {

  List<Service> findAll();

}
