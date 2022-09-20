 package com.amal.amalproject.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static Connection INSTANCE = null;

    private DBConnection() {}

    public static Connection getConnection() {

        if (INSTANCE == null) {
            try {
                INSTANCE = DriverManager.getConnection("jdbc:mysql://localhost:3306/amal?useSSL=false","root","");
            } catch (SQLException exception) {
                System.out.println(exception.getMessage());
            }
        }

        return INSTANCE;
    }
    public static void closeConnection()
    {       
        if(INSTANCE != null)
        	INSTANCE = null;
    }

}

/**
 *      JDBC (Specification)
 *
 *          MySQL : Implementation JDBC for MYSQL DATABASE
 *          POSTGRES SQL : Implementation JDBC for POSTGRES SQL
 *          ORACLE : Implementation JDBC for ORACLE
 *          Microsoft Sql Server : Implementation JDBC for MS SQL Database
 *
 */
