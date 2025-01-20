package com.yoru.Controller;

import java.io.IOException;
import java.nio.file.attribute.UserDefinedFileAttributeView;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.json.JSONException;
import org.json.JSONObject;

import com.yoru.model.DAO.OrderDAO;
import com.yoru.model.Entity.CartItem;
import com.yoru.model.Entity.User;

/**
 * Servlet implementation class AddToCart
 */
@WebServlet("/AddToCart")
public class AddToCart extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger( AddToCart.class.getName() );
	private OrderDAO orderDAO;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddToCart() {
        super();
        // TODO Auto-generated constructor stub
    }

    @Override
    public void init() throws ServletException {
    	// TODO Auto-generated method stub
    	super.init();
    	DataSource ds =  (DataSource) super.getServletContext().getAttribute("DataSource");
    	orderDAO = new OrderDAO(ds);
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
		JSONObject jsonObject = new JSONObject();
		
		String skuString = request.getParameter("sku");
		String qnt = request.getParameter("quantity");
		int sku = Integer.parseInt(skuString);
		int quantity = (qnt == null) ? 1 : Integer.parseInt(qnt);
		
		HttpSession session = (HttpSession) request.getSession();
		User user = (User) session.getAttribute("user");
		
		if (user == null) {
			@SuppressWarnings("unchecked")
			HashMap<String, Integer> cart = (HashMap<String, Integer>) session.getAttribute("cart");
			if(cart == null)
				cart = new HashMap<>();
			if(cart.containsKey(skuString))
				cart.put(skuString, cart.get(skuString) + quantity);
			else
				cart.put(skuString, quantity);
			
			session.setAttribute("cart", cart);
			try {
				jsonObject.append("response", true);
			} catch (JSONException e) {
				LOGGER.log(Level.WARNING, "JSON error: " + e.getMessage());
			}
			
			
		}else {
			try {
				if (orderDAO.insertIntoCart(user.getId(), sku, quantity)) 
					jsonObject.append("response", true);
				
				
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				LOGGER.log(Level.WARNING, "JSON error: " + e.getMessage());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				LOGGER.log(Level.WARNING, "add cart error: " + e.getMessage());
			}
		}
		
		response.getWriter().print(jsonObject);
		
	}

}
