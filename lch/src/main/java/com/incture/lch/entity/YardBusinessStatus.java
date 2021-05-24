package com.incture.lch.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "T_YARD_BUSINESS_STATUS")
public class YardBusinessStatus {

	@Id
	@Column(name = "BUSINESS_STATUS_ID")
	private  String businessStatusId;
	
	@Column(name = "BUSINESS_STATUS_DESC")
	private String businessStatusDesc;
	
	@Column(name = "BUSINESS_ROLE")
	private String businessRole;
	
	public String getBusinessStatusId() {
		return businessStatusId;
	}

	public void setBusinessStatusId(String businessStatusId) {
		this.businessStatusId = businessStatusId;
	}

	public String getBusinessStatusDesc() {
		return businessStatusDesc;
	}

	public void setBusinessStatusDesc(String businessStatusDesc) {
		this.businessStatusDesc = businessStatusDesc;
	}

	public String getBusinessRole() {
		return businessRole;
	}

	public void setBusinessRole(String businessRole) {
		this.businessRole = businessRole;
	}

}
