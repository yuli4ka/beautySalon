package io.mathlina.beautysalon.controller;

import io.mathlina.beautysalon.dto.UserRegistrationDto;
import io.mathlina.beautysalon.service.UserService;
import io.mathlina.beautysalon.validation.PasswordEqualityValidator;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/registration")
public class RegistrationController {
  private final UserService userService;

  public RegistrationController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping()
  public String registration(@ModelAttribute("userRegistrationDto") UserRegistrationDto userRegistrationDto) {
    return "registration";
  }

  @PostMapping()
  public String addUser(@ModelAttribute("userRegistrationDto")
                        @Valid UserRegistrationDto userRegistrationDto,
                        BindingResult bindingResult) {
    PasswordEqualityValidator validator = new PasswordEqualityValidator();
    validator.validate(userRegistrationDto, bindingResult);

    if (!bindingResult.hasErrors() && !userService.addUser(userRegistrationDto)) {
      //TODO check username and email uniqueness
      bindingResult.rejectValue("username", "username.exists");
    }

    if (bindingResult.hasErrors()) {
      return "registration";
    }

    return "redirect:/login";
  }

}
