package com.incture.lch.service;

import java.util.List;

import com.incture.lch.dto.ResponseDto;
import com.incture.lch.dto.SecurityGuardKpiDto;
import com.incture.lch.dto.YardManagementDto;
import com.incture.lch.dto.YardManagementFilterDto;
import com.incture.lch.dto.YardManagementKpiDto;
import com.incture.lch.dto.YardManagementRequestDto;

public interface YardManagementService {

	public ResponseDto addYardManagement(YardManagementDto yardManagementDto);
		
	public ResponseDto addYardManagement(YardManagementRequestDto yardManagementDto);
	
	public ResponseDto addSecurityYardManagement(YardManagementRequestDto yardManagementDto);
	
	public List<YardManagementDto> getYardManagementDetail(YardManagementFilterDto filterDto);
	
	public List<YardManagementDto> getAllYardManagements();
	
	public List<YardManagementKpiDto>  getYardManagementKpi(YardManagementFilterDto filter);
	
	public List<SecurityGuardKpiDto> getSecurityGaurdKpi(String loggedInUserId);
	
	public ResponseDto downLoadYardManagementAgingData(YardManagementFilterDto filterDto);

	public ResponseDto downloadYardDetails(List<YardManagementDto> yardManagementDto);
	
}
