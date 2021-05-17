package io.mathlina.beautysalon.repos.jdbc;

import io.mathlina.beautysalon.domain.Service;
import io.mathlina.beautysalon.repos.MyServiceRepo;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

@Qualifier("myServiceRepoJdbc")
public interface MyServiceRepoJdbc extends CrudRepository<Service, Long>, MyServiceRepo {

    Page<Service> findAll(Pageable pageable);

    List<Service> findAll();
}
