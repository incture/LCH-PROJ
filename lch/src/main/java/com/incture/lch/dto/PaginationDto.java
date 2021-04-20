package com.incture.lch.dto;

import java.util.List;

import com.incture.lch.premium.custom.dto.PremiumManagerCustomDto;

public class PaginationDto {

	// PagiantionDto
	// private List<PremiumFreightOrderDto> premiumFreightOrderDtos;
	private List<PremiumManagerCustomDto> premiumFreightOrderDetailsList;
	private int count;

	/*
	 * public List<PremiumFreightOrderDto> getPremiumFreightOrderDtos() { return
	 * premiumFreightOrderDtos; } public void
	 * setPremiumFreightOrderDtos(List<PremiumFreightOrderDto>
	 * premiumFreightOrderDtos) { this.premiumFreightOrderDtos =
	 * premiumFreightOrderDtos; }
	 */
	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public List<PremiumManagerCustomDto> getPremiumFreightOrderDetailsList() {
		return premiumFreightOrderDetailsList;
	}

	public void setPremiumFreightOrderDetailsList(List<PremiumManagerCustomDto> premiumFreightOrderDetailsList) {
		this.premiumFreightOrderDetailsList = premiumFreightOrderDetailsList;
	}

}
