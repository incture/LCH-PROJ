package com.incture.lch.dto;



import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class YardManagementDto {

	private Long id;
	
	private String freightOrderNo;
	
	private String trailer;
	
	private String carrier;
	
	private String carrierName;
	
	private String carrierDesc;
	
	private String createdBy;
	
	private String createdDate;
	
	private String updatedBy;
	
	private String updatedDate;
	
	private String supplier;
	
	private String supplierAddress;
	
	private String arrival;
	
	private String location;
	
	private String status;
	
	private String lineSeq;
	
	private String commodities;
	
	private String priority;
	
	private String bol;
	
	private String destId;
	
	private String destDesc;
	
	private String plannedShipDate;
	
	private String handlingUnit;
	
	private String comments;
	
	private String proNo;
	
	private String pickNo;
	
	private String referenceNo;
	
	private String weight;
	
	private String qty;
	
	//private String pendingWith;
	
	private String pendingWith;
	
	private String yardId;
	
	private String yardLocation;

	private String sealNo;

	private String licencePlateNo;
	
	private String role;
	
	private Boolean isPpKit;
	
	private String securityComments;
	
	private String securityLocation;
	
	private String fuCount;
	
	private String latitude;
	
	private String longitude;
	
	private String yardIn;
	
	private String yardOut;
	
	private int agingCount;
	
	private String adhocType;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFreightOrderNo() {
		return freightOrderNo;
	}

	public void setFreightOrderNo(String freightOrderNo) {
		this.freightOrderNo = freightOrderNo;
	}

	public String getTrailer() {
		return trailer;
	}

	public void setTrailer(String trailer) {
		this.trailer = trailer;
	}

	public String getCarrier() {
		return carrier;
	}

	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	public String getArrival() {
		return arrival;
	}

	public void setArrival(String arrival) {
		this.arrival = arrival;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getLineSeq() {
		return lineSeq;
	}

	public void setLineSeq(String lineSeq) {
		this.lineSeq = lineSeq;
	}

	public String getCommodities() {
		return commodities;
	}

	public void setCommodities(String commodities) {
		this.commodities = commodities;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getBol() {
		return bol;
	}

	public void setBol(String bol) {
		this.bol = bol;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getSupplierAddress() {
		return supplierAddress;
	}

	public void setSupplierAddress(String supplierAddress) {
		this.supplierAddress = supplierAddress;
	}

	public String getDestId() {
		return destId;
	}

	public void setDestId(String destId) {
		this.destId = destId;
	}

	public String getDestDesc() {
		return destDesc;
	}

	public void setDestDesc(String destDesc) {
		this.destDesc = destDesc;
	}

	public String getPlannedShipDate() {
		return plannedShipDate;
	}

	public void setPlannedShipDate(String plannedShipDate) {
		this.plannedShipDate = plannedShipDate;
	}

	public String getHandlingUnit() {
		return handlingUnit;
	}

	public void setHandlingUnit(String handlingUnit) {
		this.handlingUnit = handlingUnit;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getProNo() {
		return proNo;
	}

	public void setProNo(String proNo) {
		this.proNo = proNo;
	}

	public String getPickNo() {
		return pickNo;
	}

	public void setPickNo(String pickNo) {
		this.pickNo = pickNo;
	}

	public String getReferenceNo() {
		return referenceNo;
	}

	public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getQty() {
		return qty;
	}

	public void setQty(String qty) {
		this.qty = qty;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getCarrierName() {
		return carrierName;
	}

	public void setCarrierName(String carrierName) {
		this.carrierName = carrierName;
	}

	public String getCarrierDesc() {
		return carrierDesc;
	}

	public void setCarrierDesc(String carrierDesc) {
		this.carrierDesc = carrierDesc;
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

	public String getSealNo() {
		return sealNo;
	}

	public void setSealNo(String sealNo) {
		this.sealNo = sealNo;
	}

	public String getLicencePlateNo() {
		return licencePlateNo;
	}

	public void setLicencePlateNo(String licencePlateNo) {
		this.licencePlateNo = licencePlateNo;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Boolean getIsPpKit() {
		return isPpKit;
	}

	public void setIsPpKit(Boolean isPpKit) {
		this.isPpKit = isPpKit;
	}

	public String getSecurityComments() {
		return securityComments;
	}

	public void setSecurityComments(String securityComments) {
		this.securityComments = securityComments;
	}

	public String getSecurityLocation() {
		return securityLocation;
	}

	public void setSecurityLocation(String securityLocation) {
		this.securityLocation = securityLocation;
	}

	public String getFuCount() {
		return fuCount;
	}

	public void setFuCount(String fuCount) {
		this.fuCount = fuCount;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getYardIn() {
		return yardIn;
	}

	public void setYardIn(String yardIn) {
		this.yardIn = yardIn;
	}

	public String getYardOut() {
		return yardOut;
	}

	public void setYardOut(String yardOut) {
		this.yardOut = yardOut;
	}

	public int getAgingCount() {
		return agingCount;
	}

	public void setAgingCount(int agingCount) {
		this.agingCount = agingCount;
	}

	public String getAdhocType() {
		return adhocType;
	}

	public void setAdhocType(String adhocType) {
		this.adhocType = adhocType;
	}

	public String getPendingWith() {
		return pendingWith;
	}

	public void setPendingWith(String pendingWith) {
		this.pendingWith = pendingWith;
	}

	@Override
	public String toString() {
		return "YardManagementDto [id=" + id + ", freightOrderNo=" + freightOrderNo + ", trailer=" + trailer
				+ ", carrier=" + carrier + ", carrierName=" + carrierName + ", carrierDesc=" + carrierDesc
				+ ", createdBy=" + createdBy + ", createdDate=" + createdDate + ", updatedBy=" + updatedBy
				+ ", updatedDate=" + updatedDate + ", supplier=" + supplier + ", supplierAddress=" + supplierAddress
				+ ", arrival=" + arrival + ", location=" + location + ", status=" + status + ", lineSeq=" + lineSeq
				+ ", commodities=" + commodities + ", priority=" + priority + ", bol=" + bol + ", destId=" + destId
				+ ", destDesc=" + destDesc + ", plannedShipDate=" + plannedShipDate + ", handlingUnit=" + handlingUnit
				+ ", comments=" + comments + ", proNo=" + proNo + ", pickNo=" + pickNo + ", referenceNo=" + referenceNo
				+ ", weight=" + weight + ", qty=" + qty + ", pendingWith=" + pendingWith + ", yardId=" + yardId
				+ ", yardLocation=" + yardLocation + ", sealNo=" + sealNo + ", licencePlateNo=" + licencePlateNo
				+ ", role=" + role + ", isPpKit=" + isPpKit + ", securityComments=" + securityComments
				+ ", securityLocation=" + securityLocation + ", fuCount=" + fuCount + ", latitude=" + latitude
				+ ", longitude=" + longitude + ", yardIn=" + yardIn + ", yardOut=" + yardOut + ", agingCount="
				+ agingCount + ", adhocType=" + adhocType + "]";
	}


	
	
	


}
