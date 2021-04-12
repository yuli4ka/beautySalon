package io.mathlina.beautysalon.controller;

import io.mathlina.beautysalon.domain.User;
import io.mathlina.beautysalon.dto.UserProfileDto;
import io.mathlina.beautysalon.exception.CannotSaveUserToDatabase;
import io.mathlina.beautysalon.exception.EmailIsAlreadyTaken;
import io.mathlina.beautysalon.exception.UserNotFound;
import io.mathlina.beautysalon.exception.WrongPassword;
import io.mathlina.beautysalon.service.UserService;
import io.mathlina.beautysalon.validation.PasswordEqualityValidatorProfile;
import javax.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/profile")
  public String getProfile(@AuthenticationPrincipal UserDetails userDetails,
      @ModelAttribute("userProfileDto") UserProfileDto userProfileDto) {

    User user = userService.loadUserByUsername(userDetails.getUsername());

    userProfileDto.setUsername(user.getUsername());
    userProfileDto.setEmail(user.getEmail());

    return "profile";
  }

  //TODO: add message for code activation, user not activated
  //TODO: add 'send activation code again' button
  //TODO: add message for successful changes
  @PostMapping("/profile")
  public String updateProfile(@RequestParam("oldPassword") String oldPassword,
      @ModelAttribute("userProfileDto")
      @Valid UserProfileDto userProfileDto,
      BindingResult bindingResult) {

    PasswordEqualityValidatorProfile validator = new PasswordEqualityValidatorProfile();
    validator.validate(userProfileDto, bindingResult);

    if (oldPassword.isEmpty() && !userProfileDto.getPassword().isEmpty()) {
      bindingResult.rejectValue("password", "fill.the.field.if.password.change");
    }

    if (!bindingResult.hasErrors()) {
      try {
        userService.updateUser(userProfileDto, oldPassword);
      } catch (UserNotFound e) {
        return "error/error";
      } catch (EmailIsAlreadyTaken e) {
        bindingResult.rejectValue("email", "email.is.already.taken");
      } catch (WrongPassword e) {
        bindingResult.rejectValue("password", "wrong.password");
      } catch (CannotSaveUserToDatabase e) {
        bindingResult.rejectValue("username", "failed.to.update.user.data");
      }
    }

    return "profile";
  }

//  //TODO: add size picker
//  @GetMapping("/allMasters")
//  public String MastersList(Model model, @PageableDefault(size=6) Pageable pageable) {
//    Page<MasterDto> mastersPage = userService.findPaginatedMasters(pageable);
//    model.addAttribute("mastersPage", mastersPage);
//
//    return "mastersList";
//  }

}