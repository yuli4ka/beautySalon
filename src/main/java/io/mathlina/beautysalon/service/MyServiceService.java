package io.mathlina.beautysalon.service;

import io.mathlina.beautysalon.dto.MasterDto;
import io.mathlina.beautysalon.dto.ServiceDto;
import io.mathlina.beautysalon.model.ServiceModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface MyServiceService {

    Page<ServiceDto> findAll(Pageable pageable);

    List<MasterDto> findServiceMasters(ServiceModel service);

    List<MasterDto> findServiceMastersLike(ServiceModel service, String filter);

    Page<ServiceDto> findAll(String filter, Pageable pageable);

    //TODO: Optional
    Optional<ServiceModel> findById(Long serviceId);

    void save(ServiceModel serviceModel);
}
