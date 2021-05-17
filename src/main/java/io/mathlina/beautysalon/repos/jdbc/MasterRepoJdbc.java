package io.mathlina.beautysalon.repos.jdbc;

import io.mathlina.beautysalon.domain.Master;
import io.mathlina.beautysalon.repos.MasterRepo;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

@Qualifier("masterRepoJdbc")
public interface MasterRepoJdbc extends CrudRepository<Master, Long>, MasterRepo {

    Page<Master> findAllByNameContainingOrSurnameContaining(String name, String surname,
                                                            Pageable pageable);

    Page<Master> findAll(Pageable pageable);

    List<Master> findAll();

    Master save(Master master);
}
