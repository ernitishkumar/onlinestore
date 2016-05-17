/**
 * 
 */
package com.wingify.beans;

/**
 * @author NITISH
 *
 */
public class ErrorMessage {

	private String error;

	/**
	 * @return the error
	 */
	public String getError() {
		return error;
	}

	/**
	 * @param error the error to set
	 */
	public void setError(String error) {
		this.error = error;
	}

	/**
	 * @param error
	 */
	public ErrorMessage(String error) {
		this.error = error;
	}

	/**
	 * Default Constructor
	 */
	public ErrorMessage() {
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ErrorMessage [error=" + error + "]";
	}
	
}
