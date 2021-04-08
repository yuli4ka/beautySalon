package io.mathlina.beautysalon.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Log4j2
@Controller
public class MainController {

//  @GetMapping("/greeting")
//  public String helloWorld(@RequestParam(name="name", required=false, defaultValue="World")
//      String name, Model model) {
//    model.addAttribute("name", name);
//    return "greeting";

  @GetMapping("/")
  public String helloWorld() {
    log.info("Processing GET request to \"/\"");
    return "greeting";
  }
}
