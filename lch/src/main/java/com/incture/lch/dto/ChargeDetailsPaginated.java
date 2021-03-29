package com.incture.lch.dto;

import java.util.List;

import com.incture.lch.entity.PremiumFreightChargeDetails;

public class ChargeDetailsPaginated 
{
	List<PremiumFreightChargeDetails> premiumFreightChargeDetails;
	int count=0;
	public List<PremiumFreightChargeDetails> getPremiumFreightChargeDetails() {
		return premiumFreightChargeDetails;
	}
	public void setPremiumFreightChargeDetails(List<PremiumFreightChargeDetails> premiumFreightChargeDetails) {
		this.premiumFreightChargeDetails = premiumFreightChargeDetails;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
	

}
