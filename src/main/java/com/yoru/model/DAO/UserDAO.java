package com.yoru.model.DAO;




import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import javax.sql.DataSource;

import com.mysql.cj.util.Util;
import com.yoru.DBServices.GenericDBOp;
import com.yoru.model.Entity.Role;
import com.yoru.model.Entity.User;

import Util.Argon2Hashing;

public class UserDAO implements GenericDBOp<User> {


    private static int passCode = 256;
    private static final String LOGIN = "bool";
    public static final String TABLE_NAME = "user";
    private DataSource ds;
	private String loginsql;
    
    public UserDAO(DataSource ds) {
    	this.ds = ds;
    	
    }

    @Override
    public Collection<User> getAll(){
        return null;
    }

    @Override
    public User getById(int id) throws SQLException{
    	User user = null;
        String loginsql = "SELECT nome, cognome, role, email, telefono FROM "+ UserDAO.TABLE_NAME + " WHERE id = ?";
        ResultSet resultSet = null;
    	
    	try(Connection connection = ds.getConnection();
    			PreparedStatement pStatement = connection.prepareStatement(loginsql);
    			){
    		
    		pStatement.setInt(1, id);
    		
    		resultSet = pStatement.executeQuery();
    		
    		if (resultSet.next()) {
				user = new User();
				
				user.setId(id);
				user.setNome(resultSet.getString(1));
				user.setCognome(resultSet.getString(2));
				user.setRole(resultSet.getString(3));
				user.setEmail(resultSet.getString(4));
				user.setTelefono(resultSet.getString(5));
			}
    		
    		
    		
    	}finally {
			if (resultSet != null) {
				resultSet.close();
			}
		}
    	return user;
    }


    public User getById(String email)throws SQLException{
        Connection connection =null;
        PreparedStatement ps = null;
        User user = new User();
        ResultSet rs = null;


        try {
            connection = ds.getConnection();
            connection.setAutoCommit(false);
            String sql = "SELECT nome, cognome, role, telefono FROM "+ UserDAO.TABLE_NAME + " WHERE email = ?";
            ps = connection.prepareStatement(sql);
            ps.setString(1, email);



            rs = ps.executeQuery();

            if (rs.next()) {
                user.setNome(rs.getString(User.COLUMNLABEL2));
                user.setCognome(rs.getString(User.COLUMNLABEL3));
                user.setRole(rs.getString(3));
                user.setEmail(email);
                user.setTelefono(rs.getString(4));
                
            }
            else
                System.out.println("Impossibile inserire il record \n");

            connection.commit();

        } finally {

            if (rs != null)
                rs.close();

            if (ps != null)
                ps.close();
            connection.close();
             
        }
        return user;
    }

    @Override
    public synchronized boolean insert(User user) throws SQLException{
        Connection connection =null;
        PreparedStatement ps = null;
        boolean statement = false;


        try {
            connection = ds.getConnection();
            connection.setAutoCommit(false);

            String sql = "INSERT INTO "  + UserDAO.TABLE_NAME + " (email, password, nome, cognome, telefono)" +
                    "VALUE (?,SHA2(?, ?),?,?,?)";
            ps = connection.prepareStatement(sql);
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPassword());
            ps.setInt(3, passCode);
            ps.setString(4, user.getNome());
            ps.setString(5, user.getCognome());
            ps.setString(6, user.getTelefono());



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
    public synchronized boolean update(User user)throws SQLException{
        Connection connection =null;
        PreparedStatement ps = null;
        boolean statement = false;


        try {
            connection = ds.getConnection();
            connection.setAutoCommit(false);
            String sql = "UPDATE "+ UserDAO.TABLE_NAME + " SET password = sha2(?, ?), nome=?, cognome=?, telefono=? where id= ?";
            ps = connection.prepareStatement(sql);
            ps.setString(1, user.getPassword());
            ps.setInt(2, passCode);
            ps.setString(3, user.getNome());
            ps.setString(4, user.getCognome());
            ps.setString(5, user.getTelefono());
            ps.setInt(6, user.getId());
            


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
    public synchronized boolean remove(User user)throws SQLException {
        Connection connection =null;
        PreparedStatement ps = null;
        boolean statement = false;


        try {
            connection = ds.getConnection();
            connection.setAutoCommit(false);
            String sql = "DELETE FROM "+ UserDAO.TABLE_NAME + " WHERE email = ?";
            ps = connection.prepareStatement(sql);
            ps.setString(1, user.getEmail());


            int result = ps.executeUpdate();

            if (result > 0) {
                System.out.println("Cancellazione effettuata con successo\n");
                statement = true;
            }
            else
                System.out.println("Impossibile Cancellare il record \n");

            connection.commit();

        } finally {

            if (ps != null)
                ps.close();
            connection.close();
             
        }
        return statement;
    }

    public User login(String email, String  password)throws SQLException{
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        User user = null;

        try {
            connection = ds.getConnection();
            connection.setAutoCommit(false);

            String sql = "SELECT password FROM "+ UserDAO.TABLE_NAME + " WHERE email = ?";
            
            ps = connection.prepareStatement(sql);
            ps.setString(1, email);
            

            rs = ps.executeQuery();
            
            if(rs.next()) {
                String pass = rs.getString(1);
                if (Argon2Hashing.checkPass( rs.getString(1), password)) {
                	String loginsql = "SELECT id, email, nome, cognome, telefono, role FROM "+ UserDAO.TABLE_NAME + " WHERE email = ?";
                    ps = connection.prepareStatement(loginsql);
                    ps.setString(1, email);
                    rs = ps.executeQuery();
                    
                    if (rs.next()) {
						
                    	user = new User();
                    	user.setId(rs.getInt(1));
                    	user.setNome(rs.getString(User.COLUMNLABEL2));
                    	user.setEmail(rs.getString(User.COLUMNLABEL1));
                    	user.setCognome(rs.getString(User.COLUMNLABEL3));
                    	user.setTelefono(rs.getString(5));
                    	user.setRole(rs.getString(6));
					}
                    
                    
                }
                	 
            }

            connection.commit();

        
        } finally {

            if (rs != null)
                rs.close();

            if (ps != null)
                ps.close();
            connection.close();
             
        }
        return user;
    }

}
