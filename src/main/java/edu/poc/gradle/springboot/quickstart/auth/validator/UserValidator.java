package edu.poc.gradle.springboot.quickstart.auth.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import edu.poc.gradle.springboot.quickstart.auth.model.User;
import edu.poc.gradle.springboot.quickstart.auth.service.UserService;

@Component
public class UserValidator implements Validator {
    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }
    
	public String validate(String userName, String password) {
		String errorMessage = null;
        // Check for user name basic validation TODO: Use regex
    	if (StringUtils.isEmpty(userName)) {
        	errorMessage = "Size.userForm.username";
        } else if (StringUtils.isEmpty(password)) {
        	errorMessage = "Size.userForm.password";
        }
    	
		return errorMessage;
	}

    public String validate(User user) {

    	String errorMessage = null;
        // Check for user name basic validation TODO: Use regex
    	if (StringUtils.isEmpty(user.getUsername()) || user.getUsername().length() < 6 || user.getUsername().length() > 32) {
        	errorMessage = "Size.userForm.username";
        }
        
        // Check for user name database validation
        if (userService.findByUsername(user.getUsername()) != null) {
        	errorMessage = "Duplicate.userForm.username";
        }

        // Check for password basic validation TODO: Use regex
        if (StringUtils.isEmpty(user.getPassword()) || user.getPassword().length() < 8 || user.getPassword().length() > 32) {
        	errorMessage = "Size.userForm.password";
        }

        // Check for confirm password validation
        if (!user.getPasswordConfirm().equals(user.getPassword())) {
        	errorMessage = "Diff.userForm.passwordConfirm";
        }
        
        return errorMessage;
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty");
        if (user.getUsername().length() < 6 || user.getUsername().length() > 32) {
            errors.rejectValue("username", "Size.userForm.username");
        }
        if (userService.findByUsername(user.getUsername()) != null) {
            errors.rejectValue("username", "Duplicate.userForm.username");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
        if (user.getPassword().length() < 8 || user.getPassword().length() > 32) {
            errors.rejectValue("password", "Size.userForm.password");
        }

        if (!user.getPasswordConfirm().equals(user.getPassword())) {
            errors.rejectValue("passwordConfirm", "Diff.userForm.passwordConfirm");
        }
    }
}