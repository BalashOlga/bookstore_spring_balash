package com.belhard.bookstore.data.repository.Impl;

import com.belhard.bookstore.data.conversion.UserDataСonversion;
import com.belhard.bookstore.data.dao.UserDao;
import com.belhard.bookstore.data.entity.User;
import com.belhard.bookstore.data.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Slf4j
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final UserDao userDao;

    @Override
    public User findById(long id) {
        try {
            return UserDataСonversion.toUser(userDao.findById(id));
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public User findByEmail(String email) {
        try {
            return UserDataСonversion.toUser(userDao.findByEmail(email));
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<User> findByLastName(String lastName) {
        return   userDao.findByLastName(lastName)
                .stream()
                .map(UserDataСonversion::toUser)
                .toList();
    }

    @Override
    public User findByLogin(String login) {
        try {
            return UserDataСonversion.toUser(userDao.findByLogin(login));
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<User> findAll() {
        return   userDao.findAll()
                .stream()
                .map(UserDataСonversion::toUser)
                .toList();
    }

    @Override
    public User create(User user) {
        try {
            return  UserDataСonversion.toUser(userDao.create(UserDataСonversion.toDto(user)));
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public User update(User user) {
        try {
            return UserDataСonversion.toUser(userDao.update(UserDataСonversion.toDto(user)));
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public boolean delete(long id) {
        return userDao.delete(id);
    }

    @Override
    public long countAll() {
        return userDao.countAll();
    }

    @Override
    public String findPasswordById(long id) {
        return userDao.findPasswordById(id);
    }
}

