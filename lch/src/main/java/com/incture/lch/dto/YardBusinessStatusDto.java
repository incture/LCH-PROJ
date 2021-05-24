package com.incture.lch.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class YardBusinessStatusDto {

	private  String businessStatusId;
	
	private String businessStatusDesc;
	
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
