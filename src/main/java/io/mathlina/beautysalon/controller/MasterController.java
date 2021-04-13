package io.mathlina.beautysalon.controller;

import io.mathlina.beautysalon.domain.Comment;
import io.mathlina.beautysalon.domain.Master;
import io.mathlina.beautysalon.dto.MasterDto;
import io.mathlina.beautysalon.dto.ServiceDto;
import io.mathlina.beautysalon.service.CommentService;
import io.mathlina.beautysalon.service.MasterService;
import java.util.List;
import java.util.Objects;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MasterController {

  private final MasterService masterService;
  private final CommentService commentService;

  public MasterController(MasterService masterService,
      CommentService commentService) {
    this.masterService = masterService;
    this.commentService = commentService;
  }

  //TODO: add size picker
  @GetMapping("/masters")
  public String mastersList(Model model, @PageableDefault(size = 6) Pageable pageable) {
    Page<MasterDto> mastersPage = masterService.findAllPaginated(pageable);
    model.addAttribute("mastersPage", mastersPage);

    return "mastersList";
  }

  //TODO: uuid
  @GetMapping("/master/{master}")
  public String getMaster(Model model, @PathVariable Master master) {
    List<ServiceDto> serviceDTOs = masterService.findMasterServices(master);
    model.addAttribute("services", serviceDTOs);
    model.addAttribute("master", new MasterDto(master));

    return "master";
  }

  //TODO: hide pagination if pageNum==1
  @GetMapping("/master/{master}/comments")
  public String getMasterComments(@AuthenticationPrincipal UserDetails userDetails,
      @PathVariable Master master, Model model, Pageable pageable) {

    Page<Comment> comments = commentService.getComments(master, pageable);
    model.addAttribute("comments", comments);
    model.addAttribute("master", new MasterDto(master));

    Comment userComment;
    if (Objects.nonNull(userDetails)) {
      userComment = commentService.getComment(master, userDetails);
    } else {
      userComment = Comment.builder().grade((byte) 1).text("").build();
    }
    model.addAttribute("userComment", userComment);

    return "masterComments";
  }

  @PreAuthorize("hasAuthority('CLIENT')")
  @PostMapping("/master/{master}/comments")
  public String doComment(@AuthenticationPrincipal UserDetails userDetails,
      @RequestParam("grade") Byte grade, @RequestParam("commentText") String commentText,
      @PathVariable Master master) {

    commentService.updateComment(userDetails, master, grade, commentText);
    masterService.updateAverageGrade(master); //TODO: move to quartz with additional conditions

    return "redirect:/master/{master}/comments";
  }

}