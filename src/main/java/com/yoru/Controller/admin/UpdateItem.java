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
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.json.JSONException;
import org.json.JSONObject;

import com.yoru.Controller.GetAllBook;
import com.yoru.model.DAO.ItemDAO;
import com.yoru.model.Entity.Prodotto;
import com.yoru.model.Entity.Role;
import com.yoru.model.Entity.User;

/**
 * Servlet implementation class UpdateItem
 */
@WebServlet("/admin/UpdateItem")
public class UpdateItem extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(UpdateItem.class.getName()); 
	
	private ItemDAO itemDAO;
       
    
    public UpdateItem() {
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

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		
		String sku = request.getParameter("sku");
		String priceStr = request.getParameter("price");
		String quantity = request.getParameter("quantity");
		
		JSONObject json_response = new JSONObject();
		
		
		if(sku.isBlank() || sku == null || priceStr.isBlank() || priceStr == null || quantity.isBlank() || quantity == null) {
			try {
				json_response.put("error", "i valori non sono stati inseriti");
			} catch (JSONException e) {
				LOGGER.log(Level.WARNING, "errore nel put del json UpdateItem valori errari: " + e.getMessage());
			}
			
			response.getWriter().print(json_response);
			return;
		}
		
		int id = Integer.parseInt(sku);
		int qnt = Integer.parseInt(quantity);
		float price = Float.parseFloat(priceStr);
		
		if(qnt < 0 || price < 0) {
			try {
				json_response.put("error", "i valori negativi non sono consentiti");
			} catch (JSONException e) {
				LOGGER.log(Level.WARNING, "errore valori negativi non consentiti: " + e.getMessage());
			}
			
			response.getWriter().print(json_response);
			return;
		}
			
		
		Prodotto prodotto = new Prodotto();
		prodotto.setSKU(id);
		prodotto.setPrezzo(price);
		prodotto.setQuantità(qnt);
		
		try {
			
			if(itemDAO.update(prodotto))
				json_response.put("result", true);
			else
				json_response.put("result", false);
			
		} catch (SQLException | JSONException e) {
			LOGGER.log(Level.WARNING, "errore nella servlet UpdateItem, update fallito: " + e.getMessage());
		}
		
		response.getWriter().print(json_response);
		
	}

}
