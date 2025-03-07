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
import com.yoru.model.Entity.Indirizzo;
import com.yoru.model.Entity.User;

public class AddressDAO implements GenericDBOp<Indirizzo>{
	
	private DataSource ds;
	
	public AddressDAO(DataSource ds) {
		this.ds = ds;
		
	}

	@Override
	public Collection<Indirizzo> getAll() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Indirizzo getById(int id) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Collection<Indirizzo> getIndirizziByEmail(String email) throws SQLException{

		List<Indirizzo> indirizziCollection = new ArrayList<>();
		String sql = "SELECT * FROM user_address WHERE email = ?";
		ResultSet rSet = null;
		
		try(Connection connection = ds.getConnection();
				PreparedStatement pStatement = connection.prepareStatement(sql);){
			
			pStatement.setString(1, email);
			rSet = pStatement.executeQuery();
			while(rSet.next()) {
				Indirizzo indirizzo = new Indirizzo(
						rSet.getInt(1),
						rSet.getString(2),
						rSet.getString(3),
						rSet.getString(4),
						rSet.getString(5),
						rSet.getString(6));
				indirizziCollection.add(indirizzo);
			}
			
		}finally {
			if(rSet != null)
				rSet.close();
		}
		
		return indirizziCollection;
	}

	@Override
	public int insert(Indirizzo entity) throws SQLException {
		
		String sql = "INSERT INTO user_address(email, via, provincia, città, CAP) value (?, ?, ?, ?, ?)";
		int res = -1;
		
		try(Connection conn = ds.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql);){
			
			ps.setString(1, entity.getEmail());
			ps.setString(2, entity.getVia());
			ps.setString(3, entity.getProvincia());
			ps.setString(4, entity.getCity());
			ps.setString(5, entity.getCAP());
			
			res = ps.executeUpdate();
		}
		
		return res;
		
	}

	@Override
	public boolean update(Indirizzo entity) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean remove(Indirizzo entity) throws SQLException {
		
		
		String sql = "DELETE FROM user_address where id = ? AND email = ?";
		
		try(Connection conn = ds.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql);){
			
			ps.setInt(1, entity.getId());
			ps.setString(2, entity.getEmail());
			
			
			if (ps.executeUpdate() > 0)
				return true;
		}
		
		
		return false;
	}

}
