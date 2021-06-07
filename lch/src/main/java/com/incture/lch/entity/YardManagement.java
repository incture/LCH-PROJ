package com.incture.lch.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "T_YARD_MANAGEMENT")
public class YardManagement {

	@Id
	@Column(name = "ID", nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "FREIGHT_ORDER")
	private String freightOrderNo;

	@Column(name = "CREATED_BY")
	private String createdBy;

	@Column(name = "CREATED_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	@Column(name = "UPDATED_BY")
	private String updatedBy;

	@Column(name = "UPDATED_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedDate;

	@Column(name = "TRAILER")
	private String trailer;

	@Column(name = "CARRIER")
	private String carrier;

	@Column(name = "CARRIER_NAME")
	private String carrierName;

	@Column(name = "CARRIER_DESC")
	private String carrierDesc;

	@Column(name = "SUPPLIER")
	private String supplier;

	@Column(name = "SHIPPER_ADDRESS")
	private String supplierAddress;

	@Column(name = "ARRIVAL")
	private String arrival;

	@Column(name = "LOCATION")
	private String location;

	@Column(name = "STATUS")
	private String status;

	@Column(name = "LINE_SEQ")
	private String lineSeq;

	@Column(name = "COMMODITIES")
	private String commodities;

	@Column(name = "PRIORITY")
	private String priority;

	@Column(name = "BOL")
	private String bol;

	@Column(name = "DEST_ID")
	private String destId;

	@Column(name = "DEST_DESC")
	private String destDesc;

	@Column(name = "PLANNED_SHIP_DATE")
	private String plannedShipDate;

	@Column(name = "HANDLING_UNIT")
	private String handlingUnit;

	@Column(name = "COMMENTS")
	private String comments;

	@Column(name = "PRO_NO")
	private String proNo;

	@Column(name = "PICK_NO")
	private String pickNo;

	@Column(name = "REFERENCE_NO")
	private String referenceNo;

	@Column(name = "WEIGHT")
	private String weight;

	@Column(name = "QTY")
	private String qty;

	@Column(name = "PENDING_WITH")
	private String pendingWith;

	@Column(name = "YARD_ID")
	private String yardId;

	@Column(name = "YARD_LOCATION")
	private String yardLocation;

	@Column(name = "SEAL_NO")
	private String sealNo;

	@Column(name = "LICENCE_PLATE_NO")
	private String licencePlateNo;

	@Column(name = "ROLE")
	private String role;

	@Column(name = "PP_KIT")
	private String ppKit;

	@Column(name = "SECURITY_COMMENTS")
	private String securityComments;

	@Column(name = "SECURITY_LOCATION")
	private String securityLocation;

	@Column(name = "FU_COUNT")
	private String fuCount;

	@Column(name = "YARD_IN")
	private String yardIn;

	@Column(name = "YARD_OUT")
	private String yardOut;

	@Column(name = "LATITUDE")
	private String latitude;

	@Column(name = "LONGITUDE")
	private String longitude;

	@Column(name = "ADHOC_TYPE")
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

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
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

	public String getPendingWith() {
		return pendingWith;
	}

	public void setPendingWith(String pendingWith) {
		this.pendingWith = pendingWith;
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

	public String getPpKit() {
		return ppKit;
	}

	public void setPpKit(String ppKit) {
		this.ppKit = ppKit;
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

	public String getAdhocType() {
		return adhocType;
	}

	public void setAdhocType(String adhocType) {
		this.adhocType = adhocType;
	}

	@Override
	public String toString() {
		return "YardManagement [id=" + id + ", freightOrderNo=" + freightOrderNo + ", createdBy=" + createdBy
				+ ", createdDate=" + createdDate + ", updatedBy=" + updatedBy + ", updatedDate=" + updatedDate
				+ ", trailer=" + trailer + ", carrier=" + carrier + ", carrierName=" + carrierName + ", carrierDesc="
				+ carrierDesc + ", supplier=" + supplier + ", supplierAddress=" + supplierAddress + ", arrival="
				+ arrival + ", location=" + location + ", status=" + status + ", lineSeq=" + lineSeq + ", commodities="
				+ commodities + ", priority=" + priority + ", bol=" + bol + ", destId=" + destId + ", destDesc="
				+ destDesc + ", plannedShipDate=" + plannedShipDate + ", handlingUnit=" + handlingUnit + ", comments="
				+ comments + ", proNo=" + proNo + ", pickNo=" + pickNo + ", referenceNo=" + referenceNo + ", weight="
				+ weight + ", qty=" + qty + ", pendingWith=" + pendingWith + ", yardId=" + yardId + ", yardLocation="
				+ yardLocation + ", sealNo=" + sealNo + ", licencePlateNo=" + licencePlateNo + ", role=" + role
				+ ", ppKit=" + ppKit + ", securityComments=" + securityComments + ", securityLocation="
				+ securityLocation + ", fuCount=" + fuCount + ", yardIn=" + yardIn + ", yardOut=" + yardOut
				+ ", latitude=" + latitude + ", longitude=" + longitude + ", adhocType=" + adhocType + "]";
	}

}
