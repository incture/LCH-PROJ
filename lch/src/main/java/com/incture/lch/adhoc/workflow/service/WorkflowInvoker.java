package com.incture.lch.adhoc.workflow.service;

import java.io.IOException;

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
import com.incture.lch.util.ServiceUtil;

/**
 * <h1>This class is used to call SAP cloud foundry Workflow services</h1>
 * 
 * @author Ravi Kumar.P
 * @since 2021-03-02
 * @version 1.0
 *
 */
@Transactional
@Service
public class WorkflowInvoker implements WorkflowInvokerLocal {

	private final Logger MYLOGGER = LoggerFactory.getLogger(this.getClass());

	private String workflow_rest_url;
	private String url;
	private String clientid;
	private String clientsecret;/*

	private static String workflow_rest_url = "https://api.workflow-sap.cfapps.eu10.hana.ondemand.com/workflow-service/rest";
	private static String url = "https://hrapps.authentication.eu10.hana.ondemand.com";
	private static String clientid = "sb-clone-100d9392-d07e-4ed1-be50-9c2b4ea8a187!b19391|workflow!b10150";
	private static String clientsecret = "b643d0fa-8f03-4632-b8f5-02a59c6f1e63$hCJcWZ947HFtpS0C6Ni0tGwini2EIUJULDLF7H7IgGQ=";

	*/public WorkflowInvoker() {
		try {
			JSONObject jsonObj = new JSONObject(System.getenv("VCAP_SERVICES"));
			System.err.println("[WorkflowInvoker:VCAP_SERVICES] : " + jsonObj.toString());

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

			System.err.println("[WorkflowInvoker] : " + jsonArr.toString());

		} catch (JSONException e) {
			MYLOGGER.error("[WorkflowInvoker] reading environmental variables failed:" + e.getMessage());
		}
	}

	@Override
	public JSONObject triggerWorkflow(String input) throws ClientProtocolException, IOException, JSONException {

		MYLOGGER.error("Ravi: Input" + input);

		HttpResponse httpResponse = null;
		String jsonString = null;
		JSONObject responseObj = null;

		HttpRequestBase httpRequestBase = null;
		StringEntity data = null;
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();

		String bearerToken = getBearerToken(httpClient);

		MYLOGGER.error("Ravi :Token" + bearerToken);

		httpRequestBase = new HttpPost(workflow_rest_url + WorkflowConstants.WORKFLOW_TRIGGER_URL);

		data = new StringEntity(input);
		data.setContentType(WorkflowConstants.CONTENT_TYPE);

		((HttpPost) httpRequestBase).setEntity(data);
		httpRequestBase.addHeader(WorkflowConstants.ACCEPT, WorkflowConstants.CONTENT_TYPE);
		httpRequestBase.addHeader(WorkflowConstants.AUTHORIZATION, AuthorizationConstants.BEARER + " " + bearerToken);

		httpResponse = httpClient.execute(httpRequestBase);

		jsonString = EntityUtils.toString(httpResponse.getEntity());

		if (httpResponse.getStatusLine().getStatusCode() == 400) {
			MYLOGGER.error("WorkflowInvoker | triggerWorkflow | Error :" + input);
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

	@Override
	public JSONArray getAllWorkflowTaskInstanceId(String userId)
			throws ClientProtocolException, IOException, JSONException {

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

}
