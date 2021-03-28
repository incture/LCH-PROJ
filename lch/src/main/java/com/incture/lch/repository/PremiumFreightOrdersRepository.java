package com.incture.lch.repository;

import java.util.List;

import com.incture.lch.dto.CarrierDetailsDto;
import com.incture.lch.dto.ChargeRequestDto;
import com.incture.lch.dto.PaginationDto;
import com.incture.lch.dto.PremiumFreightOrderDto;
import com.incture.lch.dto.PremiumRequestDto;
import com.incture.lch.dto.ResponseDto;
import com.incture.lch.entity.AdhocOrders;
import com.incture.lch.entity.PremiumFreightChargeDetails;

public interface PremiumFreightOrdersRepository 
{

	public PremiumFreightOrderDto exportPremiumFreightOrders(AdhocOrders adhocOrders);
		
	public PaginationDto getAllPremiumFreightOrders(PremiumRequestDto premiumRequestDto);

	public List<CarrierDetailsDto> getAllCarrierDetails();
	
	public List<String> getMode(String bpNumber);

	public List<PremiumFreightOrderDto> setCarrierDetails(List<ChargeRequestDto> chargeRequestDto);
	
	public String setCharge(List<ChargeRequestDto> dto);

	public ResponseDto forwardToApprover(List<PremiumRequestDto> premiumRequestDtos);
	
	public ResponseDto RejectPremiumOrder (List<String> adhocOrderIds);
	
	public ResponseDto addCarrier(CarrierDetailsDto carrierdto);

}
