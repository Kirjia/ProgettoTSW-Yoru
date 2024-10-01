package com.yoru.Controller;

import java.io.IOException;
import java.nio.file.attribute.UserDefinedFileAttributeView;
import java.sql.SQLException;
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
		
		HttpSession session = (HttpSession) request.getSession(false);
		if (session == null) {
			return;
		}
		User user = (User) session.getAttribute("user");
		if (user == null) {
			return;
		}
		
		String skuString = request.getParameter("sku");
		int sku = Integer.parseInt(skuString);
		
		try {
			if (orderDAO.insertIntoCart(user.getId(), sku, 1)) 
				jsonObject.append("response", true);
			
			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			LOGGER.log(Level.WARNING, "JSON error", e);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			LOGGER.log(Level.WARNING, "add cart error", e);
		}
		
		response.getWriter().print(jsonObject);
		
	}

}
