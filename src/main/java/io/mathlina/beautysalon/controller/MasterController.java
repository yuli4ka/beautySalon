package io.mathlina.beautysalon.controller;

import io.mathlina.beautysalon.domain.Master;
import io.mathlina.beautysalon.dto.MasterDto;
import io.mathlina.beautysalon.dto.ServiceDto;
import io.mathlina.beautysalon.service.MasterService;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class MasterController {

  private final MasterService masterService;

  public MasterController(MasterService masterService) {
    this.masterService = masterService;
  }

  //TODO: add size picker
  @GetMapping("/allMasters")
  public String mastersList(Model model, @PageableDefault(size = 6) Pageable pageable) {
    Page<MasterDto> mastersPage = masterService.findAllPaginated(pageable);
    model.addAttribute("mastersPage", mastersPage);

    return "mastersList";
  }

  //TODO: uuid
  @GetMapping("/master/{master}")
  public String getMaster(Model model, @PathVariable Master master) {
    List<ServiceDto> serviceDTOs = masterService.findMasterServices(master);
    model.addAttribute("services", serviceDTOs);
    model.addAttribute("master", new MasterDto(master));

    return "master";
  }

}