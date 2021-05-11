package com.incture.lch.adhoc.workflow.dto;

import com.incture.lch.dto.AdhocOrderDto;

public class PremiumWorkflowApprovalTaskDto {

	private String adhocOrderId;

	private String role;
	//Role here means the page we're on
	private String businessDivision;

	private String createdBy;

	private String createdDate;

	private String countryOfOrigin;

	private String currency;

	private String customerOrderNo;

	private String dimensionL;

	private String dimensionH;

	private String dimensionB;

	private String expectedDeliveryDate;

	private String glcode;

	private String hazmatNumber;

	private String projectNumber;

	private String quantity;

	private Boolean isInternational;
	private Boolean isTruck;

	private Boolean isHazmat;

	private String packageType;

	private String partNum;

	private String partDescription;

	private String PODataNumber;
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
	//private String requestId;

	private String userName;

	private String userEmail;

	private String premiumReasonCode;

	private String adhocType;

	private String manager;

	private String planner;

	private String userGroup;

	private String receivingContact;

	private String referenceNumber;

	private String shipDate;

	private String shipperName;

	private String shippingInstruction;

	private String shippingContact;

	private String terms;

	private String userId;

	private String uom;

	private String value;

	/* private BigDecimal weight; */

	private String weight;

	private String vinNumber;

	private String shipperNameFreeText;

	private String destinationNameFreeText;

	private String hazmatUn;

	private String weightUom;

	private String dimensionsUom;

	private String shipperNameDesc;

	private String destinationNameDesc;

	private String pendingWithApprover;
	
	private String pendingWithAccountant;

	public String getPendingWithAccountant() {
		return pendingWithAccountant;
	}

	public void setPendingWithAccountant(String pendingWithAccountant) {
		this.pendingWithAccountant = pendingWithAccountant;
	}

	private AdhocOrderDto adhocOrderInfo;
	// COST DETAILS

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String cratedBy) {
		this.createdBy = cratedBy;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getCountryOfOrigin() {
		return countryOfOrigin;
	}

	public void setCountryOfOrigin(String countryOfOrigin) {
		this.countryOfOrigin = countryOfOrigin;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getCustomerOrderNo() {
		return customerOrderNo;
	}

	public void setCustomerOrderNo(String customerOrderNo) {
		this.customerOrderNo = customerOrderNo;
	}

	public String getDimensionL() {
		return dimensionL;
	}

	public void setDimensionL(String dimensionL) {
		this.dimensionL = dimensionL;
	}

	public String getDimensionH() {
		return dimensionH;
	}

	public void setDimensionH(String dimensionH) {
		this.dimensionH = dimensionH;
	}

	public String getDimensionB() {
		return dimensionB;
	}

	public void setDimensionB(String dimensionB) {
		this.dimensionB = dimensionB;
	}

	public String getExpectedDeliveryDate() {
		return expectedDeliveryDate;
	}

	public void setExpectedDeliveryDate(String expectedDeliveryDate) {
		this.expectedDeliveryDate = expectedDeliveryDate;
	}

	public String getGlcode() {
		return glcode;
	}

	public void setGlcode(String glcode) {
		this.glcode = glcode;
	}

	public String getHazmatNumber() {
		return hazmatNumber;
	}

	public void setHazmatNumber(String hazmatNumber) {
		this.hazmatNumber = hazmatNumber;
	}

	public String getProjectNumber() {
		return projectNumber;
	}

	public void setProjectNumber(String projectNumber) {
		this.projectNumber = projectNumber;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public Boolean getIsInternational() {
		return isInternational;
	}

	public void setIsInternational(Boolean isInternational) {
		this.isInternational = isInternational;
	}

	public Boolean getIsTruck() {
		return isTruck;
	}

	public void setIsTruck(Boolean isTruck) {
		this.isTruck = isTruck;
	}

	public Boolean getIsHazmat() {
		return isHazmat;
	}

	public void setIsHazmat(Boolean isHazmat) {
		this.isHazmat = isHazmat;
	}

	public String getPackageType() {
		return packageType;
	}

	public void setPackageType(String packageType) {
		this.packageType = packageType;
	}

	public String getPartNum() {
		return partNum;
	}

	public void setPartNum(String partNum) {
		this.partNum = partNum;
	}

	public String getPartDescription() {
		return partDescription;
	}

	public void setPartDescription(String partDescription) {
		this.partDescription = partDescription;
	}

	public String getPODataNumber() {
		return PODataNumber;
	}

	public void setPODataNumber(String pODataNumber) {
		PODataNumber = pODataNumber;
	}

	public String getReceivingContact() {
		return receivingContact;
	}

	public void setReceivingContact(String receivingContact) {
		this.receivingContact = receivingContact;
	}

	public String getReferenceNumber() {
		return referenceNumber;
	}

	public void setReferenceNumber(String referenceNumber) {
		this.referenceNumber = referenceNumber;
	}

	public String getShipDate() {
		return shipDate;
	}

	public void setShipDate(String shipDate) {
		this.shipDate = shipDate;
	}

	public String getShipperName() {
		return shipperName;
	}

	public void setShipperName(String shipperName) {
		this.shipperName = shipperName;
	}

	public String getShippingInstruction() {
		return shippingInstruction;
	}

	public void setShippingInstruction(String shippingInstruction) {
		this.shippingInstruction = shippingInstruction;
	}

	public String getShippingContact() {
		return shippingContact;
	}

	public void setShippingContact(String shippingContact) {
		this.shippingContact = shippingContact;
	}

	public String getTerms() {
		return terms;
	}

	public void setTerms(String terms) {
		this.terms = terms;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUom() {
		return uom;
	}

	public void setUom(String uom) {
		this.uom = uom;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getVinNumber() {
		return vinNumber;
	}

	public void setVinNumber(String vinNumber) {
		this.vinNumber = vinNumber;
	}

	public String getShipperNameFreeText() {
		return shipperNameFreeText;
	}

	public void setShipperNameFreeText(String shipperNameFreeText) {
		this.shipperNameFreeText = shipperNameFreeText;
	}

	public String getDestinationNameFreeText() {
		return destinationNameFreeText;
	}

	public void setDestinationNameFreeText(String destinationNameFreeText) {
		this.destinationNameFreeText = destinationNameFreeText;
	}

	public String getHazmatUn() {
		return hazmatUn;
	}

	public void setHazmatUn(String hazmatUn) {
		this.hazmatUn = hazmatUn;
	}

	public String getWeightUom() {
		return weightUom;
	}

	public void setWeightUom(String weightUom) {
		this.weightUom = weightUom;
	}

	public String getDimensionsUom() {
		return dimensionsUom;
	}

	public void setDimensionsUom(String dimensionsUom) {
		this.dimensionsUom = dimensionsUom;
	}

	public String getShipperNameDesc() {
		return shipperNameDesc;
	}

	public void setShipperNameDesc(String shipperNameDesc) {
		this.shipperNameDesc = shipperNameDesc;
	}

	public String getDestinationNameDesc() {
		return destinationNameDesc;
	}

	public void setDestinationNameDesc(String destinationNameDesc) {
		this.destinationNameDesc = destinationNameDesc;
	}

	private int charge;

	// STATUS
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

	/*public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	*/public int getCharge() {
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
		return "PremiumWorkflowApprovalTaskDto [premiumId=" + ", orderId=" + orderId + ", originName=" + originName
				+ ", originAddress=" + originAddress + ", originCity=" + originCity + ", originState=" + originState
				+ ", originZip=" + originZip + ", originCountry=" + originCountry + ", destinationName="
				+ destinationName + ", destinationAdress=" + destinationAdress + ", destinationCity=" + destinationCity
				+ ", destinationState=" + destinationState + ", destinationZip=" + destinationZip
				+ ", destinationCountry=" + destinationCountry + ", bpNumber=" + bpNumber + ", carrierDetails="
				+ carrierDetails + ", carrierScac=" + carrierScac + ", carrierRatePerKM=" + carrierRatePerKM
				+ ", carrierMode=" + carrierMode +  ", charge=" + charge + ", reasonCode="
				+ reasonCode + ", status=" + status + ", plannerEmail=" + plannerEmail + "]";
	}

	
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getPendingWithApprover() {
		return pendingWithApprover;
	}

	public void setPendingWithApprover(String pendingWithApprover) {
		this.pendingWithApprover = pendingWithApprover;
	}

	
}
