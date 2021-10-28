package com.project1.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import com.project1.log.LogHelper;

public class DBConnection {
	
	ClassLoader classLoader = getClass().getClassLoader();
	InputStream inputSteam;
	Properties properties = new Properties();
	
	static private LogHelper log = new LogHelper();
	
	public DBConnection() {
		inputSteam = classLoader.getResourceAsStream("connection.properties");
		try {
			properties.load(inputSteam);
		} catch (IOException e) {
			e.printStackTrace();
			log.ExceptionLog(e);
		}
	}
	
	public Connection getDBConnection() throws SQLException {
		final String URL = properties.getProperty("url"); 
		final String USERNAME = properties.getProperty("username");
		final String PASSWORD = properties.getProperty("password");
		try {
		    Class.forName("org.postgresql.Driver");
		} catch(ClassNotFoundException e) {
		    e.printStackTrace();
		    log.ExceptionLog(e);
		}
		
		return DriverManager.getConnection(URL, USERNAME, PASSWORD);
	}
}
