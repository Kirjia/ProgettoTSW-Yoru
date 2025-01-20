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

import com.yoru.model.DAO.ItemDAO;
import com.yoru.model.DAO.ProducerDAO;
import com.yoru.model.Entity.Producer;

/**
 * Servlet implementation class InsertProducer
 */
@WebServlet("/admin/InsertProducer")
public class InsertProducer extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(InsertProducer.class.getName());
       
    private ProducerDAO producerDAO;
	
    public InsertProducer() {
        super();
        // TODO Auto-generated constructor stub
    }

    @Override
    public void init() throws ServletException {
    	super.init();
    	DataSource ds = (DataSource) super.getServletContext().getAttribute("DataSource");
    	if (ds != null) {
			producerDAO = new ProducerDAO(ds);
			}
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		JSONObject json = new JSONObject();
		
		String nome = request.getParameter("nome");
		String telefono = request.getParameter("telefono");
		String email = request.getParameter("email");
		
		Producer producer = new Producer();
		producer.setEmail(email);
		producer.setNome(nome);
		producer.setTelefono(telefono);
		
		try {
			if(producerDAO.insert(producer) > 0 ) {
				json.put("result", true);
			}
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "insert producer error: " + e.getMessage());
		} catch (JSONException e) {
			LOGGER.log(Level.WARNING, "insert producer json error: " + e.getMessage());
		}
		
		response.getWriter().print(json);
	}
	
	

}
