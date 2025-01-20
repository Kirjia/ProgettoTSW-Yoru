package com.yoru.Controller.admin;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.sql.DataSource;

import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
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
@MultipartConfig(
	    fileSizeThreshold = 1024 * 1024 * 2,  // 2 MB
	    maxFileSize = 1024 * 1024 * 5,    // 5 MB
	    maxRequestSize = 1024 * 1024 * 10 // 10 MB
	)
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
		Part coverPart = request.getPart("file");
		
		  // Ottieni il JSON del prodotto
        String productJson = request.getParameter("item");
        
        System.out.println(productJson);
			
		
		
        
        try {

        	JSONObject jsonBody = new JSONObject(productJson);
        
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
	        		String autoreId = jsonArray.getString(i);
	        		Autore autore = new Autore();
	        		autore.setID(Integer.parseInt(autoreId));
	        		autori.add(autore);
	        	}
	        	libro.setAutori(autori);
	        	
	        	int id = itemDAO.insert(libro);
	        	if(id > 0){
	        		if(coverPart.getSize() > 0) {
	                    String filename = id + ".png";
	                    LOGGER.log(Level.INFO, "image id: " + id);
	                    request.setAttribute("Upload", true);
	                    request.setAttribute("InputStream", coverPart.getInputStream());
	                    request.setAttribute("Path", filename);
	                    request.getRequestDispatcher("../FileManager").include(request, response);
	                }
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
	        		String item = jsonArray.getString(i);        		
        			materiali.add(item);
	        	}
	        	gadgets.setMateriali(materiali);
	        	
				int id = itemDAO.insert(gadgets);
				
				if(id > 0) {
					if(coverPart.getSize() > 0) {
	                    String filename = id + ".png";
	                    request.setAttribute("Upload", true);
	                    request.setAttribute("InputStream", coverPart.getInputStream());
	                    request.setAttribute("Path", filename);
	                    request.getRequestDispatcher("../FileManager").include(request, response);
	                }
					
					responeObject.put("result", true);
					response.getWriter().print(responeObject);
					return;
				}
				
			}
	        
	        responeObject.put("result", false);
			response.getWriter().print(responeObject);
		
		
		
			
			
		}catch (SQLException e) {
			LOGGER.log(Level.WARNING, "insert item error: " + e.getMessage());
		}catch (JSONException e) {
			LOGGER.log(Level.WARNING, "insert item json error: " + e.getMessage());
		}catch (NumberFormatException e) {
			LOGGER.log(Level.WARNING, "insert item data format error: " + e.getMessage());
		}
		
		
		
		
		
		
		
	}

}
