package io.mathlina.beautysalon.repos.jpa;

import io.mathlina.beautysalon.domain.Service;
import io.mathlina.beautysalon.repos.MyServiceRepo;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;

@Qualifier("myServiceRepoJpa")
@Primary
public interface MyServiceRepoJpa extends JpaRepository<Service, Long>, MyServiceRepo {

}
