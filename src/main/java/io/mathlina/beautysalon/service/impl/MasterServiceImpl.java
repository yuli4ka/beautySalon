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
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
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

  public Page<MasterDto> findAll(Pageable pageable) {
    return masterRepo.findAll(pageable).map(MasterDto::new);
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

  @Override
  public List<ServiceDto> findMasterServicesLike(Master master, String filter) {
    if (Objects.isNull(filter) || filter.equals("")) {
      return findMasterServices(master);
    } else {
      Locale currentLocale = LocaleContextHolder.getLocale();
      Collator collator = Collator.getInstance(currentLocale);

      return master.getServices().stream()
          .map(service -> new ServiceDto(service, currentLocale.toString()))
          .filter(serviceDto -> serviceDto.getName()
              .toLowerCase(currentLocale).contains(filter.toLowerCase(currentLocale)))
          .sorted((s1, s2) -> collator.compare(s1.getName(), s2.getName()))
          .collect(Collectors.toList());
    }
  }

  @Override
  public Page<MasterDto> findAllLike(String filter, Pageable pageable) {
    if (Objects.isNull(filter) || filter.equals("")) {
      return findAll(pageable);
    } else {
      return masterRepo.findAllByNameContainingOrSurnameContaining(filter, filter, pageable)
          .map(MasterDto::new);
    }
  }

}
