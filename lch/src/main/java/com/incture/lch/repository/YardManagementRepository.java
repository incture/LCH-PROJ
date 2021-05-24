package com.incture.lch.repository;

import java.util.List;

import com.incture.lch.dto.ResponseDto;
import com.incture.lch.dto.YardManagementDto;
import com.incture.lch.dto.YardManagementFilterDto;
import com.incture.lch.dto.YardManagementKpiDto;

public interface YardManagementRepository {

	public ResponseDto addYardManagement(YardManagementDto yardManagementDto);
	
	public List<YardManagementDto> getYardManagementDetail(YardManagementFilterDto filterDto);
	
	public List<YardManagementDto> getAllYardManagements();
	
	public YardManagementKpiDto getYardManagementKpi(YardManagementFilterDto dto);
	
	public int getSecurityGaurdKpi(String loggedInUserId,List<String> status);
	
	public ResponseDto updateYardManagement(YardManagementDto yardManagementDto);
	
	
}
