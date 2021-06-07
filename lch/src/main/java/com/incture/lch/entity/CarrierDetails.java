package com.incture.lch.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "T_CARRIER_DETAILS")
public class CarrierDetails
{
	
	
	//@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	@Column(name= "ID")
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy = "uuid")
	private String id;
	
	
	@Column(name= "BP_NUMBER")
	private String bpNumber;
	
	@Column(name = "CARRIER_SCAC")
	private String carrierScac;
	
	@Column(name= "CARRIER_DETAILS")
	private String carrierDetails;
	
	@Column(name= "CARRIER_MODE")
	private String carrierMode;

	/*@Column(name="CARRIER_MODE")
	private List<String> carrierMode;*/
	
	@Column(name="CARRIER_RATE_PER_KM")
	private String carrierRatePerKM;
	
	@Column(name="CARRIER_EMAIL")
	private String carrierEmail;
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCarrierRatePerKM() {
		return carrierRatePerKM;
	}

	public void setCarrierRatePerKM(String carrierRatePerKM) {
		this.carrierRatePerKM = carrierRatePerKM;
	}

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

	/*public List<String> getCarrierMode() {
		return carrierMode;
	}

	public void setCarrierMode(List<String> carrierMode) {
		this.carrierMode = carrierMode;
	}
*/
	public String getCarrierMode() {
		return carrierMode;
	}

	public void setCarrierMode(String carrierMode) {
		this.carrierMode = carrierMode;
	}

	public String getCarrierEmail() {
		return carrierEmail;
	}

	public void setCarrierEmail(String carrierEmail) {
		this.carrierEmail = carrierEmail;
	}
	
	
	
	

}
