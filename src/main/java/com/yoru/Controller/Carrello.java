package com.yoru.Controller;

import java.io.IOException;
import java.sql.SQLException;

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
		
		User user = (User) session.getAttribute("user");
		Cart cart;
		
		if (user == null) {
			request.getRequestDispatcher("jsp/login.jsp").forward(request, response);
			return;
		}
			
		try {
			cart = orderDAO.getCart(user.getId());
			request.setAttribute("carrello", cart.getItems());
			request.setAttribute("tot", cart.getTotal());
			request.getRequestDispatcher("jsp/carrello.jsp").forward(request, response);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
	}

}