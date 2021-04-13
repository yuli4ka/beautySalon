package io.mathlina.beautysalon.service.impl;

import io.mathlina.beautysalon.domain.Comment;
import io.mathlina.beautysalon.domain.Master;
import io.mathlina.beautysalon.dto.MasterDto;
import io.mathlina.beautysalon.dto.ServiceDto;
import io.mathlina.beautysalon.repos.CommentRepo;
import io.mathlina.beautysalon.repos.MasterRepo;
import io.mathlina.beautysalon.service.MasterService;
import java.text.Collator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@org.springframework.stereotype.Service
public class MasterServiceImpl implements MasterService {

  private final MasterRepo masterRepo;
  private final CommentRepo commentRepo;

  @Autowired
  public MasterServiceImpl(MasterRepo masterRepo, CommentRepo commentRepo) {
    this.masterRepo = masterRepo;
    this.commentRepo = commentRepo;
  }

  public Page<MasterDto> findAllPaginated(Pageable pageable) {
    Page<Master> masterPage = masterRepo.findAll(pageable);

    List<MasterDto> masterDTOs = masterPage.getContent().stream()
        .map(MasterDto::new)
        .collect(Collectors.toList());

    return new PageImpl<>(masterDTOs, pageable, masterPage.getTotalElements());
  }

  public List<ServiceDto> findMasterServices(Master master) {
    Locale currentLocale = LocaleContextHolder.getLocale();
    Collator collator = Collator.getInstance(currentLocale);
    return master.getServices().stream()
        .map(service -> new ServiceDto(service, currentLocale.toString()))
        .sorted((s1, s2) -> collator.compare(s1.getName(), s2.getName()))
        .collect(Collectors.toList());
  }

  @Override
  public void updateAverageGrade(Master master) {
    double averageGrade = commentRepo.findAllByMaster(master).stream()
        .mapToInt(Comment::getGrade)
        .average()
        .orElse(0);

    master.setGrade(averageGrade);

    masterRepo.save(master);
  }

  //TODO: update for quartz with additional optimizing conditions
  @Override
  public void updateAverageGrades() {
    masterRepo.findAll().forEach(this::updateAverageGrade);
  }

}
