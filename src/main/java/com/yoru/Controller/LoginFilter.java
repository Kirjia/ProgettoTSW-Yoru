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

import org.apache.commons.lang3.RandomStringUtils;

import com.yoru.model.DAO.AuthDAO;
import com.yoru.model.Entity.UserAuthToken;

import Util.Argon2Hashing;

/**
 * Servlet Filter implementation class LoginFilter
 */
@WebFilter("/Login")
public class LoginFilter extends HttpFilter implements Filter {
	

	private static final long serialVersionUID = 7049656670586656561L;

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

    
	public void destroy() {

	}


	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		HttpSession session = httpRequest.getSession(false);
		
		 
		boolean loggedIn = session != null && session.getAttribute("user") != null;
		 
		Cookie[] cookies = httpRequest.getCookies();
		 
		if (!loggedIn && cookies != null) {
		    // process auto login for remember me feature
		    String selector = "";
		    String rawValidator = "";
		    UserAuthToken token = null;
		     
		    for (Cookie aCookie : cookies) {
		        if (aCookie.getName().equals("selector")) {
		            selector = aCookie.getValue();
		        } else if (aCookie.getName().equals("validator")) {
		            rawValidator = aCookie.getValue();
		        }
		    }
		     
		    if (!"".equals(selector) && !"".equals(rawValidator)) {
		       
		        try {
		        	System.out.println(selector);
					token = authDAO.findBySelector(selector);
				} catch (SQLException e) {
					LOGGER.log(Level.WARNING, "Login error", e);
				}

		        
		         
		        if (token != null) {
		            String hashedValidatorDatabase = token.getValidator();
		             
		            if (Argon2Hashing.checkPass(hashedValidatorDatabase, rawValidator)) {
		                session = httpRequest.getSession();
		                //session.setAttribute("loggedCustomer", token.getCustomer());
		                loggedIn = true;
		                 
		                // update new token in database
		                String newSelector = RandomStringUtils.randomAlphabetic(16);
		                String newRawValidator =  Argon2Hashing.generateToken();
		                 
		                String newHashedValidator = Argon2Hashing.hashPassword(newRawValidator);
		                 
		                token.setSelector(newSelector);
		                token.setValidator(newHashedValidator);
		                try {
							authDAO.update(token);
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							LOGGER.log(Level.WARNING, "Login error", e);
						}
		                 
		                // update cookie
		                Cookie cookieSelector = new Cookie("selector", newSelector);
		                cookieSelector.setMaxAge(604800);
		                 
		                Cookie cookieValidator = new Cookie("validator", newRawValidator);
		                cookieValidator.setMaxAge(604800);
		                 
		                httpResponse.addCookie(cookieSelector);
		                httpResponse.addCookie(cookieValidator);                       
		                httpResponse.sendRedirect("..Yoru/jsp/home.jsp");
		            }
		        }
		    }
		chain.doFilter(request, response);
		}
	}

}
