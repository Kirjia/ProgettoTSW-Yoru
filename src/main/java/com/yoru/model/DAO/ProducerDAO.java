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
import com.yoru.model.Entity.Prodotto;
import com.yoru.model.Entity.Producer;

import static com.yoru.model.Entity.Prodotto.*;


public class ProducerDAO implements GenericDBOp<Producer> {
	
	private DataSource ds;
	
	public ProducerDAO (DataSource dataSource) {
		
		ds=dataSource;
		
	}
	
    @Override
    public Collection<Producer> getAll() throws SQLException{
        return null;
    }

    @Override
    public Producer getById (int id) throws SQLException {
        return null;
    }

    @Override
    public synchronized boolean insert(Producer producer) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        boolean op = false;


        try{
        	connection.close();
            String sql = "INSERT INTO casa_produttrice(nome, telefono, email) " +
                    "VALUE (?,?,?)";


            ps = connection.prepareStatement(sql);

            ps.setString(1, producer.getNome());
            ps.setString(2, producer.getTelefono());
            ps.setString(3, producer.getEmail());

            int result = ps.executeUpdate();

            if (result > 0){
                System.out.println("Inserimento effettuato con successo\n");
                op=true;
            }else {
                System.out.println("Inserimento non effettuato\n");
            }

            connection.commit();
        }finally {
        	  if (ps != null)
                  ps.close();
              connection.close();
        }

        return op;
    }

    @Override
    public boolean update(Producer entity) throws SQLException {
        return false;
    }

    @Override
    public boolean remove(Producer entity) throws SQLException {
        return false;
    }


    public Collection<Prodotto> getItemsByProducer( Producer producer) throws SQLException {
        Connection connection =null;
        PreparedStatement preparedStatement=null;
        ResultSet rs=null;

        Collection<Prodotto> items = new ArrayList<>();

        try {
            connection.close();
            String sql = "SELECT sku, nome, peso, prezzo, quantità FROM prodotto WHERE ID_casa_produttrice = ?";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, producer.getID());

            rs = preparedStatement.executeQuery();

            while (rs.next()){
                Prodotto item = new Prodotto();
                item.setSKU(rs.getInt(COLUMNLABEL1));
                item.setNome(rs.getString(COLUMNLABEL2));
                item.setPeso(rs.getFloat(COLUMNLABEL3));
                item.setPrezzo(rs.getFloat(COLUMNLABEL4));
                item.setQuantità(rs.getInt(COLUMNLABEL5));
                item.setId_produttore(producer.getID());

                items.add(item);

            }


        }finally {
        	if (rs != null)
                rs.close();
            if (preparedStatement!= null)
                preparedStatement.close();
            connection.close();
        }

        return items;
    }

}
