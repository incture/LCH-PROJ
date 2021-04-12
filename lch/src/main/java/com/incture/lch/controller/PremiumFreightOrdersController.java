package com.incture.lch.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.incture.lch.adhoc.custom.dto.ResponseMessage;
import com.incture.lch.dao.PremiumFreightApprovalRuleDao;
import com.incture.lch.dto.ApprovalDto;
import com.incture.lch.dto.CarrierDetailsDto;
import com.incture.lch.dto.ChargeDetailsPaginated;
import com.incture.lch.dto.ChargeRequestDto;
import com.incture.lch.dto.PaginationDto;
import com.incture.lch.dto.PaginationDto1;
import com.incture.lch.dto.PremiumFreightApprovalRuleDTO;
import com.incture.lch.dto.PremiumFreightOrderDto;
import com.incture.lch.dto.PremiumRequestDto;
import com.incture.lch.dto.ResponseDto;
import com.incture.lch.dto.TaskDetailsDto;
import com.incture.lch.entity.PremiumFreightChargeDetails;
import com.incture.lch.premium.custom.dto.PremiumManagerCustomDto;
import com.incture.lch.premium.custom.dto.PremiumRequestUserInfoCustomDto;
import com.incture.lch.premium.custom.dto.WorkflowPremiumCustomDto;
import com.incture.lch.premium.workflow.service.PremiumWorkflowInvokerLocal;
import com.incture.lch.service.PremiumFreightOrdersService;

@RestController
@CrossOrigin
@RequestMapping(value = "/premiumOrders", produces = "application/json")
public class PremiumFreightOrdersController 
{
	@Autowired
	private PremiumFreightOrdersService premiumFreightOrdersService;
	
	@Autowired
	private PremiumWorkflowInvokerLocal wflocal;
	@Autowired
	private PremiumFreightApprovalRuleDao premiumFreightApprovalRuleDao;

	
	@RequestMapping(value = "/getAllPremiumOrders", method = RequestMethod.POST,consumes = { "application/json" })
	@ResponseBody
	public PaginationDto getAllPremiumFreightOrders(@RequestBody PremiumRequestDto premiumRequestDto) {
			return premiumFreightOrdersService.getAllPremiumFreightOrders(premiumRequestDto);
	}
	
	@RequestMapping(value = "/getAllPremiumOrders1", method = RequestMethod.POST,consumes = { "application/json" })
	@ResponseBody
	public PaginationDto1 getAllPremiumFreightOrders1(@RequestBody PremiumRequestDto premiumRequestDto) {
			return premiumFreightOrdersService.getAllPremiumFreightOrders1(premiumRequestDto);
	}

	
	@RequestMapping(value = "/getAllCarrierDetails", method = RequestMethod.POST, consumes = { "application/json" })
	@ResponseBody
	public List<CarrierDetailsDto> getAllCarrierDetails() {
		return premiumFreightOrdersService.getAllCarrierDetails();
	}

	@RequestMapping(value = "/getAllCarrierOrders", method = RequestMethod.POST, consumes = { "application/json" })
	@ResponseBody
	public ChargeDetailsPaginated getAllCarrierOrders(@RequestBody PremiumRequestDto premiumRequestDto) {
		return premiumFreightOrdersService.getAllCarrierOrders(premiumRequestDto);
	}
	
	@RequestMapping(value = "/getAllManagerOrders", method = RequestMethod.POST, consumes = { "application/json" })
	@ResponseBody
	public ChargeDetailsPaginated getAllManagerOrders(@RequestBody PremiumRequestDto premiumRequestDto)
	{
		return premiumFreightOrdersService.getAllManagerOrders(premiumRequestDto);
	}

	@RequestMapping(value = "/getMode", method = RequestMethod.POST, consumes = { "application/json" })
	@ResponseBody
	public List<HashMap<String,String>>getMode(@RequestBody  JSONObject bpNumber)
	{
		
		String bpNo = (String) bpNumber.get("bpNumber");
		List<String> mode = premiumFreightOrdersService.getMode(bpNo);
	
		List<HashMap<String,String>> listMode= new ArrayList<HashMap<String,String>>();
		
		for(String s:mode)
		{
			HashMap<String,String> modes= new HashMap<String,String>();
			
			modes.put("mode",s);
			
			listMode.add(modes);
		}
		return listMode;
	}

	

	@RequestMapping(value = "/setCarrierDetails", method = RequestMethod.POST, consumes = { "application/json" })
	@ResponseBody
	public List<PremiumFreightOrderDto> setCarrierDetails(@RequestBody List<ChargeRequestDto> chargeRequestDto) {
		
		return premiumFreightOrdersService.setCarrierDetails(chargeRequestDto);
	}
	
	@RequestMapping(value = "/setCharge", method = RequestMethod.POST, consumes = { "application/json" })
	@ResponseBody
	public String setCharge(@RequestBody List<ChargeRequestDto> dto) {
		return premiumFreightOrdersService.setCharge(dto);
	}

	
	@RequestMapping(value = "/forwardToApprover", method = RequestMethod.POST, consumes = { "application/json" })
	@ResponseBody
	public ResponseDto forwardToApprover(@RequestBody PremiumRequestUserInfoCustomDto premiumRequestCustomDtos) {
		
		return premiumFreightOrdersService.forwardToApprover(premiumRequestCustomDtos);
	}


	@RequestMapping(value = "/rejectPremiumOrder", method = RequestMethod.POST, consumes = { "application/json" })
	@ResponseBody
	public ResponseDto RejectPremiumOrder(@RequestBody List<String>  adhocOrderIds) {
		//String adid= (String) adhocOrderId.get("adhocOrderId");
		return premiumFreightOrdersService.RejectPremiumOrder(adhocOrderIds);

		
	}

	@RequestMapping(value = "/getAllPremiumApprovalList", method = RequestMethod.POST, consumes = { "application/json" })
	@ResponseBody
	public List<PremiumFreightApprovalRuleDTO> getAllPremiumApprovalList() 
	{
	return premiumFreightApprovalRuleDao.getAllPremiumApprovalList();
	}
	
	@RequestMapping(value = "/saveApproval", method = RequestMethod.POST, consumes = { "application/json" })
	@ResponseBody	
	public ResponseDto saveApproval(List<PremiumFreightApprovalRuleDTO> ruleList) 
	{
		return premiumFreightApprovalRuleDao.saveApproval(ruleList);
	}

	@RequestMapping(value="/addCarrier", method = RequestMethod.POST, consumes={"application/json"})
	public ResponseDto addCarrier(@RequestBody CarrierDetailsDto carrierdto)
	{
		return premiumFreightOrdersService.addCarrier(carrierdto);
	}

	
	@GetMapping("/getManagerOrders/{userId}")
	public List<PremiumManagerCustomDto> getManagerOrders(@PathVariable String userId) throws ClientProtocolException, IOException, JSONException
	{
		return premiumFreightOrdersService.getManagerOrders(userId);
	}

	@GetMapping("/getallTaskInstancesId/{userId}")
	public List<TaskDetailsDto> getAllWorkflowTaskInstanceId(@PathVariable String userId)
			throws ClientProtocolException, IOException, JSONException
			{
		return wflocal.getAllWorkflowTaskInstanceId(userId);
			}
	
	
	@PostMapping("/completeManagerTask")
	public HttpResponse completeManagerTask(@RequestBody ApprovalDto dto)
			throws ClientProtocolException, IOException, JSONException
	{
		return wflocal.completeManagerTask(dto);
	}
	
	@PostMapping("/completeAccountantTask")
	public HttpResponse completeAccountantTask(@RequestBody ApprovalDto dto)
			throws ClientProtocolException, IOException, JSONException
	{
		return wflocal.completeAccountantTask(dto);
	}

	@PostMapping("/updateTableDetails")
	public ResponseMessage updateTableDetails(@RequestBody WorkflowPremiumCustomDto dto)
			throws ClientProtocolException, JSONException, IOException {
		return premiumFreightOrdersService.updateTableDetails(dto);
	}
}
