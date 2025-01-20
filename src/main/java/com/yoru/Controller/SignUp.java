package com.yoru.Controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.InitialContext;
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
	
       

    public SignUp() {
        super();
        // TODO Auto-generated constructor stub
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config); // Chiama il metodo super per la corretta inizializzazione

        try {
            // Ottieni il contesto JNDI
            InitialContext context = new InitialContext();
            LOGGER.info("Context Initialized");

            // Recupera il DataSource usando il nome che hai definito nel context.xml
            DataSource ds = (DataSource) context.lookup("java:/comp/env/jdbc/YoruDB");
            
            if (ds == null) {
                throw new ServletException("DataSource non trovato nel contesto");
            }

            // Inizializza il DAO con il DataSource
            userDAO = new UserDAO(ds);

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Errore nell'inizializzazione della servlet: " + e.getMessage());
            
        }
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
		String telefono = request.getParameter("phone");
		
		
		if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
		    try {
				jsonObject.put("result", false).put("error", "Email non valida.");
			} catch (JSONException e) {
				LOGGER.log(Level.WARNING, e.getMessage());
			}
		    response.getWriter().print(jsonObject);
		    return;
		}

		if (!telefono.matches("^[0-9]{10}$")) {
		    try {
				jsonObject.put("result", false).put("error", "Numero di telefono non valido.");
			} catch (JSONException e) {
				LOGGER.log(Level.WARNING, e.getMessage());
			}
		    response.getWriter().print(jsonObject);
		    return;
		}

		
		
		
		if (password == null || password.isEmpty() || 
			    email == null || email.isEmpty() || 
			    nome == null || nome.isEmpty() || 
			    cognome == null || cognome.isEmpty() || 
			    telefono == null || telefono.isEmpty()) {
			    try {
					jsonObject.put("result", false);
				} catch (JSONException e) {
					LOGGER.log(Level.WARNING, e.getMessage());
				}
			    response.getWriter().print(jsonObject);
			    return;
			}

		
		
		if (password != null && email != null && nome != null && cognome != null && telefono != null) {
			
			User user = new User();
			user.setEmail(email);
			user.setCognome(cognome);
			user.setNome(nome);
			user.setTelefono(telefono);
			user.setRole(Role.USER);
			
			user.setPassword(password);
			
			try {
				int result = userDAO.insert(user);
			    if (result > 0) {
			        jsonObject.put("result", true);
			    } else {
			        LOGGER.log(Level.WARNING, "Inserimento utente fallito per email: " + email);
			        jsonObject.put("result", false);
			    }
			} catch (SQLException | JSONException e) {

				LOGGER.log(Level.WARNING, "SignUp error: " + e.getMessage());
			}
			
			
		}
		
		response.getWriter().print(jsonObject);
		
	}

}
