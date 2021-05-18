package io.mathlina.beautysalon.service;

import io.mathlina.beautysalon.model.CommentModel;
import io.mathlina.beautysalon.model.MasterModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;

public interface CommentService {

    Page<CommentModel> getComments(MasterModel master, Pageable pageable);

    CommentModel getComment(MasterModel master, UserDetails userDetails);

    void updateComment(UserDetails userDetails, MasterModel master, Byte grade, String commentText);
}
