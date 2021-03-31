package com.incture.lch.adhoc.workflow.dto;

import com.incture.lch.dto.AdhocOrderDto;

public class PremiumWorkflowApprovalTaskDto {
	
	
	private String originName;
	private String originAddress;
	private String originCity;
	private String originState;
	private String originZip;
	private String originCountry;
	private String destinationName;
	private String destinationAdress;
	private String destinationCity;
	private String destinationState;
	private String destinationZip;
	private String destinationCountry;
	private String bpNumber;
	private String carrierDetails;
	private String carrierScac;
	private String carrierRatePerKM;
	private String carrierMode;
	private String requestId;
	
	private String userName;

	private String userEmail;

	private String premiumReasonCode;


	private String adhocType;

	private String manager;

	private String planner;

	private String userGroup;
	
	private String adhocOrderId;

	private String businessDivision;

	private AdhocOrderDto adhocOrderInfo;
	//COST DETAILS
	
	private int charge;
	
	//STATUS
	private String reasonCode;
	private String status;
	
	private String plannerEmail;

	private String orderId;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getPremiumReasonCode() {
		return premiumReasonCode;
	}
	public void setPremiumReasonCode(String premiumReasonCode) {
		this.premiumReasonCode = premiumReasonCode;
	}
	public String getAdhocType() {
		return adhocType;
	}
	public void setAdhocType(String adhocType) {
		this.adhocType = adhocType;
	}
	public String getManager() {
		return manager;
	}
	public void setManager(String manager) {
		this.manager = manager;
	}
	public String getPlanner() {
		return planner;
	}
	public void setPlanner(String planner) {
		this.planner = planner;
	}
	public String getUserGroup() {
		return userGroup;
	}
	public void setUserGroup(String userGroup) {
		this.userGroup = userGroup;
	}
	public String getAdhocOrderId() {
		return adhocOrderId;
	}
	public void setAdhocOrderId(String adhocOrderId) {
		this.adhocOrderId = adhocOrderId;
	}
	public String getBusinessDivision() {
		return businessDivision;
	}
	public void setBusinessDivision(String businessDivision) {
		this.businessDivision = businessDivision;
	}
	public AdhocOrderDto getAdhocOrderInfo() {
		return adhocOrderInfo;
	}
	public void setAdhocOrderInfo(AdhocOrderDto adhocOrderInfo) {
		this.adhocOrderInfo = adhocOrderInfo;
	}

	
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getOriginName() {
		return originName;
	}
	public void setOriginName(String originName) {
		this.originName = originName;
	}
	public String getOriginAddress() {
		return originAddress;
	}
	public void setOriginAddress(String originAddress) {
		this.originAddress = originAddress;
	}
	public String getOriginCity() {
		return originCity;
	}
	public void setOriginCity(String originCity) {
		this.originCity = originCity;
	}
	public String getOriginState() {
		return originState;
	}
	public void setOriginState(String originState) {
		this.originState = originState;
	}
	public String getOriginZip() {
		return originZip;
	}
	public void setOriginZip(String originZip) {
		this.originZip = originZip;
	}
	public String getOriginCountry() {
		return originCountry;
	}
	public void setOriginCountry(String originCountry) {
		this.originCountry = originCountry;
	}
	public String getDestinationName() {
		return destinationName;
	}
	public void setDestinationName(String destinationName) {
		this.destinationName = destinationName;
	}
	public String getDestinationAdress() {
		return destinationAdress;
	}
	public void setDestinationAdress(String destinationAdress) {
		this.destinationAdress = destinationAdress;
	}
	public String getDestinationCity() {
		return destinationCity;
	}
	public void setDestinationCity(String destinationCity) {
		this.destinationCity = destinationCity;
	}
	public String getDestinationState() {
		return destinationState;
	}
	public void setDestinationState(String destinationState) {
		this.destinationState = destinationState;
	}
	public String getDestinationZip() {
		return destinationZip;
	}
	public void setDestinationZip(String destinationZip) {
		this.destinationZip = destinationZip;
	}
	public String getDestinationCountry() {
		return destinationCountry;
	}
	public void setDestinationCountry(String destinationCountry) {
		this.destinationCountry = destinationCountry;
	}
	public String getBpNumber() {
		return bpNumber;
	}
	public void setBpNumber(String bpNumber) {
		this.bpNumber = bpNumber;
	}
	public String getCarrierDetails() {
		return carrierDetails;
	}
	public void setCarrierDetails(String carrierDetails) {
		this.carrierDetails = carrierDetails;
	}
	public String getCarrierScac() {
		return carrierScac;
	}
	public void setCarrierScac(String carrierScac) {
		this.carrierScac = carrierScac;
	}
	public String getCarrierRatePerKM() {
		return carrierRatePerKM;
	}
	public void setCarrierRatePerKM(String carrierRatePerKM) {
		this.carrierRatePerKM = carrierRatePerKM;
	}
	public String getCarrierMode() {
		return carrierMode;
	}
	public void setCarrierMode(String carrierMode) {
		this.carrierMode = carrierMode;
	}
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	public int getCharge() {
		return charge;
	}
	public void setCharge(int charge) {
		this.charge = charge;
	}
	public String getReasonCode() {
		return reasonCode;
	}
	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPlannerEmail() {
		return plannerEmail;
	}
	public void setPlannerEmail(String plannerEmail) {
		this.plannerEmail = plannerEmail;
	}
	
	public String toString() {
		return "PremiumWorkflowApprovalTaskDto [premiumId="+ ", orderId=" + orderId + ", originName="
				+ originName + ", originAddress=" + originAddress + ", originCity=" + originCity + ", originState="
				+ originState + ", originZip=" + originZip + ", originCountry=" + originCountry + ", destinationName="
				+ destinationName + ", destinationAdress=" + destinationAdress + ", destinationCity=" + destinationCity
				+ ", destinationState=" + destinationState + ", destinationZip=" + destinationZip
				+ ", destinationCountry=" + destinationCountry + ", bpNumber=" + bpNumber + ", carrierDetails="
				+ carrierDetails + ", carrierScac=" + carrierScac + ", carrierRatePerKM=" + carrierRatePerKM
				+ ", carrierMode=" + carrierMode + ", requestId=" + requestId + ", charge=" + charge + ", reasonCode="
				+ reasonCode + ", status=" + status + ", plannerEmail=" + plannerEmail + "]";
	}

}
