package com.incture.lch.dto;

public class PremiumFreightApprovalRuleDTO 
{
    private Long id;
	
	private String plant;
	
	private int cost_min;
	
	private int cost_max;

	private String approver;
	
	private String approverEmail;
	
	private String backup;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getCost_min() {
		return cost_min;
	}

	public void setCost_min(int cost_min) {
		this.cost_min = cost_min;
	}

	public int getCost_max() {
		return cost_max;
	}

	public void setCost_max(int cost_max) {
		this.cost_max = cost_max;
	}

	public String getApprover() {
		return approver;
	}

	public void setApprover(String approver) {
		this.approver = approver;
	}

	public String getPlant() {
		return plant;
	}

	public void setPlant(String plant) {
		this.plant = plant;
	}

	public String getApproverEmail() {
		return approverEmail;
	}

	public void setApproverEmail(String approverEmail) {
		this.approverEmail = approverEmail;
	}

	public String getBackup() {
		return backup;
	}

	public void setBackup(String backup) {
		this.backup = backup;
	}
	
	
	
	

}
