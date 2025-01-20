package com.yoru.Controller.admin;

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

import org.json.JSONException;
import org.json.JSONObject;

import com.yoru.model.DAO.MaterialDAO;
import com.yoru.model.DAO.ProducerDAO;
import com.yoru.model.Entity.Producer;

/**
 * Servlet implementation class InsertMateriale
 */
@WebServlet("/admin/InsertMateriale")
public class InsertMateriale extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(InsertMateriale.class.getName());
	
	private MaterialDAO materialeDAO;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InsertMateriale() {
        super();
        // TODO Auto-generated constructor stub
    }

    @Override
    public void init() throws ServletException {
    	super.init();
    	DataSource ds = (DataSource) super.getServletContext().getAttribute("DataSource");
    	if (ds != null) {
			materialeDAO = new MaterialDAO(ds);
			}
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		JSONObject json = new JSONObject();
		
		String materiale = request.getParameter("materiale");
		
		
		try {
			if(materialeDAO.insert(materiale) > 0 ) {
				json.put("result", true);
			}
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "insert producer error: "+  e.getMessage());
		} catch (JSONException e) {
			LOGGER.log(Level.WARNING, "insert producer json error: " + e.getMessage());
		}
		
		response.getWriter().print(json);
	}

}
