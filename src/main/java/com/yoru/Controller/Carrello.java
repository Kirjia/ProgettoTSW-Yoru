package com.yoru.Controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mysql.cj.Session;
import com.yoru.model.DAO.ItemDAO;
import com.yoru.model.DAO.OrderDAO;
import com.yoru.model.Entity.User;
import com.yoru.model.Entity.Cart;
import com.yoru.model.Entity.CartItem;
import com.yoru.model.Entity.Prodotto;


/**
 * Servlet implementation class Cart
 */
@WebServlet("/Carrello")
public class Carrello extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(Carrello.class.getName());
	
	private OrderDAO orderDAO;
	private ItemDAO itemDAO;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Carrello() {
        super();
    }
    
    @Override
    public void init() throws ServletException {
    	// TODO Auto-generated method stub
    	super.init();
    	DataSource ds =  (DataSource) super.getServletContext().getAttribute("DataSource");
    	orderDAO = new OrderDAO(ds);
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
		
		JSONObject cartJson = new JSONObject();
		
		HttpSession session = (HttpSession) request.getSession();
		
		
		User user = (User) session.getAttribute("user");
	
		if(user != null) {
			
			try {
				Cart cart = orderDAO.getCart(user.getId());
				cartJson.put("total", cart.getTotal());
				JSONArray array = cartJson(cart.getItems());
				
				cartJson.put("cart", array);
				
				//request.setAttribute("carrello", cart.getItems());
				//request.setAttribute("tot", cart.getTotal());
				
			} catch (NumberFormatException e) {
				LOGGER.log(Level.INFO, "format error", e);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				LOGGER.log(Level.SEVERE, "sql error", e);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				LOGGER.log(Level.SEVERE, "json error", e);
			}
		}else {
			
			@SuppressWarnings("unchecked")
			HashMap<String, Integer> items = (HashMap<String, Integer>) session.getAttribute("cart");
			
			List<CartItem> cart = new ArrayList<>();
			if (items != null) {
				Iterator<String> iter = items.keySet().iterator();
				
				int total = 0;
				
			
				while(iter.hasNext()) {
					String key = iter.next();
					int skuIter = Integer.parseInt(key);
					try {
						Prodotto item = itemDAO.getById(skuIter);
						CartItem itemCart = new CartItem(-1,
								skuIter,
								items.get(key),
								item.getPrezzo(),
								item.getNome());
						total += item.getPrezzo() * item.getQuantità();
						cart.add(itemCart);
						
						
						
					} catch (SQLException e) {
						LOGGER.log(Level.WARNING, "retrieve guest cart error", e);
					}
					
				}
				try {
					cartJson.put("total", total);
					JSONArray array = cartJson(cart);
					cartJson.put("cart", array);
				} catch (JSONException e) {
					LOGGER.log(Level.WARNING, "retrieve guest cart, json error", e);
				}
				
			}
			
			//request.setAttribute("carrello", cart);
			
		}
		
		//request.getRequestDispatcher("carrello.jsp").forward(request, response);
		response.getWriter().print(cartJson);

		
	}
	
	
	private JSONArray cartJson(Collection<CartItem> cart) throws JSONException {
		JSONArray array = new JSONArray();
		
		for (Iterator<CartItem> iterator = cart.iterator(); iterator.hasNext();) {
			CartItem item = (CartItem) iterator.next();
			JSONObject itemJson = new JSONObject();
			itemJson.put("nome", item.getNome());
			itemJson.put("SKU", item.getSKU());
			itemJson.put("prezzo", item.getPrezzo());
			itemJson.put("id", item.getIdcartId());
			itemJson.put("quantity", item.getQuantity());
			array.put(itemJson);
			
		}
		return array;
		
	}

}
