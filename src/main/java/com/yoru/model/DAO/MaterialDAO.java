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

public class MaterialDAO implements GenericDBOp<String>{
	
	private DataSource ds;
	
	public MaterialDAO(DataSource ds) {
		this.ds = ds;
	}

	@Override
	public Collection<String> getAll() throws SQLException {
		
		String sql = "SELECT * FROM Struttura";
		List<String> materiali = new ArrayList<>();
		ResultSet rSet = null;
		
		try(Connection connection = ds.getConnection();
				PreparedStatement pStatement = connection.prepareStatement(sql);){
			
			rSet = pStatement.executeQuery();
			
			while(rSet.next()) {
				String materiale = rSet.getString(1);
				materiali.add(materiale);
			}
			
		}finally {
			if(rSet != null)
				rSet.close();
		}
		
		return materiali;
	}

	@Override
	public String getById(int id) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean insert(String entity) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(String entity) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean remove(String entity) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}
	
	

}
