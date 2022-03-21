package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.Repository;
import ru.kata.spring.boot_security.demo.model.User;


import java.util.List;

@Service
public class UserServiceImpl implements UserDetailsService {
    private Repository repository;

    @Autowired
    public UserServiceImpl(Repository repository) {
        this.repository = repository;
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
    public void update(long id, String name, String surname) {
        repository.update(id, name, surname);
    }

    @Transactional
    public void deleteByID(long id) {
        repository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByUsername(username);
        return user;
    }
}
