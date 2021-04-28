package com.incture.lch.lchWorkflowApplication.workflow.constant;

/**
 * this class Contains application related Constants
 * 
 * @author Ravi Kumar P
 * @since 2021-03-04
 * @version 1.0
 */
public class WorkflowConstants {

	public static final String XSCRF_TOKEN_URL = "/v1/xsrf-token";
	public static final String WORKFLOW_TRIGGER_URL = "/v1/workflow-instances";
	public static final String APPROVE_TASK_URL = "/v1/task-instances/";
	public static final String GET_TASK_INSTANCE_ID_URL = "/v1/task-instances?workflowInstanceId=";
	public static final String GET_ALL_TASK_INSTANCE_ID = "/v1/task-instances?processor=";
	public static final String GET_WORKFLOW_CONTEXT = "/v1/workflow-instances/";

	public static final String LCH_APP_URL_FOR_UPDATE = "https://LCH.cfapps.eu10.hana.ondemand.com/adhocorders/updateApprovalWorkflowDetails";
	public static final String LCH_APP_URL_FOR_UPDATE2 = "https://LCH.cfapps.eu10.hana.ondemand.com/adhocorders/updateApprovalWorkflowDetails1";
	public static final String LCH_APP_URL_FOR_UPDATETYPE4= "https://LCH.cfapps.eu10.hana.ondemand.com/adhocorders/updateApprovalWorkflowDetailsForType4";
	public static final String PREMIUM_APP_URL_FOR_UPDATE = "https://LCH.cfapps.eu10.hana.ondemand.com/adhocorders/updateTableDetails";
	public static final String PREMIUM_APP_URL_FOR_APPROVAL="https://LCH.cfapps.eu10.hana.ondemand.com/adhocorders/approveTask";
	public static final String PREMIUM_APP_URL_FOR_TASK_COMPLETION="https://hrapps.authentication.eu10.hana.ondemand.com/v1/task-instances?taskInstanceId=";
	public static final String PREMIUM_APP_URL_FOR_TABLE_UPDATE="https://LCH.cfapps.eu10.hana.ondemand.com/premiumOrders/updateTableDetails";

	public static final String APPROVED = "APPROVED";
	public static final String REJECTED = "REJECTED";
	public static final String COMPLETED = "COMPLETED";
	public static final String INPROGRESS = "INPROGRESS";
	public static final String STATUS = "status";
	public static final String WORKFLOWTRIGGERED = "WORKFLOW TRIGGERED";
	public static final String PENDING_AT_MANAGER = "Pending with Manager";

	public static final String CONTENT_TYPE = "application/json";
	public static final String X_CSRF_TOKEN = "x-csrf-token";
	public static final String AUTHORIZATION = "Authorization";
	public static final String ACCEPT = "accept";
	public static final String FETCH = "fetch";

	public static final String DEFINITION_ID = "definitionId";
	public static final String USER_WF_DEFINITION_ID = "adhoc_name";
	public static final String CONTEXT = "context";

	public static final String ID = "id";
	public static final int SUCCESS_CODE = 204;
	public static final String MANAGER = "MANAGER";
	public static final String PLANNER = "PLANNER";

}
