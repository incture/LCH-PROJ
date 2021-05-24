package com.incture.lch.service;

import java.util.List;

import com.incture.lch.dto.ResponseDto;
import com.incture.lch.dto.YardManagementDto;
import com.incture.lch.dto.YardManagementFilterDto;
import com.incture.lch.dto.YardManagementHistoryDto;

public interface YardManagementHistoryService {

	public ResponseDto addYardManagementHistory(YardManagementHistoryDto yardManagementHistoryDto);
	
	public ResponseDto addYardManagementHistoryFromYard(YardManagementDto dto);
	
	public List<YardManagementHistoryDto> getYardManagementHistory(YardManagementFilterDto yardManagementFilterDto);

	public ResponseDto downloadYardHistoryDetails(List<YardManagementHistoryDto> yardManagementHistoryDto);
}
