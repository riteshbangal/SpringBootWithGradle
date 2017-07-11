package edu.poc.gradle.springboot.quickstart.auth.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import edu.poc.gradle.springboot.quickstart.auth.model.User;
import edu.poc.gradle.springboot.quickstart.auth.service.SecurityService;
import edu.poc.gradle.springboot.quickstart.auth.service.UserService;
import edu.poc.gradle.springboot.quickstart.auth.validator.UserValidator;
import edu.poc.gradle.springboot.quickstart.upload.controller.RestUploadController;

@RestController
public class UserController {
	
	private final Logger logger = LoggerFactory.getLogger(RestUploadController.class);
	
    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;
    
	@RequestMapping(value="/authenticate", method=RequestMethod.POST)
	@ResponseBody
	public String authenticate(@RequestBody Map<String, String> inputs) {

		String userName = inputs.get("username");
		String password = inputs.get("password");
		logger.debug("User Name: {0} and Password: ", userName, password);
		String errorMessage = userValidator.validate(userName, password);
		if (!StringUtils.isEmpty(errorMessage)) { // TODO: Create custom util and implement isBlank method 
			return errorMessage;
		}

		return "authentication successful";
	}

	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String login(@RequestBody Map<String, String> inputs) {
		String userName = inputs.get("username");
		String password = inputs.get("password");
		logger.debug("User Name: {0} and Password: ", userName, password);
		String errorMessage = userValidator.validate(userName, password);
		if (!StringUtils.isEmpty(errorMessage)) { // TODO: Create custom util and implement isBlank method 
			return errorMessage;
		}

		// Do auto login for logged in user.
		securityService.autologin(userName, password);

		return "login successful";
	}

	@RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(@RequestBody User user) {
		
		if (user != null) {
			String errorMessage = userValidator.validate(user);
			if (!StringUtils.isEmpty(errorMessage)) { // TODO: Create custom util and implement isBlank method 
				return errorMessage;
			}
			
			// Insert user details into database
			userService.save(user);

			// Do auto login for saved user.
	        securityService.autologin(user.getUsername(), user.getPasswordConfirm());
		}

		return "Registration successful";
    }
	
    /*
    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(Model model) {
        model.addAttribute("userForm", new User());

        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult, Model model) {
        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "registration";
        }

        userService.save(userForm);

        securityService.autologin(userForm.getUsername(), userForm.getPasswordConfirm());

        return "redirect:/welcome";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, String error, String logout) {
        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");

        return "login";
    }
    */
	
	@RequestMapping(value={"/", "/welcome"}, method=RequestMethod.GET)
    public String welcome() {
        return "welcome";
    }
}
