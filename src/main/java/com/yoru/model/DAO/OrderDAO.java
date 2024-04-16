package com.yoru.model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

import javax.sql.DataSource;

import com.yoru.DBServices.GenericDBOp;
import com.yoru.model.Entity.Order;
import com.yoru.model.Entity.OrderItem;

import static com.yoru.model.Entity.Order.*;

public class OrderDAO implements GenericDBOp<Order>{
	
	private static final Logger LOGGER = Logger.getLogger(OrderDAO.class.getName());
	
	private DataSource ds;
	
	public OrderDAO(DataSource dataSource) {
		ds = dataSource;
	}
	
	
	  @Override
	    public Collection<Order> getAll() throws SQLException{
	        return null;
	    }

	    public Collection<Order> getAllByUser(String email) throws SQLException{
	        Connection connection = null;
	        PreparedStatement ps = null;
	        ResultSet rs = null;
	        Collection<Order> orders = new ArrayList<>();


	        try{
	            connection = ds.getConnection();
	            String sql = "SELECT * FROM order_details WHERE email = ?";

	            ps = connection.prepareStatement(sql);
	            ps.setString(1,email);

	            rs = ps.executeQuery();

	            while (rs.next()){
	                Order order = new Order();
	                order.setId(rs.getInt(COLUMNLABEL1));
	                order.setCostoTotOrdine(rs.getFloat(COLUMNLABEL2));
	                order.setId_pagamento(rs.getInt(COLUMNLABEL4));
	                order.setDataPagamento(rs.getDate(COLUMNLABEL3));
	                order.setImportoPagamento(rs.getFloat(COLUMNLABEL5));
	                order.setEmail(email);

	                orders.add(order);
	            }

	            connection.commit();
	        } finally {

                if (ps != null)
                    ps.close();
                connection.close();

	        }
	        return orders;
	    }

	    @Override
	    public Order getById(int id) throws SQLException{
	        Connection connection =null;
	        PreparedStatement ps = null;
	        ResultSet rs = null;
	        Order ordine = null;


	        try {
	            connection = ds.getConnection();

	            String sql = "SELECT order_details.* FROM order_details WHERE order_details.ID_ordine = ?";
	            ps = connection.prepareStatement(sql);
	            ps.setInt(1, id);

	            rs = ps.executeQuery();

	            if(rs.next()){
	                ;
	            }

	            connection.commit();

	        } finally {

                if (ps != null)
                    ps.close();
                if(rs != null)
                	rs.close();
                connection.close();

	        }
	        return ordine;
	    }
	    
	    
	    public synchronized boolean checkOut(int cartID, int userID) throws SQLException{
	    	Connection connection = null;
	    	PreparedStatement ps = null;
	    	ResultSet rs = null;
	    	Savepoint savepoint = null;
	    	String item = "SELECT SKU, quantità FROM prodotto WHERE SKU = ?";
	    	String order = "INSERT INTO order_details (userId, totale, idPagamento, data_pagamento) VALUE (?, ?, ?, ?)";
	    	
	    	try {
	    		connection = ds.getConnection();
	    		connection.setAutoCommit(false);
	    		savepoint = connection.setSavepoint("checkOut");
	    		connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
	    		
	    		ps = connection.prepareStatement(order);
	    		
	    		if(ps.executeUpdate() > 0) {
		    		
		    		String sql = "SELECT * FROM Cart WHERE cartId = ?";
		    		
		    		ps = connection.prepareStatement(sql);
		    		ps.setInt(1, userID);
		    		
		    		rs = ps.executeQuery();
		    		while(rs.next()) {
		    			
		    			
		    			
		    			
		    		}
	    		}else {
	    			if(savepoint != null)
	    				connection.rollback(savepoint);
	    			else
	    				connection.rollback();
	    		}
	    		
	    		
	    		
	    		
	    	}finally{
	    		if(ps != null)
	    			ps.close();
	    		if(rs != null)
	    			rs.close();
	    		connection.close();
	    	}
	    	
	    	
	    	
	    	return false;
	    	
	    	
	    	
	    	
	    	
	    }

	    @Override
	    public synchronized boolean insert(Order order) throws SQLException{
	        Connection connection =null;
	        PreparedStatement ps = null;
	        PreparedStatement prodId = null;
	        PreparedStatement bookSt = null;
	        Savepoint savepoint = null;
	        ResultSet rs = null;
	        boolean statement = false;



	        try {
	            connection = ds.getConnection();
	            connection.setAutoCommit(false);
	            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
	            savepoint = connection.setSavepoint("insertOrder");
	            String sql = "INSERT INTO order_details (ID_ordine, costo_totale_ordine, data_pagamento, ID_pagamento, importo_pagamento, email)" +
	                    "VALUE (?,?,?,?,?,?)";
	            ps = connection.prepareStatement(sql);
	            ps.setInt(1, order.getId());
	            ps.setFloat(2, order.getCostoTotOrdine());
	            ps.setDate(3, order.getDataPagamento());
	            ps.setInt(4, order.getId_pagamento());
	            ps.setFloat(5, order.getImportoPagamento());
	            ps.setString(6, order.getEmail());

	            int result = ps.executeUpdate();

	            if (result > 0) {
	                System.out.println("Inserimento effettuato con successo\n");
	                String productIdSql = "SELECT MAX(ID_ordine) as lastId FROM order_details";
	                ps = connection.prepareStatement(productIdSql);
	                rs = ps.executeQuery();
	                int ID = -1;
	                if (rs.next()) {
	                    ID = rs.getInt("lastId");
	                    System.out.println("SKU: " + ID);

	                    sql = "INSERT INTO order_items(ID_ordine, email, SKU, quantità)" +
	                            "VALUES (?,?,?,?)";

	                    int id = order.getId();
	                    String email = order.getEmail();
	                    List<OrderItem> orderDetails = order.getOrderDetailList();
	                    bookSt = connection.prepareStatement(sql);
	                    int i =0;

	                    for (OrderItem orderItem : orderDetails) {

	                        bookSt.setInt(1, id);
	                        bookSt.setString(2, email);
	                        bookSt.setInt(3, orderItem.getSKU());
	                        bookSt.setInt(4, orderItem.getQuantity());
	                        bookSt.addBatch();
	                        i++;


	                        if (i % 1000 == 0 || i == orderDetails.size()) {
	                            bookSt.executeBatch(); // Execute every 1000 items.
	                        }
	                    }
	                }
	                else {
	                    connection.rollback(savepoint);

	                }

	            }
	            else {
	                System.out.println("Impossibile inserire il record \n");
	                connection.rollback(savepoint);
	            }

	            connection.commit();


	      

	        } finally {
	            
                if (rs != null)
                    rs.close();
                if(bookSt != null)
                    bookSt.close();;

                if(prodId != null)
                    prodId.close();

                if (ps != null)
                    ps.close();
                connection.close();
	            
	        }
	        return statement;
	    }


		@Override
		public boolean update(Order entity) throws SQLException {
			// TODO Auto-generated method stub
			return false;
		}


		@Override
		public boolean remove(Order entity) throws SQLException {
			// TODO Auto-generated method stub
			return false;
		}


}
