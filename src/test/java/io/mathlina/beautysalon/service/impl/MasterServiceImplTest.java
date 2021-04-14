package io.mathlina.beautysalon.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.mathlina.beautysalon.domain.Comment;
import io.mathlina.beautysalon.domain.Master;
import io.mathlina.beautysalon.domain.Service;
import io.mathlina.beautysalon.dto.MasterDto;
import io.mathlina.beautysalon.dto.ServiceDto;
import io.mathlina.beautysalon.repos.CommentRepo;
import io.mathlina.beautysalon.repos.MasterRepo;
import io.mathlina.beautysalon.service.MasterService;
import java.text.Collator;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest(classes = MasterService.class)
@ExtendWith({SpringExtension.class})
class MasterServiceImplTest {

  @Mock
  CommentRepo commentRepo;

  @Mock
  MasterRepo masterRepo;

  @InjectMocks
  MasterServiceImpl masterService;

  @BeforeAll
  static void setUp() {
    Locale locale = new Locale("en");
    Locale.setDefault(locale);
  }

  @Test
  void findAllShouldReturnMasterDtoPage() {
    Master master = new Master();
    Page<Master> masters = new PageImpl<>(List.of(master));

    Mockito.when(masterRepo.findAll(Pageable.unpaged())).thenReturn(masters);
    Page<MasterDto> expected = masters.map(MasterDto::new);

    Page<MasterDto> actual = masterService.findAll(Pageable.unpaged());

    assertEquals(expected, actual);

    Mockito.verify(masterRepo).findAll(Pageable.unpaged());
    Mockito.verifyNoMoreInteractions(commentRepo, masterRepo);
  }

  @Test
  void findMasterServicesShouldReturnServiceDtoList() {
    List<Service> services = new ArrayList<>();
    Master master = Master.builder().services(services).build();
    Collator collator = Collator.getInstance(Locale.getDefault());

    List<ServiceDto> expected = services.stream()
        .map(service -> new ServiceDto(service, Locale.getDefault().toString()))
        .sorted((s1, s2) -> collator.compare(s1.getName(), s2.getName()))
        .collect(Collectors.toList());

    List<ServiceDto> actual = masterService.findMasterServices(master);

    assertEquals(expected, actual);

    Mockito.verifyNoMoreInteractions(commentRepo, masterRepo);
  }

  @Test
  void updateAverageGradeShouldUpdateGrade() {
    Master oldMaster = new Master();
    List<Comment> comments = new ArrayList<>();

    Mockito.when(commentRepo.findAllByMaster(oldMaster)).thenReturn(comments);

    double grade = comments.stream()
        .mapToInt(Comment::getGrade)
        .average()
        .orElse(0);

    Master newMaster = Master.builder().grade(grade).build();

    masterService.updateAverageGrade(oldMaster);

    Mockito.verify(commentRepo).findAllByMaster(oldMaster);
    Mockito.verify(masterRepo).save(newMaster);
    Mockito.verifyNoMoreInteractions(commentRepo, masterRepo);
  }

  @Test
  void findMasterServicesLikeShouldReturnServiceDtoList() {
    List<Service> services = new ArrayList<>();
    Master master = Master.builder().services(services).build();
    Collator collator = Collator.getInstance(Locale.getDefault());
    String filter = "filter";

    List<ServiceDto> expected = services.stream()
        .map(service -> new ServiceDto(service, Locale.getDefault().toString()))
        .filter(serviceDto -> serviceDto.getName()
            .toLowerCase(Locale.getDefault()).contains(filter.toLowerCase(Locale.getDefault())))
        .sorted((s1, s2) -> collator.compare(s1.getName(), s2.getName()))
        .collect(Collectors.toList());

    List<ServiceDto> actual = masterService.findMasterServicesLike(master, filter);

    assertEquals(expected, actual);

    Mockito.verifyNoMoreInteractions(commentRepo, masterRepo);
  }

  @Test
  void findAllLikeShouldReturnMasterDtoPage() {
    String filter = "filter";
    Master master = new Master();
    Page<Master> masters = new PageImpl<>(List.of(master));

    Mockito.when(masterRepo
        .findAllByNameContainingOrSurnameContaining(filter, filter, Pageable.unpaged()))
        .thenReturn(masters);
    Page<MasterDto> expected = masters.map(MasterDto::new);

    Page<MasterDto> actual = masterService.findAllLike(filter, Pageable.unpaged());

    assertEquals(expected, actual);

    Mockito.verify(masterRepo)
        .findAllByNameContainingOrSurnameContaining(filter, filter, Pageable.unpaged());
    Mockito.verifyNoMoreInteractions(commentRepo, masterRepo);
  }

}