package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.RolesRepository;
import ru.kata.spring.boot_security.demo.dao.UserRepository;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserDetailsService {
    private final UserRepository repository;
    private final RolesRepository rolesRepository;

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    public UserServiceImpl(UserRepository repository, RolesRepository rolesRepository) {
        this.repository = repository;
        this.rolesRepository = rolesRepository;
    }

    @Transactional
    public List<User> getAllUser() {
        return repository.findAll();
    }

    @Transactional
    public void saveUser(User user) {
        repository.save(user);
    }

    @Transactional
    public User getUserById(long id) {
        return repository.getById(id);
    }

    @Transactional
    public void update(User user, String[] roles) {
        Set<Role> roleSet = rolesRepository.getRolesByName(Arrays.asList(roles));
        user.setRoles(roleSet);
        entityManager.merge(user);
    }

    @Transactional
    public List<Role> allRoles() {

        return rolesRepository.findAll();
    }

    @Transactional
    public void deleteByID(long id) {
        repository.deleteById(id);
    }

    @Transactional
    public User getUserByUsername(String name) {
        return repository.getByUsername(name);
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByUsername(username);
        return user;
    }
}
