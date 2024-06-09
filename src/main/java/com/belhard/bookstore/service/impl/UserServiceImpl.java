package com.belhard.bookstore.service.impl;

import com.belhard.bookstore.service.exception.NoEditException;
import com.belhard.bookstore.service.exception.NoValidException;
import com.belhard.bookstore.service.exception.NotFoundException;
import com.belhard.bookstore.data.entity.Role;
import com.belhard.bookstore.data.entity.User;
import com.belhard.bookstore.data.repository.UserRepository;
import com.belhard.bookstore.service.UserService;
import com.belhard.bookstore.service.dto.UserDto;
import com.belhard.bookstore.service.dto.UserDtoLogin;
import com.belhard.bookstore.service.dto.UserDtoWithoutPassword;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private UserDto toDto(User user) {
        UserDto userDto = new UserDto();

        userDto.setId(user.getId());
        userDto.setLogin(user.getLogin());
        userDto.setPassword(user.getPassword());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        userDto.setRole(user.getRole());

        return userDto;
    }

    private UserDtoWithoutPassword toDtoWithoutPassport(User user) {
        UserDtoWithoutPassword userDto = new UserDtoWithoutPassword();

        userDto.setId(user.getId());
        userDto.setLogin(user.getLogin());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        userDto.setRole(user.getRole());

        return userDto;
    }

    private UserDtoLogin toDtoLogin(User user) {
        UserDtoLogin userDtoLogin = new UserDtoLogin();

        userDtoLogin.setId(user.getId());
        userDtoLogin.setLogin(user.getLogin());
        userDtoLogin.setPassword(user.getPassword());

        return userDtoLogin;
    }

    private User toUser(UserDto userDto) {
        User user = new User();

        user.setId(userDto.getId());
        user.setLogin(userDto.getLogin());
        user.setPassword(userDto.getPassword());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setRole(userDto.getRole());


        return user;
    }

    private User toUser(UserDtoLogin userDtoLogin) {
        User user = new User();

        user.setLogin(userDtoLogin.getLogin());
        user.setPassword(userDtoLogin.getPassword());
        user.setEmail(userDtoLogin.getLogin());
        user.setRole(userDtoLogin.getRole());
        return user;
    }

    @Override
    public UserDtoWithoutPassword getById(long id) {
        log.debug("Calling getById");

        User user = userRepository.findById(id);

        if (user == null) {
            throw new NotFoundException("User by id = " + id + " is not found!");
        } else {
            return toDtoWithoutPassport(user);
        }
    }

    @Override
    public List<UserDtoWithoutPassword> getByEmail(String email) {
        log.debug("Calling getByEmail");

        List<User> listUser = userRepository.findByEmail(email);

        if (listUser.isEmpty()) {
            throw new NotFoundException("User by email = " + email + " is not found!");
        } else {
            return listUser
                    .stream()
                    .map(this::toDtoWithoutPassport)
                    .toList();
        }
    }

    @Override
    public List<UserDtoWithoutPassword> getByLastName(String lastName) {
        log.debug("Calling getByLastName");


        List<User> listUser = userRepository.findByLastName(lastName);


        if (listUser.isEmpty()) {
            throw new NotFoundException("Users by lastnName = " + lastName + " are not found!");
        } else {
            return listUser
                    .stream()
                    .map(this::toDtoWithoutPassport)
                    .toList();
        }
    }

    @Override
    public UserDtoWithoutPassword getByLogin(String login) {
        log.debug("Calling getByLogin");

        User user = userRepository.findByLogin(login);

        if (user == null) {
            throw new NotFoundException("User by login = " + login + " is not found!");
        } else {
            return toDtoWithoutPassport(user);
        }
    }

    @Override
    public List<UserDtoWithoutPassword> getAll() {
        log.debug("Calling getAll");

        List<User> listUser = userRepository.findAll();


        if (listUser.isEmpty()) {
            throw new NotFoundException("Users are not found!");
        } else {
            return listUser
                    .stream()
                    .map(this::toDtoWithoutPassport)
                    .toList();
        }
    }

    @Override
    public UserDtoLogin create(UserDtoLogin userDtoLogin) {
        log.debug("Calling create");
        userDtoLogin.setRole(Role.valueOf("CUSTOMER"));
        String loginToBeSaved = userDtoLogin.getLogin();
        User byLogin = userRepository.findByLogin(loginToBeSaved);
        if (byLogin != null) {
            throw new NoValidException("No valid login " + userDtoLogin.getLogin() + "! User is not created!");
        }
        User user = userRepository.save(toUser(userDtoLogin));

        if (user == null) {
            throw new NoEditException("User is not create!");
        } else {
            return toDtoLogin(user);
        }
    }


    @Override
    public UserDto update(UserDto userDto) {
        log.debug("Calling update");

        String loginToBeUpdate = userDto.getLogin();
        User byLogin = userRepository.findByLogin(loginToBeUpdate);

        if (byLogin != null && !byLogin.getId().equals(userDto.getId())) {
            throw new NoValidException("No valid login" + userDto.getLogin() + "! User is not updated!");
        }
        if (userDto.getPassword() == null) {
            userDto.setPassword(byLogin.getPassword());
        }

        if (userDto.getRole() == null) {
            userDto.setRole(byLogin.getRole());
        }

        User user = userRepository.save(toUser(userDto));
        if (user == null) {
            throw new NotFoundException("User is not update!");
        } else {
            return toDto(user);
        }
    }

    @Override
    public void delete(long id) {
        log.debug("Calling delete");

        if (!userRepository.delete(id)) {
            throw new NoEditException("Deletion error by id = " + id + "!");
        }
    }

    @Override
    public long getCountAll() {
        log.debug("Calling getCountAll");

        return userRepository.countAll();
    }

    @Override
    public UserDtoLogin login(String login, String password) {
        log.debug("Calling login");

        User user = userRepository.findByLogin(login);

        if (user == null) {
            throw new NoValidException("No login!");
        }
        if (!password.equals(user.getPassword())) {
            throw new NoValidException("No login!");
        }
        return toDtoLogin(user);
    }

    @Override
    public String getPassword(long id) {
        log.debug("Calling getPassword");

        String password = userRepository.findPasswordById(id);
        return password;
    }
}
