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

import com.wingify.beans.Variant;
import com.wingify.utility.GlobalResources;

/**
 * @author NITISH
 *
 */
public class VariantsDAO {

	private Connection connection = GlobalResources.getConnection();
	
	/**
	 * @param variant
	 * @return
	 * @throws SQLException
	 */
	public Variant insert(Variant variant) throws SQLException{
		Variant insertedVariant = null;
		PreparedStatement ps = connection.prepareStatement("INSERT INTO `variants` (`product_id`,`variant_sku`,`quantity`,`price`,`attribute_id`,`attribute_value`) VALUES(?,?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, variant.getProductId());
        ps.setString(2, variant.getVariantSKU());
        ps.setInt(3,variant.getQuantity());
        ps.setFloat(4, variant.getPrice());
        ps.setInt(5,variant.getAttributeId());
        ps.setString(6, variant.getAttributeValue());
        ps.executeUpdate();
        ResultSet keys = ps.getGeneratedKeys();
        keys.next();  
		int lastInsertedId = keys.getInt(1);
		ps.close();
		insertedVariant = getById(lastInsertedId);
		return insertedVariant;
	}
	
	/**
	 * @param variantId
	 * @return
	 * @throws SQLException
	 */
	public Variant getById(int variantId) throws SQLException{
		Variant variant = null;
		PreparedStatement ps = connection.prepareStatement("select * from variants where variant_id = ?");
		ps.setInt(1, variantId);
		ResultSet rs = ps.executeQuery();
		while(rs.next()){
			variant = new Variant();
			variant.setVariantId(rs.getInt(1));
			variant.setProductId(rs.getInt(2));
			variant.setVariantSKU(rs.getString(3).trim());
			variant.setQuantity(rs.getInt(4));
			variant.setPrice(rs.getFloat(5));
			variant.setAttributeId(rs.getInt(6));
			variant.setAttributeValue(rs.getString(7).trim());
		}
		rs.close();
		ps.close();
		return variant;
	}
	
	/**
	 * @param productId
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<Variant> getByProductId(int productId) throws SQLException{
		ArrayList<Variant> variants= new ArrayList<Variant>();
		PreparedStatement ps = connection.prepareStatement("select * from variants where product_id = ?");
		ps.setInt(1, productId);
		ResultSet rs = ps.executeQuery();
		resultSetMapper(rs, variants);
		rs.close();
		ps.close();
		return variants;
	}
	
	/**
	 * @param productId
	 * @param variantId
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<Variant> getByProductIdAndVariantId(int productId,int variantId) throws SQLException{
		ArrayList<Variant> variants= new ArrayList<Variant>();
		PreparedStatement ps = connection.prepareStatement("select * from variants where product_id = ? and variant_id=?");
		ps.setInt(1, productId);
		ps.setInt(2, variantId);
		ResultSet rs = ps.executeQuery();
		resultSetMapper(rs, variants);
		rs.close();
		ps.close();
		return variants;
	}
	
	/**
	 * @param productId
	 * @param variantSKU
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<Variant> getByProductIdAndVariantSKU(int productId,String variantSKU) throws SQLException{
		ArrayList<Variant> variants= new ArrayList<Variant>();
		PreparedStatement ps = connection.prepareStatement("select * from variants where product_id = ? and variant_sku=?");
		ps.setInt(1, productId);
		ps.setString(2, variantSKU);
		ResultSet rs = ps.executeQuery();
		resultSetMapper(rs, variants);
		rs.close();
		ps.close();
		return variants;
	}
	
	/**
	 * @param description
	 * @return
	 * @throws SQLException
	 */
	public Variant getBySKU(String variantSKU) throws SQLException{
		Variant variant = null;
		PreparedStatement ps = connection.prepareStatement("select * from variants where variant_sku = ?");
		ps.setString(1, variantSKU);
		ResultSet rs = ps.executeQuery();
		while(rs.next()){
			variant = new Variant();
			variant.setVariantId(rs.getInt(1));
			variant.setProductId(rs.getInt(2));
			variant.setVariantSKU(rs.getString(3).trim());
			variant.setQuantity(rs.getInt(4));
			variant.setPrice(rs.getFloat(5));
			variant.setAttributeId(rs.getInt(6));
			variant.setAttributeValue(rs.getString(7).trim());
		}
		rs.close();
		ps.close();
		return variant;
	}
	
	/**
	 * @param attributeId
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<Variant> getByAttributeId(int attributeId) throws SQLException{
		ArrayList<Variant> variants= new ArrayList<Variant>();
		PreparedStatement ps = connection.prepareStatement("select * from variants where attribute_id = ?");
		ps.setInt(1, attributeId);
		ResultSet rs = ps.executeQuery();
		resultSetMapper(rs, variants);
		rs.close();
		ps.close();
		return variants;
	}
	
	/**
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<Variant> getAll()throws SQLException{
		ArrayList<Variant> variants= new ArrayList<Variant>();
		PreparedStatement ps = connection.prepareStatement("select * from variants");
		ResultSet rs = ps.executeQuery();
		resultSetMapper(rs, variants);
		rs.close();
		ps.close();
		return variants;
	}
	
	/**
	 * @param variantId
	 * @return
	 * @throws SQLException
	 */
	public Variant delete(int variantId) throws SQLException{
		Variant variantToDelete = getById(variantId);
		if(variantToDelete != null){
			PreparedStatement ps = connection.prepareStatement("delete from variants where variant_id = ?");
			ps.setInt(1, variantId);
			ps.executeUpdate();	
			ps.close();
		}
		return variantToDelete;
	}
	
	/**
	 * @param variant
	 * @return
	 * @throws SQLException
	 */
	public Variant delete(Variant variant)throws SQLException{
		Variant variantToDelete = null;
		if(variant != null){
			variantToDelete = delete(variant.getVariantId());
		}
		return variantToDelete;
	}
	
	/**
	 * @param variant
	 * @return
	 * @throws SQLException
	 */
	public Variant update(Variant variant)throws SQLException{
		if(variant != null && variant.getVariantId()==0){
			return null;
		}
		if(variant != null){
			PreparedStatement ps = connection.prepareStatement("update variants set product_id = ?,variant_sku = ?, quantity=?,price=?,attribute_id=?,attribute_value=? where variant_id = ?");
			ps.setInt(1, variant.getProductId());
	        ps.setString(2, variant.getVariantSKU());
	        ps.setInt(3,variant.getQuantity());
	        ps.setFloat(4, variant.getPrice());
	        ps.setInt(5,variant.getAttributeId());
	        ps.setString(6, variant.getAttributeValue());
	        ps.setInt(7, variant.getVariantId());
			ps.executeUpdate();
			ps.close();
		}
		return variant;
	}
	
	public void resultSetMapper(ResultSet rs,ArrayList<Variant> variants)throws SQLException{
		while(rs.next()){
			Variant variant = new Variant();
			variant.setVariantId(rs.getInt(1));
			variant.setProductId(rs.getInt(2));
			variant.setVariantSKU(rs.getString(3).trim());
			variant.setQuantity(rs.getInt(4));
			variant.setPrice(rs.getFloat(5));
			variant.setAttributeId(rs.getInt(6));
			variant.setAttributeValue(rs.getString(7).trim());
			variants.add(variant);
		}
	}

}
