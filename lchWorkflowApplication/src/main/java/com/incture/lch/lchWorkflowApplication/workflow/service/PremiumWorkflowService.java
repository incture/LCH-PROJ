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
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.incture.lch.lchWorkflowApplication.workflow.constant.AuthorizationConstants;
import com.incture.lch.lchWorkflowApplication.workflow.constant.WorkflowConstants;
import com.incture.lch.lchWorkflowApplication.workflow.dto.ApprovalDto;
import com.incture.lch.lchWorkflowApplication.workflow.dto.ContextDto;
import com.incture.lch.lchWorkflowApplication.workflow.dto.PremiumOrderAccountingDetailsDto;
import com.incture.lch.lchWorkflowApplication.workflow.dto.PremiumWorkflowCustomDto;
import com.incture.lch.lchWorkflowApplication.workflow.dto.ResponseMessage;
import com.incture.lch.lchWorkflowApplication.workflow.dto.WorkflowPremiumCustomDto;
import com.incture.lch.lchWorkflowApplication.workflow.util.ServiceUtil;


@Service
public class PremiumWorkflowService {
	private final Logger MYLOGGER = LoggerFactory.getLogger(this.getClass());

	private String workflow_rest_url;
	private String url;
	private String clientid;
	private String clientsecret;

	/*
	 * private static String workflow_rest_url =
	 * "https://api.workflow-sap.cfapps.eu10.hana.ondemand.com/workflow-service/rest";
	 * private static String url =
	 * "https://hrapps.authentication.eu10.hana.ondemand.com"; private static
	 * String clientid =
	 * "sb-clone-100d9392-d07e-4ed1-be50-9c2b4ea8a187!b19391|workflow!b10150";
	 * private static String clientsecret =
	 * "45403583-1744-42fd-a5ef-d85d95cbd2fd$9eaUN3H8rCOFrJ_6EQS9dJqRuVxOnYT0EZls7uPzeeg=";
	 */
	// Calling the VCAP Environment Variable for fetching the Credentials

	public PremiumWorkflowService() {
		try {
			JSONObject jsonObj = new JSONObject(System.getenv("VCAP_SERVICES"));
			System.err.println("[WorkflowInvoker:VCAP_SERVICES] : " + jsonObj.toString());

			JSONArray jsonArr = jsonObj.getJSONArray("xsuaa");
			JSONObject credentials = jsonArr.getJSONObject(0).getJSONObject("credentials");
			clientid = credentials.getString("clientid");
			clientsecret = credentials.getString("clientsecret");
			url = credentials.getString("url");

			System.err.println("[WorkflowInvoker] : " + jsonArr.toString());

		} catch (JSONException e) {
			MYLOGGER.error("[WorkflowInvoker] reading environmental variables failed:" + e.getMessage());
		}
	}

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

	public JSONArray getAllTaskInstanceId(String userId) throws ClientProtocolException, IOException, JSONException {

		HttpResponse httpResponse = null;
		String jsonString = null;
		JSONArray responseObj = null;

		HttpRequestBase httpRequestBase = null;
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();

		String bearerToken = getBearerToken(httpClient);

		httpRequestBase = new HttpGet(workflow_rest_url + WorkflowConstants.GET_ALL_TASK_INSTANCE_ID + userId);

		httpRequestBase.addHeader(WorkflowConstants.ACCEPT, WorkflowConstants.CONTENT_TYPE);
		httpRequestBase.addHeader(WorkflowConstants.AUTHORIZATION, AuthorizationConstants.BEARER + " " + bearerToken);

		httpResponse = httpClient.execute(httpRequestBase);

		jsonString = EntityUtils.toString(httpResponse.getEntity());

		responseObj = new JSONArray(jsonString);

		httpClient.close();

		return responseObj;
	}

	public ResponseMessage callPremiumAppToUpdateWorkflowTables(WorkflowPremiumCustomDto input)
			throws ClientProtocolException, IOException, JSONException {
		MYLOGGER.error("Enter into PremiumWorkflowService: callLchAppToUpdateWorkflowTables:");
		RestTemplate callRestApi = new RestTemplate();

		try {
			CloseableHttpClient httpClient = HttpClientBuilder.create().build();
			String bearerToken = getBearerToken(httpClient);
			MYLOGGER.error("ENTERING INTO callPremiumAppToUpdateWorkflowTables bearerToken:: " + bearerToken);

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set("Authorization", "Bearer " + bearerToken);
			MYLOGGER.error("HEADERSS:: " + headers);

			HttpEntity<WorkflowPremiumCustomDto> entity = new HttpEntity<WorkflowPremiumCustomDto>(input, headers);
			String responseMessage = callRestApi.postForObject(WorkflowConstants.PREMIUM_APP_URL_FOR_TABLE_UPDATE, entity,
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

	public ResponseMessage updateTableDetails(WorkflowPremiumCustomDto dto)
			throws ClientProtocolException, JSONException, IOException {
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

	public HttpResponse workflowActionForPremium(String input, String taskInstanceId, String Status)
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
			if (Status == "Accepted") {

				context.put("actionType", "Accepted");

			} else {
				context.put("actionType", "Rejected");
			}
			context.put("status", "Completed");
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

	public HttpResponse workflowAction(ApprovalDto dto) throws ClientProtocolException, IOException, JSONException {
		MYLOGGER.error("PremiumWorkFlowService : ApproveTask : enter");
		// return callApprovalTask(dto);
		return workflowActionForPremium(null, dto.getTaskIdDetails(), dto.getStatus());

	}

	public HttpResponse completeTaskForManager(ApprovalDto dto)
			throws ClientProtocolException, IOException, JSONException {

		MYLOGGER.error("Enter into PremiumWorkflowService: callLchAppToUpdateWorkflowTables:");
		RestTemplate callRestApi = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
		String taskInstanceId = dto.getTaskIdDetails();
		String status = dto.getStatus();
		try {
			CloseableHttpClient httpClient = HttpClientBuilder.create().build();
			String bearerToken = getBearerToken(httpClient);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set("Authorization", "Bearer " + bearerToken);
			
			JSONObject context = new JSONObject();
			System.err.println("After Object creation");
			ContextDto contextdto= new ContextDto();
			if (status.equalsIgnoreCase("Approved")) {

				//context.put("managerActionType", "Approved");
				contextdto.setManagerActionType("Approved");

			} else {
				//context.put("managerActionType", "Rejected");
				contextdto.setManagerActionType("Rejected");

			}

			//context.put("managerActionType", status);
			
			System.err.println("After Object creation Status  " + status);

			//context.put("taskInstanceId", taskInstanceId);
			contextdto.setTaskInstanceId(taskInstanceId);

			System.err.println("After Object creation instance Id" + taskInstanceId);

			System.err.println("After Object creation Context" + context);

			//response.put(WorkflowConstants.CONTEXT, context);
			//response.put("status", "COMPLETED");

			contextdto.setStatus("COMPLETED");
			
			
			
			//HttpEntity<JSONObject> entity= new HttpEntity<JSONObject>(response, headers);
			HttpEntity<ContextDto> entity= new HttpEntity<ContextDto>(contextdto, headers);

			String responseMessageForPatch = callRestApi.patchForObject(url+WorkflowConstants.APPROVE_TASK_URL+taskInstanceId, entity, String.class);

		  ResponseMessage response1 = new ResponseMessage(responseMessageForPatch);

			return (HttpResponse) response1;
		} catch (Exception e) {
			MYLOGGER.error("PremiumWorkflowService: completeTask for MANAGER: error " + e.toString());
		}
		return null;
		/*
		 * MYLOGGER.error("ENTERING INTO approveTask INVOKER METHOD");
		 * HttpResponse httpResponse = null;
		 * 
		 * HttpRequestBase httpRequestBase = null; StringEntity data = null;
		 * CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		 * try { String bearerToken = getBearerToken(httpClient); MYLOGGER.
		 * error("ENTERING INTO approveTask INVOKER METHOD bearerToken:: " +
		 * bearerToken); httpRequestBase = new HttpPatch(url +
		 * WorkflowConstants.APPROVE_TASK_URL + taskInstanceId); MYLOGGER.
		 * error("ENTERING INTO approveTask INVOKER METHOD httpRequestBase:: " +
		 * httpRequestBase); System.err.println("Before Object creation");
		 * JSONObject context = new JSONObject(); JSONObject response = new
		 * JSONObject(); System.err.println("After Object creation");
		 * 
		 * 
		 * if (Status.equalsIgnoreCase("Approved")) {
		 * 
		 * context.put("managerActionType", "Approved");
		 * 
		 * } else { context.put("managerActionType", "Rejected"); }
		 * 
		 * context.put("managerActionType", Status);
		 * System.err.println("After Object creation Status  " + Status);
		 * 
		 * // context.put("status", "COMPLETED");
		 * 
		 * context.put("taskInstanceId", taskInstanceId);
		 * System.err.println("After Object creation instance Id" +
		 * taskInstanceId);
		 * 
		 * System.err.println("After Object creation Context" + context);
		 * 
		 * response.put(WorkflowConstants.CONTEXT, context);
		 * response.put("status", "COMPLETED");
		 * System.err.println("After Object creation Context" + context);
		 * 
		 * input = response.toString();
		 * System.err.println("After Object creation input" + input);
		 * 
		 * data = new StringEntity(input, "UTF-8");
		 * data.setContentType(WorkflowConstants.CONTENT_TYPE);
		 * MYLOGGER.error("ENTERING INTO approveTask INVOKER METHOD input:: " +
		 * input); ((HttpPatch) httpRequestBase).setEntity(data);
		 * 
		 * httpRequestBase.addHeader(WorkflowConstants.ACCEPT,
		 * WorkflowConstants.CONTENT_TYPE);
		 * httpRequestBase.addHeader(WorkflowConstants.AUTHORIZATION,
		 * AuthorizationConstants.BEARER + " " + bearerToken); MYLOGGER.
		 * error("ENTERING INTO approveTask INVOKER METHOD BEFORE EXECUTE:: " +
		 * input); httpResponse = httpClient.execute(httpRequestBase); MYLOGGER.
		 * error("ENTERING INTO approveTask INVOKER METHOD AFTER EXECUTE:: " +
		 * httpResponse.toString());
		 * 
		 * if (httpResponse.getStatusLine().getStatusCode() == 400) {
		 * MYLOGGER.error("WorkflowInvoker | approveTask | Error :" + input); }
		 * } catch (Exception e) {
		 * MYLOGGER.error("WorkflowInvoker | approveTask | Exception :" +
		 * e.toString()); } finally { httpClient.close(); }
		 * 
		 * MYLOGGER.error("WorkflowInvoker | approveTask | httpResponse :" +
		 * httpResponse.toString()); return httpResponse;
		 */
	}

	public HttpResponse completeManagerTask(ApprovalDto dto)
			throws ClientProtocolException, IOException, JSONException {
		MYLOGGER.error("PremiumWorkFlowService : ApproveTask : enter");
		// return completeTaskForManager(null, dto.getTaskIdDetails(),
		// dto.getStatus());
		return completeTaskForManager(dto);

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
			httpRequestBase = new HttpPatch(url + WorkflowConstants.APPROVE_TASK_URL + taskInstanceId);
			MYLOGGER.error("ENTERING INTO approveTask INVOKER METHOD httpRequestBase:: " + httpRequestBase);
			JSONObject context = new JSONObject();
			if (Status.equalsIgnoreCase("Approved")) {

				context.put("accountantActionType", "Approved");

			} else {
				context.put("accountantActionType", "Rejected");
			}
			context.put("status", "Completed");
			context.put("taskInstanceId", taskInstanceId);
			context.put("accountantDetailInfo", dto);
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

	public HttpResponse completeAccountantTask(ApprovalDto dto)
			throws ClientProtocolException, IOException, JSONException {
		MYLOGGER.error("PremiumWorkFlowService : ApproveTask : enter");
		return completeTaskForAccountant(dto.getAccountantDto(), dto.getTaskIdDetails(), dto.getStatus());

	}

	

}
