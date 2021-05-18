package io.mathlina.beautysalon.repos.jpa;

import io.mathlina.beautysalon.domain.Service;
import io.mathlina.beautysalon.model.ServiceModel;
import io.mathlina.beautysalon.model.mapper.Mapper;
import io.mathlina.beautysalon.repos.MyServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
@Primary
public class MyServiceRepositoryJpa implements MyServiceRepository {

    @Autowired
    private Mapper mapper;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public ServiceModel findById(Long id) {
        Query query = this.entityManager.createQuery(
                "SELECT service FROM Service service " +
                "WHERE service.id = :id");
        query.setParameter("id", id);
        Service service = (Service) query.getSingleResult();

        return mapper.map(service, ServiceModel.class);
    }

    @Override
    public Page<ServiceModel> findAll(Pageable pageable) {
        Query query = this.entityManager.createQuery(
                "SELECT service FROM Service service");

        List<Service> services = query.getResultList();
        List<ServiceModel> serviceModel = mapper.mapAsList(services, ServiceModel.class);

        int pageSize = pageable.getPageSize();
        int page = pageable.getPageNumber();
        int last = Math.min(pageSize * (page + 1), serviceModel.size());

        return new PageImpl<>(serviceModel.subList(page * pageSize, last), pageable, serviceModel.size());
    }

    @Override
    public List<ServiceModel> findAll() {
        Query query = this.entityManager.createQuery(
                "SELECT service FROM Service service");
        List<Service> services = query.getResultList();

        return mapper.mapAsList(services, ServiceModel.class);
    }
}