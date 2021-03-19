package com.incture.lch.service;

import java.util.List;

import com.incture.lch.dto.CarrierDetailsDto;
import com.incture.lch.dto.ChargeRequestDto;
import com.incture.lch.dto.PremiumFreightOrderDto;
import com.incture.lch.dto.PremiumRequestDto;
import com.incture.lch.dto.ResponseDto;
import com.incture.lch.entity.PremiumFreightChargeDetails;

public interface PremiumFreightOrdersService 
{
	public List<PremiumFreightOrderDto> getAllPremiumFreightOrders(PremiumRequestDto premiumRequestDto);

	public List<CarrierDetailsDto> getAllCarrierDetails();
	
	public List<String> getMode(String bpNumber);

	public List<PremiumFreightOrderDto> setCarrierDetails(List<ChargeRequestDto> chargeRequestDto);
	
	public String setCharge(ChargeRequestDto dto);

	public ResponseDto forwardToApprover(List<PremiumRequestDto> premiumRequestDtos);
	
	public ResponseDto RejectPremiumOrder (String adhocOrderId);
	public ResponseDto addCarrier(CarrierDetailsDto carrierdto);


}
