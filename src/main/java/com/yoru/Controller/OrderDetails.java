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
        JSONArray jsonResponse = new JSONArray();

        try {
            // Recupera l'ID dell'ordine dalla richiesta
            String orderIdStr = request.getParameter("orderId");
            int orderId = Integer.parseInt(orderIdStr);

            // Ottieni gli articoli dell'ordine dal database usando l'ID dell'ordine
            List<OrderItem> items = orderDAO.orderItems(orderId);

            // Crea un array JSON con gli articoli
            for (OrderItem item : items) {
                JSONObject jsonItem = new JSONObject();
                jsonItem.put("SKU", item.getSKU());
                jsonItem.put("nome", item.getNome());
                jsonItem.put("quantity", item.getQuantity());
                jsonItem.put("prezzo", item.getPrezzo());

                jsonResponse.put(jsonItem);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Db error: " + e.getMessage());
        } catch (JSONException e) {
            LOGGER.log(Level.WARNING, "json error: " + e.getMessage());
        } catch (NumberFormatException e) {
            LOGGER.log(Level.WARNING, "Invalid order ID format: " + e.getMessage());
        }

        response.getWriter().println(jsonResponse);
    }


}
