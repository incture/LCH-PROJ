package com.incture.lch.premium.workflow.service;

import java.io.IOException;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.incture.lch.dto.ApprovalDto;
import com.incture.lch.dto.TaskDetailsDto;

public interface PremiumWorkflowInvokerLocal {
	public JSONObject triggerPremiumWorkflow(String input) throws ClientProtocolException, IOException, JSONException;

	HttpResponse approveTask(String input, String taskInstanceId)
			throws ClientProtocolException, IOException, JSONException;

	JSONArray getWorkflowTaskInstanceId(String workflowInstanceId)
			throws ClientProtocolException, IOException, JSONException;

	JSONObject getWorkflowApprovalTaskInstanceId(String workflowInstanceId)
			throws ClientProtocolException, IOException, JSONException;

	Boolean validateString(String input);

	List<TaskDetailsDto> getAllWorkflowTaskInstanceId(String userId) throws ClientProtocolException, IOException, JSONException;

	public HttpResponse completeManagerTask(ApprovalDto dto) throws ClientProtocolException, IOException, JSONException;

	public HttpResponse completeAccountantTask(ApprovalDto dto) throws ClientProtocolException, IOException, JSONException;

	}




