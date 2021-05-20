package io.mathlina.beautysalon.service.impl;

import io.mathlina.beautysalon.dto.MasterDto;
import io.mathlina.beautysalon.dto.ServiceDto;
import io.mathlina.beautysalon.model.CommentModel;
import io.mathlina.beautysalon.model.MasterModel;
import io.mathlina.beautysalon.model.mapper.Mapper;
import io.mathlina.beautysalon.repos.CommentRepository;
import io.mathlina.beautysalon.repos.MasterRepository;
import io.mathlina.beautysalon.repos.MyServiceRepository;
import io.mathlina.beautysalon.service.MasterService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.text.Collator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@org.springframework.stereotype.Service
public class MasterServiceImpl implements MasterService {

    @Autowired
    private Mapper mapper;

    private final MasterRepository masterRepository;
    private final CommentRepository commentRepository;
    private final MyServiceRepository serviceRepository;

    public Page<MasterDto> findAll(Pageable pageable) {
        return masterRepository.findAll(pageable).map(m -> mapper.map(m, MasterDto.class));
    }

    public List<ServiceDto> findMasterServices(MasterModel masterModel) {
        Locale currentLocale = LocaleContextHolder.getLocale();
        Collator collator = Collator.getInstance(currentLocale);

        return masterModel.getServiceIds().stream()
                .map(serviceId ->
                        new ServiceDto(serviceRepository.findById(serviceId), currentLocale.toString()))
                .sorted((s1, s2) -> collator.compare(s1.getName(), s2.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public void updateAverageGrade(MasterModel master) {
        double averageGrade = commentRepository.findAllByMaster(master).stream()
                .mapToInt(CommentModel::getGrade)
                .average()
                .orElse(0);

        master.setGrade(averageGrade);

        masterRepository.save(master);
    }

    //TODO: update for quartz with additional optimizing conditions
    @Override
    public void updateAverageGrades() {
        masterRepository.findAll().forEach(this::updateAverageGrade);
    }

    @Override
    public List<ServiceDto> findMasterServicesLike(MasterModel masterModel, String filter) {
        if (Objects.isNull(filter) || filter.equals("")) {
            return findMasterServices(masterModel);
        } else {
            Locale currentLocale = LocaleContextHolder.getLocale();
            Collator collator = Collator.getInstance(currentLocale);

            return masterModel.getServiceIds().stream()
                    .map(serviceId ->
                            new ServiceDto(serviceRepository.findById(serviceId), currentLocale.toString()))
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
            return masterRepository.findAllByNameContainingOrSurnameContaining(filter, filter, pageable)
                    .map(m -> mapper.map(m, MasterDto.class));
        }
    }

    @Override
    public MasterModel findById(Long masterId) {
        return masterRepository.findById(masterId);
    }

}
