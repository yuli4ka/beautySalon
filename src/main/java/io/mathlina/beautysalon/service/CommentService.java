package io.mathlina.beautysalon.service;

import io.mathlina.beautysalon.domain.Comment;
import io.mathlina.beautysalon.domain.Master;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;

public interface CommentService {

  Page<Comment> getComments(Master master, Pageable pageable);

  Comment getComment(Master master, UserDetails userDetails);
}
