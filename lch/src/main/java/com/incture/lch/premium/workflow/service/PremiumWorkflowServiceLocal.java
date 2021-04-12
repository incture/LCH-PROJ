package com.incture.lch.premium.workflow.service;

import com.incture.lch.adhoc.workflow.dto.PremiumWorkflowApprovalTaskDto;
import com.incture.lch.dto.ResponseDataDto;
import com.incture.lch.dto.UserDetailsDto;

public interface PremiumWorkflowServiceLocal {
	
	public ResponseDataDto triggerPremiumWorkflow(PremiumWorkflowApprovalTaskDto triggerWorkFlowDto,UserDetailsDto userDetails) ;


}
