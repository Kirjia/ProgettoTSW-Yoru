package com.yoru.model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.sql.DataSource;

import com.yoru.DBServices.GenericDBOp;
import com.yoru.model.Entity.Autore;
import com.yoru.model.Entity.Gadgets;
import com.yoru.model.Entity.Libro;
import com.yoru.model.Entity.OrderItem;
import com.yoru.model.Entity.Prodotto;

import static com.yoru.model.Entity.Prodotto.*;
import static com.yoru.model.Entity.Libro.*;

public class ItemDAO implements GenericDBOp<Prodotto> {
	
	private DataSource dSource;
	private static final String LIBRIVIEW = "libriview";
	private static final String TABLE_NAME = "Prodotto";
	private static final String TABLE_REALIZZA = "realizza";
	
	public ItemDAO(DataSource ds) {
		dSource = ds;
	}
	
	
	
	
	public Collection<Autore> getAutorsByBook(int bookId) throws SQLException{
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Autore> autori = new ArrayList<>();
		
		
		try {
			 connection = dSource.getConnection();
	            String sql = "SELECT a.* FROM " + TABLE_REALIZZA + " r inner join " + Autore.TABLE_NAME + " a on r.ID_autore = a.ID_autore WHERE SKU = ?";

	            ps = connection.prepareStatement(sql);
	            ps.setInt(1, bookId);

	            rs = ps.executeQuery();

	            while (rs.next()){
	                Autore autore = new Autore();
	                autore.setNome(rs.getString(Autore.COLMUNLABEL2));
	                autore.setCognome(rs.getString(Autore.COLMUNLABEL3));
	                autore.setID(rs.getInt(Autore.COLMUNLABEL1));
	                autore.setNomeArte(rs.getString(Autore.COLMUNLABEL4));
	               


	                autori.add(autore);
	            }

	            connection.commit();
	        }finally {

	            if (ps != null)
	                ps.close();
	            connection.close();

	        }
		
		
		
		return autori;
	}
	
    @Override
    public Collection<Prodotto> getAll()  throws SQLException{
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Prodotto> items = new ArrayList<>();


        try{
            connection = dSource.getConnection();
            String sql = "SELECT * FROM prodotto";

            ps = connection.prepareStatement(sql);

            rs = ps.executeQuery();

            while (rs.next()){
                Prodotto item = new Libro();
                item.setSKU(rs.getInt(COLUMNLABEL1));
                item.setNome(rs.getString(COLUMNLABEL2));
                item.setPeso(rs.getFloat(COLUMNLABEL3));
                item.setPrezzo(rs.getFloat(COLUMNLABEL4));
                item.setQuantità(rs.getInt(COLUMNLABEL5));
                item.setId_produttore(rs.getInt(COLUMNLABEL6));


                items.add(item);
            }

            connection.commit();
        }finally {

            if (ps != null)
                ps.close();
            connection.close();

        }
        return items;
    }
    
    
    public Collection<Prodotto> getBestSellerBook(int limit) throws SQLException{
    	PreparedStatement  ps = null;
    	Connection connection = null;
    	ResultSet resultSet = null;
    	String sql = "SELECT sum(o.quantità) as vendite, p.* from " + OrderItem.TABLE_NAME + " o inner join " + Prodotto.TABLE_NAME +
    				" p on o.SKU=p.SKU WHERE p.category = ? group by p.sku  order by vendite desc limit ?";
    	List<Prodotto> books = new ArrayList<>();
    	try {
			connection = dSource.getConnection();
			connection.setAutoCommit(false);
			ps = connection.prepareStatement(sql);
			
			ps.setString(1, Prodotto.ItemType.LIBRO.toString());
			ps.setInt(2, limit);
			resultSet = ps.executeQuery();
			
			while(resultSet.next()) {
				Prodotto book = new Prodotto();
				book.setSKU(resultSet.getInt(Prodotto.COLUMNLABEL1));
				book.setNome(resultSet.getString(Prodotto.COLUMNLABEL2));
				book.setPrezzo(resultSet.getFloat(Prodotto.COLUMNLABEL4));
				book.setQuantità(resultSet.getInt(Prodotto.COLUMNLABEL5));
				book.setId_produttore(resultSet.getInt(Prodotto.COLUMNLABEL6));
				book.setDescrizione(resultSet.getString(Prodotto.COLUMNLABEL7));
				book.setItemType(Prodotto.ItemType.LIBRO);
				
				books.add(book);
			}
			
			
    		
		} finally {
			if (ps != null) {
				ps.close();
			}
			if (resultSet != null) {
				resultSet.close();
			}
			connection.close();
		}
    	
    	
    	return books;
    }
    
    
    
    public Collection<Prodotto> getNewBooks(int limit) throws SQLException{
    	PreparedStatement  ps = null;
    	Connection connection = null;
    	ResultSet resultSet = null;
    	String sql = "SELECT * from "  + Prodotto.TABLE_NAME + "  WHERE category = " + Prodotto.ItemType.LIBRO + " order by SKU desc limit ?";
    	List<Prodotto> books = new ArrayList<>();
    	try {
			connection = dSource.getConnection();
			connection.setAutoCommit(false);
			ps = connection.prepareStatement(sql);
			
			ps.setInt(1, limit);
			resultSet = ps.executeQuery();
			
			while(resultSet.next()) {
				Prodotto book = new Prodotto();
				book.setSKU(resultSet.getInt(Prodotto.COLUMNLABEL1));
				book.setNome(resultSet.getString(Prodotto.COLUMNLABEL2));
				book.setPrezzo(resultSet.getFloat(Prodotto.COLUMNLABEL4));
				book.setQuantità(resultSet.getInt(Prodotto.COLUMNLABEL5));
				book.setId_produttore(resultSet.getInt(Prodotto.COLUMNLABEL6));
				book.setDescrizione(resultSet.getString(Prodotto.COLUMNLABEL7));
				book.setItemType(Prodotto.ItemType.LIBRO);
				
			}
			
			
    		
		} finally {
			if (ps != null) {
				ps.close();
			}
			if (resultSet != null) {
				resultSet.close();
			}
			connection.close();
		}
    	
    	
    	return books;
    }
    
    public Collection<Prodotto> getAllBooks(int page, int limit)throws SQLException{
    	PreparedStatement ps = null;
    	Connection connection = null;
        ResultSet rs = null;
        List<Prodotto> items = new ArrayList<>();
        int offset = (page - 1)*limit;
        String queryString = "SELECT SKU, nome, prezzo, quantità FROM "+ LIBRIVIEW + "\r\n"
        		+ "    INNER JOIN (\r\n"
        		+ "      SELECT SKU FROM " + LIBRIVIEW + " ORDER BY SKU LIMIT ? OFFSET ?\r\n"
        		+ "    ) AS tmp USING (SKU)\r\n"
        		+ "ORDER BY\r\n"
        		+ "  SKU";
        
    	
    	try {
    		connection = dSource.getConnection();
    		ps = connection.prepareStatement(queryString);
    		ps.setInt(1, limit);
    		ps.setInt(2, offset);
    		
    		
    		rs = ps.executeQuery();
    		while(rs.next()) {
    			Prodotto item = new Prodotto();
    			item.setSKU(rs.getInt(Prodotto.COLUMNLABEL1));
    			item.setNome(rs.getString(Prodotto.COLUMNLABEL2));
    			item.setPrezzo(rs.getFloat(Prodotto.COLUMNLABEL4));
    			item.setQuantità(rs.getInt(Prodotto.COLUMNLABEL5));
    			item.setItemType(Prodotto.ItemType.LIBRO);
    			items.add(item);
    			
    		}
        	
			
		} finally {
			connection.close();
			ps.close();
			rs.close();
		}
    	
    	return items;
    }
    
    public Collection<Gadgets> getAllGadgets(int page)throws SQLException{
    	PreparedStatement ps = null;
    	Connection connection = null;
        ResultSet rs = null;
        List<Gadgets> items = new ArrayList<>();
        String queryString = "SELECT * from" + LIBRIVIEW;

    	
    	try {
    		connection = dSource.getConnection();
        	
			
		} finally {
			connection.close();
			ps.close();
			rs.close();
		}
    	
    	return null;
    }

    @Override
    public Prodotto getById(int id)throws SQLException{
        String item_sql = "SELECT category FROM prodotto WHERE SKU = ?";

        
        ResultSet itemSet = null;

        Prodotto item = null;
        
        
        try(Connection connection = dSource.getConnection();
        		PreparedStatement itemStatement = connection.prepareStatement(item_sql);
        		){
        	
        	
        		itemStatement.setInt(1, id);
        		
        		itemSet = itemStatement.executeQuery();
        		
        		item = new Prodotto();
        		
        		if (itemSet.next()) {
					item.setItemType(itemSet.getString("category"));
					
				}
        }
        
        if (item.getItemType() == Prodotto.ItemType.LIBRO) {
			item = getBook(id);
		}else {
			item = getGadget(id);
		}
        
        return item;
    }
    
    private Libro getBook(int sku) throws SQLException{
    	
    	String book_sql = "SELECT * FROM libriview WHERE SKU = ?";
    	String autors_sql = "SELECT a.* FROM autori a LEFT JOIN realizza r ON r.ID_autore = a.ID_autore WHERE r.SKU = ?";
    	ResultSet resultSet = null;
    	ResultSet autoriSet = null;
    	Libro book = null;
    	
    	try(Connection connection = dSource.getConnection();
    			PreparedStatement psStatement = connection.prepareStatement(book_sql);
    			PreparedStatement autorStatement = connection.prepareStatement(autors_sql)){
    		
    		psStatement.setInt(1, sku);
    		
    		resultSet = psStatement.executeQuery();
    		
    		if (resultSet.next()) {
				book = new Libro();
				
				book.setSKU(sku);
				book.setItemType(Prodotto.ItemType.LIBRO);
				book.setNome(resultSet.getString(Libro.COLUMNLABEL2));
				book.setDescrizione(resultSet.getString(Libro.COLUMNLABEL7));
				book.setId_produttore(resultSet.getInt(Libro.COLUMNLABEL6));
				book.setISBN(resultSet.getString(Libro.COLUMNLABEL8));
				book.setLingua(resultSet.getString(Libro.COLUMNLABEL10));
				book.setNumeroPagine(resultSet.getInt(Libro.COLUMNLABEL9));
				book.setPeso(resultSet.getFloat(Libro.COLUMNLABEL3));
				book.setPrezzo(resultSet.getFloat(Libro.COLUMNLABEL4));
				book.setQuantità(resultSet.getInt(Libro.COLUMNLABEL5));
				
				autorStatement.setInt(1, sku);
				
				autoriSet = autorStatement.executeQuery();
				
				List<Autore> autori = new ArrayList<>();
				
				while(autoriSet.next()) {
					Autore autore = new Autore(
							autoriSet.getInt(1),
							autoriSet.getString(2),
							autoriSet.getString(3),
							autoriSet.getString(4));
					
					autori.add(autore);
				}
				
				book.setAutori(autori);
    			
				
			}
    		
    	}
    	
    	
    	return book;
    }
    
    private Gadgets getGadget(int sku) throws SQLException{
    	String gadget_sql = "SELECT * FROM GadgetView WHERE SKU = ?";
    	String materials_sql = "SELECT s.Materiale FROM struttura s INNER JOIN costituito c ON c.Materiale = s.Materiale WHERE c.SKU = ?;";
    	ResultSet resultSet = null;
    	ResultSet materialSet = null;
    	Gadgets gadget = null;
    	
    	try(Connection connection = dSource.getConnection();
    			PreparedStatement psStatement = connection.prepareStatement(gadget_sql);
    			PreparedStatement materialsStatement = connection.prepareStatement(materials_sql)){
    		
    		psStatement.setInt(1, sku);
    		
    		resultSet = psStatement.executeQuery();
    		
    		if (resultSet.next()) {
    			gadget = new Gadgets();
				
    			gadget.setSKU(sku);
    			gadget.setItemType(Prodotto.ItemType.GADGET);
    			gadget.setNome(resultSet.getString(Gadgets.COLUMNLABEL2));
    			gadget.setDescrizione(resultSet.getString(Gadgets.COLUMNLABEL7));
    			gadget.setId_produttore(resultSet.getInt(Gadgets.COLUMNLABEL6));
    			gadget.setPeso(resultSet.getFloat(Gadgets.COLUMNLABEL3));
    			gadget.setPrezzo(resultSet.getFloat(Gadgets.COLUMNLABEL4));
    			gadget.setQuantità(resultSet.getInt(Gadgets.COLUMNLABEL5));
    			gadget.setMarchio(resultSet.getString("Marchio"));
    			gadget.setModello(resultSet.getString("modello"));
				

				materialsStatement.setInt(1, sku);
				
				materialSet = materialsStatement.executeQuery();
				
				List<String> materialsList = new ArrayList<>();
				
				while(materialSet.next()) {
					String tmp = materialSet.getString(1);
					
					materialsList.add(tmp);
				}
				
				gadget.setMateriali(materialsList);
    			
				
			}
    		
    	}
    	
    	
    	return gadget;
    }


    @Override
    public synchronized boolean insert(Prodotto entity) throws SQLException{
    	Connection connection =null;
        PreparedStatement ps = null;
        PreparedStatement prodId = null;
        PreparedStatement autor = null;
        Savepoint savepoint = null;
        ResultSet rs = null;
        boolean statement = false;



        try {
            connection = dSource.getConnection();
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            savepoint = connection.setSavepoint("insertProduct");
            String sql = "INSERT INTO prodotto (nome, peso, prezzo, quantità, ID_casa_produttrice, category)" +
                    "VALUE (?,?,?,?,?, ?)";
            ps = connection.prepareStatement(sql);
            ps.setString(1, entity.getNome());
            ps.setFloat(2, entity.getPeso());
            ps.setFloat(3, entity.getPrezzo());
            ps.setInt(4, entity.getQuantità());
            ps.setInt(5, entity.getId_produttore());

            int result = ps.executeUpdate();

            if (result > 0) {
                System.out.println("Inserimento effettuato con successo\n");
                String productIdSql = "SELECT MAX(SKU) AS lastID FROM prodotto";
                prodId = connection.prepareStatement(productIdSql);
                rs = prodId.executeQuery();
                int SKU = -1;
                if (rs.next()) {
                    SKU = rs.getInt("lastID");
                    System.out.println("SKU: " + SKU);
                    if (entity.getItemType() == Prodotto.ItemType.LIBRO) {
						
                    	Libro book = (Libro) entity;
	                	int resultChild = insertBook(book, connection, SKU);
	                    
	                    if(resultChild > 0) {
	                        sql = "INSERT INTO realizza(ID_autore, SKU) VALUES (?,?)";
	                        autor = connection.prepareStatement(sql);
	                        
                        	List<Autore> autoriList =  book.getAutori();
	                        for (Autore a : autoriList) {
	                            int id = a.getID();
	                            autor.setInt(1, id);
	                            autor.setInt(2, SKU);
	                            autor.addBatch();
	                        }
	
	                        autor.executeBatch();
	                        System.out.println("Inserimento effettuato con successo\n");
	                        statement = true;
	
	                    }
	                    else
	                        connection.rollback(savepoint);
                    }else if (entity.getItemType() == Prodotto.ItemType.LIBRO) {
                    	Gadgets gadget = (Gadgets) entity;
                    	int resultChild = insertGadget(gadget, connection, SKU);
                    	if(resultChild > 0) {
                            sql = "INSERT INTO costituito(SKU, Materiale) VALUES (?, ?)";

                            PreparedStatement psMateriali = connection.prepareStatement(sql);

                            for (String s : gadget.getMateriali()) {
                                psMateriali.setInt(1, SKU);
                                psMateriali.setString(2, s);
                                psMateriali.addBatch();
                            }

                            psMateriali.executeBatch();


                            System.out.println("Inserimento effettuato con successo\n");
                            statement = true;
                        }
                        else
                            connection.rollback(savepoint);
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
			
			    if(prodId != null)
			        prodId.close();
			
		    	if (ps != null)
                    ps.close();
		    	connection.close();
            
        }
        return statement;
    }
    
    private synchronized int insertBook(Libro book, Connection connection, int SKU) throws SQLException{
		PreparedStatement bookSt = null;
		String sql = "INSERT INTO Libro(SKU, ISBN, pagine, lingua)" +
		         "VALUE (?,?,?,?)";
		try {
			
		
		bookSt = connection.prepareStatement(sql);
		bookSt.setInt(1, SKU);
		bookSt.setString(2, book.getISBN());
		bookSt.setInt(3, book.getNumeroPagine());
		bookSt.setString(4, book.getLingua());

        return bookSt.executeUpdate();
		}finally {
			if (bookSt!= null) {
				bookSt.close();
			}
		}
        
	}
    
    
    private synchronized int insertGadget(Gadgets gadget, Connection connection, int SKU) throws SQLException{
    	PreparedStatement gadSt = null;
    	String sql = "INSERT INTO gadgets(SKU, modello, Marchio)" +
                "VALUE (?,?,?)";

    	try {
        gadSt = connection.prepareStatement(sql);
        gadSt.setInt(1, SKU);
        gadSt.setString(2, gadget.getMarchio());
        gadSt.setString(3, gadget.getModello());


        return gadSt.executeUpdate();
    	}finally {
			if (gadSt != null) {
				gadSt.close();
			}
		}

        
    }
    
    
    

    @Override
    public synchronized boolean update(Prodotto item) throws SQLException{
        Connection con = null;
        PreparedStatement ps= null;
        boolean op = false;

        try {
            con = dSource.getConnection();

            String sql = "UPDATE" + TABLE_NAME +" SET  prezzo = ?, quantità = ?  WHERE prodotto.SKU = ?";
            ps = con.prepareStatement(sql);
            System.out.println("QUERY: " + sql);

            ps.setFloat(1, item.getPrezzo());
            ps.setInt(2, item.getQuantità());
            ps.setInt(3, item.getSKU());

            int result = ps.executeUpdate();

            if (result > 0) {
                op=true;
                System.out.println("Update effettuato");

            } else {
                System.out.println("Impossibile effettuare l'update");
            }
            con.commit();
        }finally {

            if (ps != null)
                ps.close();
            con.close();

        }
        return  op;
    }

    @Override
    public synchronized boolean remove(Prodotto entity) {
        return false;
    }
}
