package com.yoru.model.DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import javax.sql.DataSource;

import com.yoru.DBServices.GenericDBOp;
import com.yoru.DBServices.InvalidTransactionException;
import com.yoru.model.Entity.Cart;
import com.yoru.model.Entity.CartItem;
import com.yoru.model.Entity.Order;
import com.yoru.model.Entity.OrderItem;
import com.yoru.model.Entity.Prodotto;

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
	    
	    public synchronized Cart getCart(int userId) throws SQLException {
	    	Cart cart = null;
	    	Connection connection = null;
	    	PreparedStatement cartPs = null;
	    	PreparedStatement cartItemStatement = null;
	    	ResultSet cartRs = null;
	    	ResultSet cartItemRs = null;
	    	String cartSQL = "SELECT * FROM Cart WHERE user_id = ?";
	    	String cartItemsSQL = "SELECT order_items.sku, order_items.ID_ordine, order_items.quantità, nome, prezzo FROM order_items Inner Join prodotto WHERE cart_id = ?";
	    	
	    	
	    	try {
	    		connection = ds.getConnection();
	    		connection.setAutoCommit(false);
	    		
	    		cartPs = connection.prepareStatement(cartSQL);
	    		cartPs.setInt(1, userId);
	    		
	    		cartRs = cartPs.executeQuery();
	    		
	    		if (cartRs.next()) {
					cart = new Cart(cartRs.getInt("cart_id"),
							userId,
							cartRs.getFloat("total"),
							cartRs.getTimestamp("created_at"),
							cartRs.getTimestamp("modified_at"));
	    			
				cartItemStatement = connection.prepareStatement(cartItemsSQL);
	    		cartItemStatement.setInt(1, cart.getId());	
	    		cartItemRs = cartItemStatement.executeQuery();
	    		
	    		Collection<CartItem> cartItems = new ArrayList<CartItem>();
	    		
	    		while(cartItemRs.next()) {
	    			CartItem item = new CartItem(cartItemRs.getInt(1),
	    										cartItemRs.getInt(2),
	    										cartItemRs.getInt(3),
	    										cartItemRs.getFloat(4),
	    										cartItemRs.getString(5));
	    			cartItems.add(item);
	    			
	    		}
	    		
	    		cart.setItems(cartItems);
	    		connection.commit();	
	    			
				}
	    		
				
			} finally {
				if (cartPs != null) {
					cartPs.close();
				}
				if (cartItemStatement != null) {
					cartItemStatement.close();
				}
				if (cartRs != null) {
					cartRs.close();
				}
				if (cartItemRs != null) {
					cartItemRs.close();
				}
				
				connection.close();
			}
	    	
	    	return cart;
	    	
	    }
	    
	    
	    public synchronized boolean checkOut(Cart cart, int paymentId) throws SQLException{
	    	Connection connection = null;
	    	PreparedStatement ps = null;
	    	PreparedStatement updateItemPs = null;
	    	PreparedStatement insertOrderItemsPs = null;
	    	PreparedStatement removeCartPs = null;
	    	ResultSet rs = null;
	    	ResultSet itemRs = null;
	    	Savepoint savepoint = null;
	    	String item = "SELECT quantità FROM prodotto WHERE SKU = ?";
	    	String order = "INSERT INTO order_details (userId, importo_pagamento, ID_pagamento, data_pagamento, createdAt, modifiedAt) VALUE (?, ?, ?, now(), now(), now())";
	    	String updateItemStr = "UPDATE " + Prodotto.TABLE_NAME + " SET quantità = ? WHERE SKU = ?";
	    	String insertOrderItemsStr = "INSERT INTO Order_items (ID_ordine, SKU, quantità) VALUE(?, ?, ?)";
	    	String cleanCart = "DELETE FROM cart WHERE cart.cart_id = ?";
	    	
	    	String errString = "invalidCheckOut";
	    	
	    	int orderId = -1;
	    	boolean result = false;
	    	
	    	
	    	try {
	    		connection = ds.getConnection();
	    		connection.setAutoCommit(false);
	    		savepoint = connection.setSavepoint("checkOut");
	    		connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
	    		
	    		
	    		//"INSERT INTO order_details (userId, importo_pagamento, ID_pagamento, data_pagamento, createdAt, modifiedAt) VALUE (?, ?, ?, now(), now(), now())";
	    		ps = connection.prepareStatement(order, PreparedStatement.RETURN_GENERATED_KEYS);
	    		ps.setInt(1, cart.getUserId());
	    		ps.setFloat(2, cart.getTotal());
	    		ps.setInt(3, paymentId);
	    		
	    		
	    		if(ps.executeUpdate() < 1) 
	    			throw new InvalidTransactionException(errString);
		    	
				rs = ps.getGeneratedKeys();
				if (rs.next()) {
					orderId = rs.getInt(1);
				
	    		
		    		updateItemPs = connection.prepareStatement(updateItemStr);
		    		insertOrderItemsPs = connection.prepareStatement(insertOrderItemsStr);
		    		
		    		Iterator<CartItem> cartItems = cart.getItems().iterator();
		    		
		    		
		    		while(cartItems.hasNext()) {
		    			CartItem cartItem = cartItems.next();
		    			//SELECT quantità FROM prodotto WHERE SKU = ?
		    			ps = connection.prepareStatement(item);
		    			
		    			int SKU =  cartItem.getSKU();
		    			int quantity = cartItem.getQuantity();
		    			
		    			ps.setInt(1, SKU);
		    			
		    			itemRs = ps.executeQuery();
		    			if(itemRs.next()){
		    				int stockQuantity = itemRs.getInt("quantità");
		    				if(stockQuantity >= quantity) {
		    					//"INSERT INTO Order_items (ID_ordine, SKU, quantity) VALUE(?, ?, ?)";
		    					insertOrderItemsPs.setInt(1, orderId);
		    					insertOrderItemsPs.setInt(2, SKU);
		    					insertOrderItemsPs.setInt(3, quantity);
		    					insertOrderItemsPs.addBatch();
		    					
		    					//"UPDATE " + Prodotto.TABLE_NAME + " SET quantità = ? WHERE SKU = ?"
		    					updateItemPs.setInt(1, stockQuantity - quantity);
		    					updateItemPs.setInt(2, SKU);
		    					updateItemPs.addBatch();
	
		    				}else {
								throw new InvalidTransactionException(errString);
							}
		    			}else 
		    				throw new InvalidTransactionException(errString);
		    			
	
		    			int[] orderResult = insertOrderItemsPs.executeBatch();
		    			int[] itemResult = updateItemPs.executeBatch();
		    			for(int i = 0; i < orderResult.length; i++) {
		    				if (orderResult[i] < 1 || itemResult[i] < 1) 
								throw new InvalidTransactionException(errString);
							
		    			}	
		    		}
		    		
		    		removeCartPs = connection.prepareStatement(cleanCart);
		    		removeCartPs.setInt(1, cart.getId());
		    		if (removeCartPs.executeUpdate() < 1 ) 
	    				throw new InvalidTransactionException(errString);
		    		
		    		
	    		}else {
	    			throw new InvalidTransactionException(errString);
	    		}
	    		
	    		
	    		connection.commit();
	    		result = true;

	    	}catch (InvalidTransactionException e) {
				connection.rollback(savepoint);
			
	    	}finally{
	    		if(ps != null)
	    			ps.close();
	    		if (updateItemPs != null)
	    			updateItemPs.close();
	    		if(insertOrderItemsPs != null)
	    			insertOrderItemsPs.close();
	    		if(removeCartPs != null)
	    			removeCartPs.close();
	    		
	    		if(rs != null)
	    			rs.close();
	    		if(itemRs != null)
	    			itemRs.close();
				

	    		connection.close();
	    	}
	    	
	    	
	    	
	    	return result;

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
