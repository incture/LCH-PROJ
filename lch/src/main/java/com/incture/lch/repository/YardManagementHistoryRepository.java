package com.incture.lch.repository;

import java.util.List;
import java.util.Map;

import com.incture.lch.dto.ResponseDto;
import com.incture.lch.dto.YardManagementDto;
import com.incture.lch.dto.YardManagementFilterDto;
import com.incture.lch.dto.YardManagementHistoryDto;

public interface YardManagementHistoryRepository {

	public ResponseDto addYardManagementHistory(YardManagementHistoryDto yardManagementHistoryDto);
	
	public ResponseDto addYardManagementHistoryFromYard(YardManagementDto dto);
	
	public List<YardManagementHistoryDto> getYardManagementHistory(YardManagementFilterDto yardManagementFilterDto);
	
	public List<Map<String, Object>> inYarddayscount(List<String> foNums);
	
}
