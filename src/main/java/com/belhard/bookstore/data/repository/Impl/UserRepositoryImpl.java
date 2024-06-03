package com.belhard.bookstore.data.repository.Impl;

import com.belhard.bookstore.data.entity.User;
import com.belhard.bookstore.data.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Slf4j
@Transactional
public class UserRepositoryImpl implements UserRepository {

    private static final String FIND_BY_ID = "from User where id = :id and deleted = false";
    private static final String FIND_ALL = "from User where deleted = false";
    private static final String FIND_BY_EMAIL = "from User where email = :email and deleted = false";
    private static final String FIND_BY_LAST_NAME = "from User where last_name = :lastName and deleted = false";
    private static final String FIND_BY_LOGIN = "from User where login = :login and deleted = false";
    private static final String COUNT_ALL = "select count(*) from User";

    @PersistenceContext
    private EntityManager manager;

    @Override
    public User findById(long id) {
        try {
            return manager.createQuery(FIND_BY_ID, User.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<User> findByEmail(String email) {
        try {
            return manager.createQuery(FIND_BY_EMAIL, User.class)
                    .setParameter("email", email)
                    .getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<User> findByLastName(String lastName) {
        try {
            return manager.createQuery(FIND_BY_LAST_NAME, User.class)
                    .setParameter("lastName", lastName)
                    .getResultList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User findByLogin(String login) {
        try {
            return manager.createQuery(FIND_BY_LOGIN, User.class)
                    .setParameter("login", login)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }

    }

    @Override
    public List<User> findAll() {
        try {
            return manager.createQuery(FIND_ALL, User.class)
                    .getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public User save(User user) {
        if (user.getId() != null) {
            manager.merge(user);
            manager.flush();
            return findById(user.getId());
        } else {
            manager.persist(user);
            manager.flush();
            manager.refresh(user);
            return user;
        }
    }

    @Override
    public boolean delete(long id) {
        User user = manager.find(User.class, id);
        user.setDeleted(true);
        manager.flush();
        return true;
    }

    @Override
    public String findPasswordById(long id) {
        return manager.find(User.class, id).getPassword();
    }

    @Override
    public long countAll() {
        return Long.valueOf(manager.createQuery(COUNT_ALL).getFirstResult());
    }
}

