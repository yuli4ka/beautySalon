package io.mathlina.beautysalon.repos.jpa;

import io.mathlina.beautysalon.domain.Comment;
import io.mathlina.beautysalon.domain.Master;
import io.mathlina.beautysalon.model.CommentModel;
import io.mathlina.beautysalon.model.UserModel;
import io.mathlina.beautysalon.model.mapper.Mapper;
import io.mathlina.beautysalon.repos.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@Repository
@Primary
public class JpaCommentRepositoryImpl implements CommentRepository {

    @Autowired
    private Mapper mapper;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<CommentModel> findByMasterAndClient(Master master, UserModel client) {
        Query query = this.entityManager.createQuery(
                "SELECT comm FROM Comment comm " +
                        "WHERE comm.master.id = :masterId AND comm.client.id = :clientId");
        query.setParameter("masterId", master.getId());
        query.setParameter("clientId", client.getId());
        Comment com = (Comment) query.getSingleResult();

        return Optional.ofNullable(mapper.map(com, CommentModel.class));
    }

    @Override
    public Page<CommentModel> findAllByMaster(Master master, Pageable pageable) {
        Query query = this.entityManager.createQuery(
                "SELECT comm FROM Comment comm " +
                        "WHERE comm.master.id = :masterId");
        query.setParameter("masterId", master.getId());

        List<Comment> com = query.getResultList();
        List<CommentModel> commentModels = mapper.mapAsList(com, CommentModel.class);

        int pageSize = pageable.getPageSize();
        int page = pageable.getPageNumber();
        int last = Math.min(pageSize * (page + 1), commentModels.size());

        return new PageImpl<>(commentModels.subList(page * pageSize, last), pageable, commentModels.size());
    }

    @Override
    public List<CommentModel> findAllByMaster(Master master) {
        Query query = this.entityManager.createQuery(
                "SELECT comm FROM Comment comm " +
                        "WHERE comm.master.id = :masterId");
        query.setParameter("masterId", master.getId());
        List<Comment> com = query.getResultList();

        return mapper.mapAsList(com, CommentModel.class);
    }

    @Override
    public void save(CommentModel commentModel) {
        Comment comment = mapper.map(commentModel, Comment.class);
        if (comment.getId() == null) {
            this.entityManager.persist(comment);
        } else {
            this.entityManager.merge(comment);
        }
    }

}
