package io.mathlina.beautysalon.repos.jdbc;

import io.mathlina.beautysalon.domain.Comment;
import io.mathlina.beautysalon.domain.Master;
import io.mathlina.beautysalon.domain.User;
import io.mathlina.beautysalon.repos.CommentRepo;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

@Qualifier("commentRepoJdbc")
public interface CommentRepoJdbc extends CrudRepository<Comment, Long>, CommentRepo {

    Optional<Comment> findByMasterAndClient(Master master, User client);

    Page<Comment> findAllByMaster(Master master, Pageable pageable);

    List<Comment> findAllByMaster(Master master);

    Comment save(Comment comment);
}
