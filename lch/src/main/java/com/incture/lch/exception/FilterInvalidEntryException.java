package com.incture.lch.exception;

public class FilterInvalidEntryException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String filter_feild;

	public FilterInvalidEntryException(String filter_feild) {
		//super();
		this.filter_feild = filter_feild;
	}

	public String getFilter_feild() {
		return filter_feild;
	}

	public void setFilter_feild(String filter_feild) {
		this.filter_feild = filter_feild;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
		

}
