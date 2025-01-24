package com.yoru.model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale.Category;

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
	private static final String TABLE_GADGET = "gadgetview";
	
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
            connection.setAutoCommit(false);
            String sql = "SELECT * FROM prodotto";

            ps = connection.prepareStatement(sql);

            rs = ps.executeQuery();

            while (rs.next()){
                Prodotto item = new Libro();
                item.setSKU(rs.getInt(COLUMNLABEL1));
                item.setNome(rs.getString(COLUMNLABEL2));
                item.setPrezzo(rs.getFloat(COLUMNLABEL4));
                item.setQuantità(rs.getInt(COLUMNLABEL5));
                item.setId_produttore(rs.getInt(COLUMNLABEL6));
                item.setItemType(rs.getString("Category"));

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
    
    public Collection<Prodotto> getNewBooks(int limit) throws SQLException{
    	
    	List<Prodotto> booksList = new ArrayList<>();
    	ResultSet resultSet = null;
    	
    	String sql = "SELECT SKU, nome FROM shiru.libriview WHERE is_deleted = 0 order by SKU desc limit ? ";
    
    	try(Connection connection = dSource.getConnection();
    			PreparedStatement pStatement = connection.prepareStatement(sql)){
    		
    		pStatement.setInt(1, limit);
    		
    		resultSet = pStatement.executeQuery();
    		
    		
    		while(resultSet.next()) {
    			Prodotto prodotto = new Prodotto();
    			prodotto.setSKU(resultSet.getInt(1));
    			prodotto.setNome(resultSet.getString(2));
    			
    			booksList.add(prodotto);
    		}
    		
    	}
    	
    	return booksList;
    }
    
 public Collection<Prodotto> getNewGadgets(int limit) throws SQLException{
    	
    	List<Prodotto> gadgetList = new ArrayList<>();
    	ResultSet resultSet = null;
    	
    	String sql = "SELECT SKU, nome FROM shiru.gadgetview WHERE is_deleted = 0 order by SKU desc limit ?";
    
    	try(Connection connection = dSource.getConnection();
    			PreparedStatement pStatement = connection.prepareStatement(sql)){
    		
    		pStatement.setInt(1, limit);
    		
    		resultSet = pStatement.executeQuery();
    		
    		
    		while(resultSet.next()) {
    			Prodotto prodotto = new Prodotto();
    			prodotto.setSKU(resultSet.getInt(1));
    			prodotto.setNome(resultSet.getString(2));
    			
    			gadgetList.add(prodotto);
    		}
    		
    	}
    	
    	return gadgetList;
    }
    
    
    public Collection<Prodotto> getBestSellerBook(int limit) throws SQLException{
    	PreparedStatement  ps = null;
    	Connection connection = null;
    	ResultSet resultSet = null;
    	String sql = "SELECT sum(o.quantità) as vendite, p.* from " + OrderItem.TABLE_NAME + " o inner join " + Prodotto.TABLE_NAME +
    				" p on o.SKU=p.SKU WHERE p.category = ? AND p.is_deleted = 0 group by p.sku  order by vendite desc limit ?";
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
    
    
    /*
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
    }*/
    
    public Collection<Prodotto> getAllBooks(int page, int limit)throws SQLException{
    	PreparedStatement ps = null;
    	Connection connection = null;
        ResultSet rs = null;
        List<Prodotto> items = new ArrayList<>();
        int offset = (page - 1)*limit;
        String queryString = "SELECT SKU, nome, prezzo, quantità FROM "+ LIBRIVIEW + "\r\n"
        		+ "    INNER JOIN (\r\n"
        		+ "      SELECT SKU FROM " + LIBRIVIEW + " WHERE is_deleted = 0  ORDER BY SKU LIMIT ? OFFSET ?\r\n"
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
    
    public Collection<Gadgets> getAllGadgets(int page,int limit)throws SQLException{
    	PreparedStatement ps = null;
    	Connection connection = null;
        ResultSet rs = null;
        List<Gadgets> items = new ArrayList<>();
        int offset = (page - 1)*limit;
        String queryString = "SELECT SKU, nome, prezzo, quantità FROM "+ TABLE_GADGET + "\r\n"
        		+ "    INNER JOIN (\r\n"
        		+ "      SELECT SKU FROM " + TABLE_GADGET + " WHERE is_deleted = 0 ORDER BY SKU LIMIT ? OFFSET ?\r\n"
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
    			Gadgets item = new Gadgets();
    			item.setSKU(rs.getInt(Gadgets.COLUMNLABEL1));
    			item.setNome(rs.getString(Gadgets.COLUMNLABEL2));
    			item.setPrezzo(rs.getFloat(Gadgets.COLUMNLABEL4));
    			item.setQuantità(rs.getInt(Gadgets.COLUMNLABEL5));
    			item.setItemType(Gadgets.ItemType.GADGET);
    			items.add(item);

    		}
        	
			
		} finally {
			connection.close();
			ps.close();
			rs.close();
		}
    	
    	return items;
    }

    @Override
    public Prodotto getById(int id)throws SQLException{
        String item_sql = "SELECT category FROM prodotto WHERE SKU = ? AND is_deleted = 0";

        
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
    	
    	String book_sql = "SELECT * FROM libriview WHERE SKU = ? AND is_deleted = 0";
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
    	String gadget_sql = "SELECT * FROM GadgetView WHERE SKU = ? AND is_deleted = 0";
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
    
  public int itemsCount(Prodotto.ItemType type) throws SQLException{
    	
    	String booksql = "SELECT count(SKU) as items FROM libriview";
    	String gadgetsql = "SELECT count(SKU) as items FROM gadgetview";
    	int counts = -1;
    	ResultSet rSet = null;
    	PreparedStatement pStatement = null;
    	
    	
    	try(Connection connection = dSource.getConnection();
    			){
    		
    		
    		if (type == ItemType.LIBRO) {
    			pStatement = connection.prepareStatement(booksql);
			}else {
				pStatement = connection.prepareStatement(gadgetsql);
			}
    		
    		rSet = pStatement.executeQuery();
    		if (rSet.next()) {
				 counts = rSet.getInt(1);
			}
    		
    	}finally {
			if (rSet != null) {
				rSet.close();
			}
			if (pStatement != null) {
				pStatement.close();
			}
		}
    			
    	return counts;
    }

  	@Override
    public synchronized int insert(Prodotto entity) throws SQLException{
    	Connection connection =null;
        PreparedStatement ps = null;
        PreparedStatement prodId = null;
        PreparedStatement autor = null;
        Savepoint savepoint = null;
        ResultSet rs = null;
        int SKU = -1;



        try {
            connection = dSource.getConnection();
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            savepoint = connection.setSavepoint("insertProduct");
            String sql = "INSERT INTO prodotto (nome, prezzo, quantità, ID_casa_produttrice, category, descrizione)" +
                    "VALUE (?,?,?,?, ?,?)";
            ps = connection.prepareStatement(sql);
            ps.setString(1, entity.getNome());
            ps.setFloat(2, entity.getPrezzo());
            ps.setInt(3, entity.getQuantità());
            ps.setInt(4, entity.getId_produttore());
            ps.setString(5, entity.getItemType().toString());
            ps.setString(6, entity.getDescrizione());

            int result = ps.executeUpdate();

            if (result > 0) {
                System.out.println("Inserimento effettuato con successo\n");
                String productIdSql = "SELECT MAX(SKU) AS lastID FROM prodotto";
                prodId = connection.prepareStatement(productIdSql);
                rs = prodId.executeQuery();
                SKU = -1;
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
	                        
	
	                    }
	                    else
	                        connection.rollback(savepoint);
                    }else if (entity.getItemType() == Prodotto.ItemType.GADGET) {
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
        return SKU;
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

            String sql = "UPDATE " + TABLE_NAME +" SET  prezzo = ?, quantità = ?  WHERE prodotto.SKU = ?";
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
        }finally {

            if (ps != null)
                ps.close();
            con.close();

        }
        return  op;
    }

    @Override
    public synchronized boolean remove(Prodotto entity) throws SQLException {
    	
    	boolean  op = false;
    	
    	String sql = "UPDATE " + TABLE_NAME +" SET  is_deleted = 1 WHERE prodotto.SKU = ?";
    	
    	try(Connection conn = dSource.getConnection();
    			PreparedStatement ps = conn.prepareStatement(sql);){
    		
    		ps.setInt(1, entity.getSKU());
    		
    		if(ps.executeUpdate() > 0) 
    			op = true;
    		
    		
    	}
    	
        return op;
    }
}
