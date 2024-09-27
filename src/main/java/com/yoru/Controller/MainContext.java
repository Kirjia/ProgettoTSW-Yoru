package com.yoru.Controller;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;

import com.yoru.model.DAO.AutoreDAO;

import Util.Argon2Hashing;

import java.io.PrintStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * Application Lifecycle Listener implementation class MainContext
 *
 */
@WebListener
public class MainContext implements ServletContextListener {

	private static final Logger LOGGER = Logger.getLogger( MainContext.class.getName() );
    /**
     * Default constructor. 
     * 
     */
    public MainContext() {
        // TODO Auto-generated constructor stub
    }
    
    public void contextInitialized(ServletContextEvent sce)  { 
    	ServletContext context = sce.getServletContext();
    	
    	DataSource ds = null;
    	try {
    		System.out.println("inizio");
    		Context initCtx = new InitialContext();
    		System.out.println("metà");
    		Context envCtx = (Context) initCtx.lookup("java:comp/env");
    		System.out.println("fine");
    		ds = (DataSource) envCtx.lookup("jdbc/YoruDB");
    	}catch(NamingException e) {
    		MainContext.LOGGER.log(Level.SEVERE, "MainContext", e);
    	}
    	
    	context.setAttribute("DataSource", ds);
		System.out.println("DataSource creation...."+ds.toString());

   }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent sce)  { 
         ServletContext context = sce.getServletContext();
         DataSource ds = (DataSource) context.getAttribute("DataSource");
         System.out.println("DataSource deletion...."+ds.toString());
    }

	
}
