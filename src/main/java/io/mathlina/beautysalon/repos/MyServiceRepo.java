package io.mathlina.beautysalon.repos;

import io.mathlina.beautysalon.domain.Service;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyServiceRepo extends JpaRepository<Service, Long> {

}
