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
import com.yoru.Controller.GetAllBook;
import com.yoru.model.DAO.ItemDAO;
import com.yoru.model.Entity.Gadgets;
import com.yoru.model.Entity.Libro;
import com.yoru.model.Entity.Prodotto;

/**
 * Servlet implementation class GetAllGadget
 */
@WebServlet("/GetAllGadget")
public class GetAllGadget extends HttpServlet {
	private static final long serialVersionUID = 1L;
private static final Logger LOGGER = Logger.getLogger(GetAllGadget.class.getName());
	
	private ItemDAO itemDAO;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetAllGadget() {
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
		// TODO Auto-generated method stub
		doPost(request, response);
}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//response.setContentType("application/json");
		response.setContentType("text/html");
		System.out.println("GetAllGadget");
		int page = 1;
		int limit = 20;
		String pageString = request.getParameter("page");
		
		if (pageString != null) 
			page = Integer.parseInt(pageString);
		

		


		
		try {
			Collection<Gadgets> items = itemDAO.getAllGadgets(page, limit);
			int counts = itemDAO.itemsCount(Prodotto.ItemType.GADGET);
			Iterator<Gadgets> iterator = items.iterator();
			request.setAttribute("items", items);
			request.setAttribute("currentPAge", page);
			request.setAttribute("counts", counts);
			request.getRequestDispatcher("gadget.jsp").forward(request, response);
			return;
		
		} //catch (SQLException | JSONException e) {
		catch (Exception e) {
			
			LOGGER.log(Level.WARNING, "gadget error: " + e.getMessage());
		}
		
		
		//response.getWriter().print(jsonObject);
		
		
		
		
		
	}

}

