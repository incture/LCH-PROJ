package com.incture.lch.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class YardManagementKpiDto {
	
	private String status;
	
	private String statusDesc;
	
	private int count;
	
	private String groupOrKpi;
	
	private List<YardManagementDto> ymRecords;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public List<YardManagementDto> getYmRecords() {
		return ymRecords;
	}

	public void setYmRecords(List<YardManagementDto> ymRecords) {
		this.ymRecords = ymRecords;
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
		return "YardManagementKpiDto [status=" + status + ", statusDesc=" + statusDesc + ", count=" + count
				+ ", groupOrKpi=" + groupOrKpi + ", ymRecords=" + ymRecords + "]";
	}

	
	
}
