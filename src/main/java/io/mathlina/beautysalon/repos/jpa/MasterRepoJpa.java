package io.mathlina.beautysalon.repos.jpa;

import io.mathlina.beautysalon.domain.Master;
import io.mathlina.beautysalon.repos.MasterRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MasterRepoJpa extends JpaRepository<Master, Long>, MasterRepo {

  Page<Master> findAllByNameContainingOrSurnameContaining(String name, String surname,
      Pageable pageable);

}
