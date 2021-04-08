package com.incture.lch.dto;

import javax.persistence.Column;
import javax.persistence.Id;

public class PremiumFreightChargeDetailsDto {

	private String orderId;

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

	private int charge;

	private String reasonCode;
	private String status;
	private String plannerEmail;
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
	
	

}
