package io.mathlina.beautysalon.controller;

import io.mathlina.beautysalon.domain.Master;
import io.mathlina.beautysalon.dto.MasterDto;
import io.mathlina.beautysalon.dto.ServiceDto;
import io.mathlina.beautysalon.model.CommentModel;
import io.mathlina.beautysalon.model.MasterModel;
import io.mathlina.beautysalon.model.mapper.Mapper;
import io.mathlina.beautysalon.service.CommentService;
import io.mathlina.beautysalon.service.MasterService;
import org.springframework.beans.factory.annotation.Autowired;
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

import java.util.List;
import java.util.Objects;

@Controller
public class MasterController {

    @Autowired
    private Mapper mapper;

    private final MasterService masterService;
    private final CommentService commentService;

    public MasterController(MasterService masterService,
                            CommentService commentService) {
        this.masterService = masterService;
        this.commentService = commentService;
    }

    @GetMapping("/masters")
    public String mastersList(Model model, @PageableDefault(size = 6) Pageable pageable,
                              @RequestParam(required = false) String filter) {

        Page<MasterDto> mastersPage = masterService.findAllLike(filter, pageable);

        model.addAttribute("masters", mastersPage);
        model.addAttribute("filter", filter);

        return "mastersList";
    }

    //TODO: make work
    //TODO: uuid
    @GetMapping("/master/{master}")
    public String getMaster(Model model, @PathVariable Master master,
                            @RequestParam(required = false) String filter) {

        MasterModel masterModel = mapper.map(master, MasterModel.class);

        List<ServiceDto> serviceDTOs = masterService.findMasterServicesLike(masterModel, filter);

        model.addAttribute("services", serviceDTOs);
        model.addAttribute("master", new MasterDto(masterModel));
        model.addAttribute("filter", filter);

        return "master";
    }

    @GetMapping("/master/{master}/comments")
    public String getMasterComments(@AuthenticationPrincipal UserDetails userDetails,
                                    @PathVariable Master master, Model model, Pageable pageable) {

        MasterModel masterModel = mapper.map(master, MasterModel.class);

        Page<CommentModel> comments = commentService.getComments(masterModel, pageable);
        model.addAttribute("comments", comments);
        model.addAttribute("master", new MasterDto(masterModel));

        //TODO: refactor
        CommentModel userComment;
        if (Objects.nonNull(userDetails)) {
            userComment = commentService.getComment(masterModel, userDetails);
        } else {
            userComment = CommentModel.builder().grade((byte) 1).text("").build();
        }
        model.addAttribute("userComment", userComment);

        return "masterComments";
    }

    @PreAuthorize("hasAuthority('CLIENT')")
    @PostMapping("/master/{master}/comments")
    public String doComment(@AuthenticationPrincipal UserDetails userDetails,
                            @RequestParam("grade") Byte grade, @RequestParam("commentText") String commentText,
                            @PathVariable Master master) {

        MasterModel masterModel = mapper.map(master, MasterModel.class);

        commentService.updateComment(userDetails, masterModel, grade, commentText);
        masterService.updateAverageGrade(masterModel); //TODO: move to quartz with additional conditions

        return "redirect:/master/{master}/comments";
    }

}