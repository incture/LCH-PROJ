/**
 * 
 */
package com.incture.lch.service.implementation;

import java.io.IOException;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.incture.lch.adhoc.custom.dto.ResponseMessage;
import com.incture.lch.dto.CarrierDetailsDto;
import com.incture.lch.dto.ChargeRequestDto;
import com.incture.lch.dto.PaginationDto;
import com.incture.lch.dto.PremiumFreightOrderDto;
import com.incture.lch.dto.PremiumOrderAccountingDetailsDto;
import com.incture.lch.dto.PremiumRequestDto;
import com.incture.lch.dto.RejectAtPlannerDto;
import com.incture.lch.dto.ResponseDto;
import com.incture.lch.premium.custom.dto.PremiumCustomDto;
import com.incture.lch.premium.custom.dto.PremiumRequestUserInfoCustomDto;
import com.incture.lch.premium.custom.dto.WorkflowPremiumCustomDto;
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
	public PaginationDto getAllPremiumFreightOrders(PremiumRequestDto premiumRequestDto) throws ClientProtocolException, IOException, JSONException {
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
/*
	@Override
	public PaginationDto1 getPlannerOrders(PremiumRequestDto premiumRequestDto) 
	{
		return premiumFreightOrdersRepo.getPlannerOrders(premiumRequestDto);
	}

	@Override
	public ChargeDetailsPaginated getAllManagerOrders(PremiumRequestDto premiumRequestDto)
	{
		return premiumFreightOrdersRepo.getAllManagerOrders(premiumRequestDto);
	}*/
	@Override
	public List<PremiumFreightOrderDto> setCarrierDetails(List<ChargeRequestDto> chargeRequestDto) {
		return premiumFreightOrdersRepo.setCarrierDetails(chargeRequestDto);
	}

	@Override
	public String setCharge(List<ChargeRequestDto> dto) {
		return premiumFreightOrdersRepo.setCharge(dto);
	}

	@Override
	public ResponseDto forwardToApprover(PremiumRequestUserInfoCustomDto  premiumRequestCustomDtos) {
		return premiumFreightOrdersRepo.forwardToApprover(premiumRequestCustomDtos);
	}

	
	@Override
	public ResponseDto RejectPremiumOrder (RejectAtPlannerDto dto){
		return premiumFreightOrdersRepo.RejectPremiumOrder(dto);
	}
	
	@Override
	public ResponseDto addCarrier(CarrierDetailsDto carrierdto)
	{
		return premiumFreightOrdersRepo.addCarrier(carrierdto); 
	}
/*
	@Override
	public ChargeDetailsPaginated getAllCarrierOrders(PremiumRequestDto premiumRequestDto) 
	{
		return  premiumFreightOrdersRepo.getAllCarrierOrders(premiumRequestDto);
	}

	@Override
	public List<PremiumCustomDto> getManagerOrders(PremiumRequestDto premiumRequestDto)
			throws ClientProtocolException, IOException, JSONException {
		return premiumFreightOrdersRepo.getManagerOrders(premiumRequestDto);
	}
*/
	@Override
	public ResponseMessage updateTableDetails(WorkflowPremiumCustomDto dto)
			throws ClientProtocolException, JSONException, IOException {
		return premiumFreightOrdersRepo.updateTableDetails(dto);
	}
	
	
	@Override
	public PremiumOrderAccountingDetailsDto getPremiumAccountingDetails(String workflowInstanceId)
	{
		return premiumFreightOrdersRepo.getPremiumAccountingDetails(workflowInstanceId);
	}

	
	@Override
	public ResponseDto updatePremiumAccountingDetails(@RequestBody PremiumOrderAccountingDetailsDto AccountingDetailsDto){
		return premiumFreightOrdersRepo.updatePremiumAccountingDetails(AccountingDetailsDto);
	}

	@Override
	public List<PremiumCustomDto> getAllAccountantOrders(PremiumRequestDto premiumRequestDto) throws ClientProtocolException, IOException, JSONException {
		
		return premiumFreightOrdersRepo.getAllAccountantOrders(premiumRequestDto);
	}
	

}
