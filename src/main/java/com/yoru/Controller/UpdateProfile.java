package com.yoru.Controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.yoru.model.DAO.UserDAO;
import com.yoru.model.Entity.User;


@WebServlet("/UpdateProfile")
public class UpdateProfile extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(UpdateProfile.class.getName());
       
	private UserDAO userDAO;
	
	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		DataSource dataSource = (DataSource) super.getServletContext().getAttribute("DataSource");
		userDAO = new UserDAO(dataSource);
	}
 
    public UpdateProfile() {
        super();
        // TODO Auto-generated constructor stub
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
		
		String passwordString = request.getParameter("upPassword");
		String nameString = request.getParameter("upname");
		String surnameString = request.getParameter("upSurname");
		String numString = request.getParameter("upNumber");
		
		
		if(passwordString != null && nameString != null && surnameString != null) {
			User updateUser = new User();
			updateUser.setNome(nameString);
			updateUser.setCognome(surnameString);
			updateUser.setPassword(passwordString);
			updateUser.setTelefono(numString);

			
			try {
				userDAO.update(updateUser);
				
			} catch (SQLException e) {
				LOGGER.log(Level.WARNING, "update profilo fallito", e);
			}
			
			
		}
		
		
	}

}
