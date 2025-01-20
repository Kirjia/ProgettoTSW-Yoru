package com.yoru.Controller.based;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mysql.cj.Session;
import com.yoru.model.DAO.AddressDAO;
import com.yoru.model.Entity.Indirizzo;
import com.yoru.model.Entity.User;

/**
 * Servlet implementation class Indirizzi
 */
@WebServlet("/based/Indirizzi")
public class Indirizzi extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(Indirizzi.class.getName());
	
	private AddressDAO addressDAO;
       
   
    public Indirizzi() {
        super();
    }
    @Override
    public void init() throws ServletException {
    	super.init();
    	DataSource ds = (DataSource) super.getServletContext().getAttribute("DataSource");
    	if (ds != null) {
			addressDAO = new AddressDAO(ds);
		}
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("application/json");
		JSONObject jsonObject = new JSONObject();
		HttpSession session = request.getSession(false);
		User user = (User) session.getAttribute("user");
		
		
		try {
			Collection<Indirizzo> indirizziCollection = addressDAO.getIndirizziByEmail(user.getEmail());
			Iterator<Indirizzo> iter = indirizziCollection.iterator();
			JSONArray array = new JSONArray();
			while(iter.hasNext()) {
				Indirizzo indirizzo = (Indirizzo) iter.next();
				JSONObject item = new JSONObject();
				item.put("id", indirizzo.getId());
				item.put("email", indirizzo.getEmail());
				item.put("via", indirizzo.getVia());
				item.put("provincia", indirizzo.getProvincia());
				item.put("city", indirizzo.getCity());
				item.put("CAP", indirizzo.getCAP());
				array.put(item);
			}
			jsonObject.put("indirizzi", array);
			
		}catch (Exception e) {
			LOGGER.log(Level.WARNING, "indirizzi error: " + e.getMessage());
		}
		response.getWriter().print(jsonObject);
	}

}
