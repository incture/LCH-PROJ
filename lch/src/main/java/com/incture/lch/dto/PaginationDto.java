package com.incture.lch.dto;

import java.util.List;

public class PaginationDto 
{
	
	//PagiantionDto
	private List<PremiumFreightOrderDto> premiumFreightOrderDtos;
	private int count;
	public List<PremiumFreightOrderDto> getPremiumFreightOrderDtos() {
		return premiumFreightOrderDtos;
	}
	public void setPremiumFreightOrderDtos(List<PremiumFreightOrderDto> premiumFreightOrderDtos) {
		this.premiumFreightOrderDtos = premiumFreightOrderDtos;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
	
	

}
