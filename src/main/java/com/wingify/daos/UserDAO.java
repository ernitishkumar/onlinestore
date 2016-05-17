/**
 * 
 */
package com.wingify.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.wingify.beans.User;
import com.wingify.utility.GlobalResources;

/**
 * @author NITISH
 *
 */
public class UserDAO {

	private Connection connection = GlobalResources.getConnection();
	
	/**
	 * @param user
	 * @return
	 * @throws SQLException
	 */
	public User insert(User user) throws SQLException{
		User insertedUser = null;
		PreparedStatement ps = connection.prepareStatement("INSERT INTO users (`username`, `password`, `role`) VALUES(?,?,?)",Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, user.getUsername());
        ps.setString(2, user.getPassword());
        ps.setString(3, user.getRole());
        ps.executeUpdate();
        ResultSet keys = ps.getGeneratedKeys();
        keys.next();  
		int lastInsertedId = keys.getInt(1);
		ps.close();
		insertedUser = getById(lastInsertedId);
		return insertedUser;
	}
	
	/**
	 * @param userId
	 * @return
	 * @throws SQLException
	 */
	public User getById(int userId) throws SQLException{
		User user = null;
		PreparedStatement ps = connection.prepareStatement("select * from users where user_id = ?");
		ps.setInt(1, userId);
		ResultSet rs = ps.executeQuery();
		while(rs.next()){
			user = new User(rs.getInt(1),rs.getString(2).trim(),rs.getString(3).trim(),rs.getString(4).trim());
		}
		rs.close();
		ps.close();
		return user;
	}
	
	/**
	 * @param username
	 * @return
	 * @throws SQLException
	 */
	public User getByUsername(String username) throws SQLException{
		User user = null;
		PreparedStatement ps = connection.prepareStatement("select * from users where username = ?");
		ps.setString(1, username);
		ResultSet rs = ps.executeQuery();
		while(rs.next()){
			user = new User(rs.getInt(1),rs.getString(2).trim(),rs.getString(3).trim(),rs.getString(4).trim());
		}
		rs.close();
		ps.close();
		return user;
	}
	
	/**
	 * @param userId
	 * @return
	 * @throws SQLException
	 */
	public User delete(int userId)throws SQLException{
		User userToDelete = getById(userId);
		if(userToDelete!=null){
			PreparedStatement ps = connection.prepareStatement("delete from users where user_id=?");
			ps.setInt(1, userId);
			ps.close();
		}
		return userToDelete;
	}
	
	/**
	 * @param userToDelete
	 * @return
	 * @throws SQLException
	 */
	public User delete(User userToDelete)throws SQLException{
		if(userToDelete != null){
			delete(userToDelete.getUserId());
		}
		return userToDelete;
	}
}
