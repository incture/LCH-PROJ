/**
 * 
 */
package com.incture.lch.service.implementation;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.incture.lch.dto.CarrierDetailsDto;
import com.incture.lch.dto.ChargeDetailsPaginated;
import com.incture.lch.dto.ChargeRequestDto;
import com.incture.lch.dto.PaginationDto;
import com.incture.lch.dto.PaginationDto1;
import com.incture.lch.dto.PremiumFreightOrderDto;
import com.incture.lch.dto.PremiumRequestDto;
import com.incture.lch.dto.ResponseDto;
import com.incture.lch.entity.PremiumFreightChargeDetails;
import com.incture.lch.repository.PremiumFreightOrdersRepository;
import com.incture.lch.service.PremiumFreightOrdersService;

/**
 * @author Urmil Sarit
 *
 */

@Service
@Transactional
public class PremiumFreightOrdersServiceImpl implements PremiumFreightOrdersService{

	@Autowired
	private PremiumFreightOrdersRepository premiumFreightOrdersRepo;
	
	
	@Override
	public PaginationDto getAllPremiumFreightOrders(PremiumRequestDto premiumRequestDto) {
		return premiumFreightOrdersRepo.getAllPremiumFreightOrders(premiumRequestDto);
	}

	@Override
	public List<CarrierDetailsDto> getAllCarrierDetails() {
		return premiumFreightOrdersRepo.getAllCarrierDetails();
	}

	@Override
	public List<String> getMode(String bpNumber) {
		return premiumFreightOrdersRepo.getMode(bpNumber);
	}

	@Override
	public PaginationDto1 getAllPremiumFreightOrders1(PremiumRequestDto premiumRequestDto) 
	{
		return premiumFreightOrdersRepo.getAllPremiumFreightOrders1(premiumRequestDto);
	}

	@Override
	public ChargeDetailsPaginated getAllManagerOrders(PremiumRequestDto premiumRequestDto)
	{
		return premiumFreightOrdersRepo.getAllManagerOrders(premiumRequestDto);
	}
	@Override
	public List<PremiumFreightOrderDto> setCarrierDetails(List<ChargeRequestDto> chargeRequestDto) {
		return premiumFreightOrdersRepo.setCarrierDetails(chargeRequestDto);
	}

	@Override
	public String setCharge(List<ChargeRequestDto> dto) {
		return premiumFreightOrdersRepo.setCharge(dto);
	}

	@Override
	public ResponseDto forwardToApprover(List<PremiumRequestDto> premiumRequestDtos) {
		return premiumFreightOrdersRepo.forwardToApprover(premiumRequestDtos);
	}

	@Override
	public ResponseDto RejectPremiumOrder (List<String> adhocOrderIds){
		return premiumFreightOrdersRepo.RejectPremiumOrder(adhocOrderIds);
	}
	
	@Override
	public ResponseDto addCarrier(CarrierDetailsDto carrierdto)
	{
		return premiumFreightOrdersRepo.addCarrier(carrierdto); 
	}

	@Override
	public ChargeDetailsPaginated getAllCarrierOrders(PremiumRequestDto premiumRequestDto) 
	{
		return  premiumFreightOrdersRepo.getAllCarrierOrders(premiumRequestDto);
	}

}
