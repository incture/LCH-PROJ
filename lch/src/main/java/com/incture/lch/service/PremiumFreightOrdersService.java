package com.incture.lch.service;

import java.util.List;

import com.incture.lch.dto.CarrierDetailsDto;
import com.incture.lch.dto.ChargeDetailsPaginated;
import com.incture.lch.dto.ChargeRequestDto;
import com.incture.lch.dto.PaginationDto;
import com.incture.lch.dto.PaginationDto1;
import com.incture.lch.dto.PremiumFreightOrderDto;
import com.incture.lch.dto.PremiumRequestDto;
import com.incture.lch.dto.ResponseDto;
import com.incture.lch.entity.PremiumFreightChargeDetails;

public interface PremiumFreightOrdersService {
	public PaginationDto getAllPremiumFreightOrders(PremiumRequestDto premiumRequestDto);

	public List<CarrierDetailsDto> getAllCarrierDetails();

	public List<String> getMode(String bpNumber);

	public ChargeDetailsPaginated getAllCarrierOrders(PremiumRequestDto premiumRequestDto);

	public PaginationDto1 getAllPremiumFreightOrders1(PremiumRequestDto premiumRequestDto) ;
	public ChargeDetailsPaginated getAllManagerOrders(PremiumRequestDto premiumRequestDto) ;
	public List<PremiumFreightOrderDto> setCarrierDetails(List<ChargeRequestDto> chargeRequestDto);

	public String setCharge(List<ChargeRequestDto> dto);

	public ResponseDto forwardToApprover(List<PremiumRequestDto> premiumRequestDtos);

	public ResponseDto RejectPremiumOrder(List<String> adhocOrderIds);

	public ResponseDto addCarrier(CarrierDetailsDto carrierdto);

}
