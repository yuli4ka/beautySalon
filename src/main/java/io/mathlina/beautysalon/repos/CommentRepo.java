package io.mathlina.beautysalon.repos;

import io.mathlina.beautysalon.domain.Comment;
import io.mathlina.beautysalon.domain.Master;
import io.mathlina.beautysalon.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CommentRepo {

    Optional<Comment> findByMasterAndClient(Master master, User client);

    Page<Comment> findAllByMaster(Master master, Pageable pageable);

    List<Comment> findAllByMaster(Master master);

    Comment save(Comment comment);
}
