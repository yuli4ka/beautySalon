package io.mathlina.beautysalon.service;

import io.mathlina.beautysalon.domain.Master;
import io.mathlina.beautysalon.model.CommentModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;

public interface CommentService {

  Page<CommentModel> getComments(Master master, Pageable pageable);

  CommentModel getComment(Master master, UserDetails userDetails);

  void updateComment(UserDetails userDetails, Master master, Byte grade, String commentText);
}
