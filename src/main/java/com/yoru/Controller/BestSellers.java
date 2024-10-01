package com.yoru.Controller;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.json.JSONObject;

import com.mysql.cj.xdevapi.JsonArray;
import com.yoru.model.DAO.ItemDAO;
import com.yoru.model.Entity.Prodotto;

/**
 * Servlet implementation class BestSellers
 */
@WebServlet("/BestSellers")
public class BestSellers extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(BestSellers.class.getName());
    
	private ItemDAO itemDAO;
    
    public BestSellers() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    @Override
    public void init() throws ServletException {
    	// TODO Auto-generated method stub
    	super.init();
    	DataSource ds = (DataSource) super.getServletContext().getAttribute("DataSource");
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

		
		
		try {
			Collection<Prodotto> collection = itemDAO.getBestSellerBook(9);
			request.setAttribute("bestsellers", collection);
			
			
		} catch (Exception e) {
			
		}
		request.getRequestDispatcher("./jsp/home.jsp").forward(request, response);
		
	}

}
