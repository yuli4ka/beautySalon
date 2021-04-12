package io.mathlina.beautysalon.controller;

import io.mathlina.beautysalon.domain.Master;
import io.mathlina.beautysalon.domain.User;
import io.mathlina.beautysalon.dto.MasterDto;
import io.mathlina.beautysalon.dto.UserProfileDto;
import io.mathlina.beautysalon.exception.CannotSaveUserToDatabase;
import io.mathlina.beautysalon.exception.EmailIsAlreadyTaken;
import io.mathlina.beautysalon.exception.UserNotFound;
import io.mathlina.beautysalon.exception.WrongPassword;
import io.mathlina.beautysalon.service.MasterService;
import io.mathlina.beautysalon.service.UserService;
import io.mathlina.beautysalon.validation.PasswordEqualityValidatorProfile;
import javax.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MasterController {

  private final MasterService masterService;

  public MasterController(MasterService masterService) {
    this.masterService = masterService;
  }

  //TODO: add size picker
  @GetMapping("/allMasters")
  public String MastersList(Model model, @PageableDefault(size=6) Pageable pageable) {
    Page<MasterDto> mastersPage = masterService.findAllPaginated(pageable);
    model.addAttribute("mastersPage", mastersPage);

    return "mastersList";
  }

}