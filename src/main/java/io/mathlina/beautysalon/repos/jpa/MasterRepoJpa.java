package io.mathlina.beautysalon.repos.jpa;

import io.mathlina.beautysalon.domain.Master;
import io.mathlina.beautysalon.repos.MasterRepo;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

@Qualifier("masterRepoJpa")
@Primary
public interface MasterRepoJpa extends JpaRepository<Master, Long>, MasterRepo {

  Page<Master> findAllByNameContainingOrSurnameContaining(String name, String surname,
      Pageable pageable);

}
