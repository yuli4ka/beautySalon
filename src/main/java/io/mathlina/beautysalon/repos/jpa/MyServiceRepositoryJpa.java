package io.mathlina.beautysalon.repos.jpa;

import io.mathlina.beautysalon.domain.Service;
import io.mathlina.beautysalon.repos.MyServiceRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;

@Qualifier("myServiceRepoJpa")
@Primary
public interface MyServiceRepositoryJpa extends JpaRepository<Service, Long>, MyServiceRepository {

}
