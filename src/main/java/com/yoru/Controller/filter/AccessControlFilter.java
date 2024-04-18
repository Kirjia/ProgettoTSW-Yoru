package com.yoru.Controller.filter;

import java.io.IOException;
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

import com.yoru.model.Entity.Role;
import com.yoru.model.Entity.User;



@WebFilter(filterName = "/AccessControlFilter", urlPatterns = "/*")
public class AccessControlFilter extends HttpFilter implements Filter {
       


	private static final long serialVersionUID = -7435455108706561755L;
	
	private static final String ADMIN = "/admin/";
	private static final String BASED = "/based/";



	public AccessControlFilter() {
        super();

    }


	public void destroy() {

	}


	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		
		User user = (User) httpServletRequest.getSession(false).getAttribute("user");
		String pathString = httpServletRequest.getServletPath();
		
		if ((user==null || user.getRole().compareTo(Role.ADMIN) != 0)  && pathString.contains(ADMIN)) {
			httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/login.jsp");
			return;
			
		}else if (pathString.contains(BASED) && user == null) {
			httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/login.jsp");
			return;
		}
		
		

		chain.doFilter(request, response);
	}


	
	public void init(FilterConfig fConfig) throws ServletException {

	}

}
