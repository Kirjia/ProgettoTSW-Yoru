package com.yoru.Controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
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
import com.yoru.model.Entity.OrderItem;

/**
 * Servlet implementation class OrderDetails
 */
@WebServlet("/admin/OrderDetails")
public class OrderDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(OrderDetails.class.getName());
       
    private OrderDAO orderDAO;
	
    public OrderDetails() {
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

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		JSONObject json = new JSONObject();
		
		
		try {
			List<OrderItem> items = orderDAO.orderItems(10);
			Iterator<OrderItem> iter = items.iterator();
			while(iter.hasNext()) {
				OrderItem item = iter.next();
				json.put("SKU", item.getSKU());
				json.put("nome", item.getNome());
				json.put("quantity", item.getQuantity());
				json.put("prezzo", item.getPrezzo());
				
			}
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "Db error", e);
		} catch (JSONException e) {
			LOGGER.log(Level.WARNING, "json error", e);
		}
		
		response.getWriter().println(json);
	}


}
