package io.mathlina.beautysalon.repos;

import io.mathlina.beautysalon.model.MasterModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MasterRepository {

    //TODO: Make return Optional
    MasterModel findById(Long id);

    Page<MasterModel> findAllByNameContainingOrSurnameContaining(String name, String surname,
                                                                 Pageable pageable);

    Page<MasterModel> findAll(Pageable pageable);

    List<MasterModel> findAll();

    void save(MasterModel master);
}
