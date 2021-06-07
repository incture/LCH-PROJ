package com.incture.lch.service.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.incture.lch.dto.ResponseDto;
import com.incture.lch.dto.YardManagementDto;
import com.incture.lch.dto.YardManagementFilterDto;
import com.incture.lch.dto.YardManagementHistoryDto;
import com.incture.lch.helper.YardHistoryDetailsExcelUtil;
import com.incture.lch.repository.YardManagementHistoryRepository;
import com.incture.lch.service.YardManagementHistoryService;

@Service
@Transactional
public class YardManagementHistoryServiceImpl implements YardManagementHistoryService {

	@Autowired
	YardManagementHistoryRepository yardManagementHistoryRepository;

	@Autowired
	YardHistoryDetailsExcelUtil excelUtil;
	
	@Override
	public ResponseDto addYardManagementHistory(YardManagementHistoryDto yardManagementHistoryDto) {
		return yardManagementHistoryRepository.addYardManagementHistory(yardManagementHistoryDto);
	}

	@Override
	public ResponseDto addYardManagementHistoryFromYard(YardManagementDto dto) {
		return yardManagementHistoryRepository.addYardManagementHistoryFromYard(dto);
	}

	@Override
	public List<YardManagementHistoryDto> getYardManagementHistory(YardManagementFilterDto yardManagementFilterDto) {
		return yardManagementHistoryRepository.getYardManagementHistory(yardManagementFilterDto);
	}

	@Override
	public ResponseDto downloadYardHistoryDetails(List<YardManagementHistoryDto> yardManagementHistoryDto) {
		return excelUtil.excelDownload(yardManagementHistoryDto);
	}
	
	
}
