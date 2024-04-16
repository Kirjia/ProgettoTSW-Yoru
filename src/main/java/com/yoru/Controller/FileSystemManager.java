package com.yoru.Controller;

import java.io.*;
import java.net.URLDecoder;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
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
    	this.filePath = "/files";
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

            InputStream in = (InputStream) request.getAttribute("InputStream");
            String path = (String) request.getAttribute("Path");

            File file = new File(filePath + "/" + path);
            OutputStream out = null;
            try {
            out = new FileOutputStream(file);
            	byte[] buffer = new byte[BUFFER];
                while(in.read(buffer) >= 0)
                    out.write(buffer);
            }catch (IOException e) {
				LOGGER.log(Level.WARNING, "Uploder error", e);
			
			} finally {
				 
	            out.close();
	            in.close();
			}
            
            

        }


    }
	

}
