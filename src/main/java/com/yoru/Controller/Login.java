package com.yoru.Controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.json.JSONObject;

import com.yoru.model.DAO.AutoreDAO;
import com.yoru.model.DAO.UserDAO;
import com.yoru.model.Entity.User;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger( Login.class.getName() );
	
	private DataSource ds;
	
  
	
	
    public Login() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init() throws ServletException {
		super.init();
		ds = (DataSource) super.getServletContext().getAttribute("DataSource");
	}

	/**
	 * @see Servlet#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		User user = null;
		
		if(email == null && password == null)
			System.out.println("login fallita");
		else {
			UserDAO userDAO = new UserDAO(ds);
			try {
				user = userDAO.login(email, password);
			} catch (SQLException e) {
				LOGGER.log(Level.SEVERE, "Login error", e);
			}
			
			if (user != null) {
				HttpSession session = request.getSession(true);
				session.setAttribute("User", user);
				response.setContentType("text/html");
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/home.jsp");
				dispatcher.forward(request, response);
				
				return;
			}/*else {
				response.setContentType("application/json");
				JSONObject jsonObject = new JSONObject();
		        try {
		        	jsonObject.append("outcome", false);
		        } catch (Exception e) {
		        	LOGGER.log(Level.SEVERE, "Login error", e);
		        }
		        response.getWriter().print(jsonObject);
			}*/
			
		}
		
		
		
		
	}

}
