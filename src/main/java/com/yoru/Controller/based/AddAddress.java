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

import com.yoru.model.DAO.AddressDAO;
import com.yoru.model.Entity.Indirizzo;
import com.yoru.model.Entity.User;

/**
 * Servlet implementation class AddAddress
 */
@WebServlet("/based/AddAddress")
public class AddAddress extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(AddAddress.class.getName());
       
    private AddressDAO dao;
	
    public AddAddress() {
        super();
        // TODO Auto-generated constructor stub
    }

    @Override
    public void init() throws ServletException {

        DataSource dSource = (DataSource) super.getServletContext().getAttribute("DataSource");
        dao = new AddressDAO(dSource);
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		
		JSONObject json = new JSONObject();
		
		
		String via = request.getParameter("via");
		String provincia = request.getParameter("provincia");
		String citta = request.getParameter("citta");
		String cap = request.getParameter("CAP");
		
		HttpSession session = request.getSession(false);
		User user = (User) session.getAttribute("user");
		
		if(via.isBlank() || provincia.isBlank() || citta.isBlank() || cap.isBlank() 
				|| via == null || provincia == null || citta == null || cap == null) {
			
			try {
				json.put("error", "campi non compilati correttamente");
			} catch (JSONException e) {
				LOGGER.log(Level.ALL, e.getMessage());
			}
			
			response.getWriter().print(json);
			return;
			
		}
		
		Indirizzo address = new Indirizzo();
		address.setEmail(user.getEmail());
		address.setCity(citta);
		address.setVia(via);
		address.setCAP(cap);
		address.setProvincia(provincia);
		
		try {
			if(dao.insert(address) > 0) {
				json.put("result", true);
			}else {
				json.put("result", false);
			}
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, e.getMessage());
		} catch (JSONException e) {
			LOGGER.log(Level.INFO, e.getMessage());
		}
		
		response.getWriter().print(json);
	}

}
