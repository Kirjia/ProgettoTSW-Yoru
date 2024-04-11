package com.yoru.Controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.crypto.Cipher;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import com.yoru.model.DAO.AuthDAO;
import com.yoru.model.Entity.UserAuthToken;

/**
 * Servlet Filter implementation class LoginFilter
 */
@WebFilter("/LoginFilter")
public class LoginFilter extends HttpFilter implements Filter {
	
	private static final Logger LOGGER = Logger.getLogger(LoginFilter.class.getName());
	
    private AuthDAO authDAO;
    
	public void init(FilterConfig fConfig) throws ServletException {
		DataSource ds = (DataSource) fConfig.getServletContext().getAttribute("DataSource");
		authDAO = new AuthDAO(ds);
		
	}
	
    public LoginFilter() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		HttpSession session = httpRequest.getSession(false);
		 
		boolean loggedIn = session != null && session.getAttribute("loggedCustomer") != null;
		 
		Cookie[] cookies = httpRequest.getCookies();
		 
		if (!loggedIn && cookies != null) {
		    // process auto login for remember me feature
		    String selector = "";
		    String rawValidator = "";  
		     
		    for (Cookie aCookie : cookies) {
		        if (aCookie.getName().equals("selector")) {
		            selector = aCookie.getValue();
		        } else if (aCookie.getName().equals("validator")) {
		            rawValidator = aCookie.getValue();
		        }
		    }
		     
		    if (!"".equals(selector) && !"".equals(rawValidator)) {
		       
		        try {
					UserAuthToken token = authDAO.findBySelector(selector);
				} catch (SQLException e) {
					LOGGER.log(Level.WARNING, "Login error", e);
				}

		        /*
		         
		        if (token != null) {
		            String hashedValidatorDatabase = token.getValidator();
		            //String hashedValidatorCookie = HashGenerator.generateSHA256(rawValidator);
		             
		            if (hashedValidatorCookie.equals(hashedValidatorDatabase)) {
		                session = httpRequest.getSession();
		                session.setAttribute("loggedCustomer", token.getCustomer());
		                loggedIn = true;
		                 
		                // update new token in database
		                String newSelector = RandomStringUtils.randomAlphanumeric(12);
		                String newRawValidator =  RandomStringUtils.randomAlphanumeric(64);
		                 
		                String newHashedValidator = HashGenerator.generateSHA256(newRawValidator);
		                 
		                token.setSelector(newSelector);
		                token.setValidator(newHashedValidator);
		                authDAO.update(token);
		                 
		                // update cookie
		                Cookie cookieSelector = new Cookie("selector", newSelector);
		                cookieSelector.setMaxAge(604800);
		                 
		                Cookie cookieValidator = new Cookie("validator", newRawValidator);
		                cookieValidator.setMaxAge(604800);
		                 
		                httpResponse.addCookie(cookieSelector);
		                httpResponse.addCookie(cookieValidator);                       
		                 
		            }*/
		        }
		    }
		chain.doFilter(request, response);
	}


}
