package com.belhard.bookstore.data.repository.Impl;

import com.belhard.bookstore.data.entity.Order;
import com.belhard.bookstore.data.repository.OrderRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Slf4j
@Transactional
public class OrderRepositoryImpl implements OrderRepository {

    private static final String FIND_ALL = "from Order where deleted = false";
    private static final String FIND_BY_ID = "from Order where id = :id and deleted = false";
    private static final String FIND_BY_USER_ID = "from Order where user.id = :user_id and deleted = false";

    @PersistenceContext
    private EntityManager manager;


    @Override
    public Order findById(long id) {
        try {
            return manager.createQuery(FIND_BY_ID, Order.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<Order> findAll() {
        try {
            return manager.createQuery(FIND_ALL, Order.class)
                    .getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<Order> findByUserId(long id) {
        try {
            return manager.createQuery(FIND_BY_USER_ID, Order.class)
                    .setParameter("user_id", id)
                    .getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public Order save(Order order) {
        if (order.getId() != null) {
            manager.merge(order);
            manager.flush();
            return findById(order.getId());
        } else {
            manager.persist(order);
            manager.flush();
            manager.refresh(order);
            return order;
        }
    }

    @Override
    public boolean delete(long id) {
        Order order = manager.find(Order.class, id);
        order.setDeleted(true);
        manager.flush();
        return true;
    }

}
