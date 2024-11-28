package com.yoru.Controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.UUID;
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
import com.yoru.model.Entity.Cart;
import com.yoru.model.Entity.User;


@WebServlet("/based/CheckOut")
public class CheckOut extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(CheckOut.class.getName());
	
	private OrderDAO orderDAO;
       

    public CheckOut() {
        super();

    }
    
    @Override
    public void init() throws ServletException {
    	super.init();
    	DataSource ds = (DataSource) super.getServletContext().getAttribute("DataSource");
    	orderDAO = new OrderDAO(ds);
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		JSONObject jsonObject = new JSONObject();
		
		HttpSession session = request.getSession(false);
		
		User user = (User) session.getAttribute("user");
		int addressId = Integer.parseInt(request.getParameter("addressId"));
		
		
		
		try {
			Cart cart = orderDAO.getCart(user.getId());
			UUID payUuid = UUID.randomUUID();
			if(orderDAO.checkOut(cart, payUuid.toString(), addressId)) {
				jsonObject.put("result", true);
			}else {
				jsonObject.put("result", false);
			}
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "CheckOut error", e);
		} catch (JSONException e) {
			LOGGER.log(Level.WARNING, "CheckOut done but an error has accured on json data", e);
		}
		
		
		response.getWriter().print(jsonObject);
		
	}

}
