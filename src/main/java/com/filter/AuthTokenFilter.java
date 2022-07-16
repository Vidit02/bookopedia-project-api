package com.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

@Component
public class AuthTokenFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest req = ((HttpServletRequest) (request));
		StringBuffer url = req.getRequestURL();
		System.out.println("url : " + url);
		String authtoken = req.getHeader("authToken");
		String userid = req.getHeader("userid");
		System.out.println("authoken filter is called");
		System.out.println("token == >" + authtoken);
		if (url.toString().contains("/private/")) {
//			chain.doFilter(request, response);
			if (authtoken == null || userid == null) {
				HttpServletResponse resp = ((HttpServletResponse) (response));
				resp.setContentType("application/json");
				resp.setStatus(401);
				resp.getWriter().write("{'msg':'Please Login and access Service'}");
			} else {
				chain.doFilter(request, response);
			}
		} else {
			chain.doFilter(request, response);
		}
	}

}
