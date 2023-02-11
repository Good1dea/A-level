package com.sydoruk.repository;

import com.sydoruk.config.HibernateUtil;
import com.sydoruk.model.CarHibernate;

import javax.persistence.EntityManager;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

public class CarHibernateRepository implements InterfaceRepository<CarHibernate>{

    @Override
    public void save(@Valid final CarHibernate car) {
        final EntityManager entityManager = HibernateUtil.getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(car);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Override
    public List<CarHibernate> getAll() {
        final EntityManager entityManager = HibernateUtil.getEntityManager();
        return entityManager.createQuery("from  CarHibernate", CarHibernate.class)
                .getResultList();
    }

    @Override
    public Optional<CarHibernate> getById(String id) {
        final EntityManager entityManager = HibernateUtil.getEntityManager();
        return Optional.ofNullable(entityManager.find(CarHibernate.class, id));
    }

    @Override
    public void delete(String id) {
        final EntityManager entityManager = HibernateUtil.getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.createQuery("delete from CarHibernate where  id = :id_car")
                .setParameter("id_car", id)
                .executeUpdate();
        entityManager.getTransaction().commit();
    }
}