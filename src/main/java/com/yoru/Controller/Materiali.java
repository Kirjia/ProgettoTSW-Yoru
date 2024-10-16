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

import com.yoru.model.DAO.MaterialDAO;

/**
 * Servlet implementation class Materiali
 */
@WebServlet("/Materiali")
public class Materiali extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(Materiali.class.getName());
	
	private MaterialDAO materialDAO;
       
    
    public Materiali() {
        super();
        
    }

	@Override
	public void init() throws ServletException {
		super.init();
		DataSource ds = (DataSource) super.getServletContext().getAttribute("DataSource");
		materialDAO = new MaterialDAO(ds);
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
            Collection<String> materiali = materialDAO.getAll();
            Iterator<String> iterator = materiali.iterator();
            JSONArray aJsonArray = new JSONArray();
            while(iterator.hasNext()) {
                String tmpString = iterator.next();
                JSONObject jsonTmp = new JSONObject();
                jsonTmp.put("materiale", tmpString);
                aJsonArray.put(jsonTmp);
            }

            response.getWriter().print(aJsonArray);
            return;

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "retrieve material error");
        } catch (JSONException e) {
            LOGGER.log(Level.WARNING, "retrieve material error");
        }
		
	}

}
