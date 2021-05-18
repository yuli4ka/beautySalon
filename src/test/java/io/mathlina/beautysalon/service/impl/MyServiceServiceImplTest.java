package io.mathlina.beautysalon.service.impl;

import io.mathlina.beautysalon.dto.MasterDto;
import io.mathlina.beautysalon.dto.ServiceDto;
import io.mathlina.beautysalon.model.MasterModel;
import io.mathlina.beautysalon.model.ServiceModel;
import io.mathlina.beautysalon.repos.MyServiceRepository;
import io.mathlina.beautysalon.service.MyServiceService;
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

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = MyServiceService.class)
@ExtendWith({SpringExtension.class})
class MyServiceServiceImplTest {

    private static final String FILTER = "filter";

    @Mock
    MyServiceRepository myServiceRepository;

    @InjectMocks
    MyServiceServiceImpl myServiceService;

    @BeforeAll
    static void setUp() {
        Locale locale = new Locale("en");
        Locale.setDefault(locale);
    }

    @Test
    void findAllShouldReturnServiceDtoPage() {
        ServiceModel service = new ServiceModel();
        Page<ServiceModel> services = new PageImpl<>(List.of(service));

        Page<ServiceDto> expected = services
                .map(s -> new ServiceDto(s, Locale.getDefault().toString()));

        Mockito.when(myServiceRepository
                .findAll(Pageable.unpaged()))
                .thenReturn(services);

        Page<ServiceDto> actual = myServiceService.findAll(Pageable.unpaged());

        assertEquals(expected, actual);

        Mockito.verify(myServiceRepository).findAll(Pageable.unpaged());
        Mockito.verifyNoMoreInteractions(myServiceRepository);
    }

    @Test
    void findServiceMastersShouldReturnMasterDTOs() {
        MasterModel master = new MasterModel();
        List<MasterModel> masters = List.of(master);
        List<MasterDto> expected = masters.stream()
                .map(MasterDto::new)
                .collect(Collectors.toList());
        List<Long> masterIds = masters.stream()
                .map(MasterModel::getId)
                .collect(Collectors.toList());
        ServiceModel service = ServiceModel.builder().masterIds(masterIds).build();

        List<MasterDto> actual = myServiceService.findServiceMasters(service);

        assertEquals(expected, actual);
        Mockito.verifyNoMoreInteractions(myServiceRepository);
    }

    @Test
    void findServiceMastersLikeShouldReturnFilteredMasterDTOs() {
        MasterModel master1 = MasterModel.builder().id(1L).name("name1").surname("surname1").build();
        MasterModel master2 = MasterModel.builder().id(2L).name("name2" + FILTER).surname("surname2").build();
        MasterModel master3 = MasterModel.builder().id(3L).name("name3").surname("surname3" + FILTER).build();
        MasterDto masterDto2 = new MasterDto(master2);
        MasterDto masterDto3 = new MasterDto(master3);
        List<Long> masterIds = List.of(master1, master2, master3)
                .stream()
                .map(MasterModel::getId)
                .collect(Collectors.toList());
        ServiceModel service = ServiceModel.builder()
                .masterIds(masterIds).build();

        List<MasterDto> expected = List.of(masterDto2, masterDto3);

        List<MasterDto> actual = myServiceService.findServiceMastersLike(service, FILTER);

        assertEquals(expected, actual);
        Mockito.verifyNoMoreInteractions(myServiceRepository);
    }

    @Test
    void findAllWithFilterShouldReturnPageOfServiceDTOs() {
        ServiceModel service1 = ServiceModel.builder().nameEn("name1").nameUa("назва1").build();
        ServiceModel service2 = ServiceModel.builder().nameEn("name2" + FILTER).nameUa("назва2").build();
        ServiceDto serviceDto2 = new ServiceDto(service2, Locale.getDefault().toString());
        PageRequest pageRequest = PageRequest.of(0, 10);

        List<ServiceDto> expected = List.of(serviceDto2);

        Mockito.when(myServiceRepository.findAll()).thenReturn(List.of(service1, service2));

        List<ServiceDto> actual = myServiceService.findAll(FILTER, pageRequest).getContent();

        assertEquals(expected, actual);
        Mockito.verify(myServiceRepository).findAll();
        Mockito.verifyNoMoreInteractions(myServiceRepository);
    }
}