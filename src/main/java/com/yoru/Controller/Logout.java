package com.yoru.Controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import com.yoru.model.DAO.AuthDAO;
import com.yoru.model.Entity.UserAuthToken;

/**
 * Servlet implementation class Logout
 */
@WebServlet("/Logout")
public class Logout extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(Logout.class.getName());
       
	
	private AuthDAO authDAO;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Logout() {
        super();
        // TODO Auto-generated constructor stub
    }

    @Override
    public void init() throws ServletException {
    	// TODO Auto-generated method stub
    	super.init();
    	DataSource ds = (DataSource) super.getServletContext().getAttribute("DataSource");
    	authDAO = new AuthDAO(ds);
    }
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		 session.removeAttribute("user");
		 session.invalidate();
		 
	     
		    Cookie[] cookies = request.getCookies();
		     
		    if (cookies != null) {
		        String selector = "";
		         
		        for (Cookie aCookie : cookies) {
		            if (aCookie.getName().equals("selector")) {
		                selector = aCookie.getValue();
		            }
		        }
		         
		        if (!selector.isEmpty()) {
		            // delete token from database

		            UserAuthToken token = null;
					try {
						token = authDAO.findBySelector(selector);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						LOGGER.log(Level.SEVERE, "Logout error: " + e.getMessage());
					}
		             
		            if (token != null) {
		                try {
							if(!authDAO.remove(token))
								LOGGER.log(Level.ALL, "logout error");
						} catch (SQLException e) {
							LOGGER.log(Level.SEVERE, "Logout error: " + e.getMessage());
						}
		                 
		                Cookie cookieSelector = new Cookie("selector", "");
		                cookieSelector.setMaxAge(0);
		                 
		                Cookie cookieValidator = new Cookie("validator", "");
		                cookieValidator.setMaxAge(0);
		                response.addCookie(cookieSelector);
		                response.addCookie(cookieValidator);                   
		            }
		        }
		    }
		    
		    response.sendRedirect("./login.jsp");
	
	}

}
