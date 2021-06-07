package com.incture.lch.dto;

public class PartNumberDescDto {

	private String partNum;
	private String partDesc;
	private String message;
	

	public PartNumberDescDto() {

	}

	public PartNumberDescDto(String partNum, String partDesc, String message) { // NO_UCD (unused code)
		super();
		this.partNum = partNum;
		this.partDesc = partDesc;
		this.message = message;
		
	}

	public String getPartNum() {
		return partNum;
	}

	public void setPartNum(String partNum) {
		this.partNum = partNum;
	}

	public String getPartDesc() {
		return partDesc;
	}

	public void setPartDesc(String partDesc) {
		this.partDesc = partDesc;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	
}