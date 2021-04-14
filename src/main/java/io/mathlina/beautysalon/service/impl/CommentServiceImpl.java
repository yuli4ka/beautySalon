package io.mathlina.beautysalon.service.impl;

import io.mathlina.beautysalon.domain.Comment;
import io.mathlina.beautysalon.domain.Master;
import io.mathlina.beautysalon.domain.User;
import io.mathlina.beautysalon.exception.UserNotFound;
import io.mathlina.beautysalon.repos.CommentRepo;
import io.mathlina.beautysalon.repos.UserRepo;
import io.mathlina.beautysalon.service.CommentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

  private final CommentRepo commentRepo;
  private final UserRepo userRepo;

  public CommentServiceImpl(CommentRepo commentRepo,
      UserRepo userRepo) {
    this.commentRepo = commentRepo;
    this.userRepo = userRepo;
  }

  @Override
  public Page<Comment> getComments(Master master, Pageable pageable) {
    return commentRepo.findAllByMaster(master, pageable);
  }

  @Override
  public Comment getComment(Master master, UserDetails userDetails) {
    User user = userRepo.findByUsername(userDetails.getUsername())
        .orElseThrow(() -> new UserNotFound("User not found"));

    return commentRepo.findByMasterAndClient(master, user)
        .orElse(Comment.builder()
            .grade((byte) 5)
            .build()
        );
  }

  @Override
  public void updateComment(UserDetails userDetails, Master master,
      Byte grade, String commentText) {

    User user = userRepo.findByUsername(userDetails.getUsername())
        .orElseThrow(() -> new UserNotFound("User not found"));

    Comment comment = commentRepo.findByMasterAndClient(master, user)
        .orElse(Comment.builder()
            .client(user)
            .master(master)
            .build()
        );

    if (!grade.equals(comment.getGrade()) || !commentText.equals(comment.getText())) {
      comment.setGrade(grade);
      comment.setText(commentText);
      commentRepo.save(comment);
    }
  }
}
