package com.yoru.Controller.based;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.json.JSONException;
import org.json.JSONObject;

import com.yoru.model.DAO.UserDAO;
import com.yoru.model.Entity.User;


@WebServlet("/based/UpdateProfile")
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
		response.setContentType("application/json");
		String passwordString = request.getParameter("upPassword");
		String nameString = request.getParameter("upName");
		String surnameString = request.getParameter("upSurname");
		String numString = request.getParameter("upNumber");
		JSONObject jsonObject = new JSONObject();
		
		
		
		if(passwordString != null && nameString != null && surnameString != null) {
			HttpSession session = request.getSession(false);
			if (session != null) {
				User updateUser = (User) session.getAttribute("user");
				updateUser.setNome(nameString);
				updateUser.setCognome(surnameString);
				updateUser.setPassword(passwordString);
				updateUser.setTelefono(numString);

			
			

			
				try {
					Boolean result = userDAO.update(updateUser);
					jsonObject.append("result", result);
					if (result) {
						session.setAttribute("Profile", updateUser);
					}
				} catch (SQLException e) {
					LOGGER.log(Level.WARNING, "update profilo fallito", e);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					LOGGER.log(Level.WARNING, "json fallito", e);
				}
			}
			
			
		}
		
		response.getWriter().print(jsonObject);
		
	}

}
