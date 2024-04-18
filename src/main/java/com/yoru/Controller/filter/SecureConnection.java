package com.yoru.Controller.filter;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet Filter implementation class SecureConnection
 */
@WebFilter(filterName = "/SecureConnection", urlPatterns="/*")
public class SecureConnection extends HttpFilter implements Filter {
       
	private static final String SECURE = "https:localhost";
	
	
    public SecureConnection() {
        super();
        // TODO Auto-generated constructor stub
    }


	public void destroy() {

	}


	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;

		
		
		
		if (!request.isSecure()) {
			String path = httpServletRequest.getRequestURI();
			httpServletResponse.sendRedirect(SECURE + path);
			return;
		}
		
		
		
		
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
