package io.mathlina.beautysalon.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

//  @GetMapping("/greeting")
//  public String helloWorld(@RequestParam(name="name", required=false, defaultValue="World")
//      String name, Model model) {
//    model.addAttribute("name", name);
//    return "greeting";

  @GetMapping("/")
  public String helloWorld() {
    return "greeting";

  }
}
