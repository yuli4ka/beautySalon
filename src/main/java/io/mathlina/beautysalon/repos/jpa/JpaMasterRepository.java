package io.mathlina.beautysalon.repos.jpa;

import io.mathlina.beautysalon.domain.Master;
import io.mathlina.beautysalon.model.MasterModel;
import io.mathlina.beautysalon.model.mapper.Mapper;
import io.mathlina.beautysalon.repos.MasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public class JpaMasterRepository implements MasterRepository {

    @Autowired
    private Mapper mapper;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public MasterModel findById(Long id) {
        Query query = this.entityManager.createQuery(
                "SELECT master FROM Master master " +
                        "WHERE master.id = :id");
        query.setParameter("id", id);

        Master master = (Master) query.getSingleResult();
        return mapper.map(master, MasterModel.class);
    }

    public Page<MasterModel> findAllByNameContainingOrSurnameContaining(String name, String surname,
                                                                        Pageable pageable) {
        Query query = this.entityManager.createQuery(
                "SELECT master FROM Master master " +
                        "WHERE lower(master.name) LIKE lower(:name) AND lower(master.surname) LIKE lower(:surname)");
        query.setParameter("name", "%" + name + "%");
        query.setParameter("surname", "%" + surname + "%");

        List<Master> masters = query.getResultList();

        if (masters.isEmpty()) {
            return new PageImpl<>(Collections.emptyList());
        }

        List<MasterModel> masterModels = mapper.mapAsList(masters, MasterModel.class);

        int pageSize = pageable.getPageSize();
        int page = pageable.getPageNumber();
        int last = Math.min(pageSize * (page + 1), masterModels.size());

        return new PageImpl<>(masterModels.subList(page * pageSize, last), pageable, masterModels.size());
    }

    @Override
    public Page<MasterModel> findAll(Pageable pageable) {
        Query query = this.entityManager.createQuery(
                "SELECT master FROM Master master");
        List<Master> masters = query.getResultList();

        if (masters.isEmpty()) {
            return new PageImpl<>(Collections.emptyList());
        }

        List<MasterModel> masterModels = mapper.mapAsList(masters, MasterModel.class);

        int pageSize = pageable.getPageSize();
        int page = pageable.getPageNumber();
        int last = Math.min(pageSize * (page + 1), masterModels.size());

        return new PageImpl<>(masterModels.subList(page * pageSize, last), pageable, masterModels.size());
    }

    @Override
    public List<MasterModel> findAll() {
        Query query = this.entityManager.createQuery(
                "SELECT master FROM Master master");
        List<Master> masters = query.getResultList();

        if (masters.isEmpty()) {
            return Collections.emptyList();
        }
        return mapper.mapAsList(masters, MasterModel.class);
    }

    @Override
    public void save(MasterModel masterModel) {
        Master master = mapper.map(masterModel, Master.class);
        if (master.getId() == null) {
            this.entityManager.persist(master);
        } else {
            this.entityManager.merge(master);
        }
    }

}
