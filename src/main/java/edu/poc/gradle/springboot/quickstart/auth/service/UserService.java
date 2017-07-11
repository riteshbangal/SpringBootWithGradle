package edu.poc.gradle.springboot.quickstart.auth.service;

import edu.poc.gradle.springboot.quickstart.auth.model.User;

public interface UserService {
    void save(User user);

    User findByUsername(String username);
}