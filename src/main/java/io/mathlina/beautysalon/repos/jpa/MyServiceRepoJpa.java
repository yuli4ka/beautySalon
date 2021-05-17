package io.mathlina.beautysalon.repos.jpa;

import io.mathlina.beautysalon.domain.Service;
import io.mathlina.beautysalon.repos.MyServiceRepo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyServiceRepoJpa extends JpaRepository<Service, Long>, MyServiceRepo {

}
