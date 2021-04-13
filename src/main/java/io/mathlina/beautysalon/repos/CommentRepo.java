package io.mathlina.beautysalon.repos;

import io.mathlina.beautysalon.domain.Comment;
import io.mathlina.beautysalon.domain.Master;
import io.mathlina.beautysalon.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepo extends JpaRepository<Comment, Long> {

  Comment findByMasterAndClient(Master master, User client);

  Page<Comment> findAllByMaster(Master master, Pageable pageable);

}
