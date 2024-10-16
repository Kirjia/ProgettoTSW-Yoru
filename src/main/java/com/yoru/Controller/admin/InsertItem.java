package com.yoru.Controller.admin;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.jasper.tagplugins.jstl.core.If;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.yoru.model.DAO.ItemDAO;
import com.yoru.model.Entity.Autore;
import com.yoru.model.Entity.Gadgets;
import com.yoru.model.Entity.Libro;
import com.yoru.model.Entity.Prodotto;

/**
 * Servlet implementation class InsertItem
 */
@WebServlet("/admin/InsertItem")
public class InsertItem extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(InsertItem.class.getName());
	
	private ItemDAO itemDAO;

    public InsertItem() {
        super();
  
    }

    @Override
    public void init() throws ServletException {
    	super.init();
    	DataSource ds = (DataSource) super.getServletContext().getAttribute("DataSource");
    	if (ds != null) {
			itemDAO = new ItemDAO(ds);
			}
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("application/json");
		JSONObject jsonObject = new JSONObject();
		JSONObject responeObject = new JSONObject();
		
		 // 1. Leggi il body della richiesta come testo (String)
        StringBuilder bodyBuffer = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            bodyBuffer.append(line);
        }
        String body = bodyBuffer.toString();
        
        try {

        // 2. Converte il body della richiesta in un oggetto JSON
        JSONObject jsonBody = new JSONObject(body);
        
	        if (jsonBody.getString("itemType").equals("libro")) {
	        	Libro libro = new Libro();
	        	libro.setNome(jsonBody.getString("nome"));
	        	libro.setPrezzo(Float.parseFloat(jsonBody.getString("prezzo")));
	        	libro.setDescrizione(jsonBody.getString("descrizione"));
	        	libro.setId_produttore(Integer.parseInt(jsonBody.getString("produttore")));
	        	libro.setISBN(jsonBody.getString("ISBN"));
	        	libro.setItemType(Prodotto.ItemType.LIBRO);
	        	libro.setLingua(jsonBody.getString("lingua"));
	        	libro.setNumeroPagine(Integer.parseInt(jsonBody.getString("pagine")));
	        	libro.setQuantità(Integer.parseInt(jsonBody.getString("quantity")));
	        	JSONArray jsonArray = new JSONArray();
	        	jsonArray = jsonBody.getJSONArray("autori");
	        	List<Autore> autori = new ArrayList<>();
	        	
	        	for(int i = 0; i < jsonArray.length(); i++) {
	        		JSONObject item = jsonArray.getJSONObject(i);
	        		Autore autore = new Autore();
	        		autore.setID(Integer.parseInt(item.getString("autore")));
	        		autori.add(autore);
	        	}
	        	libro.setAutori(autori);
	        	
	        	if(itemDAO.insert(libro)){
	        		responeObject.put("result", true);
					response.getWriter().print(responeObject);
					return;
	        		
	        	}
			}else {
				Gadgets gadgets = new Gadgets();
				gadgets.setItemType(Prodotto.ItemType.GADGET);
				gadgets.setNome(jsonBody.getString("nome"));
				gadgets.setPrezzo(Float.parseFloat(jsonBody.getString("prezzo")));
				gadgets.setQuantità(Integer.parseInt(jsonBody.getString("quantity")));
				gadgets.setDescrizione(jsonBody.getString("descrizione"));
				gadgets.setMarchio(jsonBody.getString("marchio"));
				gadgets.setId_produttore(Integer.parseInt(jsonBody.getString("produttore")));
				gadgets.setModello(jsonBody.getString("modello"));
				
				JSONArray jsonArray = new JSONArray();
	        	jsonArray = jsonBody.getJSONArray("materiali");
	        	List<String> materiali = new ArrayList<>();
	        	
	        	for(int i = 0; i < jsonArray.length(); i++) {
	        		JSONObject item = jsonArray.getJSONObject(i);
	        		
	        		String materialeString = item.getString("materiale");
        			materiali.add(materialeString);
	        	}
	        	gadgets.setMateriali(materiali);
	        	
				
				if(itemDAO.insert(gadgets)) {
					responeObject.put("result", true);
					response.getWriter().print(responeObject);
					return;
				}
				
			}
	        
	        responeObject.put("result", false);
			response.getWriter().print(responeObject);
		
		
		
			
			
		}catch (SQLException e) {
			LOGGER.log(Level.WARNING, "insert item error");
		}catch (JSONException e) {
			LOGGER.log(Level.WARNING, "insert item json error", e);
		}catch (NumberFormatException e) {
			LOGGER.log(Level.WARNING, "insert item data format error", e);
		}
		
		
		
		
		
		
		
	}

}
