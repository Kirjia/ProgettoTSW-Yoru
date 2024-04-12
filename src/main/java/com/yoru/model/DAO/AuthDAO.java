package com.yoru.model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import javax.sql.DataSource;

import com.yoru.DBServices.GenericDBOp;
import com.yoru.model.Entity.UserAuthToken;

public class AuthDAO implements GenericDBOp<UserAuthToken>{

	private DataSource ds;
	private static final String TABLE_NAME = "cookieAuth";
	
	public AuthDAO(DataSource ds) {
		ds = ds;
	}
	
	
	public UserAuthToken findBySelector(String selector) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        UserAuthToken auth = new UserAuthToken();
        
        try {
        	
        	String sql = "SELECT * FROM " + TABLE_NAME + "WHERE validator = ?";
        	
			connection = ds.getConnection();
			ps = connection.prepareStatement(sql);
			ps.setString(1, selector);
			rs = ps.executeQuery();
			if (rs.next()) {
				auth.setUserID(rs.getInt(UserAuthToken.COLUMN_LABEL1));
				auth.setSelector(rs.getString(UserAuthToken.COLUMN_LABEL2));
				auth.setValidator(rs.getString(UserAuthToken.COLUMN_LABEL3));
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
	public boolean insert(UserAuthToken entity) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(UserAuthToken entity) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean remove(UserAuthToken entity) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	
	
}
