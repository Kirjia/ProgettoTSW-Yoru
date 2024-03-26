package com.yoru.model.DAO;




import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import javax.sql.DataSource;

import com.yoru.DBServices.GenericDBOp;
import com.yoru.model.Entity.User;

public class UserDAO implements GenericDBOp<User> {


    private static int passCode = 256;
    private static final String LOGIN = "bool";
    private DataSource ds;
    
    public UserDAO(DataSource ds) {
    	this.ds = ds;
    }

    @Override
    public Collection<User> getAll(){
        return null;
    }

    @Override
    public User getById(int id) {
        return null;
    }


    public User getById(String email){
        Connection connection =null;
        PreparedStatement ps = null;
        User user = new User();
        ResultSet rs = null;


        try {
            connection = ds.getConnection();
            String sql = "SELECT nome, cognome FROM user WHERE email = ?";
            ps = connection.prepareStatement(sql);
            ps.setString(1, email);



            rs = ps.executeQuery();

            if (rs.next()) {
                user.setNome(rs.getString(User.COLUMNLABEL2));
                user.setCognome(rs.getString(User.COLUMNLABEL3));
                user.setEmail(email);
            }
            else
                System.out.println("Impossibile inserire il record \n");

            connection.commit();

        }catch (SQLException e){
            e.printStackTrace();
        } finally {
            try {
                if(rs != null)
                    rs.close();
                if (ps != null)
                    ps.close();
                connection.close();
            } catch (SQLException s) {
                System.err.println(s.getMessage());
            }
        }
        return user;
    }

    @Override
    public synchronized boolean insert(User user) {
        Connection connection =null;
        PreparedStatement ps = null;
        boolean statement = false;


        try {
            connection = ds.getConnection();

            String sql = "INSERT INTO user (email, password, nome, cognome, telefono)" +
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

        }catch (SQLException e){
            e.printStackTrace();
        } finally {
            try {

                if (ps != null)
                    ps.close();
                connection.close();
            } catch (SQLException s) {
                System.err.println(s.getMessage());
            }
        }
        return statement;
    }

    @Override
    public synchronized boolean update(User user){
        Connection connection =null;
        PreparedStatement ps = null;
        boolean statement = false;


        try {
            connection = ds.getConnection();
            String sql = "UPDATE user SET password = sha2(?, ?), telefono=?";
            ps = connection.prepareStatement(sql);
            ps.setString(1, user.getPassword());
            ps.setInt(2, passCode);
            ps.setString(3, user.getTelefono());


            int result = ps.executeUpdate();

            if (result > 0) {
                System.out.println("Inserimento effettuato con successo\n");
                statement = true;
            }
            else
                System.out.println("Impossibile inserire il record \n");

            connection.commit();

        }catch (SQLException e){
            e.printStackTrace();
        } finally {
            try {

                if (ps != null)
                    ps.close();
                connection.close();
            } catch (SQLException s) {
                System.err.println(s.getMessage());
            }
        }
        return statement;
    }

    @Override
    public synchronized boolean remove(User user) {
        Connection connection =null;
        PreparedStatement ps = null;
        boolean statement = false;


        try {
            connection = ds.getConnection();
            String sql = "DELETE FROM user WHERE email = ?";
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

        }catch (SQLException e){
            e.printStackTrace();
        } finally {
            try {

                if (ps != null)
                    ps.close();
                connection.close();
            } catch (SQLException s) {
                System.err.println(s.getMessage());
            }
        }
        return statement;
    }

    public boolean login(User user){
        boolean login = false;
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            connection = ds.getConnection();
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

            String sql = "SELECT count(*) AS bool FROM user WHERE email = ? AND password = sha2(?, ?)";
            ps = connection.prepareStatement(sql);
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPassword());
            ps.setInt(3, passCode);


            rs = ps.executeQuery();

            if (rs.next()){

                int result = rs.getInt(LOGIN);
                if (result == 1)
                    login = true;
            }

            connection.commit();

        }catch (SQLException e){
            e.printStackTrace();
        } finally {
            try {
                if (rs != null)
                    rs.close();

                if (ps != null)
                    ps.close();
                connection.close();
            } catch (SQLException s) {
                System.err.println(s.getMessage());
            }
        }
        return login;
    }

}
