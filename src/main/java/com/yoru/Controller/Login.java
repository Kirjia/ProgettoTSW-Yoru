package com.yoru.Controller;

import java.io.IOException;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
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

import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.yoru.model.DAO.AuthDAO;
import com.yoru.model.DAO.AutoreDAO;
import com.yoru.model.DAO.OrderDAO;
import com.yoru.model.DAO.UserDAO;
import com.yoru.model.Entity.CartItem;
import com.yoru.model.Entity.User;
import com.yoru.model.Entity.UserAuthToken;

import Util.Argon2Hashing;


/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger( Login.class.getName() );
	private static final String REMEMBER_ME = "true";
	
	private UserDAO userDAO;
	private OrderDAO cartDAO;
	private AuthDAO authDAO;
	
  
	
	
    public Login() {
        // TODO Auto-generated constructor stub
    }

    
	public void init() throws ServletException {
		super.init();
		DataSource ds = (DataSource) super.getServletContext().getAttribute("DataSource");
		userDAO = new UserDAO(ds);
		cartDAO = new OrderDAO(ds);
		authDAO = new AuthDAO(ds);
		
	}


	public void destroy() {
		// TODO Auto-generated method stub
	}

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		JSONObject jsonObject = new JSONObject();
		
		

		
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String boString = request.getParameter("rememberMe");
		boolean rememberMe = false;
		User user = null;
		
		
		if (boString!= null && boString.equals(REMEMBER_ME)) {
			rememberMe = true;
		}
		
		

		if(email == null && password == null) {
			System.out.println("login fallita");
			try {
				jsonObject.append("outcome", false);
			} catch (JSONException e) {
				LOGGER.log(Level.SEVERE, "Login error: " + e.getMessage());
			}
		}
		else {
			
			try {
				user = userDAO.login(email, password);
			} catch (SQLException e) {
				LOGGER.log(Level.SEVERE, "Login error: " + e.getMessage());
			}
			
			 try {
			
				if (user != null) {
					System.out.println("login");
					HttpSession session = request.getSession(true);
					session.setAttribute("user", user);
					
					mergeCart(session, user.getId());
					
					jsonObject.append("outcome", true);
					if (rememberMe) {
						String selector = RandomStringUtils.randomAlphabetic(16);
					 	String rawValidator =  Argon2Hashing.generateToken();
					 	String validatorString = Argon2Hashing.hashPassword(rawValidator);
					 	
					 	UserAuthToken userAuthToken = new UserAuthToken();
					 	userAuthToken.setUserID(user.getId());
					 	userAuthToken.setSelector(selector);
					 	userAuthToken.setValidator(validatorString);
					 	authDAO.insert(userAuthToken);
					 	
					 	
					 	Cookie cookieSelector = new Cookie("selector", selector);
					 	cookieSelector.setMaxAge(604800);
					 	 
					 	Cookie cookieValidator = new Cookie("validator", rawValidator);
					 	cookieValidator.setMaxAge(604800);
					 	 
					 	response.addCookie(cookieSelector);
					 	response.addCookie(cookieValidator);
					}
				}else {
					jsonObject.append("outcome", false);
				}
	        } catch (Exception e) {
	        	LOGGER.log(Level.SEVERE, "Login error: " + e.getMessage());
	        }

	        response.getWriter().print(jsonObject);
			
			
		}
		
		
		
		
	}
	
	@SuppressWarnings("unchecked")
	private void mergeCart(HttpSession session, int user_id) {
		
		HashMap<String, Integer> items = (HashMap<String, Integer>) session.getAttribute("cart");
		if(items != null) {
			try {
				cartDAO.mergeCart(user_id, items);
			} catch (SQLException e) {
				LOGGER.log(Level.WARNING, "errore nel merge del carrello: " + e.getMessage());
			}
		}
		session.removeAttribute("cart");
		
	}
	


}
