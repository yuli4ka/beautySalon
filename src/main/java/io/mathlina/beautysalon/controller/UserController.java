package io.mathlina.beautysalon.controller;

import io.mathlina.beautysalon.domain.User;
import io.mathlina.beautysalon.dto.UserRegistrationDto;
import io.mathlina.beautysalon.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("profile")
  public String getProfile(@AuthenticationPrincipal UserDetails userDetails,
      @ModelAttribute("userRegistrationDto") UserRegistrationDto userRegistrationDto) {

    User user = userService.loadUserByUsername(userDetails.getUsername());

    userRegistrationDto.setUsername(user.getUsername());
    userRegistrationDto.setEmail(user.getEmail());

    return "profile";
  }

  @PostMapping("/profile")
  public String updateProfile(@AuthenticationPrincipal UserDetails userDetails,
      @ModelAttribute("userRegistrationDto") UserRegistrationDto userRegistrationDto) {

    return "redirect:/profile";
  }

}