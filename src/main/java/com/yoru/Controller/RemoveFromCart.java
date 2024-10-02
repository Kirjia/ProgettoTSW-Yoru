package com.yoru.Controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.LogRecord;
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
import com.yoru.model.Entity.User;

/**
 * Servlet implementation class RemoveFromCart
 */
@WebServlet("/RemoveFromCart")
public class RemoveFromCart extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(RemoveFromCart.class.getName());
	
	private OrderDAO orderDAO;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RemoveFromCart() {
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
		
		HttpSession session = (HttpSession) request.getSession(false);
		
		if (session == null) {
			return;
		}
		User user = (User) session.getAttribute("user");
		
		String skuStr = request.getParameter("sku");
		
		
		
		try {
			int sku = Integer.parseInt(skuStr);
			if (orderDAO.removeFromCart(user.getId(), sku))
				jsonObject.append("result", true);
			else
				jsonObject.append("result", false);
			
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "remove from cart error", e);
		}
		catch (NumberFormatException e) {
			LOGGER.log(Level.WARNING, "remove from cart error not valid SKU is not a number", e);
		} catch (JSONException e) {
			LOGGER.log(Level.WARNING, "remove from cart error, cant create json", e);
		}
		
		response.getWriter().print(jsonObject);
		
	}

}
