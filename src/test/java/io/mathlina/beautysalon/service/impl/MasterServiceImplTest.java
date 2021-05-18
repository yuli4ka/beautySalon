package io.mathlina.beautysalon.service.impl;

import io.mathlina.beautysalon.domain.Master;
import io.mathlina.beautysalon.domain.Service;
import io.mathlina.beautysalon.dto.MasterDto;
import io.mathlina.beautysalon.dto.ServiceDto;
import io.mathlina.beautysalon.model.CommentModel;
import io.mathlina.beautysalon.repos.CommentRepository;
import io.mathlina.beautysalon.repos.MasterRepo;
import io.mathlina.beautysalon.service.MasterService;
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

import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = MasterService.class)
@ExtendWith({SpringExtension.class})
class MasterServiceImplTest {

  private static final String FILTER = "filter";

  @Mock
  CommentRepository commentRepository;

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

    Page<MasterDto> expected = masters.map(MasterDto::new);

    Mockito.when(masterRepo.findAll(Pageable.unpaged())).thenReturn(masters);

    Page<MasterDto> actual = masterService.findAll(Pageable.unpaged());

    assertEquals(expected, actual);

    Mockito.verify(masterRepo).findAll(Pageable.unpaged());
    Mockito.verifyNoMoreInteractions(commentRepository, masterRepo);
  }

  @Test
  void findMasterServicesShouldReturnServiceDtoList() {
    Service service1 = Service.builder().nameEn("name1").build();
    Service service2 = Service.builder().nameEn("name2").build();
    Master master = Master.builder().services(List.of(service1, service2)).build();
    ServiceDto serviceDto1 = new ServiceDto(service1, Locale.getDefault().toString());
    ServiceDto serviceDto2 = new ServiceDto(service2, Locale.getDefault().toString());

    List<ServiceDto> expected = List.of(serviceDto1, serviceDto2);

    List<ServiceDto> actual = masterService.findMasterServices(master);

    assertEquals(expected, actual);

    Mockito.verifyNoMoreInteractions(commentRepository, masterRepo);
  }

  @Test
  void updateAverageGradeShouldUpdateGrade() {
    double averageGrade = 2;
    CommentModel comment1 = CommentModel.builder().grade((byte) 1).build();
    CommentModel comment2 = CommentModel.builder().grade((byte) 3).build();
    List<CommentModel> comments = List.of(comment1, comment2);
    Master oldMaster = new Master();
    Master newMaster = Master.builder().grade(averageGrade).build();

    Mockito.when(commentRepository.findAllByMaster(oldMaster)).thenReturn(comments);

    masterService.updateAverageGrade(oldMaster);

    Mockito.verify(commentRepository).findAllByMaster(oldMaster);
    Mockito.verify(masterRepo).save(newMaster);
    Mockito.verifyNoMoreInteractions(commentRepository, masterRepo);
  }

  @Test
  void findMasterServicesLikeShouldReturnServiceDtoList() {
    Service service1 = Service.builder().nameEn("name1").build();
    Service service2 = Service.builder().nameEn("name2" + FILTER).build();
    Master master = Master.builder().services(List.of(service1, service2)).build();
    ServiceDto serviceDto2 = new ServiceDto(service2, Locale.getDefault().toString());

    List<ServiceDto> expected = List.of(serviceDto2);

    List<ServiceDto> actual = masterService.findMasterServicesLike(master, FILTER);

    assertEquals(expected, actual);

    Mockito.verifyNoMoreInteractions(commentRepository, masterRepo);
  }

  @Test
  void findAllLikeShouldReturnMasterDtoPage() {
    Master master = new Master();
    Page<Master> masters = new PageImpl<>(List.of(master));

    Page<MasterDto> expected = masters.map(MasterDto::new);

    Mockito.when(masterRepo
        .findAllByNameContainingOrSurnameContaining(FILTER, FILTER, Pageable.unpaged()))
        .thenReturn(masters);

    Page<MasterDto> actual = masterService.findAllLike(FILTER, Pageable.unpaged());

    assertEquals(expected, actual);

    Mockito.verify(masterRepo)
        .findAllByNameContainingOrSurnameContaining(FILTER, FILTER, Pageable.unpaged());
    Mockito.verifyNoMoreInteractions(commentRepository, masterRepo);
  }

}