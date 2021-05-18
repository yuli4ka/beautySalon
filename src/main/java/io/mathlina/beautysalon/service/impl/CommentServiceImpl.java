package io.mathlina.beautysalon.service.impl;

import io.mathlina.beautysalon.domain.Master;
import io.mathlina.beautysalon.domain.User;
import io.mathlina.beautysalon.exception.UserNotFound;
import io.mathlina.beautysalon.model.CommentModel;
import io.mathlina.beautysalon.model.UserModel;
import io.mathlina.beautysalon.repos.CommentRepository;
import io.mathlina.beautysalon.repos.UserRepository;
import io.mathlina.beautysalon.service.CommentService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

  private final CommentRepository commentRepository;
  private final UserRepository userRepository;

  public CommentServiceImpl(CommentRepository commentRepository,
                            @Qualifier("userRepoJdbc") UserRepository userRepository) {
    this.commentRepository = commentRepository;
    this.userRepository = userRepository;
  }

  @Override
  public Page<CommentModel> getComments(Master master, Pageable pageable) {
    return commentRepository.findAllByMaster(master, pageable);
  }

  @Override
  public CommentModel getComment(Master master, UserDetails userDetails) {
    UserModel user = userRepository.findByUsername(userDetails.getUsername())
        .orElseThrow(() -> new UserNotFound("User not found"));

    return commentRepository.findByMasterAndClient(master, user)
        .orElse(CommentModel.builder()
            .grade((byte) 5)
            .build()
        );
  }

  @Override
  public void updateComment(UserDetails userDetails, Master master,
      Byte grade, String commentText) {

    UserModel user = userRepository.findByUsername(userDetails.getUsername())
        .orElseThrow(() -> new UserNotFound("User not found"));

    CommentModel comment = commentRepository.findByMasterAndClient(master, user)
        .orElse(CommentModel.builder()
            .clientId(user.getId())
            .masterId(master.getId())
            .build()
        );

    if (!grade.equals(comment.getGrade()) || !commentText.equals(comment.getText())) {
      comment.setGrade(grade);
      comment.setText(commentText);
      commentRepository.save(comment);
    }
  }
}
