package com.incture.lch.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "T_YARD_STATUS")
public class YardStatus {

	@Id
	@Column(name = "STATUS_ID")
	private  String statusId;
	
	@Column(name = "STATUS_DESC")
	private String statusDesc;
	
	@Column(name = "GROUP_OR_KPI")
	private String groupOrKpi;

	public String getStatusId() {
		return statusId;
	}

	public void setStatusId(String statusId) {
		this.statusId = statusId;
	}

	public String getStatusDesc() {
		return statusDesc;
	}

	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}

	public String getGroupOrKpi() {
		return groupOrKpi;
	}

	public void setGroupOrKpi(String groupOrKpi) {
		this.groupOrKpi = groupOrKpi;
	}
	
	
	
}
