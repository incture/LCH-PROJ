package com.incture.lch.lchWorkflowApplication.workflow.dto;


public class WorkflowPremiumCustomDto 
{

	private String orderIdDetails;
	private String taskIdDetails;
	private String roleDetails;
	private String managerActionType;
	private String accountantActionType;
	private PremiumOrderAccountingDetailsDto accoutingDetailsDto;
	
	
	
	public PremiumOrderAccountingDetailsDto getAccoutingDetailsDto() {
		return accoutingDetailsDto;
	}
	public void setAccoutingDetailsDto(PremiumOrderAccountingDetailsDto accoutingDetailsDto) {
		this.accoutingDetailsDto = accoutingDetailsDto;
	}
	public String getManagerActionType() {
		return managerActionType;
	}
	public void setManagerActionType(String managerActionType) {
		this.managerActionType = managerActionType;
	}
	public String getAccountantActionType() {
		return accountantActionType;
	}
	public void setAccountantActionType(String accountantActionType) {
		this.accountantActionType = accountantActionType;
	}
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
	public String getRoleDetails() {
		return roleDetails;
	}
	public void setRoleDetails(String roleDetails) {
		this.roleDetails = roleDetails;
	}
	
	

}
