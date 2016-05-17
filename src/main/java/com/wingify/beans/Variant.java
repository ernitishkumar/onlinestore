/**
 * 
 */
package com.wingify.beans;

/**
 * @author NITISH
 *
 */
public class Variant {

	private int variantId;
	
	private int productId;
	
	private String variantSKU;
	
	private int quantity;
	
	private float price;
	
	private int attributeId;
	
	private String attributeValue;

	/**
	 * @return the variantId
	 */
	public int getVariantId() {
		return variantId;
	}

	/**
	 * @param variantId the variantId to set
	 */
	public void setVariantId(int variantId) {
		this.variantId = variantId;
	}

	/**
	 * @return the productId
	 */
	public int getProductId() {
		return productId;
	}

	/**
	 * @param productId the productId to set
	 */
	public void setProductId(int productId) {
		this.productId = productId;
	}

	/**
	 * @return the variantSKU
	 */
	public String getVariantSKU() {
		return variantSKU;
	}

	/**
	 * @param variantSKU the variantSKU to set
	 */
	public void setVariantSKU(String variantSKU) {
		this.variantSKU = variantSKU;
	}

	/**
	 * @return the quantity
	 */
	public int getQuantity() {
		return quantity;
	}

	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	/**
	 * @return the price
	 */
	public float getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(float price) {
		this.price = price;
	}

	/**
	 * @return the attributeId
	 */
	public int getAttributeId() {
		return attributeId;
	}

	/**
	 * @param attributeId the attributeId to set
	 */
	public void setAttributeId(int attributeId) {
		this.attributeId = attributeId;
	}

	/**
	 * @return the attributeValue
	 */
	public String getAttributeValue() {
		return attributeValue;
	}

	/**
	 * @param attributeValue the attributeValue to set
	 */
	public void setAttributeValue(String attributeValue) {
		this.attributeValue = attributeValue;
	}

	/**
	 * @param variantId
	 * @param productId
	 * @param variantSKU
	 * @param quantity
	 * @param price
	 * @param attributeId
	 * @param attributeValue
	 */
	public Variant(int variantId, int productId, String variantSKU, int quantity, float price, int attributeId,
			String attributeValue) {
		this.variantId = variantId;
		this.productId = productId;
		this.variantSKU = variantSKU;
		this.quantity = quantity;
		this.price = price;
		this.attributeId = attributeId;
		this.attributeValue = attributeValue;
	}

	/**
	 * 
	 */
	public Variant() {
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Variant [variantId=" + variantId + ", productId=" + productId + ", variantSKU=" + variantSKU
				+ ", quantity=" + quantity + ", price=" + price + ", attributeId=" + attributeId + ", attributeValue="
				+ attributeValue + "]";
	}
}
