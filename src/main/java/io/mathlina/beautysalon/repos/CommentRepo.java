package io.mathlina.beautysalon.repos;

import io.mathlina.beautysalon.domain.Comment;
import io.mathlina.beautysalon.domain.Master;
import io.mathlina.beautysalon.domain.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepo extends JpaRepository<Comment, Long> {

  Optional<Comment> findByMasterAndClient(Master master, User client);

  Page<Comment> findAllByMaster(Master master, Pageable pageable);

  List<Comment> findAllByMaster(Master master);

}
