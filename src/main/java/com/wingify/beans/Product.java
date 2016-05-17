/**
 * 
 */
package com.wingify.beans;

import javax.xml.bind.annotation.XmlTransient;

/**
 * @author NITISH
 *
 */
public class Product {

	private int productId;
	
	private String name;
	
	private String description;
	
	private int quantity;
	
	private String availability;
	
	private float price;
	
	private String categories;
	
	private String attributes;
	
	private String createdAt;
	
	private String lastUpdatedAt;

	private String attributes_url;
	
	private String variants_url;
	
	private final String PRODUCT_URL = "localhost:8080/onlinestore/products/";
	
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
		setAttributes_url(PRODUCT_URL+this.productId+"/attributes");
		setVariants_url(PRODUCT_URL+this.productId+"/variants");
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
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
	 * @return the availability
	 */
	public String getAvailability() {
		return availability;
	}

	/**
	 * @param availability the availability to set
	 */
	public void setAvailability(String availability) {
		this.availability = availability;
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
	 * @return the categories
	 */
	public String getCategories() {
		return categories;
	}

	/**
	 * @param categories the categories to set
	 */
	public void setCategories(String categories) {
		this.categories = categories;
	}

	/**
	 * @return the attributes
	 */
	public String getAttributes() {
		return attributes;
	}

	/**
	 * @param attributes the attributes to set
	 */
	public void setAttributes(String attributes) {
		this.attributes = attributes;
	}

	/**
	 * @return the createdAt
	 */
	public String getCreatedAt() {
		return createdAt;
	}

	/**
	 * @param createdAt the createdAt to set
	 */
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * @return the lastUpdatedAt
	 */
	public String getLastUpdatedAt() {
		return lastUpdatedAt;
	}

	/**
	 * @param lastUpdatedAt the lastUpdatedAt to set
	 */
	public void setLastUpdatedAt(String lastUpdatedAt) {
		this.lastUpdatedAt = lastUpdatedAt;
	}

	/**
	 * @return the attributes_url
	 */
	public String getAttributes_url() {
		return attributes_url;
	}

	/**
	 * @param attributes_url the attributes_url to set
	 */
	public void setAttributes_url(String attributes_url) {
		this.attributes_url = attributes_url;
	}

	/**
	 * @return the variants_url
	 */
	public String getVariants_url() {
		return variants_url;
	}

	/**
	 * @param variants_url the variants_url to set
	 */
	public void setVariants_url(String variants_url) {
		this.variants_url = variants_url;
	}

	/**
	 * @param productId
	 * @param name
	 * @param description
	 * @param quantity
	 * @param availability
	 * @param price
	 * @param categories
	 * @param attributes
	 * @param createdAt
	 * @param lastUpdatedAt
	 */
	public Product(int productId, String name, String description, int quantity, String availability, float price,
			String categories, String attributes, String createdAt, String lastUpdatedAt) {
		this.productId = productId;
		this.name = name;
		this.description = description;
		this.quantity = quantity;
		this.availability = availability;
		this.price = price;
		this.categories = categories;
		this.attributes = attributes;
		this.createdAt = createdAt;
		this.lastUpdatedAt = lastUpdatedAt;
	}

	/**
	 * 
	 */
	public Product() {
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Product [productId=" + productId + ", name=" + name + ", description=" + description + ", quantity="
				+ quantity + ", availability=" + availability + ", price=" + price + ", categories=" + categories
				+ ", attributes=" + attributes + ", createdAt=" + createdAt + ", lastUpdatedAt=" + lastUpdatedAt + "]";
	}
	
}
