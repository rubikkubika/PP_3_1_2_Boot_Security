package ru.kata.spring.boot_security.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.User;


public interface Repository extends JpaRepository<User, Long> {
    @Transactional
    @Modifying
    @Query("update User u set u.name = ?2, u.surname = ?3 where u.id = ?1")
    void update(long id, String name, String surname);

    User findByUsername(String username);

}
