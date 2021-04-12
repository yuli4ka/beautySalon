package io.mathlina.beautysalon.service;

import io.mathlina.beautysalon.domain.Master;
import io.mathlina.beautysalon.dto.MasterDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MasterService {

  Page<MasterDto> findAllPaginated(Pageable pageable);

}
