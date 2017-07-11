package edu.poc.gradle.springboot.quickstart.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import edu.poc.gradle.springboot.quickstart.auth.model.User;

@Service
public class UserServiceImpl implements UserService {
	
	/*    
	@Autowired
    private UserRepository userRepository;
    
    @Autowired
    private RoleRepository roleRepository;
    */
    
	@Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    
    @Override
    public void save(User user) {
    	//user.setPassword(user.getPassword());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        //user.setRoles(new HashSet<>(roleRepository.findAll()));
        //userRepository.save(user);
        System.out.println("User Saved.");
    }

    @Override
    public User findByUsername(String username) {
        //return userRepository.findByUsername(username);
    	
    	User user = new User();
    	user.setUsername(username);
    	user.setId((long) 1234);
    	//user.setRoles();
    	user.setPassword("1234");
    	user.setPasswordConfirm("1234");
    	return user;
    }
}