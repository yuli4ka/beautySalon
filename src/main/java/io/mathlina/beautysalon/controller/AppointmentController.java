package io.mathlina.beautysalon.controller;

import io.mathlina.beautysalon.domain.Master;
import io.mathlina.beautysalon.domain.Service;
import io.mathlina.beautysalon.domain.Timetable;
import io.mathlina.beautysalon.domain.User;
import io.mathlina.beautysalon.dto.MasterDto;
import io.mathlina.beautysalon.dto.ServiceDto;
import io.mathlina.beautysalon.service.AppointmentService;
import io.mathlina.beautysalon.service.UserService;
import java.time.LocalDateTime;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AppointmentController {

  private final UserService userService;
  private final AppointmentService appointmentService;

  public AppointmentController(UserService userService, AppointmentService appointmentService) {
    this.userService = userService;
    this.appointmentService = appointmentService;
  }

  @PreAuthorize("hasAuthority('CLIENT')")
  @GetMapping("/appoint")
  public String appointPage(@RequestParam("master_id") Master master,
      @RequestParam("service_id") Service service,
      Model model) {
    Timetable timetable = Timetable.builder().service(service).master(master).build();
    model.addAttribute("master", new MasterDto(master));
    model.addAttribute("service",
        new ServiceDto(service, LocaleContextHolder.getLocale().toString()));
    model.addAttribute("timetable", timetable);
    return "appoint";
  }

  //  TODO: datetime l10n
  @PreAuthorize("hasAuthority('CLIENT')")
  @PostMapping("/appoint")
  public String appoint(@AuthenticationPrincipal UserDetails userDetails,
      @RequestParam("master_id") Master master,
      @RequestParam("service_id") Service service,
      @RequestParam("dateTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
          LocalDateTime dateTime, Model model) {

    User user = userService.loadUserByUsername(userDetails.getUsername());
    Timetable timetable = Timetable.builder()
        .master(master)
        .client(user)
        .service(service)
        .dateTime(dateTime)
        .build();
    appointmentService.appoint(timetable);

    //TODO: message
    return "redirect:/appointments";
  }


  @PreAuthorize("hasAuthority('CLIENT')")
  @GetMapping("/appointments")
  public String appointmentsPage(@AuthenticationPrincipal UserDetails userDetails, Model model) {


    return "appointments";
  }

}
