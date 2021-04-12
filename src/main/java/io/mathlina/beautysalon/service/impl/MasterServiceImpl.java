package io.mathlina.beautysalon.service.impl;

import io.mathlina.beautysalon.domain.Master;
import io.mathlina.beautysalon.dto.MasterDto;
import io.mathlina.beautysalon.repos.MasterRepo;
import io.mathlina.beautysalon.service.MasterService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MasterServiceImpl implements MasterService {

  private final MasterRepo masterRepo;

  @Autowired
  public MasterServiceImpl(MasterRepo masterRepo) {
    this.masterRepo = masterRepo;
  }

  public Page<MasterDto> findAllPaginated(Pageable pageable) {
    Page<Master> masterPage = masterRepo.findAll(pageable);
    List<MasterDto> masterDTOs = masterPage.getContent().stream()
        .map(master -> MasterDto.builder()
            .name(master.getName())
            .surname(master.getSurname())
            .grade(master.getGrade())
            .build())
        .collect(Collectors.toList());
    return new PageImpl<>(masterDTOs, pageable, masterPage.getTotalElements());

  }

}
