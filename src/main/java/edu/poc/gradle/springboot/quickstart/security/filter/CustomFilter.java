package edu.poc.gradle.springboot.quickstart.security.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.poc.gradle.springboot.quickstart.auth.service.SecurityServiceImpl;

public class CustomFilter implements Filter {
	
	private static final Logger logger = LoggerFactory.getLogger(SecurityServiceImpl.class);

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		System.out.println("init() called.");
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		System.out.println("doFilter() called.");
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		//UserPrincipal principal = (UserPrincipal) request.getUserPrincipal();
		String uri = request.getRequestURI();
		logger.debug("Requested URI: {0}", uri);
	}

	@Override
	public void destroy() {
		System.out.println("destroy() called.");
	}
}
