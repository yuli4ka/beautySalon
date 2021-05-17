package io.mathlina.beautysalon.repos.jpa;

import io.mathlina.beautysalon.domain.Comment;
import io.mathlina.beautysalon.domain.Master;
import io.mathlina.beautysalon.domain.User;
import io.mathlina.beautysalon.repos.CommentRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepoJpa extends JpaRepository<Comment, Long>, CommentRepo {

  Optional<Comment> findByMasterAndClient(Master master, User client);

  Page<Comment> findAllByMaster(Master master, Pageable pageable);

  List<Comment> findAllByMaster(Master master);

}
