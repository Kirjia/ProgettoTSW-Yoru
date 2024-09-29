package com.yoru.Controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.bouncycastle.pqc.crypto.xmss.XMSSReducedSignature;

import com.yoru.model.DAO.OrderDAO;
import com.yoru.model.Entity.Order;
import com.yoru.model.Entity.User;

/**
 * Servlet implementation class StoricoOrdini
 */
@WebServlet("/StoricoOrdini")
public class StoricoOrdini extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(StoricoOrdini.class.getName());
       
	private OrderDAO orderDAO;
    
	
    public StoricoOrdini() {
        super();
    }
    
    @Override
    public void init() throws ServletException {
    	
    	DataSource dSource = (DataSource) super.getServletContext().getAttribute("DataSource");
    	orderDAO = new OrderDAO(dSource);
    }

	
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession(false);
		User user = (User) session.getAttribute("user");
		
		try {
			Collection<Order> orders =  orderDAO.getAllByUser(user.getId());
			System.out.println(orders);
			request.setAttribute("historyOrders", orders);
			request.getRequestDispatcher("jsp/user.jsp").forward(request, response);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			LOGGER.log(Level.SEVERE, "orders error", e);
		}
		
		
		
		
	}

}
