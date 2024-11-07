package com.yoru.model.DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
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

	    public Collection<Order> getAllByUser(int userId) throws SQLException{
	        Connection connection = null;
	        PreparedStatement ps = null;
	        ResultSet rs = null;
	        ResultSet itemSet = null;
	        Collection<Order> orders = new ArrayList<>();
	        PreparedStatement itemStatement = null;
	        String itemString = "SELECT o.quantità, o.SKU, p.Nome FROM order_items o INNER JOIN Prodotto p ON p.SKU = o.SKU WHERE ID_ordine = ?";


	        try{
	            connection = ds.getConnection();
	            String sql = "SELECT * FROM order_details WHERE userId = ?";

	            ps = connection.prepareStatement(sql);
	            ps.setInt(1,userId);

	            rs = ps.executeQuery();

	            while (rs.next()){
	                Order order = new Order();
	                order.setId(rs.getInt(COLUMNLABEL1));
	                order.setId_pagamento(rs.getString(COLUMNLABEL4));
	                order.setDataPagamento(rs.getDate(COLUMNLABEL3));
	                order.setImportoPagamento(rs.getFloat(COLUMNLABEL5));
	                order.setCostoTotOrdine(rs.getFloat("importo_pagamento"));
	                order.setEmail("prova");
	                
	                itemStatement = connection.prepareStatement(itemString);
	                itemStatement.setInt(1, order.getId());
	                
	                itemSet = itemStatement.executeQuery();
	                
	                List<OrderItem> items = new ArrayList<>();
	                
	                while(itemSet.next()) {
	                	OrderItem item = new OrderItem();
	                	
	                	item.setQuantity(itemSet.getInt(1));
	                	item.setSKU(itemSet.getInt(2));
	                	item.setNome(itemSet.getString(3));
	                	items.add(item);
	                }
	                order.setorderItemList(items);
	                

	                orders.add(order);
	            }

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
	    	Cart cart = new Cart(userId);
	    	Connection connection = null;
	    	PreparedStatement cartPs = null;
	    	PreparedStatement cartItemStatement = null;
	    	ResultSet cartRs = null;
	    	ResultSet cartItemRs = null;
	    	String cartItemsSQL = "SELECT c.user_id, c.quantity, p.SKU, p.nome, p.prezzo FROM cart_items  c inner join prodotto p on c.SKU = p.SKU WHERE user_id = ?";
	    	
	    	
	    	try {
	    		connection = ds.getConnection();
	    		connection.setAutoCommit(false);
	    		
	    			
	    			
				cartItemStatement = connection.prepareStatement(cartItemsSQL);
	    		cartItemStatement.setInt(1, userId);	
	    		cartItemRs = cartItemStatement.executeQuery();
	    		
	    		Collection<CartItem> cartItems = new ArrayList<CartItem>();
	    		
	    		while(cartItemRs.next()) {
	    			CartItem item = new CartItem(cartItemRs.getInt(1),
	    										cartItemRs.getInt(3),
	    										cartItemRs.getInt(2),
	    										cartItemRs.getFloat(5),
	    										cartItemRs.getString(4));
	    			cartItems.add(item);
	    			
	    		}
	    		
	    		cart.setItems(cartItems);
	    		connection.commit();	
	    			
	    		
				
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
	    
	    
	    public synchronized boolean checkOut(Cart cart, String paymentId) throws SQLException{
	    	Connection connection = null;
	    	PreparedStatement ps = null;
	    	PreparedStatement itemPreparedStatement = null;
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
	    	String cleanCart = "DELETE FROM cart_items WHERE cart_items.user_id = ?";
	    	
	    	String errString = "invalidCheckOut";
	    	
	    	int orderId = -1;
	    	boolean result = false;
	    	
	    	
	    	try {
	    		connection = ds.getConnection();
	    		connection.setAutoCommit(false);
	    		savepoint = connection.setSavepoint("checkOut");
	    		connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
	    		
	    		//SELECT quantità FROM prodotto WHERE SKU = ?
	    		itemPreparedStatement = connection.prepareStatement(item);
	    		
	    		
	    		//"INSERT INTO order_details (userId, importo_pagamento, ID_pagamento, data_pagamento, createdAt, modifiedAt) VALUE (?, ?, ?, now(), now(), now())";
	    		ps = connection.prepareStatement(order, PreparedStatement.RETURN_GENERATED_KEYS);
	    		ps.setInt(1, cart.getUserId());
	    		ps.setFloat(2, cart.getTotal());
	    		ps.setString(3, paymentId);
	    		
	    		
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
		    			
		    			
		    			
		    			int SKU =  cartItem.getSKU();
		    			int quantity = cartItem.getQuantity();
		    			
		    			itemPreparedStatement.setInt(1, SKU);
		    			
		    			itemRs = itemPreparedStatement.executeQuery();
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
		    		removeCartPs.setInt(1, cart.getUserId());
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
	    		if (itemPreparedStatement != null) {
					itemPreparedStatement.close();
				}
	    		
	    		if(rs != null)
	    			rs.close();
	    		if(itemRs != null)
	    			itemRs.close();
				

	    		connection.close();
	    	}
	    	
	    	
	    	
	    	return result;

	    }
	    
	    public boolean insertIntoCart(int user_id, int sku, int quantity)throws SQLException {
	    	Connection connection = null;
	    	PreparedStatement pStatement = null;
	    	PreparedStatement checkPStatement = null;
	    	ResultSet checkSet = null;
	    	String checkExistSQL = "SELECT EXISTS ( SELECT 1 FROM cart_items WHERE user_id = ? and SKU = ?)";
			String cartSQL = "INSERT INTO cart_items (user_id, SKU, quantity) VALUE(?, ?, ?)";
	    	String sqlString = "UPDATE cart_items SET quantity = quantity + ? WHERE user_id = ? AND SKU = ?";
	    	
	    	boolean result = false;
	    	
	    	try {
				connection = ds.getConnection();
				
				
				checkPStatement = connection.prepareStatement(checkExistSQL);
				checkPStatement.setInt(1, user_id);
				checkPStatement.setInt(2, sku);
				checkSet = checkPStatement.executeQuery();
				
				if (checkSet.next()) {
					if (checkSet.getInt(1) == 1) {
						pStatement = connection.prepareStatement(sqlString);
						pStatement.setInt(1, quantity);
						pStatement.setInt(2, user_id);
						pStatement.setInt(3, sku);
						
					}else {
						pStatement = connection.prepareStatement(cartSQL);
						pStatement.setInt(1, user_id);
						pStatement.setInt(2, sku);
						pStatement.setInt(3, quantity);
					}
				}
				
				
				
				
				if (pStatement.executeUpdate() > 0)
					result = true;
				
				
				
			} finally {
				if (connection != null) 
					connection.close();
				if (pStatement != null) 
					pStatement.close();
				if(checkPStatement != null)
					checkPStatement.close();
				if(checkSet != null)
					checkSet.close();
				
			}
	    	
	    	
	    	return result;
			
		}
	    
	    public boolean mergeCart(int user_id, HashMap<String, Integer> items) throws SQLException{
	    	
	    	boolean result = false;
	    	String sql = "INSERT INTO cart_items (user_id, SKU, quantity, insert_at, modified_at) VALUES (?, ?, ?, now(), now())"
	    			+ " ON DUPLICATE KEY UPDATE quantity = quantity + ?, modified_at = now();";
	    	
	    	try(Connection connection = ds.getConnection();
	    			PreparedStatement ps = connection.prepareStatement(sql)){
	    		
	    		
	    		Iterator<String> iter = items.keySet().iterator();
	    		while(iter.hasNext()) {
	    			String sku = iter.next();
	    			int quantity = items.get(sku);
	    			ps.setInt(1, user_id);
		    		ps.setInt(2,Integer.parseInt(sku));
		    		ps.setInt(3, quantity);
		    		ps.setInt(4, quantity);
		    		ps.addBatch();
		    		
	    			
	    		}
	    		int[] results = ps.executeBatch();
	    		result = !results.toString().contains("0");
	    		
	    	}
	    	
	    	return result;
	    }
	    
	    

	    @Override
	    public synchronized int insert(Order order) throws SQLException{
	        Connection connection =null;
	        PreparedStatement ps = null;
	        PreparedStatement prodId = null;
	        PreparedStatement bookSt = null;
	        Savepoint savepoint = null;
	        ResultSet rs = null;
	        int result = -1;



	        try {
	            connection = ds.getConnection();
	            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
	            savepoint = connection.setSavepoint("insertOrder");
	            String sql = "INSERT INTO order_details (ID_ordine, costo_totale_ordine, data_pagamento, ID_pagamento, importo_pagamento, email)" +
	                    "VALUE (?,?,?,?,?,?)";
	            ps = connection.prepareStatement(sql);
	            ps.setInt(1, order.getId());
	            ps.setFloat(2, order.getCostoTotOrdine());
	            ps.setDate(3, order.getDataPagamento());
	            ps.setString(4, order.getId_pagamentoStr());
	            ps.setFloat(5, order.getImportoPagamento());
	            ps.setString(6, order.getEmail());

	            result = ps.executeUpdate();

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
	                    List<OrderItem> orderDetails = order.getorderItemList();
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
	        return result;
	    }

	    public boolean removeFromCart(int user_id, int sku, int del) throws SQLException {
	    	Connection connection = null;
	    	PreparedStatement pStatement = null;
	    	boolean result = false;
	    	
	    	try {
				connection = ds.getConnection();
				connection.setAutoCommit(false);
				String cartSQL = "DELETE FROM cart_items WHERE user_id = ? AND SKU = ? ";
				String noDelSQL = "UPDATE cart_items SET quantity = quantity -? user_id = ? AND WHERE SKU = ?";
				
				if(del < 1) {
					pStatement = connection.prepareStatement(cartSQL);
					pStatement.setInt(1, user_id);
					pStatement.setInt(2, sku);
					
					
					
					
					if (pStatement.executeUpdate(cartSQL) > 0)
						result = true;
				}else {
					pStatement = connection.prepareStatement(noDelSQL);
					pStatement.setInt(2, del);
					pStatement.setInt(2, user_id);
					pStatement.setInt(3, sku);
					
					
					
					
					if (pStatement.executeUpdate(cartSQL) > 0)
						result = true;
				}
				
				
			} finally {
				if (connection != null) 
					connection.close();
				if (pStatement != null) 
					pStatement.close();
				
			}
	    	
	    	return result;
	    }
	    
	    public boolean updateCartItem(Connection conn, int user_id, int sku, int quantity) throws SQLException{
	    	
	    	String sqlString = "UPDATE cart_items SET quantity = quantity + ? WHERE user_id = ? AND SKU = ?";
	    	boolean result = false;
	    	
	    	Connection connection = conn;
	    	
	    	try(
	    			PreparedStatement pStatement = connection.prepareStatement(sqlString)){
	    		
	    		pStatement.setInt(2, user_id);
	    		pStatement.setInt(3, sku);
	    		pStatement.setInt(1, quantity);
	    		
	    		if (pStatement.executeUpdate() > 0) {
					result = true;
				}
	    		
	    	}
	    	return result;
	    }

		@Override
		public boolean update(Order entity) throws SQLException {
			Connection connection = null;
			PreparedStatement pStatement = null;
			
			
			
			return false;
		}


		@Override
		public boolean remove(Order entity) throws SQLException {
			// TODO Auto-generated method stub
			return false;
		}


}
