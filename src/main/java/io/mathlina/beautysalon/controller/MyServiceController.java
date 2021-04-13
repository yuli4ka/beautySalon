package io.mathlina.beautysalon.controller;

import io.mathlina.beautysalon.domain.Master;
import io.mathlina.beautysalon.domain.Service;
import io.mathlina.beautysalon.dto.MasterDto;
import io.mathlina.beautysalon.dto.ServiceDto;
import io.mathlina.beautysalon.service.MyServiceService;
import java.util.List;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class MyServiceController {

  private final MyServiceService myServiceService;

  public MyServiceController(MyServiceService myServiceService) {
    this.myServiceService = myServiceService;
  }

  //TODO: add size picker
  @GetMapping("/services")
  public String serviceList(Model model, @PageableDefault(size = 6) Pageable pageable) {
    Page<ServiceDto> myServicePage = myServiceService.findAll(pageable);
    model.addAttribute("services", myServicePage);

    return "serviceList";
  }

  //TODO: add size picker
  @GetMapping("/service/{service}")
  public String serviceList(Model model, @PathVariable Service service,
      @PageableDefault(size = 6) Pageable pageable) {
    List<MasterDto> masterDTOs = myServiceService.findServiceMasters(service);
    model.addAttribute("masters", masterDTOs);
    model.addAttribute("service",
        new ServiceDto(service, LocaleContextHolder.getLocale().toString()));

    return "service";
  }

}
