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

import com.yoru.model.DAO.AutoreDAO;
import com.yoru.model.Entity.Autore;

/**
 * Servlet implementation class Autori
 */
@WebServlet("/Autori")
public class Autori extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(Materiali.class.getName());
	
	private AutoreDAO autoreDAO;
       
    
    public Autori() {
        super();
        
    }

	@Override
	public void init() throws ServletException {
		super.init();
		DataSource ds = (DataSource) super.getServletContext().getAttribute("DataSource");
		autoreDAO = new AutoreDAO(ds);
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
			Collection<Autore> autori = autoreDAO.getAll();
			Iterator<Autore> iterator = autori.iterator();
			JSONArray aJsonArray = new JSONArray();
			while(iterator.hasNext()) {
				Autore autore = iterator.next();
				JSONObject item = new JSONObject();
				item.put("id", autore.getID());
				item.put("nome", autore.getNome());
				item.put("cognome", autore.getCognome());
				item.put("aka", autore.getNomeArte());
				aJsonArray.put(item);
			}
			response.getWriter().print(aJsonArray);
			return;
			
		} catch (SQLException | JSONException e) {
			LOGGER.log(Level.WARNING, "retrieve autors error");
		}
		
	}

}
