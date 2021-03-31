package com.incture.lch.premium.workflow.service;

import com.incture.lch.adhoc.workflow.dto.PremiumWorkflowApprovalTaskDto;
import com.incture.lch.dto.ResponseDataDto;

public interface PremiumWorkflowServiceLocal {
	
	public ResponseDataDto triggerPremiumWorkflow(PremiumWorkflowApprovalTaskDto triggerWorkFlowDto) ;


}
