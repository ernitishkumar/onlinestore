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

import com.wingify.beans.Product;
import com.wingify.utility.GlobalResources;

/**
 * @author NITISH
 *
 */
public class ProductsDAO {
	
	private Connection connection = GlobalResources.getConnection();
	
	/**
	 * @param product
	 * @return
	 * @throws SQLException
	 */
	public Product insert(Product product) throws SQLException{
		Product insertedProduct = null;
		PreparedStatement ps = connection.prepareStatement("INSERT INTO `products` (`name`, `description`, `quantity`,`availability`, `price`, `categories`,`attributes`,`created_at`,`last_updated_at`) VALUES(?,?,?,?,?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, product.getName());
        ps.setString(2, product.getDescription());
        ps.setInt(3,product.getQuantity());
        ps.setString(4,product.getAvailability());
        ps.setFloat(5, product.getPrice());
        ps.setString(6, product.getCategories());
        ps.setString(7,product.getAttributes());
        ps.setString(8, product.getCreatedAt());
        ps.setString(9, product.getLastUpdatedAt());
        ps.executeUpdate();
        ResultSet keys = ps.getGeneratedKeys();
        keys.next();  
		int lastInsertedId = keys.getInt(1);
		ps.close();
		insertedProduct = getById(lastInsertedId);
		return insertedProduct;
	}
	
	/**
	 * @param productId
	 * @return
	 * @throws SQLException
	 */
	public Product getById(int productId) throws SQLException{
		Product product = null;
		PreparedStatement ps = connection.prepareStatement("select * from products where product_id = ?");
		ps.setInt(1, productId);
		ResultSet rs = ps.executeQuery();
		while(rs.next()){
			product = new Product();
			product.setProductId(rs.getInt(1));
			product.setName(rs.getString(2));
			product.setDescription(rs.getString(3));
			product.setQuantity(rs.getInt(4));
			product.setAvailability(rs.getString(5));
			product.setPrice(rs.getFloat(6));
			product.setCategories(rs.getString(7));
			product.setAttributes(rs.getString(8));
			product.setCreatedAt(rs.getString(9));
			product.setLastUpdatedAt(rs.getString(10));
		}
		rs.close();
		ps.close();
		return product;
	}
	
	/**
	 * @param name
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<Product> getByName(String name) throws SQLException{
		ArrayList<Product> products= new ArrayList<Product>();
		PreparedStatement ps = connection.prepareStatement("select * from products where name = ?");
		ps.setString(1, name);
		ResultSet rs = ps.executeQuery();
		resultSetMapper(rs, products);
		rs.close();
		ps.close();
		return products;
	}
	
	/**
	 * @param description
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<Product> getByDescription(String description) throws SQLException{
		ArrayList<Product> products= new ArrayList<Product>();
		PreparedStatement ps = connection.prepareStatement("select * from products where description = ?");
		ps.setString(1, description);
		ResultSet rs = ps.executeQuery();
		resultSetMapper(rs, products);
		rs.close();
		ps.close();
		return products;
	}
	
	/**
	 * @param category
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<Product> getByCategory(String category) throws SQLException{
		ArrayList<Product> products= new ArrayList<Product>();
		PreparedStatement ps = connection.prepareStatement("select * from products where categories like '%"+category+"%'");
		ResultSet rs = ps.executeQuery();
		resultSetMapper(rs, products);
		rs.close();
		ps.close();
		return products;
	}
	
	/**
	 * @param attribute
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<Product> getByAttribute(String attribute) throws SQLException{
		ArrayList<Product> products= new ArrayList<Product>();
		PreparedStatement ps = connection.prepareStatement("select * from products where attributes like '%"+attribute+"%'");
		ResultSet rs = ps.executeQuery();
		resultSetMapper(rs, products);
		rs.close();
		ps.close();
		return products;
	}
	
	
	/**
	 * @param attribute
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<Product> getByAvailability(String availability) throws SQLException{
		ArrayList<Product> products= new ArrayList<Product>();
		PreparedStatement ps = connection.prepareStatement("select * from products where availability = ?");
		ps.setString(1, availability);
		ResultSet rs = ps.executeQuery();
		resultSetMapper(rs, products);
		rs.close();
		ps.close();
		return products;
	}
	
	/**
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<Product> getAll()throws SQLException{
		ArrayList<Product> products= new ArrayList<Product>();
		PreparedStatement ps = connection.prepareStatement("select * from products");
		ResultSet rs = ps.executeQuery();
		resultSetMapper(rs, products);
		rs.close();
		ps.close();
		return products;
	}
	
	/**
	 * @param productId
	 * @return
	 * @throws SQLException
	 */
	public Product delete(int productId) throws SQLException{
		Product productToDelete = getById(productId);
		if(productToDelete != null){
			PreparedStatement ps = connection.prepareStatement("delete from products where product_id = ?");
			ps.setInt(1, productId);
			ps.executeUpdate();	
			ps.close();
		}
		return productToDelete;
	}
	
	/**
	 * @param product
	 * @return
	 * @throws SQLException
	 */
	public Product delete(Product product)throws SQLException{
		Product productToDelete = null;
		if(product != null){
			productToDelete = delete(product.getProductId());
		}
		return productToDelete;
	}
	
	/**
	 * @param product
	 * @return
	 * @throws SQLException
	 */
	public Product update(Product product)throws SQLException{
		if(product != null){
			PreparedStatement ps = connection.prepareStatement("update products set name = ?,description = ?, quantity=?,availability=?,price=?,categories=?,attributes=?,created_at=?,last_updated_at=? where product_id = ?");
			ps.setString(1, product.getName());
	        ps.setString(2, product.getDescription());
	        ps.setInt(3,product.getQuantity());
	        ps.setString(4,product.getAvailability());
	        ps.setFloat(5, product.getPrice());
	        ps.setString(6, product.getCategories());
	        ps.setString(7,product.getAttributes());
	        ps.setString(8, product.getCreatedAt());
	        ps.setString(9, product.getLastUpdatedAt());
	        ps.setInt(10, product.getProductId());
			ps.executeUpdate();
			ps.close();
		}
		return product;
	}
	
	public void resultSetMapper(ResultSet rs,ArrayList<Product> products)throws SQLException{
		while(rs.next()){
			Product product = new Product();
			product.setProductId(rs.getInt(1));
			product.setName(rs.getString(2));
			product.setDescription(rs.getString(3));
			product.setQuantity(rs.getInt(4));
			product.setAvailability(rs.getString(5));
			product.setPrice(rs.getFloat(6));
			product.setCategories(rs.getString(7));
			product.setAttributes(rs.getString(8));
			product.setCreatedAt(rs.getString(9));
			product.setLastUpdatedAt(rs.getString(10));
			products.add(product);
		}
	}
}
