package com.incture.lch.exception;

public class CarrierNotExistException extends RuntimeException 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String bpNumber;

	public CarrierNotExistException(String bpNumber) {
		super();
		this.bpNumber = bpNumber;
	}

	public String getBpNumber() {
		return bpNumber;
	}

	public void setBpNumber(String bpNumber) {
		this.bpNumber = bpNumber;
	}
	
	

}
