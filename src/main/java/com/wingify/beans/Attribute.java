/**
 * 
 */
package com.wingify.beans;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author NITISH
 *
 */
public class Attribute {

	private int attributeId;
	
	private String attributeName;
	
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
	 * @return the attributeName
	 */
	public String getAttributeName() {
		return attributeName;
	}

	/**
	 * @param attributeName the attributeName to set
	 */
	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}

	/**
	 * 
	 */
	public Attribute() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param attributeId
	 * @param attributeName
	 */
	public Attribute(int attributeId, String attributeName) {
		this.attributeId = attributeId;
		this.attributeName = attributeName;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Attribute [attributeId=" + attributeId + ", attributeName=" + attributeName + "]";
	}

}
