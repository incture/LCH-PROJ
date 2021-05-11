package com.incture.lch.dto;

public class PartNumberDescDto {

	private String partNum;
	private String partDesc;
	private String message;
	private Boolean isHazMat;
	private String hazardUnNumber;

	public PartNumberDescDto() {

	}

// TODO from UCDetector: Constructor "PartNumberDescDto.PartNumberDescDto(String,String,String,Boolean,String)" has 0 references
	public PartNumberDescDto(String partNum, String partDesc, String message, Boolean isHazMat, String hazardUnNumber) { // NO_UCD (unused code)
		super();
		this.partNum = partNum;
		this.partDesc = partDesc;
		this.message = message;
		this.isHazMat = isHazMat;
		this.hazardUnNumber = hazardUnNumber;
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

	public Boolean getIsHazMat() {
		return isHazMat;
	}

	public void setIsHazMat(Boolean isHazMat) {
		this.isHazMat = isHazMat;
	}

	public String getHazardUnNumber() {
		return hazardUnNumber;
	}

	public void setHazardUnNumber(String hazardUnNumber) {
		this.hazardUnNumber = hazardUnNumber;
	}

}
