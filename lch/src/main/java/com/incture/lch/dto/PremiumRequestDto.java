package com.incture.lch.dto;

import java.util.Date;

public class PremiumRequestDto
{
	private String orderId;
	private String fromDate;
	private String toDate;
	private String plannerEmail;
	private String status;
	private String originName;
	private String destinationName;
	private int pageNumber;
	private int noOfEntry=0;
	private String reasonCode;
	
	
	public String getorderId() {
		return orderId;
	}
	public void setorderId(String orderId) {
		this.orderId = orderId;
	}
	/*public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}*/
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	/*public String getPartNo() {
		return partNo;
	}
	public void setPartNo(String partNo) {
		this.partNo = partNo;
	}*/
	public String getPlannerEmail() {
		return plannerEmail;
	}
	public void setPlannerEmail(String plannerEmail) {
		this.plannerEmail = plannerEmail;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getOriginName() {
		return originName;
	}
	public void setOriginName(String originName) {
		this.originName = originName;
	}
	public String getDestinationName() {
		return destinationName;
	}
	public void setDestinationName(String destinationName) {
		this.destinationName = destinationName;
	}
	
	public int getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	public int getNoOfEntry() {
		return noOfEntry;
	}
	public void setNoOfEntry(int noOfEntry) {
		this.noOfEntry = noOfEntry;
	}
	public String getReasonCode() {
		return reasonCode;
	}
	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}
	
	

	
}
