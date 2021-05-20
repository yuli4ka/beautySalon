package io.mathlina.beautysalon.repos;

import io.mathlina.beautysalon.model.ServiceModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MyServiceRepository {

    //TODO: Make return Optional
    ServiceModel findById(Long id);

    Page<ServiceModel> findAll(Pageable pageable);

    List<ServiceModel> findAll();
}
