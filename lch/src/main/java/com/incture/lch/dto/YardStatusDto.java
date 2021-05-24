package com.incture.lch.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class YardStatusDto {

	private  String statusId;
	
	private String statusDesc;
	
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

	@Override
	public String toString() {
		return "YardStatusDto [statusId=" + statusId + ", statusDesc=" + statusDesc + ", groupOrKpi=" + groupOrKpi
				+ "]";
	}
	
	
	
	
}
