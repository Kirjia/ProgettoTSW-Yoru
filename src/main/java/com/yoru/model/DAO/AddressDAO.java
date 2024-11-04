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
		// TODO Auto-generated method stub
		return -1;
	}

	@Override
	public boolean update(Indirizzo entity) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean remove(Indirizzo entity) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

}
