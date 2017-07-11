package edu.poc.gradle.springboot.quickstart.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
    private UserDetailsService userDetailsService;
	
	@Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	// Authentication : User --> Roles
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
			.withUser("user").password("user").roles("USER")
			.and()
			.withUser("admin").password("admin").roles("USER", "ADMIN");
	}

	// Authorization : Role -> Access
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		// Note: Working piece.
		http.httpBasic().and().authorizeRequests()
			.antMatchers("/login", "/authenticate", "/registration").permitAll()
			.antMatchers("/**").hasRole("ADMIN")
			.antMatchers("/topics/**").hasRole("USER")
			.and().csrf().disable().headers().frameOptions().disable();
		
		// http.authorizeRequests().anyRequest().permitAll().and().httpBasic(); --> Allow all requests
		// http.authorizeRequests().anyRequest().fullyAuthenticated().and().httpBasic(); --> Authenticate all request
		
		/*
		// Note: Using custom filter
		http.authorizeRequests()
			.antMatchers("/**").hasRole("USER")
			.and()
			.addFilterBefore(customFilter(), BasicAuthenticationFilter.class)
			.httpBasic();
		
		Note: Other options
		http.authorizeRequests()
			.antMatchers("/login").permitAll()
			.antMatchers("/admin/**").access("hasRole('ADMIN')")
			.antMatchers("/**").access("hasRole('USER')")
			.and()
			.formLogin().loginPage("/login")
			.usernameParameter("username")
			.passwordParameter("password")
			.defaultSuccessUrl("/index")
			.failureUrl("/login?error");
		*/
		
	}
	
	/*
	@Bean
	public Filter customFilter() {
		return new CustomFilter();
	}
	*/

	@Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }
}
