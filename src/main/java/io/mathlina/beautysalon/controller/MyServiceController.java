package io.mathlina.beautysalon.controller;

import io.mathlina.beautysalon.domain.Service;
import io.mathlina.beautysalon.dto.MasterDto;
import io.mathlina.beautysalon.dto.ServiceDto;
import io.mathlina.beautysalon.model.ServiceModel;
import io.mathlina.beautysalon.model.mapper.Mapper;
import io.mathlina.beautysalon.service.MyServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class MyServiceController {

    @Autowired
    private Mapper mapper;

    private final MyServiceService myServiceService;

    public MyServiceController(MyServiceService myServiceService) {
        this.myServiceService = myServiceService;
    }

    @GetMapping("/services")
    public String serviceList(Model model, @PageableDefault(size = 6) Pageable pageable,
                              @RequestParam(required = false) String filter) {

        Page<ServiceDto> myServicePage = myServiceService.findAll(filter, pageable);

        model.addAttribute("services", myServicePage);
        model.addAttribute("filter", filter);

        return "serviceList";
    }

    //TODO: fix, make work
    @GetMapping("/service/{service}")
    public String serviceList(Model model, @PathVariable Service service,
                              @RequestParam(required = false) String filter) {

        ServiceModel serviceModel = mapper.map(service, ServiceModel.class);

        List<MasterDto> masterDTOs = myServiceService
                .findServiceMastersLike(serviceModel, filter);

        model.addAttribute("masters", masterDTOs);
        model.addAttribute("service",
                new ServiceDto(serviceModel, LocaleContextHolder.getLocale().toString()));
        model.addAttribute("filter", filter);

        return "service";
    }

}
