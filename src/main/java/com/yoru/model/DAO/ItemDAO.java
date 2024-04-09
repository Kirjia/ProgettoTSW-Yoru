package com.yoru.model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.sql.DataSource;

import com.yoru.DBServices.GenericDBOp;
import com.yoru.model.Entity.Gadgets;
import com.yoru.model.Entity.Libro;
import com.yoru.model.Entity.Prodotto;

import static com.yoru.model.Entity.Prodotto.*;
import static com.yoru.model.Entity.Libro.*;

public class ItemDAO implements GenericDBOp<Prodotto> {
	
	private DataSource dSource;
	private static final String LIBRIVIEW = "libriview";
	private static final String TABLE_NAME = "Prodotto";
	
	public ItemDAO(DataSource ds) {
		dSource = ds;
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
    
    
    public Collection<Libro> getAllBooks(int page)throws SQLException{
    	PreparedStatement ps = null;
    	Connection connection = null;
        ResultSet rs = null;
        List<Libro> items = new ArrayList<>();
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
        return null;
    }

    @Override
    public synchronized boolean insert(Prodotto entity) throws SQLException{
        return false;
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
