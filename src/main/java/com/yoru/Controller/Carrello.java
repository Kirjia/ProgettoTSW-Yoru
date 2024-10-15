package com.yoru.Controller;

import java.io.IOException;
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

import org.json.JSONObject;

import com.mysql.cj.Session;
import com.yoru.model.DAO.OrderDAO;
import com.yoru.model.Entity.User;
import com.yoru.model.Entity.Cart;


/**
 * Servlet implementation class Cart
 */
@WebServlet("/Carrello")
public class Carrello extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(Carrello.class.getName());
	private OrderDAO orderDAO;
       
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
		
	
		HttpSession session = (HttpSession) request.getSession(false);
		
		
		if (session == null) {
			response.sendRedirect("jsp/login.jsp");
			return;
			
		}
		
		User user = (User) session.getAttribute("user");
		Cart cart;
		
		if (user == null) {
			response.sendRedirect("jsp/login.jsp");
			return;
		}
			
		try {
			cart = orderDAO.getCart(user.getId());
			request.setAttribute("carrello", cart.getItems());
			request.setAttribute("tot", cart.getTotal());
			request.getRequestDispatcher("jsp/carrello.jsp").forward(request, response);
		} catch (NumberFormatException e) {
			LOGGER.log(Level.INFO, "format error", e);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			LOGGER.log(Level.SEVERE, "sql error", e);
		}
		
		
		
		
		
	}

}
