package io.mathlina.beautysalon.repos;

import io.mathlina.beautysalon.domain.Master;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MasterRepo extends JpaRepository<Master, Long> {

  Page<Master> findAllByNameContainingOrSurnameContaining(String name, String surname,
      Pageable pageable);

}
