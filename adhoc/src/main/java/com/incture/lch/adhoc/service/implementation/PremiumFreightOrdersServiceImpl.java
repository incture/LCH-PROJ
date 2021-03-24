/**
 * 
 */
package com.incture.lch.adhoc.service.implementation;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.incture.lch.adhoc.dto.CarrierDetailsDto;
import com.incture.lch.adhoc.dto.ChargeRequestDto;
import com.incture.lch.adhoc.dto.PremiumFreightOrderDto;
import com.incture.lch.adhoc.dto.PremiumRequestDto;
import com.incture.lch.adhoc.entity.PremiumFreightChargeDetails;
import com.incture.lch.adhoc.repository.PremiumFreightOrdersRepository;
import com.incture.lch.adhoc.service.PremiumFreightOrdersService;

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
	public List<PremiumFreightOrderDto> getAllPremiumFreightOrders(PremiumRequestDto premiumRequestDto) {
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
	public List<PremiumFreightOrderDto> getChargeByCarrierAdmin(List<String> adhocOrderIds) {
		return premiumFreightOrdersRepo.getChargeByCarrierAdmin(adhocOrderIds);
	}

	@Override
	public String setCharge(ChargeRequestDto dto) {
		return premiumFreightOrdersRepo.setCharge(dto);
	}

	@Override
	public String forwardToApprover(List<PremiumFreightChargeDetails> premiumFreightChargeDetail) {
		return premiumFreightOrdersRepo.forwardToApprover(premiumFreightChargeDetail);
	}

	@Override
	public String RejectPremiumOrder(String adhocOrderId) {
		return premiumFreightOrdersRepo.RejectPremiumOrder(adhocOrderId);
	}

}