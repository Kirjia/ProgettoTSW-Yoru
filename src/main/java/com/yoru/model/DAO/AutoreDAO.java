package com.yoru.model.DAO;




import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sql.DataSource;

import com.yoru.DBServices.DBConnectionPool;
import com.yoru.DBServices.GenericDBOp;
import com.yoru.model.Entity.Autore;
import com.yoru.model.Entity.Libro;

import static com.yoru.model.Entity.Autore.*;

public class AutoreDAO implements GenericDBOp<Autore> {

	
	private static final Logger LOGGER = Logger.getLogger( AutoreDAO.class.getName() );
	private DataSource ds;
	
	public AutoreDAO(DataSource ds) {
		this.ds = ds;
	}

    @Override
    public Collection<Autore> getAll()throws SQLException{
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Autore> autori = new ArrayList<>();


        try{
            connection = ds.getConnection();
            String sql = "SELECT autori.* FROM autori";

            ps = connection.prepareStatement(sql);

            rs = ps.executeQuery();

            while (rs.next()){
                Autore autore = new Autore();
                autore.setID(rs.getInt(COLMUNLABEL1));
                autore.setNome(rs.getString(COLMUNLABEL2));
                autore.setCognome(rs.getString(COLMUNLABEL3));
                autore.setNomeArte(rs.getString(COLMUNLABEL4));
                autori.add(autore);
            }


        } finally {

            if (rs != null)
                rs.close();

            if (ps != null)
                ps.close();
            connection.close();
             
        }
        return autori;
    }

    @Override
    public Autore getById(int id) throws SQLException{
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Autore autore = new Autore();


        try{
            connection = ds.getConnection();
            String sql = "SELECT autori.* FROM autori WHERE ID_autore = ?";

            ps = connection.prepareStatement(sql);
            ps.setInt(1, id);

            rs = ps.executeQuery();

            if (rs.next()){
                autore.setID(id);
                autore.setNome(rs.getString(COLMUNLABEL2));
                autore.setCognome(rs.getString(COLMUNLABEL3));
                autore.setNomeArte(rs.getString(COLMUNLABEL4));
            }


        } finally {

            if (rs != null)
                rs.close();

            if (ps != null)
                ps.close();
            connection.close();
             
        }
        return autore;
    }

    @Override
    public synchronized boolean insert(Autore autore)throws SQLException{
        Connection connection =null;
        PreparedStatement ps = null;
        boolean statement = false;


        try {
            connection = ds.getConnection();
            String sql = "INSERT INTO autori (nome, cognome, nArte)" +
                    "VALUE (?,?,?)";
            ps = connection.prepareStatement(sql);
            ps.setString(1, autore.getNome());
            ps.setString(2, autore.getCognome());
            ps.setString(3, autore.getNomeArte());

            int result = ps.executeUpdate();

            if (result > 0) {
                System.out.println("Inserimento effettuato con successo\n");
                statement = true;
            }
            else
                System.out.println("Impossibile inserire il record \n");

            connection.commit();

        } finally {

            if (ps != null)
                ps.close();
            connection.close();
             
        }
        return statement;
    }

    @Override
    public synchronized boolean update(Autore autore)throws SQLException{
        Connection connection =null;
        PreparedStatement ps = null;
        boolean statement = false;


        try {
            connection = ds.getConnection();
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            String sql = "UPDATE autori\n" +
                    "SET nome = ?, cognome = ?, nArte = ?\n" +
                    "WHERE ID_autore = ?;";
            ps = connection.prepareStatement(sql);
            ps.setString(1, autore.getNome());
            ps.setString(2, autore.getCognome());
            ps.setString(3, autore.getNomeArte());
            ps.setInt(4, autore.getID());

            int result = ps.executeUpdate();

            if (result > 0) {
                System.out.println("Aggiornamento effettuato con successo\n");
                statement = true;
            }
            else
                System.out.println("Impossibile Aggiornare il record \n");

            connection.commit();

        } finally {

            if (ps != null)
                ps.close();
            connection.close();
             
        }
        return statement;
    }

    @Override
    public synchronized boolean remove(Autore autore)throws SQLException{
        Connection connection =null;
        PreparedStatement ps = null;
        boolean statement = false;


        try {
            connection = ds.getConnection();
            String sql = "DELETE FROM autori WHERE ID_autore = ?";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, autore.getID());

            int result = ps.executeUpdate();

            if (result > 0) {
                System.out.println("Rimozione effettuata con successo\n");
                statement = true;
            }
            else
                System.out.println("Impossibile rimuovere il record \n");

            connection.commit();

        } finally {

            if (ps != null)
                ps.close();
            connection.close();
             
        }
        return statement;
    }

    public Collection<Libro> getAuthorBook(Autore autore) throws SQLException{
        Connection connection =null;
        PreparedStatement preparedStatement=null;
        ResultSet rs=null;

        Collection<Libro> libri = new ArrayList<>();

        try {
            connection = ds.getConnection();
            String sql = "SELECT libriview.* FROM libriview INNER JOIN realizza on realizza.SKU = libriview.SKU WHERE ID_autore = ?";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, autore.getID());

            rs = preparedStatement.executeQuery();

            while (rs.next()){
                Libro libro = new Libro();
                libro.setSKU(rs.getInt(Libro.COLUMNLABEL1));
                libro.setNome(rs.getString(Libro.COLUMNLABEL2));
                libro.setPeso(rs.getFloat(Libro.COLUMNLABEL3));
                libro.setPrezzo(rs.getFloat(Libro.COLUMNLABEL4));
                libro.setQuantità(rs.getInt(Libro.COLUMNLABEL5));
                libro.setId_produttore(rs.getInt("id_Producer"));
                libro.setISBN(rs.getString(Libro.COLUMNLABEL7));
                libro.setNumeroPagine(rs.getInt(Libro.COLUMNLABEL8));
                libro.setLingua(rs.getString(Libro.COLUMNLABEL9));

                libri.add(libro);

            }


        } finally {

            if (rs != null)
                rs.close();

            if (preparedStatement != null)
                preparedStatement.close();
            connection.close();
             
        }

        return libri;
    }




}
