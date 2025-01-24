package com.yoru.Controller.admin;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.json.JSONException;
import org.json.JSONObject;

import com.yoru.model.DAO.ItemDAO;
import com.yoru.model.DAO.UserDAO;
import com.yoru.model.Entity.Prodotto;

/**
 * Servlet implementation class RemoveItem
 */
@WebServlet("/admin/RemoveItem")
public class RemoveItem extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(RemoveItem.class.getName());
	
	private ItemDAO itemDAO;
	
	
	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		DataSource dataSource = (DataSource) super.getServletContext().getAttribute("DataSource");
		itemDAO = new ItemDAO(dataSource);
	}
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RemoveItem() {
        super();
        // TODO Auto-generated constructor stub
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
		
		JSONObject json = new JSONObject();
		
		
		String sku = request.getParameter("sku");
		
		if(sku.isBlank() || sku == null) {
			return;
		}
		
		
		int id = Integer.parseInt(sku);
		
		Prodotto item = new Prodotto();
		item.setSKU(id);
		
		try {
			if(itemDAO.remove(item))
				json.put("result", true);
			else
				json.put("result", false);
		} catch (SQLException | JSONException e) {
			LOGGER.log(Level.WARNING, e.getMessage());
		}
		
		response.getWriter().print(json);
		return;
		
		
	}

}
