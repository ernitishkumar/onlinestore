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

import com.wingify.beans.Attribute;
import com.wingify.utility.GlobalResources;

/**
 * @author NITISH
 *
 */
public class AttributesDAO {

	private Connection connection = GlobalResources.getConnection();
	
	/**
	 * @param attribute
	 * @return
	 * @throws SQLException
	 */
	public Attribute insert(Attribute attribute) throws SQLException{
		Attribute insertedAttribute = null;
		PreparedStatement ps = connection.prepareStatement("INSERT INTO `attributes` ( `attribute_name`) VALUES(?)",Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, attribute.getAttributeName());
        ps.executeUpdate();
        ResultSet keys = ps.getGeneratedKeys();
        keys.next();  
		int lastInsertedId = keys.getInt(1);
		ps.close();
		insertedAttribute = getById(lastInsertedId);
		return insertedAttribute;
	}
	
	/**
	 * @param attributeId
	 * @return
	 * @throws SQLException
	 */
	public Attribute getById(int attributeId) throws SQLException{
		Attribute attribute = null;
		PreparedStatement ps = connection.prepareStatement("select * from attributes where attribute_id = ?");
		ps.setInt(1, attributeId);
		ResultSet rs = ps.executeQuery();
		while(rs.next()){
			attribute = new Attribute(rs.getInt(1),rs.getString(2).trim());
		}
		rs.close();
		ps.close();
		return attribute;
	}
	
	/**
	 * function which parses the given attributeIds in string using "," separtor
	 * and returns array of attribute ids
	 * @param attributeIds
	 * @return ArrayList<Attribute>
	 * @throws SQLException
	 */
	public ArrayList<Attribute> getById(String attributeIds) throws SQLException{
		ArrayList<Attribute> attributes = new ArrayList<Attribute>();
		String[] ids = attributeIds.split(",");
		for(String id : ids){
			int i = Integer.parseInt(id.trim());
			Attribute attribute = getById(i);
			if(attribute != null){
				attributes.add(attribute);
			}
		}
		return attributes;
	}
	
	/**
	 * @param attributeName
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<Attribute> getByAttributeName(String attributeName) throws SQLException{
		ArrayList<Attribute> attributes= new ArrayList<Attribute>();
		PreparedStatement ps = connection.prepareStatement("select * from attributes where attribute_name = ?");
		ps.setString(1, attributeName);
		ResultSet rs = ps.executeQuery();
		while(rs.next()){
			Attribute attribute = new Attribute(rs.getInt(1),rs.getString(2).trim());
			attributes.add(attribute);
		}
		rs.close();
		ps.close();
		return attributes;
	}
	
	/**
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<Attribute> getAll()throws SQLException{
		ArrayList<Attribute> attributes = new ArrayList<Attribute>();
		PreparedStatement ps = connection.prepareStatement("select * from attributes");
		ResultSet rs = ps.executeQuery();
		while(rs.next()){
			Attribute attribute = new Attribute(rs.getInt(1),rs.getString(2).trim());
			attributes.add(attribute);
		}
		return attributes;
	}
	
	/**
	 * @param attributeId
	 * @return
	 * @throws SQLException
	 */
	public Attribute delete(int attributeId) throws SQLException{
		Attribute attributeToDelete = getById(attributeId);
		if(attributeToDelete != null){
			PreparedStatement ps = connection.prepareStatement("delete from attributes where attribute_id = ?");
			ps.setInt(1, attributeId);
			ps.executeUpdate();	
			ps.close();
		}
		return attributeToDelete;
	}
	
	/**
	 * @param attribute
	 * @return
	 * @throws SQLException
	 */
	public Attribute delete(Attribute attribute)throws SQLException{
		Attribute attributeToDelete = null;
		if(attribute != null){
			attributeToDelete = delete(attribute.getAttributeId());
		}
		return attributeToDelete;
	}
	
	/**
	 * @param attribute
	 * @return
	 * @throws SQLException
	 */
	public Attribute update(Attribute attribute)throws SQLException{
		if(attribute != null){
			PreparedStatement ps = connection.prepareStatement("update attributes set attribute_name = ? where attribute_id = ?");
			ps.setString(1, attribute.getAttributeName());
			ps.setInt(2, attribute.getAttributeId());
			ps.executeUpdate();
			ps.close();
		}
		return attribute;
	}
}
