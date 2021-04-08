package com.incture.lch.lchWorkflowApplication.workflow.controller;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.incture.lch.lchWorkflowApplication.workflow.dto.PremiumWorkflowCustomDto;
import com.incture.lch.lchWorkflowApplication.workflow.service.PremiumWorkflowService;

@RestController
@CrossOrigin
@RequestMapping("/premium")
public class PremiumWorkflowController 
{
	private final Logger MYLOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private PremiumWorkflowService premiumWorkflowService;
	
	@PostMapping("/approveTask")
	public HttpResponse approveTask(@RequestBody PremiumWorkflowCustomDto dto)
			throws ClientProtocolException, IOException, JSONException {
		MYLOGGER.error("ENTERING INTO updateApprovalWorkflowDetails CONTROLLER");
		return premiumWorkflowService.approveTask(dto);
	}
	
	@PostMapping("/rejectTask")
	public HttpResponse rejectTask(@RequestBody PremiumWorkflowCustomDto dto)
			throws ClientProtocolException, IOException, JSONException {
		MYLOGGER.error("ENTERING INTO updateApprovalWorkflowDetails CONTROLLER");
		return premiumWorkflowService.rejectTask(dto);
	}
	
	
	/*@PostMapping("/updatePremiumTableData")
	public HttpResponse updatePremiumTableData(@RequestBody PremiumWorkflowCustomDto dto)
			throws ClientProtocolException, IOException, JSONException {
		MYLOGGER.error("ENTERING INTO updateApprovalWorkflowDetails CONTROLLER");
		return premiumWorkflowService.updatePremiumTableData(dto);
	}*/
	
	@GetMapping("/getAllTaskInstanceId/{userId}")
	public JSONArray getAllTaskInstanceId(@PathVariable String userId) throws ClientProtocolException, IOException, JSONException
	{
		return premiumWorkflowService.getAllTaskInstanceId(userId);
	}

}
