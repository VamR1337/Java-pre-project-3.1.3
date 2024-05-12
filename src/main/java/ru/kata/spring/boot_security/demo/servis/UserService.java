package ru.kata.spring.boot_security.demo.servis;

import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import java.util.List;

public interface UserService {

    List<User> findAll();

    User findByUsername(String username);

    User findUserById(Long id);

    void updateUser(User user, Long id);

    void saveUser(User user);

    boolean deleteUserById(Long id);

}