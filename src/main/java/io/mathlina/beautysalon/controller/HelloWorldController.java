package io.mathlina.beautysalon.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HelloWorldController {

  @GetMapping("/hello-world")
  public String helloWorld(@RequestParam(name="name", required=false, defaultValue="World")
      String name, Model model) {
    model.addAttribute("name", name);
    return "hello_world";
  }
}
