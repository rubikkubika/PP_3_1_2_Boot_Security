package ru.kata.spring.boot_security.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;

import java.util.List;
import java.util.Set;

public interface RolesRepository extends JpaRepository<Role, Long> {
    @Transactional
    @Query("select r from Role r where r.role in (:roles)")
    Set<Role> getRolesByName(List<String> roles);

    @Transactional
    @Query("select r from Role r")
    Set<Role> findAllRolesInSet();
}
