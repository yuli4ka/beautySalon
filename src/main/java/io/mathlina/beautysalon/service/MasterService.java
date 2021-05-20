package io.mathlina.beautysalon.service;

import io.mathlina.beautysalon.dto.MasterDto;
import io.mathlina.beautysalon.dto.ServiceDto;
import io.mathlina.beautysalon.model.MasterModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MasterService {

  Page<MasterDto> findAll(Pageable pageable);

  List<ServiceDto> findMasterServices(MasterModel master);

  void updateAverageGrade(MasterModel master);

  void updateAverageGrades();

  List<ServiceDto> findMasterServicesLike(MasterModel master, String filter);

  Page<MasterDto> findAllLike(String filter, Pageable pageable);

  MasterModel findById(Long masterId);
}
