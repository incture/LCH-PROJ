package com.incture.lch.dto;

import java.util.List;

public class PaginationDto1
{
	
	//PagiantionDto
	private List<PremiumFreightDto1> premiumFreightDto1;
	private int count;
	
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public List<PremiumFreightDto1> getPremiumFreightDto1() {
		return premiumFreightDto1;
	}
	public void setPremiumFreightDto1(List<PremiumFreightDto1> premiumFreightDto1) {
		this.premiumFreightDto1 = premiumFreightDto1;
	}
	
	
	

}
