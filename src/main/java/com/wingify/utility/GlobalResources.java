package com.wingify.utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.google.gson.Gson;

public class GlobalResources {

	private static Connection connection = null;
	private static Gson gson=new Gson();
	
	/*
	 * getConnection() method to provide single reference of connection object for
	 * complete application.
	 */
	public static Connection getConnection()
	{
		try {
			if(connection==null){
				Class.forName("com.mysql.jdbc.Driver");
				connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/store","wingify","wingify");	
			}
		} catch (SQLException exception) {
			System.out.println("Not able to connect to the Database "+exception.getMessage());
			exception.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.out.println("Class not found "+e.getMessage());
			e.printStackTrace();
		}
		return connection;
	}
	
	public static Gson getGson(){
		return gson;
	}
}
