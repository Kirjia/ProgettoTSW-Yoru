package com.yoru.Controller;

import java.io.IOException;
import java.sql.SQLException;
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
import org.json.JSONException;
import org.json.JSONObject;

import com.mysql.cj.xdevapi.JsonArray;
import com.yoru.model.DAO.ItemDAO;
import com.yoru.model.Entity.Libro;
import com.yoru.model.Entity.Prodotto;

/**
 * Servlet implementation class GetAllBook
 */
@WebServlet("/GetAllBook")
public class GetAllBook extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(GetAllBook.class.getName());
	
	private ItemDAO itemDAO;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetAllBook() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    @Override
    public void init() throws ServletException {
    	// TODO Auto-generated method stub
    	super.init();
    	DataSource ds =  (DataSource) super.getServletContext().getAttribute("DataSource");
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
		//response.setContentType("application/json");
		response.setContentType("text/html");
		JSONObject jsonObject = new JSONObject();
		int page = -1;
		int limit = -1;

			page = Integer.parseInt(request.getParameter("page"));
			limit = Integer.parseInt(request.getParameter("limit"));


		if (page > 0) {
			try {
				Collection<Prodotto> books = itemDAO.getAllBooks(page, limit);
				Iterator iterator = books.iterator();
				request.setAttribute("items", books);
				request.getRequestDispatcher("jsp/home.jsp").forward(request, response);
				return;
				/*
				JSONArray array = new JSONArray();
				while(iterator.hasNext()) {
					Prodotto bookProdotto = (Prodotto) iterator.next();
					
					JSONObject item = new JSONObject();
					item.put("SKU", bookProdotto.getSKU());
					item.put("name", bookProdotto.getNome());
					item.put("prezzo", bookProdotto.getPrezzo());
					item.put("quantità", bookProdotto.getQuantità());
					item.put("type", Prodotto.ItemType.Libro);
					array.put(item);
				}
				jsonObject.put("books", array);*/
			} //catch (SQLException | JSONException e) {
			catch (Exception e) {
				
				LOGGER.log(Level.WARNING, "books error", e);
			}
		}
		
		//response.getWriter().print(jsonObject);
		
		
		
		
		
	}

}
