package com.sydoruk.repository;

import com.sydoruk.config.HibernateUtil;
import com.sydoruk.model.OrderHibernate;

import javax.persistence.EntityManager;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

public class OrderHibernateRepository implements InterfaceRepository<OrderHibernate>{
    @Override
    public void save(@Valid final OrderHibernate order) {
        final EntityManager entityManager = HibernateUtil.getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(order);
        entityManager.getTransaction().commit();
        entityManager.close();

    }

    @Override
    public List<OrderHibernate> getAll() {
        final EntityManager entityManager = HibernateUtil.getEntityManager();
        return entityManager.createQuery("from OrderHibernate", OrderHibernate.class)
                .getResultList();
    }

    @Override
    public Optional<OrderHibernate> getById(String id) {
        final EntityManager entityManager = HibernateUtil.getEntityManager();
        return Optional.ofNullable(entityManager.find(OrderHibernate.class, id));
    }

    @Override
    public void delete(String id) {
        final EntityManager entityManager = HibernateUtil.getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.createQuery("delete from OrderHibernate where  id = :id_order")
                .setParameter("id_order", id)
                .executeUpdate();
        entityManager.getTransaction().commit();
    }
}