package io.mathlina.beautysalon.controller;

import io.mathlina.beautysalon.domain.Service;
import io.mathlina.beautysalon.service.MyServiceService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
  public String ServiceList(Model model,
      @RequestParam("page") Optional<Integer> page,
      @RequestParam("size") Optional<Integer> size) {

    int currentPage = page.orElse(1);
    int pageSize = size.orElse(5);

    Page<Service> myServicePage = myServiceService
        .findAllPaginated(PageRequest.of(currentPage - 1, pageSize));

    model.addAttribute("myServicePage", myServicePage);

    int totalPages = myServicePage.getTotalPages();
    if (totalPages > 0) {
      List<Integer> pageNumbers = IntStream
          .rangeClosed(1, totalPages)
          .boxed()
          .collect(Collectors.toList());
      model.addAttribute("pageNumbers", pageNumbers);
    }

    return "serviceList";
  }

}
