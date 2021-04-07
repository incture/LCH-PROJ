package com.incture.lch.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="T_PREMIUM_FREIGHT_APPROVAL_RULE")
public class PremiumFreightAprrovalRule 
{
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name="PLANT")
	private String plant;
	
	@Column(name="COST_MIN")
	private int cost_min;
	
	@Column(name="COST_MAX")
	private int cost_max;

	@Column(name="APPROVER")
	private String approver;

	@Column(name="APPROVER_EMAIL")
	private String approverEmail;
	
	@Column(name = "BACKUP")
	private String backup;
	
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
	
}
