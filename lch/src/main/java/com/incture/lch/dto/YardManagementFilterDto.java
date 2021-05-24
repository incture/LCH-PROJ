package com.incture.lch.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Sweety.Choudhary
 *
 */
@XmlRootElement
public class YardManagementFilterDto {

	private String freightOrderNo;
	
	private String fromDate;
	
	private String toDate;
	
	private List<String> status;
	
	private String kpi;
	
	private List<String> statusList;
	
	private String supplier;
	
	private String trailer;
	
	private String yardId;
	
	private String yardLocation;
	
	private String carrier;
	
	private String createdBy;
	
	private List<String> pendingWith;
	
	//private List<String> pendingWithUsers;
	
	private int agingCount;

	public String getFreightOrderNo() {
		return freightOrderNo;
	}

	public void setFreightOrderNo(String freightOrderNo) {
		this.freightOrderNo = freightOrderNo;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public List<String> getStatus() {
		return status;
	}

	public void setStatus(List<String> status) {
		this.status = status;
	}

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	public String getTrailer() {
		return trailer;
	}

	public void setTrailer(String trailer) {
		this.trailer = trailer;
	}

	public String getYardId() {
		return yardId;
	}

	public void setYardId(String yardId) {
		this.yardId = yardId;
	}

	public String getYardLocation() {
		return yardLocation;
	}

	public void setYardLocation(String yardLocation) {
		this.yardLocation = yardLocation;
	}

	public String getCarrier() {
		return carrier;
	}

	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/*public String getPendingWith() {
		return pendingWith;
	}

	public void setPendingWith(String pendingWith) {
		this.pendingWith = pendingWith;
	}*/

	public List<String> getStatusList() {
		return statusList;
	}

	public void setStatusList(List<String> statusList) {
		this.statusList = statusList;
	}

	public String getKpi() {
		return kpi;
	}

	public void setKpi(String kpi) {
		this.kpi = kpi;
	}

	public int getAgingCount() {
		return agingCount;
	}

	public void setAgingCount(int agingCount) {
		this.agingCount = agingCount;
	}

	public List<String> getPendingWith() {
		return pendingWith;
	}

	public void setPendingWith(List<String> pendingWith) {
		this.pendingWith = pendingWith;
	}

	@Override
	public String toString() {
		return "YardManagementFilterDto [freightOrderNo=" + freightOrderNo + ", fromDate=" + fromDate + ", toDate="
				+ toDate + ", status=" + status + ", kpi=" + kpi + ", statusList=" + statusList + ", supplier="
				+ supplier + ", trailer=" + trailer + ", yardId=" + yardId + ", yardLocation=" + yardLocation
				+ ", carrier=" + carrier + ", createdBy=" + createdBy + ", pendingWith=" + pendingWith + ", agingCount="
				+ agingCount + "]";
	}

	/*public List<String> getPendingWithUsers() {
		return pendingWithUsers;
	}

	public void setPendingWithUsers(List<String> pendingWithUsers) {
		this.pendingWithUsers = pendingWithUsers;
	}*/

	
	
	
	
	
	

}
