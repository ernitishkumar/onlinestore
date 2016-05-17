/**
 * 
 */
package com.wingify.beans;

/**
 * @author NITISH
 *
 */
public class Category {

	private int categoryId;
	
	private String name;
	
	private String description;
	
	private int parentId;

	/**
	 * @return the categoryId
	 */
	public int getCategoryId() {
		return categoryId;
	}

	/**
	 * @param categoryId the categoryId to set
	 */
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
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
	 * @return the parentId
	 */
	public int getParentId() {
		return parentId;
	}

	/**
	 * @param parentId the parentId to set
	 */
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	/**
	 * @param categoryId
	 * @param name
	 * @param description
	 * @param parentId
	 */
	public Category(int categoryId, String name, String description, int parentId) {
		this.categoryId = categoryId;
		this.name = name;
		this.description = description;
		this.parentId = parentId;
	}

	/**
	 * 
	 */
	public Category() {
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Category [categoryId=" + categoryId + ", name=" + name + ", description=" + description + ", parentId="
				+ parentId + "]";
	}
	
}
