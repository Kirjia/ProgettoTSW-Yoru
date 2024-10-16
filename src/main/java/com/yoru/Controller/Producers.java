package com.yoru.Controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.yoru.model.DAO.ProducerDAO;
import com.yoru.model.Entity.Producer;

/**
 * Servlet implementation class Producers
 */
@WebServlet("/Producers")
public class Producers extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(Producers.class.getName());
	
	private ProducerDAO producerDAO;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Producers() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    @Override
	public void init() throws ServletException {
		super.init();
		DataSource ds = (DataSource) super.getServletContext().getAttribute("DataSource");
		producerDAO = new ProducerDAO(ds);
	}

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("application/json");

		
		
		try {
			Collection<Producer> producers = producerDAO.getAll();
			Iterator<Producer> iterator = producers.iterator();
			JSONArray aJsonArray = new JSONArray();
			while(iterator.hasNext()) {
				Producer produttore = iterator.next();
				JSONObject item = new JSONObject();
				item.put("id", produttore.getID());
				item.put("nome", produttore.getNome());
				item.put("email", produttore.getEmail());
				item.put("telefono", produttore.getTelefono());
				aJsonArray.put(item);
			}
			response.getWriter().print(aJsonArray);
			return;
			
		} catch (SQLException | JSONException e) {
			LOGGER.log(Level.WARNING, "retrieve producers error");
		}
		
	}

}
