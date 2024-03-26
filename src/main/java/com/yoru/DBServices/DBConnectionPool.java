package com.yoru.DBServices;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBConnectionPool {

    private static List<Connection> freeDBConnections;
    private static Logger LOGGER = Logger.getLogger(DBConnectionPool.class.getName());

    static{
        freeDBConnections = new LinkedList<Connection>();
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch (ClassNotFoundException e){
            LOGGER.log(Level.SEVERE, "SB driver not found", e);
        }
    }

    private static Connection createDBConnection() throws SQLException{
        Connection newConnection = null;
        String ip = "localhost";
        String port = "3306";
        String db = "shiru";
        String username = "shiruser";
        String password = "shiruser";
        String params = "?serverTimezone=Europe/Rome&useLegacyDatetimeCode=false&useUnicode=true&useJDBCCompliantTimezoneShift=true&zeroDateTimeBehavior=CONVERT_TO_NULL&autoReconnect=true";
        String url = "jdbc:mysql://localhost:3306/shiru"+ params;


        newConnection = DriverManager.getConnection(url, username, password);

        newConnection.setAutoCommit(false);

        System.out.println("DBServices.DBConnectionPool Connessione OK \n");

        return newConnection;
    }

    public  static synchronized Connection getConnection() throws SQLException{
        Connection connection;

        if(!freeDBConnections.isEmpty()){
            connection = (Connection) freeDBConnections.get(0);
            DBConnectionPool.freeDBConnections.remove(0);

            try {
                if(connection.isClosed())
                    connection = DBConnectionPool.getConnection();
            }catch (SQLException e){
                connection = DBConnectionPool.getConnection();
            }

        }else{
            connection = DBConnectionPool.createDBConnection();
        }
        return connection;

    }

    public static synchronized void releaseConnection(Connection connection){
        DBConnectionPool.freeDBConnections.add(connection);
    }

    public static synchronized void releaseAllConnection() throws SQLException {
        while(freeDBConnections.size() > 0) {
            Connection connection = (Connection) freeDBConnections.remove(0);
            if(connection != null) {
                System.out.println("Release connection "+connection.toString());
                connection.close();
            }
        }
    }
}
