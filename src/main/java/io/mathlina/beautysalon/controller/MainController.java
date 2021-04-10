package io.mathlina.beautysalon.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Log4j2
@Controller
public class MainController {

  //TODO: locale picker without url on header.html
  @GetMapping("/")
  public String greeting() {
    log.info("Processing GET request to \"/\"");
    return "greeting";
  }
}
