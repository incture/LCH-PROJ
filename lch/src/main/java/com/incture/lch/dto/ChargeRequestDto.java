package com.incture.lch.dto;

public class ChargeRequestDto
{
	//private Long id;
	
	
	private String adhocOrderId;
	
	
	private String bpNumber;
	
	
	private String carrierScac;
	
	
	private String carrierDetails;
	
	
	private String carrierMode;
	
	private int charge;
	
	public String getAdhocOrderId() {
		return adhocOrderId;
	}

	public void setAdhocOrderId(String adhocOrderId) {
		this.adhocOrderId = adhocOrderId;
	}

	/*public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}*/

	public String getBpNumber() {
		return bpNumber;
	}

	public void setBpNumber(String bpNumber) {
		this.bpNumber = bpNumber;
	}

	public String getCarrierScac() {
		return carrierScac;
	}

	public void setCarrierScac(String carrierScac) {
		this.carrierScac = carrierScac;
	}

	public String getCarrierDetails() {
		return carrierDetails;
	}

	public void setCarrierDetails(String carrierDetails) {
		this.carrierDetails = carrierDetails;
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

	

	
	
	
	

}
