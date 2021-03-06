package io.mathlina.beautysalon.controller;

import io.mathlina.beautysalon.dto.MasterDto;
import io.mathlina.beautysalon.dto.ServiceDto;
import io.mathlina.beautysalon.model.CommentModel;
import io.mathlina.beautysalon.model.MasterModel;
import io.mathlina.beautysalon.model.mapper.Mapper;
import io.mathlina.beautysalon.service.CommentService;
import io.mathlina.beautysalon.service.MasterService;
import lombok.RequiredArgsConstructor;
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
import java.util.Optional;

@RequiredArgsConstructor
@Controller
public class MasterController {

    private final Mapper mapper;
    private final MasterService masterService;
    private final CommentService commentService;

    @GetMapping("/masters")
    public String mastersList(Model model, @PageableDefault(size = 6) Pageable pageable,
                              @RequestParam(required = false) String filter) {

        Page<MasterDto> mastersPage = masterService.findAllLike(filter, pageable);

        model.addAttribute("masters", mastersPage);
        model.addAttribute("filter", filter);

        return "mastersList";
    }

    //TODO: uuid
    @GetMapping("/master/{masterId}")
    public String getMaster(Model model, @PathVariable Long masterId,
                            @RequestParam(required = false) String filter) {

        //TODO: check exception
        MasterModel masterModel = masterService.findById(masterId).get();

        List<ServiceDto> serviceDTOs = masterService.findMasterServicesLike(masterModel, filter);

        model.addAttribute("services", serviceDTOs);
        model.addAttribute("master", mapper.map(masterModel, MasterDto.class));
        model.addAttribute("filter", filter);

        return "master";
    }

    @GetMapping("/master/{masterId}/comments")
    public String getMasterComments(@AuthenticationPrincipal UserDetails userDetails,
                                    @PathVariable Long masterId, Model model, Pageable pageable) {

        //TODO: check exception
        MasterModel masterModel = masterService.findById(masterId).get();

        Page<CommentModel> comments = commentService.getComments(masterModel, pageable);
        model.addAttribute("comments", comments);
        model.addAttribute("master", mapper.map(masterModel, MasterDto.class));

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
    @PostMapping("/master/{masterId}/comments")
    public String doComment(@AuthenticationPrincipal UserDetails userDetails,
                            @RequestParam("grade") Byte grade, @RequestParam("commentText") String commentText,
                            @PathVariable Long masterId) {

        //TODO: check exception
        MasterModel masterModel = masterService.findById(masterId).get();

        commentService.updateComment(userDetails, masterModel, grade, commentText);
        masterService.updateAverageGrade(masterModel); //TODO: move to quartz with additional conditions

        return "redirect:/master/{master}/comments";
    }

}