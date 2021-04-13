package io.mathlina.beautysalon.controller;

import io.mathlina.beautysalon.dto.ServiceDto;
import io.mathlina.beautysalon.service.MyServiceService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyServiceController {

  private final MyServiceService myServiceService;

  public MyServiceController(MyServiceService myServiceService) {
    this.myServiceService = myServiceService;
  }

  //TODO: add size picker
  @GetMapping("/serviceList")
  public String ServiceList(Model model, @PageableDefault(size = 6) Pageable pageable) {
    Page<ServiceDto> myServicePage = myServiceService.findAll(pageable);
    model.addAttribute("services", myServicePage);

    return "serviceList";
  }

}
