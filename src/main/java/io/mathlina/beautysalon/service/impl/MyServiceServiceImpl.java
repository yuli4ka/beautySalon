package io.mathlina.beautysalon.service.impl;

import io.mathlina.beautysalon.dto.MasterDto;
import io.mathlina.beautysalon.dto.ServiceDto;
import io.mathlina.beautysalon.model.ServiceModel;
import io.mathlina.beautysalon.repos.MasterRepository;
import io.mathlina.beautysalon.repos.MyServiceRepository;
import io.mathlina.beautysalon.service.MyServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
public class MyServiceServiceImpl implements MyServiceService {

    private final MyServiceRepository myServiceRepository;
    private final MasterRepository masterRepository;

    @Autowired
    public MyServiceServiceImpl(MyServiceRepository myServiceRepository, MasterRepository masterRepository) {
        this.myServiceRepository = myServiceRepository;
        this.masterRepository = masterRepository;
    }

    public Page<ServiceDto> findAll(Pageable pageable) {
        return myServiceRepository.findAll(pageable)
                .map(service -> new ServiceDto(service, LocaleContextHolder.getLocale().toString()));
    }

    @Override
    public List<MasterDto> findServiceMasters(ServiceModel serviceModel) {
        return serviceModel.getMasterIds().stream()
                .map(masterRepository::findById)
                .map(MasterDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<MasterDto> findServiceMastersLike(ServiceModel serviceModel, String filter) {
        if (Objects.isNull(filter) || filter.equals("")) {
            return findServiceMasters(serviceModel);
        } else {
            return serviceModel.getMasterIds().stream()
                    .map(masterRepository::findById)
                    .map(MasterDto::new)
                    .filter(masterDto ->
                            masterDto.getName().toLowerCase().contains(filter.toLowerCase())
                                    || masterDto.getSurname().toLowerCase().contains(filter.toLowerCase()))
                    .collect(Collectors.toList());
        }
    }

    @Override
    public Page<ServiceDto> findAll(String filter, Pageable pageable) {
        if (Objects.isNull(filter) || filter.equals("")) {
            return findAll(pageable);
        } else {
            List<ServiceDto> serviceDTOs = myServiceRepository.findAll().stream()
                    .map(service -> new ServiceDto(service, LocaleContextHolder.getLocale().toString()))
                    .filter(serviceDto -> serviceDto.getName().toLowerCase().contains(filter.toLowerCase()))
                    .collect(Collectors.toList());

            int pageSize = pageable.getPageSize();
            int page = pageable.getPageNumber();
            int last = Math.min(pageSize * (page + 1), serviceDTOs.size());

            return new PageImpl<>(serviceDTOs.subList(page * pageSize, last), pageable, serviceDTOs.size());
        }
    }

}
