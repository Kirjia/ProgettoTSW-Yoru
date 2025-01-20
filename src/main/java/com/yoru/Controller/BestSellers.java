package com.yoru.Controller;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mysql.cj.xdevapi.JsonArray;
import com.yoru.model.DAO.ItemDAO;
import com.yoru.model.Entity.Prodotto;

/**
 * Servlet implementation class BestSellers
 */
@WebServlet("/Bestseller")
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

		response.setContentType("application/json");
		JSONObject jsonObject = new JSONObject();
		
		try {
			Collection<Prodotto> collection = itemDAO.getBestSellerBook(7);
			JSONArray aJsonArray = new JSONArray();
			for (Iterator<Prodotto> iterator = collection.iterator(); iterator.hasNext();) {
				Prodotto prodotto = (Prodotto) iterator.next();
				JSONObject itemJsonObject = new JSONObject();
				itemJsonObject.put("sku", prodotto.getSKU());
				itemJsonObject.put("title", prodotto.getNome());
				aJsonArray.put(itemJsonObject);
				
			}
			jsonObject.put("items", aJsonArray);
			
			
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, e.getMessage());
		}
		response.getWriter().print(jsonObject);
		
	}

}
