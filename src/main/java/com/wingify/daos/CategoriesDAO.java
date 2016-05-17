/**
 * 
 */
package com.wingify.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.wingify.beans.Category;
import com.wingify.utility.GlobalResources;

/**
 * @author NITISH
 *
 */
public class CategoriesDAO {

	private Connection connection = GlobalResources.getConnection();
	
	/**
	 * @param category
	 * @return
	 * @throws SQLException
	 */
	public Category insert(Category category) throws SQLException{
		Category insertedCategory = null;
		PreparedStatement ps = connection.prepareStatement("INSERT INTO `categories` (`name`, `description`, `parent_id`) VALUES(?,?,?)",Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, category.getName());
        ps.setString(2, category.getDescription());
        ps.setInt(3, category.getParentId());
        ps.executeUpdate();
        ResultSet keys = ps.getGeneratedKeys();
        keys.next();  
		int lastInsertedId = keys.getInt(1);
		ps.close();
		insertedCategory = getById(lastInsertedId);
		return insertedCategory;
	}
	
	/**
	 * @param categoryId
	 * @return
	 * @throws SQLException
	 */
	public Category getById(int categoryId) throws SQLException{
		Category category = null;
		PreparedStatement ps = connection.prepareStatement("select * from categories where category_id = ?");
		ps.setInt(1, categoryId);
		ResultSet rs = ps.executeQuery();
		while(rs.next()){
			category = new Category();
			category.setCategoryId(rs.getInt(1));
			category.setName(rs.getString(2));
			category.setDescription(rs.getString(3));
			category.setParentId(rs.getInt(4));
		}
		rs.close();
		ps.close();
		return category;
	}
	
	/**
	 * @param parentId
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<Category> getByParentId(int parentId) throws SQLException{
		ArrayList<Category> categories= new ArrayList<Category>();
		PreparedStatement ps = connection.prepareStatement("select * from categories where parent_id = ?");
		ps.setInt(1, parentId);
		ResultSet rs = ps.executeQuery();
		while(rs.next()){
			Category category = new Category();
			category.setCategoryId(rs.getInt(1));
			category.setName(rs.getString(2));
			category.setDescription(rs.getString(3));
			category.setParentId(rs.getInt(4));
			categories.add(category);
		}
		rs.close();
		ps.close();
		return categories;
	}
	
	/**
	 * @param name
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<Category> getByName(String name) throws SQLException{
		ArrayList<Category> categories= new ArrayList<Category>();
		PreparedStatement ps = connection.prepareStatement("select * from categories where name = ?");
		ps.setString(1, name);
		ResultSet rs = ps.executeQuery();
		while(rs.next()){
			Category category = new Category();
			category.setCategoryId(rs.getInt(1));
			category.setName(rs.getString(2));
			category.setDescription(rs.getString(3));
			category.setParentId(rs.getInt(4));
			categories.add(category);
		}
		rs.close();
		ps.close();
		return categories;
	}
	
	/**
	 * @param description
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<Category> getByDescription(String description) throws SQLException{
		ArrayList<Category> categories= new ArrayList<Category>();
		PreparedStatement ps = connection.prepareStatement("select * from categories where description = ?");
		ps.setString(1, description);
		ResultSet rs = ps.executeQuery();
		while(rs.next()){
			Category category = new Category();
			category.setCategoryId(rs.getInt(1));
			category.setName(rs.getString(2));
			category.setDescription(rs.getString(3));
			category.setParentId(rs.getInt(4));
			categories.add(category);
		}
		rs.close();
		ps.close();
		return categories;
	}
	
	
	/**
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<Category> getAll()throws SQLException{
		ArrayList<Category> categories = new ArrayList<Category>();
		PreparedStatement ps = connection.prepareStatement("select * from categories");
		ResultSet rs = ps.executeQuery();
		while(rs.next()){
			Category category = new Category();
			category.setCategoryId(rs.getInt(1));
			category.setName(rs.getString(2));
			category.setDescription(rs.getString(3));
			category.setParentId(rs.getInt(4));
			categories.add(category);
		}
		return categories;
	}
	
	/**
	 * @param categoryId
	 * @return
	 * @throws SQLException
	 */
	public Category delete(int categoryId) throws SQLException{
		Category categoryToDelete = getById(categoryId);
		if(categoryToDelete != null){
			PreparedStatement ps = connection.prepareStatement("delete from categories where category_id = ?");
			ps.setInt(1, categoryId);
			ps.executeUpdate();	
			ps.close();
		}
		return categoryToDelete;
	}
	
	/**
	 * @param category
	 * @return
	 * @throws SQLException
	 */
	public Category delete(Category category)throws SQLException{
		Category categoryToDelete = null;
		if(category != null){
			categoryToDelete = delete(category.getCategoryId());
		}
		return categoryToDelete;
	}
	
	/**
	 * @param category
	 * @return
	 * @throws SQLException
	 */
	public Category update(Category category)throws SQLException{
		if(category != null){
			PreparedStatement ps = connection.prepareStatement("update categories set name = ?,description = ?, parent_id=? where category_id = ?");
			ps.setString(1, category.getName());
			ps.setString(2, category.getDescription());
			ps.setInt(3, category.getParentId());
			ps.setInt(4, category.getCategoryId());
			ps.executeUpdate();
			ps.close();
		}
		return category;
	}
}
