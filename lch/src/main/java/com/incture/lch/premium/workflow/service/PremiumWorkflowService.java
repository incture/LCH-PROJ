package com.incture.lch.premium.workflow.service;

import java.util.Date;

import javax.transaction.Transactional;

import org.apache.http.HttpResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.incture.lch.adhoc.workflow.constant.WorkflowConstants;
import com.incture.lch.adhoc.workflow.dto.PremiumWorkflowApprovalTaskDto;
import com.incture.lch.adhoc.workflow.service.WorkflowInvokerLocal;
import com.incture.lch.dao.AdhocOrderWorkflowDao;
import com.incture.lch.dto.AdhocOrderWorkflowDto;
import com.incture.lch.dto.ResponseDataDto;
import com.incture.lch.helper.AdhocOrderWorkflowHelper;
import com.incture.lch.premium.custom.dto.WorkflowPremiumCustomDto;
import com.incture.lch.util.ServicesUtil;

@Transactional
@Service
public class PremiumWorkflowService implements PremiumWorkflowServiceLocal {

	private final Logger MYLOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private AdhocOrderWorkflowDao adhocOrderWorkflowDao;

	@Autowired
	private WorkflowInvokerLocal workflowInvokerLocal;

	@Autowired
	private AdhocOrderWorkflowHelper adhocOrderDao;

	@Autowired
	private PremiumWorkflowInvokerLocal premiumWorkflowInvokerLocal;

	/**
	 * Trigger workflow
	 * 
	 * @param WorkFlowTriggerInputDto
	 * @return ResponseDto Success/Failure message
	 */

	@Override
	public ResponseDataDto triggerPremiumWorkflow(PremiumWorkflowApprovalTaskDto triggerWorkFlowDto) {
		MYLOGGER.info("LCH | PremiumWorkFlowService | triggerPremiumWorkflow | Execution Start Input : "
				+ triggerWorkFlowDto.toString());
		ResponseDataDto responseDto = new ResponseDataDto();
		try {

			String wfInput = buildPremiumWorkflowTriggerPayload(triggerWorkFlowDto);
			MYLOGGER.error("LCH | PremiumWorkFlowService | triggerPremiumWorkflow | wfInput : " + wfInput);
			// Map<String, String> destinationProperties = callDestination();
			JSONObject resWfObj = premiumWorkflowInvokerLocal.triggerPremiumWorkflow(wfInput);
			MYLOGGER.error("LCH | PremiumWorkFlowService | triggerPremiumWorkflow | JSON WORKFLOW OUT DATA : "
					+ resWfObj.toString());
			AdhocOrderWorkflowDto workflowDto = new AdhocOrderWorkflowDto();
			workflowDto.setInstanceId(resWfObj.getString("id"));
			workflowDto.setDefinitionId(resWfObj.getString("definitionId"));
			workflowDto.setWorkflowName("PremiumOrderWorkflow");
			workflowDto.setSubject(resWfObj.getString("subject"));
			// workflowDto.setDescription(resWfObj.getString("description"));
			workflowDto.setBusinessKey(triggerWorkFlowDto.getAdhocOrderInfo().getUserId());
			workflowDto.setRequestedDate(ServicesUtil.convertDate(new Date()));
			workflowDto.setStatus(WorkflowConstants.PENDING_AT_MANAGER);
			workflowDto.setRequestedBy(resWfObj.getString("processor"));
			workflowDto.setPendingWith(triggerWorkFlowDto.getPendingWithManager());
			workflowDto.setWorkflowInstanceId(resWfObj.getString("workflowInstanceId"));
			adhocOrderDao.updateWorflowDetails(workflowDto);
			responseDto.setStatus(Boolean.TRUE);
			responseDto.setStatusCode(200);
		} catch (Exception e) {
			MYLOGGER.error("LCH | PremiumWorkFlowService | triggerPremiumWorkflow | Exception : " + e.getMessage());
			responseDto.setStatus(Boolean.FALSE);
			responseDto.setStatusCode(500);
			responseDto.setMessage(e.getMessage());
		}
		MYLOGGER.info(
				"LCH | PremiumWorkFlowService | triggerPremiumWorkflow | Execution Output : " + responseDto.toString());
		return responseDto;
	}

	/**
	 * Approve Task
	 * 
	 * @param ApproverUiDto
	 * @return ResponseDto Success/Failure message
	 */

	/*@Override
	public ResponseDataDto approveTask(WorkflowPremiumCustomDto dto) {
		MYLOGGER.info("LCH | WorkFlowService | approveTask | Execution Start Input : " + dto.toString());
		ResponseDataDto responseDto = new ResponseDataDto();
		try {
			responseDto.setStatus(Boolean.TRUE);
			responseDto.setStatusCode(200);
			// Map<String, String> destinationProperties = callDestination();
			
			 * JSONObject workflowInstanceId =
			 * premiumWorkflowInvokerLocal.getWorkflowApprovalTaskInstanceId(dto
			 * .getTaskIdDetails()); JSONArray taskArray =
			 * workflowInvokerLocal.getWorkflowTaskInstanceId((String)
			 * workflowInstanceId.get); JSONObject taskObj =
			 * taskArray.getJSONObject(0); String taskInstanceId =
			 * taskObj.getString(WorkflowConstants.ID);
			 
			String input = buildWorkflowApproverPayload(dto);
			HttpResponse wfResponse = workflowInvokerLocal.approveTask(input, taskInstanceId);
			if ((wfResponse.getStatusLine().getStatusCode()) == WorkflowConstants.SUCCESS_CODE) {
				premiumWorkflowInvokerLocal.updateTaskDetails(approverUiDto);
			}
		} catch (Exception e) {
			MYLOGGER.error("LCH | WorkFlowService | approveTask | Exception : " + e.getMessage());
			responseDto.setStatus(Boolean.FALSE);
			responseDto.setStatusCode(500);
			responseDto.setMessage(e.getMessage());
		}
		MYLOGGER.info("LCH | WorkFlowService | approveTask | Execution Output : " + responseDto.toString());
		return responseDto;
	}*/

	/**
	 * Build payload for trigger workflow
	 * 
	 * @param WorkFlowTriggerInputDto
	 * @throws JSONException
	 * @return Context Json string
	 */
	private String buildPremiumWorkflowTriggerPayload(PremiumWorkflowApprovalTaskDto triggerWorkFlowDto)
			throws JSONException {
		JSONObject reponse = new JSONObject();
		JSONObject context = new JSONObject();
		reponse.put(WorkflowConstants.DEFINITION_ID, WorkflowConstants.USER_WF_DEFINITION_ID_PREMIUM);
		context.put("orderId", triggerWorkFlowDto.getOrderId());
		context.put("originName", triggerWorkFlowDto.getOriginName());
		context.put("originAddress", triggerWorkFlowDto.getOriginAddress());
		context.put("originCity", triggerWorkFlowDto.getOriginCity());
		context.put("originState", triggerWorkFlowDto.getOriginState());
		context.put("originZip", triggerWorkFlowDto.getOriginZip());
		context.put("originCountry", triggerWorkFlowDto.getOriginCountry());
		context.put("originZip", triggerWorkFlowDto.getOriginZip());
		context.put("bpNumber", triggerWorkFlowDto.getBpNumber());
		context.put("carrierDetails", triggerWorkFlowDto.getCarrierDetails());
		context.put("carrierScac", triggerWorkFlowDto.getCarrierScac());
		context.put("carrierRatePerKM", triggerWorkFlowDto.getCarrierRatePerKM());
		context.put("carrierMode", triggerWorkFlowDto.getCarrierMode());
		// context.put("requestId", triggerWorkFlowDto.getRequestId());

		context.put("charge", triggerWorkFlowDto.getCharge());

		context.put("destinationName", triggerWorkFlowDto.getDestinationName());
		context.put("destinationCity", triggerWorkFlowDto.getDestinationCity());
		context.put("destinationState", triggerWorkFlowDto.getDestinationState());
		context.put("destinationZip", triggerWorkFlowDto.getDestinationZip());
		context.put("reasonCode", triggerWorkFlowDto.getReasonCode());

		context.put("originCountry", triggerWorkFlowDto.getOriginCountry());
		context.put("destinationCountry", triggerWorkFlowDto.getDestinationCountry());
		context.put("plannerEmail", triggerWorkFlowDto.getPlannerEmail());
		context.put("userName", triggerWorkFlowDto.getUserName());
		context.put("userEmail", triggerWorkFlowDto.getUserEmail());
		context.put("premiumReasonCode", triggerWorkFlowDto.getPremiumReasonCode());
		context.put("adhocType", triggerWorkFlowDto.getAdhocType());
		context.put("manager", triggerWorkFlowDto.getManager());
		context.put("planner", triggerWorkFlowDto.getPlanner());
		context.put("userGroup", triggerWorkFlowDto.getUserGroup());
		context.put("adhocOrderId", triggerWorkFlowDto.getAdhocOrderId());
		context.put("businessDivison", triggerWorkFlowDto.getBusinessDivision());
		context.put("adhocOrderInfo", triggerWorkFlowDto.getAdhocOrderInfo());

		context.put("pendingWithManager",triggerWorkFlowDto.getPendingWithManager());
		context.put("pendingWithAccountant",triggerWorkFlowDto.getPendingWithAccountant());
		
		reponse.put(WorkflowConstants.CONTEXT, context);
		return reponse.toString();
	}
	

	/**
	 * Build payload for approve task
	 * 
	 * @param ApproverUiDto
	 * @throws JSONException
	 * @return Context Json string
	 */
/*
	private String buildWorkflowApproverPayload(WorkflowPremiumCustomDto dto) throws JSONException {
		JSONObject response = new JSONObject();
		JSONObject context = new JSONObject();
		response.put(WorkflowConstants.STATUS, "completed");
		context.put("status", approverUiDto.getStatus());
		context.put("approverComments", approverUiDto.getApproverComments());
		if (ServicesUtil.isEmpty(approverUiDto.getApproverName())) {
			context.put("approverName", " ");
		} else {
			context.put("approverName", approverUiDto.getApproverName());
		}
		context.put("approverId", approverUiDto.getApproverId());
		if (approverUiDto.getStatus().equals(WorkflowConstants.APPROVED)) {
			context.put("isApproved", Boolean.TRUE);
		} else {
			context.put("isApproved", Boolean.FALSE);
		}
		response.put(WorkflowConstants.CONTEXT, context);
		return response.toString();
	}*/

	// private Map<String, String> callDestination() throws NamingException {
	// return
	// DestinationReaderUtil.getDestination(ApplicationConstants.WORKFLOW_DEST_NAME);
	// }

}
