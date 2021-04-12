package io.mathlina.beautysalon.controller;

import io.mathlina.beautysalon.domain.Service;
import io.mathlina.beautysalon.service.MyServiceService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MyServiceController {

  private final MyServiceService myServiceService;

  public MyServiceController(MyServiceService myServiceService) {
    this.myServiceService = myServiceService;
  }

  //TODO: deal with '?' in url on pagination and local change
  //TODO: add size picker
  @GetMapping("/serviceList")
  public String ServiceList(Model model, @PageableDefault(size = 6) Pageable pageable) {
    Page<Service> myServicePage = myServiceService.findAllPaginated(pageable);
    model.addAttribute("myServicePage", myServicePage);

    return "serviceList";
  }

}
