/**
 * 
 */
package com.wingify.beans;

/**
 * @author NITISH
 *
 */
public class AttributeValue {

	private int valueId;
	
	private int attributeId;
	
	private String description;
	
	private String attributeValue;
	
	/**
	 * @return the valueId
	 */
	public int getValueId() {
		return valueId;
	}

	/**
	 * @param valueId the valueId to set
	 */
	public void setValueId(int valueId) {
		this.valueId = valueId;
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
	 * 
	 */
	public AttributeValue() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param valueId
	 * @param attributeId
	 * @param description
	 * @param attributeValue
	 */
	public AttributeValue(int valueId, int attributeId, String description, String attributeValue) {
		this.valueId = valueId;
		this.attributeId = attributeId;
		this.description = description;
		this.attributeValue = attributeValue;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AttributeValue [valueId=" + valueId + ", attributeId=" + attributeId + ", description=" + description
				+ ", attributeValue=" + attributeValue + "]";
	}

}
