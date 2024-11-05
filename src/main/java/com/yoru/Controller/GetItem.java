package com.yoru.Controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.CancellationException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.json.JSONException;
import org.json.JSONObject;

import com.yoru.model.DAO.ItemDAO;
import com.yoru.model.Entity.Gadgets;
import com.yoru.model.Entity.Libro;
import com.yoru.model.Entity.Prodotto;

/**
 * Servlet implementation class GetItem
 */
@WebServlet("/Item")
public class GetItem extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private ItemDAO itemDAO;   
    private static final Logger LOGGER = Logger.getLogger( GetItem.class.getName() );
	
	

    public GetItem() {
        super();

    }

    @Override
    	public void init() throws ServletException {
    		super.init();
    		DataSource dSource = (DataSource) super.getServletContext().getAttribute("DataSource");
    		itemDAO = new ItemDAO(dSource);
    	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//response.setContentType("application/json");
		//JSONObject json = new JSONObject();
		
		int itemId = Integer.parseInt(request.getParameter("sku"));
		
		
		
		
		try {
			Prodotto item = itemDAO.getById(itemId);
			
			if (item.getItemType() == Prodotto.ItemType.LIBRO) {
				Libro book = (Libro) item;
				//json.append("item", item);
			}else {
				Gadgets gadgets = (Gadgets) item;
				//json.append("item", item);
			}
			
			
			//response.getWriter().print(json);
			
			request.setAttribute("item", item);
			request.getRequestDispatcher("item.jsp").forward(request, response);;
		}catch (SQLException e) {
			LOGGER.log(Level.WARNING, "Retrive item error", e);
		} 

		
	}

}
