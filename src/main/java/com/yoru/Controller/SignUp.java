package com.yoru.Controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.jasper.tagplugins.jstl.core.If;
import org.json.JSONException;
import org.json.JSONObject;

import com.yoru.model.DAO.UserDAO;
import com.yoru.model.Entity.Role;
import com.yoru.model.Entity.User;

import Util.Argon2Hashing;

/**
 * Servlet implementation class SignUp
 */
@WebServlet("/SignUp")
public class SignUp extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final Logger LOGGER = Logger.getLogger(SignUp.class.getName());
	private UserDAO userDAO;
	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SignUp() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	public void init(ServletConfig config) throws ServletException {
		DataSource ds = (DataSource) super.getServletContext().getAttribute("DataSource");
		userDAO = new UserDAO(ds);
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		
		JSONObject jsonObject = new JSONObject();
		String password = request.getParameter("password");
		String email = request.getParameter("email");
		String nome = request.getParameter("name");
		String cognome = request.getParameter("surname");
		String telefono = request.getParameter("telefono");
		
		if (password != null && email != null && nome != null && cognome != null && telefono != null) {
			
			User user = new User();
			user.setEmail(email);
			user.setCognome(cognome);
			user.setNome(nome);
			user.setTelefono(telefono);
			user.setRole(Role.USER);
			
			user.setPassword(Argon2Hashing.hashPassword(password));
			
			try {
				if(userDAO.insert(user) > 0)
					jsonObject.append("result", true);
				else 
					jsonObject.append("result", false);
				
				
			} catch (SQLException | JSONException e) {

				LOGGER.log(Level.WARNING, "SignUp error", e);
			}
			
			
		}
		
		
		response.getWriter().print(jsonObject);
		
	}

}
