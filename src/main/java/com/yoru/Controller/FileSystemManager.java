package com.yoru.Controller;

import java.io.*;
import java.net.URLDecoder;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Uploader
 */
@WebServlet("/FileManager")
public class FileSystemManager extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(FileSystemManager.class.getName());
	
	 // Percorso della cartella esterna dove verranno salvati i file caricati
    private static final String UPLOAD_DIR = "/files/images/";
       
	private static final int BUFFER = 4096;
    private String filePath;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FileSystemManager() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    @Override
    public void init() throws ServletException {
    	super.init();
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
		Boolean upload = request.getAttribute("Upload") != null;
		
		
        
        if(!upload) {

            String requestedFile = request.getPathInfo();
            File file = new File(filePath, URLDecoder.decode(requestedFile, "UTF-8"));
            String contentType = getServletContext().getMimeType(file.getName());

            response.reset();
            response.setBufferSize(BUFFER);
            response.setContentType(contentType);
            response.setHeader("Content-Length", String.valueOf(file.length()));
            response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");

            BufferedInputStream input = null;
            BufferedOutputStream output = null;

            input = new BufferedInputStream(new FileInputStream(file), BUFFER);
            output = new BufferedOutputStream(response.getOutputStream(), BUFFER);

            byte[] buffer = new byte[BUFFER];
            int length;
            while ((length = input.read(buffer)) > 0)
                output.write(buffer, 0, length);

            output.close();
            input.close();

        } else {
        	
        	 String path = (String) request.getAttribute("Path");
            // Definisci il percorso del file di destinazione
            String relativePath = UPLOAD_DIR + path;
            
            Path uploadPath = Paths.get(getServletContext().getRealPath(""), relativePath);
    		
    		 // Crea la directory se non esiste
            Path dir = uploadPath.getParent();
            Files.createDirectories(dir);

            InputStream in = (InputStream) request.getAttribute("InputStream");
           
            
            // Salva il file nel percorso specificato
            try {
            	
                Files.copy(in, uploadPath, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
            	 LOGGER.log(Level.WARNING, "Uploader error", e);
                 response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Errore nel salvataggio del file.");
            }
            
            

        }


    }
	

}
