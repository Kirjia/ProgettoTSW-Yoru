package com.yoru.Controller.admin;

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


import com.yoru.model.DAO.OrderDAO;
import com.yoru.model.Entity.Order;

/**
 * Servlet implementation class AllOrders
 */
@WebServlet("/admin/AllOrders")
public class AllOrders extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(AllOrders.class.getName());
	
	private OrderDAO orderDAO;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AllOrders() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    @Override
    public void init() throws ServletException {
    	super.init();
    	DataSource ds = (DataSource) super.getServletContext().getAttribute("DataSource");
    	if (ds != null) {
			orderDAO = new OrderDAO(ds);
			}
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
		JSONArray jsonResponse = new JSONArray();
		
		
		
		try {
			Collection<Order> orders = orderDAO.getAllOrders("", "");
			Iterator<Order> iter = orders.iterator();
			while(iter.hasNext()) {
				Order order = iter.next();
				JSONObject json = new JSONObject();
				json.put("id", order.getId());
				json.put("data", order.getDataPagamento());
				json.put("totale", order.getImportoPagamento());
				json.put("email", order.getEmail());
				json.put("id_pagamento", order.getId_pagamentoStr());
				
				jsonResponse.put(json);
				
				
			}
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "Db error", e);
		} catch (JSONException e) {
			LOGGER.log(Level.WARNING, "json error", e);
		}
		
		response.getWriter().println(jsonResponse);
	}

}
