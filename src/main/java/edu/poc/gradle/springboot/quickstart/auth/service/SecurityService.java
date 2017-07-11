package edu.poc.gradle.springboot.quickstart.auth.service;

public interface SecurityService {
	
    String findLoggedInUsername();

    void autologin(String username, String password);
}