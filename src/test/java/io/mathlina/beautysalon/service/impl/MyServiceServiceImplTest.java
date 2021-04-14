package io.mathlina.beautysalon.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.mathlina.beautysalon.domain.Master;
import io.mathlina.beautysalon.domain.Service;
import io.mathlina.beautysalon.dto.MasterDto;
import io.mathlina.beautysalon.dto.ServiceDto;
import io.mathlina.beautysalon.repos.MyServiceRepo;
import io.mathlina.beautysalon.service.MyServiceService;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest(classes = MyServiceService.class)
@ExtendWith({SpringExtension.class})
class MyServiceServiceImplTest {

  private static final String FILTER = "filter";

  @Mock
  MyServiceRepo myServiceRepo;

  @InjectMocks
  MyServiceServiceImpl myServiceService;

  @BeforeAll
  static void setUp() {
    Locale locale = new Locale("en");
    Locale.setDefault(locale);
  }

  @Test
  void findAllShouldReturnServiceDtoPage() {
    Service service = new Service();
    Page<Service> services = new PageImpl<>(List.of(service));

    Page<ServiceDto> expected = services
        .map(s -> new ServiceDto(s, Locale.getDefault().toString()));

    Mockito.when(myServiceRepo
        .findAll(Pageable.unpaged()))
        .thenReturn(services);

    Page<ServiceDto> actual = myServiceService.findAll(Pageable.unpaged());

    assertEquals(expected, actual);

    Mockito.verify(myServiceRepo).findAll(Pageable.unpaged());
    Mockito.verifyNoMoreInteractions(myServiceRepo);
  }

  @Test
  void findServiceMastersShouldReturnMasterDTOs() {
    Master master = new Master();
    List<Master> masters = List.of(master);
    List<MasterDto> expected = masters.stream()
        .map(MasterDto::new)
        .collect(Collectors.toList());
    Service service = Service.builder().masters(masters).build();

    List<MasterDto> actual = myServiceService.findServiceMasters(service);

    assertEquals(expected, actual);
    Mockito.verifyNoMoreInteractions(myServiceRepo);
  }

  @Test
  void findServiceMastersLikeShouldReturnFilteredMasterDTOs() {
    Master master1 = Master.builder().id(1L).name("name1").surname("surname1").build();
    Master master2 = Master.builder().id(2L).name("name2" + FILTER).surname("surname2").build();
    Master master3 = Master.builder().id(3L).name("name3").surname("surname3" + FILTER).build();
    MasterDto masterDto2 = new MasterDto(master2);
    MasterDto masterDto3 = new MasterDto(master3);
    Service service = Service.builder().masters(List.of(master1, master2, master3)).build();

    List<MasterDto> expected = List.of(masterDto2, masterDto3);

    List<MasterDto> actual = myServiceService.findServiceMastersLike(service, FILTER);

    assertEquals(expected, actual);
    Mockito.verifyNoMoreInteractions(myServiceRepo);
  }

  @Test
  void findAllWithFilterShouldReturnPageOfServiceDTOs() {
    Service service1 = Service.builder().nameEn("name1").nameUa("назва1").build();
    Service service2 = Service.builder().nameEn("name2" + FILTER).nameUa("назва2").build();
    ServiceDto serviceDto2 = new ServiceDto(service2, Locale.getDefault().toString());
    PageRequest pageRequest = PageRequest.of(0, 10);

    List<ServiceDto> expected = List.of(serviceDto2);

    Mockito.when(myServiceRepo.findAll()).thenReturn(List.of(service1, service2));

    List<ServiceDto> actual = myServiceService.findAll(FILTER, pageRequest).getContent();

    assertEquals(expected, actual);
    Mockito.verify(myServiceRepo).findAll();
    Mockito.verifyNoMoreInteractions(myServiceRepo);
  }
}