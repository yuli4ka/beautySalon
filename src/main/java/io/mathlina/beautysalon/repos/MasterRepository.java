package io.mathlina.beautysalon.repos;

import io.mathlina.beautysalon.domain.Master;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface MasterRepository {

    Page<Master> findAllByNameContainingOrSurnameContaining(String name, String surname,
                                                            Pageable pageable);

    Page<Master> findAll(Pageable pageable);

    List<Master> findAll();

    Master save(Master master);
}
