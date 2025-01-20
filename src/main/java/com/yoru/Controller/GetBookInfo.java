package com.yoru.Controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.concurrent.CancellationException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.json.JSONException;
import org.json.JSONObject;

import com.yoru.model.DAO.ItemDAO;
import com.yoru.model.Entity.Autore;

/**
 * Servlet implementation class GetBookInfo
 */
@WebServlet("/GetBookInfo")
public class GetBookInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(GetBookInfo.class.getName());
	
	
	private ItemDAO itemDAO;

       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetBookInfo() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    @Override
    public void init() throws ServletException {
    	// TODO Auto-generated method stub
    	super.init();
    	DataSource ds = (DataSource) super.getServletContext().getAttribute("DataSource");
    	itemDAO = new ItemDAO(ds);
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
		String idString = request.getParameter("id");
		JSONObject jsonObject = new JSONObject();
		
		if (idString != null) {
			
		
			int id = Integer.parseInt(idString);
			
			
			try {
				Collection<Autore> autori = itemDAO.getAutorsByBook(id);
				if (!autori.isEmpty()) {
					jsonObject.accumulate(idString, jsonObject);
				}
			} catch (SQLException | JSONException e) {
				// TODO Auto-generated catch block
				LOGGER.log(Level.WARNING, "Retrieve book details failed: " + e.getMessage());
			}
			
		}
		
		
		
	}

}
