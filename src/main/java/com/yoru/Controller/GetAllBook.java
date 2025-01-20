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
    	if(ds == null)
    		System.out.println("ds NULL");
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
		System.out.println("GetAllBook");
		JSONObject jsonObject = new JSONObject();
		int page = 1;
		int limit = 20;
		String pageString = request.getParameter("page");
		
		if (pageString != null) 
			page = Integer.parseInt(pageString);
		

		


		
		try {
			Collection<Prodotto> books = itemDAO.getAllBooks(page, limit);
			int counts = itemDAO.itemsCount(Prodotto.ItemType.LIBRO);
			//Iterator<Prodotto> iterator = books.iterator();
			request.setAttribute("items", books);
			request.setAttribute("currentPage", page);
			request.setAttribute("counts", counts);
			request.getRequestDispatcher("prodotti.jsp").forward(request, response);
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
			
			LOGGER.log(Level.WARNING, "books error: " + e.getMessage());
		}
		
		
		//response.getWriter().print(jsonObject);
		
		
		
		
		
	}

}
