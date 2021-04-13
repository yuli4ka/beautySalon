package io.mathlina.beautysalon.controller;

import io.mathlina.beautysalon.dto.UserRegistrationDto;
import io.mathlina.beautysalon.exception.CannotSaveUserToDatabase;
import io.mathlina.beautysalon.exception.EmailIsAlreadyTaken;
import io.mathlina.beautysalon.exception.UserNotFoundByActivationCode;
import io.mathlina.beautysalon.exception.UsernameIsAlreadyTaken;
import io.mathlina.beautysalon.service.UserService;
import io.mathlina.beautysalon.validation.PasswordEqualityValidatorRegister;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping()
public class RegistrationController {
  private final UserService userService;

  public RegistrationController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/registration")
  public String registration(@ModelAttribute("userRegistrationDto")
      UserRegistrationDto userRegistrationDto) {
    return "registration";
  }

  @PostMapping("/registration")
  public String addUser(@ModelAttribute("userRegistrationDto")
                        @Valid UserRegistrationDto userRegistrationDto,
                        BindingResult bindingResult) {

    PasswordEqualityValidatorRegister validator = new PasswordEqualityValidatorRegister();
    validator.validate(userRegistrationDto, bindingResult);

    if (!bindingResult.hasErrors()) {
      try {
        userService.addUser(userRegistrationDto);
      } catch (UsernameIsAlreadyTaken e) {
        bindingResult.rejectValue("username", "username.is.already.taken");
      } catch (EmailIsAlreadyTaken e) {
        bindingResult.rejectValue("email", "email.is.already.taken");
      } catch (CannotSaveUserToDatabase e) {
        bindingResult.rejectValue("username", "failed.to.create.user");
      }
    }

    if (bindingResult.hasErrors()) {
      return "registration";
    }

    return "redirect:/login";
  }

  @GetMapping("/activate/{code}")
  public String activate(Model model, @PathVariable String code) {
    try {
      userService.activateUser(code);
      model.addAttribute("messageType", "success");
    } catch (UserNotFoundByActivationCode e) {
      model.addAttribute("messageType", "danger");
    }

    return "login";
  }

}
