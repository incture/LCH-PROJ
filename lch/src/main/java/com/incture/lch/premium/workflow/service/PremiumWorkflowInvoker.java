package com.incture.lch.premium.workflow.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.incture.lch.adhoc.workflow.constant.AuthorizationConstants;
import com.incture.lch.adhoc.workflow.constant.WorkflowConstants;
import com.incture.lch.dto.ApprovalDto;
import com.incture.lch.dto.PremiumOrderAccountingDetailsDto;
import com.incture.lch.dto.TaskDetailsDto;
import com.incture.lch.util.ServiceUtil;

@Transactional
@Service
public class PremiumWorkflowInvoker implements PremiumWorkflowInvokerLocal {

	private final Logger MYLOGGER = LoggerFactory.getLogger(this.getClass());

    

   /*private static String workflow_rest_url = "https://api.workflow-sap.cfapps.eu10.hana.ondemand.com/workflow-service/rest";
	private static String url = "https://hrapps.authentication.eu10.hana.ondemand.com";
	private static String clientid = "sb-clone-100d9392-d07e-4ed1-be50-9c2b4ea8a187!b19391|workflow!b10150";
	private static String clientsecret = "3569cf5b-aa9e-4a6d-8e16-3e36cb51b6d7$o94E7npqq7EFVQDXb5tLGF1oLR4t_ir4v4no13_Vwcg=";
*/
	private String workflow_rest_url;
	private String url;
	private String clientid;
	private String clientsecret;
	
   public PremiumWorkflowInvoker() {
 
		try {
			JSONObject jsonObj = new JSONObject(System.getenv("VCAP_SERVICES"));
			System.err.println("[PremiumWorkflowInvoker:VCAP_SERVICES] : " + jsonObj.toString());

			JSONArray jsonArr = jsonObj.getJSONArray("workflow");
			JSONObject credentials = jsonArr.getJSONObject(0).getJSONObject("credentials");
			JSONObject endpoints = credentials.getJSONObject("endpoints");

			// endpoint url
			workflow_rest_url = endpoints.getString("workflow_rest_url");

			// client credentials

			JSONObject uaa = credentials.getJSONObject("uaa");

			url = uaa.getString("url");
			clientid = uaa.getString("clientid");
			clientsecret = uaa.getString("clientsecret");

			System.err.println("[PremiumWorkflowInvoker] : " + jsonArr.toString());

		} catch (JSONException e) {
			MYLOGGER.error("[PremiumWorkflowInvoker] reading environmental variables failed:" + e.getMessage());
		}
	}

	@Override
	public JSONObject triggerPremiumWorkflow(String input) throws ClientProtocolException, IOException, JSONException {

		MYLOGGER.error("Ravi: Input" + input);

		System.err.println("Ravi: Input" + input);
		HttpResponse httpResponse = null;
		String jsonString = null;
		JSONObject responseObj = null;

		HttpRequestBase httpRequestBase = null;
		StringEntity data = null;
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();

		String bearerToken = getBearerToken(httpClient);

		MYLOGGER.error("Ravi :Token" + bearerToken);

		httpRequestBase = new HttpPost(workflow_rest_url + WorkflowConstants.WORKFLOW_TRIGGER_URL_PREMIUM);

		data = new StringEntity(input);
		data.setContentType(WorkflowConstants.CONTENT_TYPE);

		((HttpPost) httpRequestBase).setEntity(data);
		httpRequestBase.addHeader(WorkflowConstants.ACCEPT, WorkflowConstants.CONTENT_TYPE);
		httpRequestBase.addHeader(WorkflowConstants.AUTHORIZATION, AuthorizationConstants.BEARER + " " + bearerToken);

		httpResponse = httpClient.execute(httpRequestBase);

		jsonString = EntityUtils.toString(httpResponse.getEntity());

		if (httpResponse.getStatusLine().getStatusCode() == 400) {
			MYLOGGER.error("PremiumWorkflowInvoker | triggerPremiumWorkflow | Error :" + input);
		}

		responseObj = new JSONObject(jsonString);

		MYLOGGER.error("Poli :res" + responseObj.toString());

		httpClient.close();

		return responseObj;

	}

	@Override
	public HttpResponse approveTask(String input, String taskInstanceId)
			throws ClientProtocolException, IOException, JSONException {
		MYLOGGER.error("ENTERING INTO approveTask INVOKER METHOD");
		HttpResponse httpResponse = null;

		HttpRequestBase httpRequestBase = null;
		StringEntity data = null;
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		try {
			String bearerToken = getBearerToken(httpClient);
			MYLOGGER.error("ENTERING INTO approveTask INVOKER METHOD bearerToken:: " + bearerToken);
			httpRequestBase = new HttpPatch(workflow_rest_url + WorkflowConstants.APPROVE_TASK_URL + taskInstanceId);
			MYLOGGER.error("ENTERING INTO approveTask INVOKER METHOD httpRequestBase:: " + httpRequestBase);
			JSONObject context = new JSONObject();
			context.put("status2", "Completed2");
			context.put(WorkflowConstants.CONTEXT, context);
			input = context.toString();
			data = new StringEntity(input, "UTF-8");
			data.setContentType(WorkflowConstants.CONTENT_TYPE);
			MYLOGGER.error("ENTERING INTO approveTask INVOKER METHOD input:: " + input);
			((HttpPatch) httpRequestBase).setEntity(data);

			httpRequestBase.addHeader(WorkflowConstants.ACCEPT, WorkflowConstants.CONTENT_TYPE);
			httpRequestBase.addHeader(WorkflowConstants.AUTHORIZATION,
					AuthorizationConstants.BEARER + " " + bearerToken);
			MYLOGGER.error("ENTERING INTO approveTask INVOKER METHOD BEFORE EXECUTE:: " + input);
			httpResponse = httpClient.execute(httpRequestBase);
			MYLOGGER.error("ENTERING INTO approveTask INVOKER METHOD AFTER EXECUTE:: " + input);

			if (httpResponse.getStatusLine().getStatusCode() == 400) {
				MYLOGGER.error("PremiumWorkflowInvoker | approveTask | Error :" + input);
			}
		} catch (Exception e) {
			MYLOGGER.error("PremiumWorkflowInvoker | approveTask | Exception :" + e.toString());
		} finally {
			httpClient.close();
		}

		MYLOGGER.error("PremiumWorkflowInvoker | approveTask | httpResponse :" + httpResponse.toString());
		return httpResponse;
	}

	@Override
	public JSONArray getWorkflowTaskInstanceId(String workflowInstanceId)
			throws ClientProtocolException, IOException, JSONException {

		HttpResponse httpResponse = null;
		String jsonString = null;
		JSONArray responseObj = null;

		HttpRequestBase httpRequestBase = null;
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();

		String bearerToken = getBearerToken(httpClient);

		httpRequestBase = new HttpGet(
				workflow_rest_url + WorkflowConstants.GET_TASK_INSTANCE_ID_URL + workflowInstanceId);

		httpRequestBase.addHeader(WorkflowConstants.ACCEPT, WorkflowConstants.CONTENT_TYPE);
		httpRequestBase.addHeader(WorkflowConstants.AUTHORIZATION, AuthorizationConstants.BEARER + " " + bearerToken);

		httpResponse = httpClient.execute(httpRequestBase);

		jsonString = EntityUtils.toString(httpResponse.getEntity());

		responseObj = new JSONArray(jsonString);

		httpClient.close();

		return responseObj;
	}

	public JSONObject getWorkflowApprovalTaskInstanceId(String workflowInstanceId)
			throws ClientProtocolException, IOException, JSONException {

		HttpResponse httpResponse = null;
		String jsonString = null;
		JSONObject responseObj = null;

		HttpRequestBase httpRequestBase = null;
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();

		String bearerToken = getBearerToken(httpClient);

		httpRequestBase = new HttpGet(workflow_rest_url + WorkflowConstants.APPROVE_TASK_URL + workflowInstanceId);

		httpRequestBase.addHeader(WorkflowConstants.ACCEPT, WorkflowConstants.CONTENT_TYPE);
		httpRequestBase.addHeader(WorkflowConstants.AUTHORIZATION, AuthorizationConstants.BEARER + " " + bearerToken);

		httpResponse = httpClient.execute(httpRequestBase);

		jsonString = EntityUtils.toString(httpResponse.getEntity());

		responseObj = new JSONObject(jsonString);

		httpClient.close();

		return responseObj;
	}

	@Override
	public Boolean validateString(String input) {

		Boolean flag = Boolean.TRUE;

		if (input == null || input.trim().isEmpty()) {

			flag = Boolean.FALSE;
		}

		return flag;

	}

	private String getBearerToken(CloseableHttpClient httpClient)
			throws ClientProtocolException, IOException, JSONException {
		MYLOGGER.info("LCH | PremiumWorkflowInvoker | getBearerToken | Execution Start ");
		HttpRequestBase httpRequestBase = new HttpPost(url + "/oauth/token?grant_type=client_credentials");
		ServiceUtil.getBasicAuthWorkflow(clientid, clientsecret);
		httpRequestBase.addHeader(WorkflowConstants.AUTHORIZATION, ServiceUtil.getBasicAuth(clientid, clientsecret));
		try {
			HttpResponse httpResponse = httpClient.execute(httpRequestBase);
			ServiceUtil.getBasicAuthWorkflow(clientid, clientsecret);
			String jsonString = EntityUtils.toString(httpResponse.getEntity());
			JSONObject responseObj = new JSONObject(jsonString);
			String token = responseObj.getString("access_token");
			MYLOGGER.info("LCH | PremiumWorkflowInvoker | getBearerToken | Execution End ");
			return token;
		} catch (Exception e) {
			MYLOGGER.info("LCH | PremiumWorkflowInvoker | getBearerToken | Exception " + e.toString());
		}

		return null;

	}

	@Override
	public List<TaskDetailsDto> getAllWorkflowTaskInstanceId(String userId)
			throws ClientProtocolException, IOException, JSONException {

		HttpResponse httpResponse = null;
		String jsonString = null;
		// JSONArray responseObj = null;

		HttpRequestBase httpRequestBase = null;
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();

		String bearerToken = getBearerToken(httpClient);

		httpRequestBase = new HttpGet(workflow_rest_url + WorkflowConstants.GET_ALL_TASK_INSTANCE_ID + userId);

		httpRequestBase.addHeader(WorkflowConstants.ACCEPT, WorkflowConstants.CONTENT_TYPE);
		httpRequestBase.addHeader(WorkflowConstants.AUTHORIZATION, AuthorizationConstants.BEARER + " " + bearerToken);

		httpResponse = httpClient.execute(httpRequestBase);

		jsonString = EntityUtils.toString(httpResponse.getEntity());
		System.err.println("Error" + jsonString);

		// JSONArray responseObj = new JSONArray(jsonString);
		// System.err.println("JSONArray" + responseObj);

		httpClient.close();

		JSONArray jsonArray = new JSONArray(jsonString);

		List<TaskDetailsDto> taskInfoList = new ArrayList<>();
		for (int counter = 0; counter < jsonArray.length(); counter++) {
			JSONObject instanceObject = jsonArray.getJSONObject(counter);
			//if (instanceObject.get(WorkflowConstants.STATUS).equals(WorkflowConstants.READY)) {
				TaskDetailsDto taskInfo = new TaskDetailsDto();
				taskInfo.setTaskId(instanceObject.get(WorkflowConstants.ID).toString());
				taskInfo.setActivityId(ServiceUtil.isEmpty(instanceObject.get(WorkflowConstants.ACTIVITY_ID)) ? null
						: instanceObject.get(WorkflowConstants.ACTIVITY_ID).toString());
				taskInfo.setClaimedAt(ServiceUtil.isEmpty(instanceObject.get(WorkflowConstants.CLAIMED_AT)) ? null
						: instanceObject.get(WorkflowConstants.CLAIMED_AT).toString());
				taskInfo.setCompletedAt(ServiceUtil.isEmpty(instanceObject.get(WorkflowConstants.COMPLETED_AT)) ? null
						: instanceObject.get(WorkflowConstants.COMPLETED_AT).toString());
				taskInfo.setCreatedAt(ServiceUtil.isEmpty(instanceObject.get(WorkflowConstants.CREATED_AT)) ? null
						: instanceObject.get(WorkflowConstants.CREATED_AT).toString());
				taskInfo.setDescription(ServiceUtil.isEmpty(instanceObject.get(WorkflowConstants.DESCRIPTION)) ? null
						: instanceObject.get(WorkflowConstants.DESCRIPTION).toString());
				taskInfo.setDueDate(ServiceUtil.isEmpty(instanceObject.get(WorkflowConstants.DUE_DATE)) ? null
						: instanceObject.get(WorkflowConstants.DUE_DATE).toString());
				taskInfo.setPriority(ServiceUtil.isEmpty(instanceObject.get(WorkflowConstants.PRIORITY)) ? null
						: instanceObject.get(WorkflowConstants.PRIORITY).toString());
				taskInfo.setProcessor(ServiceUtil.isEmpty(instanceObject.get(WorkflowConstants.PROCESSOR)) ? null
						: instanceObject.get(WorkflowConstants.PROCESSOR).toString());
				taskInfo.setRecipientGroup(ServiceUtil.isEmpty(instanceObject.get(WorkflowConstants.RECIPIENT_GROUP))
						? null : instanceObject.get(WorkflowConstants.RECIPIENT_GROUP).toString());
				taskInfo.setRecipientUsers(ServiceUtil.isEmpty(instanceObject.get(WorkflowConstants.RECIPIENT_USERS))
						? null : instanceObject.get(WorkflowConstants.RECIPIENT_USERS).toString());
				taskInfo.setStatus(ServiceUtil.isEmpty(instanceObject.get(WorkflowConstants.STATUS)) ? null
						: instanceObject.get(WorkflowConstants.STATUS).toString());
				taskInfo.setSubject(ServiceUtil.isEmpty(instanceObject.get(WorkflowConstants.SUBJECT)) ? null
						: instanceObject.get(WorkflowConstants.SUBJECT).toString());
				taskInfo.setWorkflowDefinitionId(
						ServiceUtil.isEmpty(instanceObject.get(WorkflowConstants.WORKFLOW_DEFINITION_ID)) ? null
								: instanceObject.get(WorkflowConstants.WORKFLOW_DEFINITION_ID).toString());
				taskInfo.setWorkflowInstanceId(
						ServiceUtil.isEmpty(instanceObject.get(WorkflowConstants.WORKFLOW_INSTANCE_ID)) ? null
								: instanceObject.get(WorkflowConstants.WORKFLOW_INSTANCE_ID).toString());
				taskInfoList.add(taskInfo);
			//}

		}

		return taskInfoList;
	}

	public HttpResponse completeTaskForAccountant(PremiumOrderAccountingDetailsDto dto, String taskInstanceId,
			String Status) throws ClientProtocolException, IOException, JSONException {
		MYLOGGER.error("ENTERING INTO approveTask INVOKER METHOD");
		HttpResponse httpResponse = null;

		HttpRequestBase httpRequestBase = null;
		StringEntity data = null;
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		try {
			String input = null;
			String bearerToken = getBearerToken(httpClient);
			MYLOGGER.error("ENTERING INTO approveTask INVOKER METHOD bearerToken:: " + bearerToken);
			httpRequestBase = new HttpPatch(workflow_rest_url + WorkflowConstants.APPROVE_TASK_URL + taskInstanceId);
			MYLOGGER.error("ENTERING INTO approveTask INVOKER METHOD httpRequestBase:: " + httpRequestBase);
			System.err.println("Before Object creation");
			JSONObject context = new JSONObject();
			JSONObject response = new JSONObject();
			System.err.println("After Object creation");

			if (Status.equalsIgnoreCase("Approved")) {

				context.put("accountantActionType", "Approved");

			} else {
				context.put("accountantActionType", "Rejected");
			}

			context.put("accountantActionType", Status);
			System.err.println("After Object creation Status  " + Status);

			context.put("taskInstanceId", taskInstanceId);
			System.err.println("After Object creation instance Id" + taskInstanceId);
			
			context.put("accountantDetailInfo", dto);


			System.err.println("After Object creation Context" + context);

			response.put(WorkflowConstants.CONTEXT, context);
			response.put("status", "COMPLETED");
			System.err.println("After Object creation Context" + context);

			input = response.toString();
			System.err.println("After Object creation input" + input);

			data = new StringEntity(input, "UTF-8");
			data.setContentType(WorkflowConstants.CONTENT_TYPE);
			MYLOGGER.error("ENTERING INTO approveTask INVOKER METHOD input:: " + input);
			((HttpPatch) httpRequestBase).setEntity(data);

			httpRequestBase.addHeader(WorkflowConstants.ACCEPT, WorkflowConstants.CONTENT_TYPE);
			httpRequestBase.addHeader(WorkflowConstants.AUTHORIZATION,
					AuthorizationConstants.BEARER + " " + bearerToken);
			MYLOGGER.error("ENTERING INTO approveTask INVOKER METHOD BEFORE EXECUTE:: " + input);
			httpResponse = httpClient.execute(httpRequestBase);
			MYLOGGER.error("ENTERING INTO approveTask INVOKER METHOD AFTER EXECUTE:: " + input);

			if (httpResponse.getStatusLine().getStatusCode() == 400) {
				MYLOGGER.error("WorkflowInvoker | approveTask | Error :" + input);
			}
		} catch (Exception e) {
			MYLOGGER.error("WorkflowInvoker | approveTask | Exception :" + e.toString());
		} finally {
			httpClient.close();
		}

		MYLOGGER.error("WorkflowInvoker | approveTask | httpResponse :" + httpResponse.toString());
		return httpResponse;
	}

	public HttpResponse completeTaskForManager(String input, String taskInstanceId, String Status)
			throws ClientProtocolException, IOException, JSONException {
		MYLOGGER.error("ENTERING INTO approveTask INVOKER METHOD");
		HttpResponse httpResponse = null;

		HttpRequestBase httpRequestBase = null;
		StringEntity data = null;
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		try {

			String bearerToken = getBearerToken(httpClient);
			MYLOGGER.error("ENTERING INTO approveTask INVOKER METHOD bearerToken:: " + bearerToken);
			httpRequestBase = new HttpPatch(workflow_rest_url + WorkflowConstants.APPROVE_TASK_URL + taskInstanceId);
			MYLOGGER.error("ENTERING INTO approveTask INVOKER METHOD httpRequestBase:: " + httpRequestBase);
			System.err.println("Before Object creation");
			JSONObject context = new JSONObject();
			JSONObject response = new JSONObject();
			System.err.println("After Object creation");

			if (Status.equalsIgnoreCase("Approved")) {

				context.put("managerActionType", "Approved");

			} else {
				context.put("managerActionType", "Rejected");
			}

			context.put("managerActionType", Status);
			System.err.println("After Object creation Status  " + Status);

			// context.put("status", "COMPLETED");

			context.put("taskInstanceId", taskInstanceId);
			System.err.println("After Object creation instance Id" + taskInstanceId);


			System.err.println("After Object creation Context" + context);

			response.put(WorkflowConstants.CONTEXT, context);
			response.put("status", "COMPLETED");
			System.err.println("After Object creation Context" + context);

			input = response.toString();
			System.err.println("After Object creation input" + input);

			data = new StringEntity(input, "UTF-8");
			data.setContentType(WorkflowConstants.CONTENT_TYPE);
			MYLOGGER.error("ENTERING INTO approveTask INVOKER METHOD input:: " + input);
			((HttpPatch) httpRequestBase).setEntity(data);

			httpRequestBase.addHeader(WorkflowConstants.ACCEPT, WorkflowConstants.CONTENT_TYPE);
			httpRequestBase.addHeader(WorkflowConstants.AUTHORIZATION,
					AuthorizationConstants.BEARER + " " + bearerToken);
			MYLOGGER.error("ENTERING INTO approveTask INVOKER METHOD BEFORE EXECUTE:: " + input);
			httpResponse = httpClient.execute(httpRequestBase);
			MYLOGGER.error("ENTERING INTO approveTask INVOKER METHOD AFTER EXECUTE:: " + httpResponse.toString());

			if (httpResponse.getStatusLine().getStatusCode() == 400) {
				MYLOGGER.error("WorkflowInvoker | approveTask | Error :" + input);
			}
		} catch (Exception e) {
			MYLOGGER.error("WorkflowInvoker | approveTask | Exception :" + e.toString());
		} finally {
			httpClient.close();
		}

		MYLOGGER.error("WorkflowInvoker | approveTask | httpResponse :" + httpResponse.toString());
		return httpResponse;
	}

	
	@Override
	public HttpResponse completeManagerTask(ApprovalDto dto)
			throws ClientProtocolException, IOException, JSONException {
		MYLOGGER.error("PremiumWorkFlowService : ApproveTask : enter");
		// return callApprovalTask(dto);
		return completeTaskForManager(null, dto.getTaskIdDetails(), dto.getStatus());
		// return completeTaskForManager(dto);

	}

	@Override
	public HttpResponse completeAccountantTask(ApprovalDto dto)
			throws ClientProtocolException, IOException, JSONException {
		MYLOGGER.error("PremiumWorkFlowService : ApproveTask : enter");
		return completeTaskForAccountant(dto.getAccountantDto(), dto.getTaskIdDetails(), dto.getStatus());

	}

}
