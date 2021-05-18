package io.mathlina.beautysalon.repos;

import io.mathlina.beautysalon.domain.Master;
import io.mathlina.beautysalon.model.CommentModel;
import io.mathlina.beautysalon.model.UserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {

    Optional<CommentModel> findByMasterAndClient(Master master, UserModel client);

    Page<CommentModel> findAllByMaster(Master master, Pageable pageable);

    List<CommentModel> findAllByMaster(Master master);

    void save(CommentModel comment);
}
