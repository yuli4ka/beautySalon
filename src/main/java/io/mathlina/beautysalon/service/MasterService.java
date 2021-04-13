package io.mathlina.beautysalon.service;

import io.mathlina.beautysalon.domain.Master;
import io.mathlina.beautysalon.dto.MasterDto;
import io.mathlina.beautysalon.dto.ServiceDto;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MasterService {

  Page<MasterDto> findAllPaginated(Pageable pageable);

  List<ServiceDto> findMasterServices(Master master);

  void updateAverageGrade(Master master);

  void updateAverageGrades();

  List<ServiceDto> findMasterServicesLike(Master master, String filter);
}
