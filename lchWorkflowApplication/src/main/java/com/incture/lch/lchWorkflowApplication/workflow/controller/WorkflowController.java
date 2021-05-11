package com.incture.lch.lchWorkflowApplication.workflow.controller;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.incture.lch.lchWorkflowApplication.workflow.dto.AdhocWorkflowCustomDto;
import com.incture.lch.lchWorkflowApplication.workflow.dto.ResponseMessage;
import com.incture.lch.lchWorkflowApplication.workflow.dto.WorkflowCustomDto;
import com.incture.lch.lchWorkflowApplication.workflow.service.AdhocWorkflowService;

@RestController
@CrossOrigin
@RequestMapping("/adhocorders")
public class WorkflowController {
	private final Logger MYLOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private AdhocWorkflowService adhocWorkflowService;

	@PostMapping("/updateApprovalWorkflowDetails")
	public ResponseMessage updateApprovalWorkflowDetails(@RequestBody WorkflowCustomDto obj)
			throws ClientProtocolException, IOException, JSONException {
		MYLOGGER.error("ENTERING INTO updateApprovalWorkflowDetails CONTROLLER");
		return adhocWorkflowService.updateApprovalWorflowDetails(obj);
	}
	
	@PostMapping("/updateApprovalWorkflowDetails1")
	public ResponseMessage updateApprovalWorkflowDetails1(@RequestBody WorkflowCustomDto obj)
			throws ClientProtocolException, IOException, JSONException {
		MYLOGGER.error("ENTERING INTO updateApprovalWorkflowDetails CONTROLLER");
		return adhocWorkflowService.updateApprovalWorflowDetails1(obj);
	}

	@PostMapping("/updateWorflowDetailsForType4")
	public ResponseMessage updateWorflowDetailsForType4(@RequestBody AdhocWorkflowCustomDto obj)
			throws ClientProtocolException, IOException, JSONException {
		MYLOGGER.error("ENTERING INTO updateApprovalWorkflowDetails CONTROLLER");
		return adhocWorkflowService.updateApprovalWorflowDetailsForType4(obj);
	}


	@GetMapping("/forTesting")
	public ResponseMessage forTesting() throws ClientProtocolException, IOException, JSONException {
		MYLOGGER.error("ENTERING INTO forTesting CONTROLLER");
		ResponseMessage response = new ResponseMessage("welcome ravi");
		return response;
	}
}
