/*
 * @copyright (DigitalTrust Technologies private limited,Hyderabad) 2021, All rights reserved.
 * @author (anup mohite)
 */
package com.example.signatureapplication;

import java.io.Serializable;

// TODO: Auto-generated Javadoc

/**
 * The Class APIResponse.
 */

public class APIResponse implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The status. */
	private boolean success;

	/** The message. */
	private String message;

	/** The result. */
	private String result;

	public APIResponse() {
		super();
	}

	
	public APIResponse(boolean success, String message, String result) {
		super();
		this.success = success;
		this.message = message;
		this.result = result;
	}


	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "{" + "\"success\"" + ":" + success + "," + "\"message\"" + ":" + "\"" + message + "\"," + "\"result\""
				+ ":" + result + "}";
	}
}
