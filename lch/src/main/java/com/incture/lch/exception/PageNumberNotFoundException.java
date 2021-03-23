package com.incture.lch.exception;

public class PageNumberNotFoundException extends RuntimeException
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int total_entries;

	public PageNumberNotFoundException(int total_entries) {
		super();
		this.total_entries = total_entries;
	}

	public int getTotal_entries() {
		return total_entries;
	}

	public void setTotal_entries(int total_entries) {
		this.total_entries = total_entries;
	}
	

	
}
