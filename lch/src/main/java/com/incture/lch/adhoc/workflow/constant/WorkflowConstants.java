package com.incture.lch.adhoc.workflow.constant;

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
	public static final String WORKFLOW_TRIGGER_URL_PREMIUM = "/v1/workflow-instances";

	public static final String APPROVE_TASK_URL = "/v1/task-instances/";
	public static final String GET_TASK_INSTANCE_ID_URL = "/v1/task-instances?workflowInstanceId=";
	public static final String GET_WORKFLOW_CONTEXT = "/v1/workflow-instances/";
	public static final String GET_ALL_TASK_INSTANCE_ID = "/v1/task-instances?workflowDefinitionId=PremiumOrderWorkflow&processor=";

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
	public static final String USER_WF_DEFINITION_ID_PREMIUM = "PremiumOrderWorkflow";
	public static final String CONTEXT = "context";

	public static final String ID = "id";
	public static final int SUCCESS_CODE = 204;
	public static final String MANAGER = "MANAGER";
	public static final String PLANNER = "PLANNER";

	public static final String ACTIVITY_ID = "activityId";
	public static final String CLAIMED_AT = "claimedAt";
	public static final String COMPLETED_AT = "completedAt";
	public static final String CREATED_AT = "createdAt";
	public static final String DESCRIPTION = "description";
	public static final String PRIORITY = "priority";
	public static final String DUE_DATE = "dueDate";
	public static final String PROCESSOR = "processor";
	public static final String RECIPIENT_USERS = "recipientUsers";
	public static final String RECIPIENT_GROUP = "recipientGroups";
	public static final String SUBJECT = "subject";
	public static final String WORKFLOW_DEFINITION_ID = "workflowDefinitionId";
	public static final String WORKFLOW_INSTANCE_ID = "workflowInstanceId";
	public static final Object READY = "ready";

}
