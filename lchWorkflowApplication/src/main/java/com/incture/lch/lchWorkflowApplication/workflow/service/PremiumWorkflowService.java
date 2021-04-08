package com.incture.lch.lchWorkflowApplication.workflow.service;

import java.io.IOException;

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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.incture.lch.lchWorkflowApplication.workflow.constant.AuthorizationConstants;
import com.incture.lch.lchWorkflowApplication.workflow.constant.WorkflowConstants;
import com.incture.lch.lchWorkflowApplication.workflow.dto.AdhocWorkflowCustomDto;
import com.incture.lch.lchWorkflowApplication.workflow.dto.PremiumWorkflowCustomDto;
import com.incture.lch.lchWorkflowApplication.workflow.dto.ResponseMessage;
import com.incture.lch.lchWorkflowApplication.workflow.dto.WorkflowCustomDto;
import com.incture.lch.lchWorkflowApplication.workflow.util.ServiceUtil;

@Service
public class PremiumWorkflowService {
	private final Logger MYLOGGER = LoggerFactory.getLogger(this.getClass());

	/*private String workflow_rest_url;
	private String url;
	private String clientid;
	private String clientsecret;*/

	private static String workflow_rest_url="https://api.workflow-sap.cfapps.eu10.hana.ondemand.com/workflow-service/rest";
	private static String url="https://hrapps.authentication.eu10.hana.ondemand.com";
	private static String clientid="sb-clone-100d9392-d07e-4ed1-be50-9c2b4ea8a187!b19391|workflow!b10150";
	private static String clientsecret="5d1faa91-b683-4b9f-a8cc-3fb83736583b$sTFdfQiPu-NbvSV9LFmV_3u2vk5cKT3ZoStBLkWfjtw=";	
	// Calling the VCAP Environment Variable for fetching the Credentials

	/*public PremiumWorkflowService() {
		try {
			JSONObject jsonObj = new JSONObject(System.getenv("VCAP_SERVICES"));
			System.err.println("[WorkflowInvoker:VCAP_SERVICES] : " + jsonObj.toString());

			JSONArray jsonArr = jsonObj.getJSONArray("xsuaa");
			JSONObject credentials = jsonArr.getJSONObject(0).getJSONObject("credentials");
			JSONObject endpoints = credentials.getJSONObject("endpoints");

			clientid = credentials.getString("clientid");
			clientsecret = credentials.getString("clientsecret");
			url = credentials.getString("url");
			workflow_rest_url = endpoints.getString("workflow_rest_url");


			System.err.println("[WorkflowInvoker] : " + jsonArr.toString());

		} catch (JSONException e) {
			MYLOGGER.error("[WorkflowInvoker] reading environmental variables failed:" + e.getMessage());
		}
	}

	*/// Getting the bearer Token from the Variable received
	private String getBearerToken(CloseableHttpClient httpClient)
			throws ClientProtocolException, IOException, JSONException {
		MYLOGGER.info("LCH | WorkflowInvoker | getBearerToken | Execution Start ");
		HttpRequestBase httpRequestBase = new HttpPost(url + "/oauth/token?grant_type=client_credentials");
		ServiceUtil.getBasicAuthWorkflow(clientid, clientsecret);
		httpRequestBase.addHeader(WorkflowConstants.AUTHORIZATION, ServiceUtil.getBasicAuth(clientid, clientsecret));
		try {
			HttpResponse httpResponse = httpClient.execute(httpRequestBase);
			ServiceUtil.getBasicAuthWorkflow(clientid, clientsecret);
			String jsonString = EntityUtils.toString(httpResponse.getEntity());
			JSONObject responseObj = new JSONObject(jsonString);
			String token = responseObj.getString("access_token");
			MYLOGGER.info("LCH | WorkflowInvoker | getBearerToken | Execution End ");
			return token;
		} catch (Exception e) {
			MYLOGGER.info("LCH | WorkflowInvoker | getBearerToken | Exception " + e.toString());
		}

		return null;

	}

	
	public JSONArray getAllTaskInstanceId(String userId)
			throws ClientProtocolException, IOException, JSONException 
	{

		HttpResponse httpResponse = null;
		String jsonString = null;
		JSONArray responseObj = null;

		HttpRequestBase httpRequestBase = null;
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();

		String bearerToken = getBearerToken(httpClient);

		httpRequestBase = new HttpGet(
				workflow_rest_url + WorkflowConstants.GET_ALL_TASK_INSTANCE_ID + userId);

		httpRequestBase.addHeader(WorkflowConstants.ACCEPT, WorkflowConstants.CONTENT_TYPE);
		httpRequestBase.addHeader(WorkflowConstants.AUTHORIZATION, AuthorizationConstants.BEARER + " " + bearerToken);

		httpResponse = httpClient.execute(httpRequestBase);

		jsonString = EntityUtils.toString(httpResponse.getEntity());

		responseObj = new JSONArray(jsonString);

		httpClient.close();

		return responseObj;
	}
	public ResponseMessage callPremiumAppToUpdateWorkflowTables(PremiumWorkflowCustomDto input)
			throws ClientProtocolException, IOException, JSONException {
		MYLOGGER.error("Enter into PremiumWorkflowService: callLchAppToUpdateWorkflowTables:");
		RestTemplate callRestApi = new RestTemplate();

		try {
			CloseableHttpClient httpClient = HttpClientBuilder.create().build();
			String bearerToken = getBearerToken(httpClient);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set("Authorization", "Bearer " + bearerToken);
			HttpEntity<PremiumWorkflowCustomDto> entity = new HttpEntity<PremiumWorkflowCustomDto>(input, headers);
			String responseMessage = callRestApi.postForObject(WorkflowConstants.PREMIUM_APP_URL_FOR_APPROVAL, entity,
					String.class);
			MYLOGGER.error("Enter into AdhocOWorkflowService: callLchAppToUpdateWorkflowTables:responseMessage "
					+ responseMessage);
			ResponseMessage response1 = new ResponseMessage(responseMessage);

			return response1;
		} catch (Exception e) {
			MYLOGGER.error("PremiumOWorkflowService: callPremiumAppToUpdateWorkflowTables: error " + e.toString());
		}
		return null;

	}

	public ResponseMessage callApprovalTask(PremiumWorkflowCustomDto input)
			throws ClientProtocolException, IOException, JSONException {
		MYLOGGER.error("Enter into PremiumWorkflowService: callApprovalTask: entere");
		RestTemplate callRestApi = new RestTemplate();

		try {
			CloseableHttpClient httpClient = HttpClientBuilder.create().build();
			String bearerToken = getBearerToken(httpClient);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set("Authorization", "Bearer " + bearerToken);
			HttpEntity<PremiumWorkflowCustomDto> entity = new HttpEntity<PremiumWorkflowCustomDto>(input, headers);
			String responseMessage = callRestApi.postForObject(WorkflowConstants.PREMIUM_APP_URL_FOR_APPROVAL, entity,
					String.class);
			MYLOGGER.error("Enter into AdhocOWorkflowService: callLchAppToUpdateWorkflowTables:responseMessage "
					+ responseMessage);
			ResponseMessage response1 = new ResponseMessage(responseMessage);

			return response1;
		} catch (Exception e) {
			MYLOGGER.error("PremiumOWorkflowService: callPremiumAppToUpdateWorkflowTables: error " + e.toString());
		}
		return null;

	}

	public HttpResponse approveTaskForPremium(String input, String taskInstanceId)
			throws ClientProtocolException, IOException, JSONException {
		MYLOGGER.error("ENTERING INTO approveTask INVOKER METHOD");
		HttpResponse httpResponse = null;

		HttpRequestBase httpRequestBase = null;
		StringEntity data = null;
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		try {
			String bearerToken = getBearerToken(httpClient);
			MYLOGGER.error("ENTERING INTO approveTask INVOKER METHOD bearerToken:: " + bearerToken);
			httpRequestBase = new HttpPatch(url + WorkflowConstants.APPROVE_TASK_URL + taskInstanceId);
			MYLOGGER.error("ENTERING INTO approveTask INVOKER METHOD httpRequestBase:: " + httpRequestBase);
			JSONObject context = new JSONObject();
			context.put("status", "Completed");
			context.put("actionType", "Approved");
			context.put("taskInstanceId", taskInstanceId);
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
	
	public HttpResponse rejectTaskForPremium(String input, String taskInstanceId)
			throws ClientProtocolException, IOException, JSONException {
		MYLOGGER.error("ENTERING INTO approveTask INVOKER METHOD");
		HttpResponse httpResponse = null;

		HttpRequestBase httpRequestBase = null;
		StringEntity data = null;
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		try {
			String bearerToken = getBearerToken(httpClient);
			MYLOGGER.error("ENTERING INTO approveTask INVOKER METHOD bearerToken:: " + bearerToken);
			httpRequestBase = new HttpPatch(url + WorkflowConstants.APPROVE_TASK_URL + taskInstanceId);
			MYLOGGER.error("ENTERING INTO approveTask INVOKER METHOD httpRequestBase:: " + httpRequestBase);
			JSONObject context = new JSONObject();
			context.put("status", "Completed");
			context.put("actionType", "Rejected");
			context.put("taskInstanceId", taskInstanceId);
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

	public ResponseMessage updateTableDetails(PremiumWorkflowCustomDto dto)
			throws ClientProtocolException, JSONException, IOException {
		// TODO Auto-generated method stub
		MYLOGGER.error("AdhocOWorkflowService: updateApprovalWorflowDetailsForType4: enter ");
		return callPremiumAppToUpdateWorkflowTables(dto);
	}

	public HttpResponse approveTask(PremiumWorkflowCustomDto dto)
			throws ClientProtocolException, IOException, JSONException {
		MYLOGGER.error("PremiumWorkFlowService : ApproveTask : enter");
		// return callApprovalTask(dto);
		return approveTaskForPremium(null, dto.getTaskIdDetails());

	}
	
	public HttpResponse rejectTask(PremiumWorkflowCustomDto dto)
			throws ClientProtocolException, IOException, JSONException {
		MYLOGGER.error("PremiumWorkFlowService : ApproveTask : enter");
		// return callApprovalTask(dto);
		return rejectTaskForPremium(null, dto.getTaskIdDetails());

	}

}
