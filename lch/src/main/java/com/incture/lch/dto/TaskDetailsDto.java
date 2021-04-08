package com.incture.lch.dto;

public class TaskDetailsDto {
	
	private String activityId;
	private String claimedAt;
	private String completedAt;
	private String createdAt;
	private String description;
	private String taskId;
	private String priority;
	private String dueDate;
	private String processor;
	private String recipientUsers;
	private String recipientGroup;
	private String status;
	private String subject;
	private String workflowDefinitionId;
	private String workflowInstanceId;
	public String getActivityId() {
		return activityId;
	}
	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}
	public String getClaimedAt() {
		return claimedAt;
	}
	public void setClaimedAt(String claimedAt) {
		this.claimedAt = claimedAt;
	}
	public String getCompletedAt() {
		return completedAt;
	}
	public void setCompletedAt(String completedAt) {
		this.completedAt = completedAt;
	}
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public String getDueDate() {
		return dueDate;
	}
	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}
	public String getProcessor() {
		return processor;
	}
	public void setProcessor(String processor) {
		this.processor = processor;
	}
	public String getRecipientUsers() {
		return recipientUsers;
	}
	public void setRecipientUsers(String recipientUsers) {
		this.recipientUsers = recipientUsers;
	}
	public String getRecipientGroup() {
		return recipientGroup;
	}
	public void setRecipientGroup(String recipientGroup) {
		this.recipientGroup = recipientGroup;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getWorkflowDefinitionId() {
		return workflowDefinitionId;
	}
	public void setWorkflowDefinitionId(String workflowDefinitionId) {
		this.workflowDefinitionId = workflowDefinitionId;
	}
	public String getWorkflowInstanceId() {
		return workflowInstanceId;
	}
	public void setWorkflowInstanceId(String workflowInstanceId) {
		this.workflowInstanceId = workflowInstanceId;
	}
	@Override
	public String toString() {
		return "TaskDetailsDto [activityId=" + activityId + ", claimedAt=" + claimedAt + ", completedAt=" + completedAt
				+ ", createdAt=" + createdAt + ", description=" + description + ", taskId=" + taskId + ", priority="
				+ priority + ", dueDate=" + dueDate + ", processor=" + processor + ", recipientUsers=" + recipientUsers
				+ ", recipientGroup=" + recipientGroup + ", status=" + status + ", subject=" + subject
				+ ", workflowDefinitionId=" + workflowDefinitionId + ", workflowInstanceId=" + workflowInstanceId + "]";
	}
	
	/*"activityId": "usertask1",
    "claimedAt": "2017-03-08T17:26:51.637Z",
    "completedAt": "2017-03-08T17:26:51.643Z",
    "createdAt": "2017-03-08T17:26:51.577Z",
    "description": "A very important task",
    "id": "1d8e6a7a-03f6-11e7-873d-00163e2ab1ae",
    "priority": "VERY_HIGH",
    "dueDate": "2017-03-09T19:48:30.281Z",
    "processor": "UserId1",
    "recipientUsers": [],
    "recipientGroups": [
      "Admins",
      "Managers"
    ],
    "status": "COMPLETED",
    "subject": "First Task",
    "workflowDefinitionId": "myfirstworkflow",
    "workflowInstanceId": "1d4f3e91-03f6-11e7-873d-00163e2ab1ae",
    "applicationScope": "own"*/
	
	

}
