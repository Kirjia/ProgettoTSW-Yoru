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

import com.yoru.model.DAO.AutoreDAO;
import com.yoru.model.Entity.Autore;

/**
 * Servlet implementation class InsertAutore
 */
@WebServlet("/admin/InsertAutore")
public class InsertAutore extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(InsertAutore.class.getName());
       
	private AutoreDAO autoreDAO;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InsertAutore() {
        super();
        // TODO Auto-generated constructor stub
    }

    @Override
    public void init() throws ServletException {
    	super.init();
    	DataSource ds = (DataSource) super.getServletContext().getAttribute("DataSource");
    	if (ds != null) {
			autoreDAO = new AutoreDAO(ds);
			}
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		JSONObject json = new JSONObject();
		
		String nome = request.getParameter("nome");
		String cognome = request.getParameter("cognome");
		
		Autore autore = new Autore();
		autore.setNome(nome);
		autore.setCognome(cognome);
		try {
			if(autoreDAO.insert(autore) > 0 ) {
				json.put("result", true);
			}
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "insert producer error", e);
		} catch (JSONException e) {
			LOGGER.log(Level.WARNING, "insert producer json error", e);
		}
		
		response.getWriter().print(json);
	}

}
