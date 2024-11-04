package com.yoru.model.DAO;

import java.io.CharArrayReader;
import java.io.Reader;
import java.lang.System.Logger.Level;
import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.logging.Logger;

import javax.sql.DataSource;

import com.yoru.DBServices.GenericDBOp;
import com.yoru.model.Entity.UserAuthToken;

import static com.yoru.model.Entity.UserAuthToken.*;

public class AuthDAO implements GenericDBOp<UserAuthToken>{
	
	private static final Logger LOGGER = Logger.getLogger(AuthDAO.class.getName());

	private DataSource ds;
	private static final String TABLE_NAME = "cookieAuth";
	
	public AuthDAO(DataSource ds) {
		this.ds = ds;
	}
	
	
	public UserAuthToken findBySelector(String selector) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        UserAuthToken auth = null;
        
        try {
        	
        	String sql = "SELECT * FROM " + TABLE_NAME + " WHERE selector = ?";
        	
			connection = ds.getConnection();
			ps = connection.prepareStatement(sql);

			ps.setNString(1, selector);
			rs = ps.executeQuery();
			if (rs.next()) {
				auth = new UserAuthToken();
				auth.setId(rs.getInt(1));
				auth.setUserID(rs.getInt(UserAuthToken.COLUMN_LABEL1));
				auth.setSelector(rs.getString(UserAuthToken.COLUMN_LABEL2));
				auth.setValidator(rs.getString(UserAuthToken.COLUMN_LABEL3));
				System.out.println("Update AuthDAO done");
			}
			
        	
        	
		} finally{
			if (ps != null) {
				ps.close();
			}
			if (rs != null) {
				rs.close();
			}
			connection.close();
		}
       
        return auth;
    }

	@Override
	public Collection<UserAuthToken> getAll() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserAuthToken getById(int id) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int insert(UserAuthToken entity) throws SQLException {
		Connection connection = null;
        PreparedStatement ps = null;
       int result = -1;
        
        try {
        	
        	String sql = "INSERT INTO " + TABLE_NAME + "(" + COLUMN_LABEL1 + ", " + COLUMN_LABEL2 + ", " + COLUMN_LABEL3 + ") VALUES (?, ?, ?)" ;
        	
			connection = ds.getConnection();
			ps = connection.prepareStatement(sql);
			ps.setInt(1, entity.getUserID());
			ps.setString(2, entity.getSelector());
			ps.setString(3, entity.getValidator());
			if(ps.executeUpdate() > 0)
				result = 1;
			
			
        	
        	
		} finally{
			if (ps != null) {
				ps.close();
			}
			
			connection.close();
		}
         
        return result;
	}

	@Override
	public boolean update(UserAuthToken entity) throws SQLException {
		Connection connection = null;
        PreparedStatement ps = null;
        boolean result = false;
        
        try {
        	
        	String sql = "UPDATE  " + TABLE_NAME + " SET selector = ?, validator = ? WHERE id = ?" ;
        	
			connection = ds.getConnection();
			ps = connection.prepareStatement(sql);
			ps.setString(1, entity.getSelector());
			ps.setString(2, entity.getValidator());
			ps.setInt(3, entity.getId());
			if(ps.executeUpdate() > 0)
				result = true;
			
			
        	
        	
		} finally{
			if (ps != null) {
				ps.close();
			}
			
			connection.close();
		}
         
        return result;
	}

	@Override
	public boolean remove(UserAuthToken entity) throws SQLException {
		Connection connection = null;
        PreparedStatement ps = null;
        boolean result = false;
        
        try {
        	
        	String sql = "DELETE FROM  " + TABLE_NAME + " WHERE id = ?" ;
        	
			connection = ds.getConnection();
			ps = connection.prepareStatement(sql);
			ps.setInt(1, entity.getId());
			if(ps.executeUpdate() > 0)
				result = true;
			
        	
		} finally{
			if (ps != null) {
				ps.close();
			}
			
			connection.close();
		}
         
        return result;
	}
	
	
	
	
}
