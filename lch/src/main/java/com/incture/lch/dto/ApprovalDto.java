package com.incture.lch.dto;

public class ApprovalDto {

	private String orderIdDetails;
	private String taskIdDetails;
	private String Status;
	private PremiumOrderAccountingDetailsDto accountantDto;

	public String getOrderIdDetails() {
		return orderIdDetails;
	}

	public void setOrderIdDetails(String orderIdDetails) {
		this.orderIdDetails = orderIdDetails;
	}

	public String getTaskIdDetails() {
		return taskIdDetails;
	}

	public void setTaskIdDetails(String taskIdDetails) {
		this.taskIdDetails = taskIdDetails;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}

	public PremiumOrderAccountingDetailsDto getAccountantDto() {
		return accountantDto;
	}

	public void setAccountantDto(PremiumOrderAccountingDetailsDto accountantDto) {
		this.accountantDto = accountantDto;
	}

}
