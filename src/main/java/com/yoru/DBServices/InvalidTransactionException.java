package com.yoru.DBServices;

import java.sql.SQLException;

public class InvalidTransactionException extends SQLException{
	
	public InvalidTransactionException(String err) {
		super(err);
	}

}
