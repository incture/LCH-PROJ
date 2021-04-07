package com.incture.lch.lchWorkflowApplication.workflow.dto;

public class WorkflowCustomDto {

	private String orderIdDetails;
	private String taskIdDetails;
	private String plannerDetails;

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
	
	

	public String getPlannerDetails() {
		return plannerDetails;
	}

	public void setPlannerDetails(String plannerDetails) {
		this.plannerDetails = plannerDetails;
	}

	@Override
	public String toString() {
		return "WorkflowCustomDto [orderIdDetails=" + orderIdDetails + ", taskIdDetails=" + taskIdDetails + ", plannerDetails="
				+ plannerDetails + "]";
	}

}
