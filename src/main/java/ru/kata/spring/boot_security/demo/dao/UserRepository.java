package ru.kata.spring.boot_security.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.Set;


public interface UserRepository extends JpaRepository<User, Long> {
    @Transactional
    @Modifying
    @Query("update User u set u.name = ?2, u.surname = ?3, u.roles = ?4 where u.id = ?1")
    void update(long id, String name, String surname,Set<Role> roleSet);

    User findByUsername(String username);

    User getByUsername(String username);

    User getByName(String name);

}