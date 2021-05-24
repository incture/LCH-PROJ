package com.incture.lch.service.implementation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.incture.lch.dto.ResponseDto;
import com.incture.lch.dto.SecurityGuardKpiDto;
import com.incture.lch.dto.YardManagementDto;
import com.incture.lch.dto.YardManagementFilterDto;
import com.incture.lch.dto.YardManagementKpiDto;
import com.incture.lch.dto.YardManagementRequestDto;
import com.incture.lch.dto.YardStatusDto;
import com.incture.lch.helper.YardDetailsExcelUtil;
import com.incture.lch.helper.YardManagementExcelUtil;
import com.incture.lch.repository.YardManagementRepository;
import com.incture.lch.repository.YardStatusRepository;
import com.incture.lch.service.YardManagementService;
import com.incture.lch.util.ServiceUtil;

@Service
@Transactional()
public class YardManagementServiceImpl implements YardManagementService {

	@Autowired
	YardManagementRepository yardManagementRepository;

	@Autowired
	YardStatusRepository yardStatusRepository;

	@Autowired
	YardManagementExcelUtil yardManagementExcelUtil;
	
	@Autowired
	YardDetailsExcelUtil excelUtil;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(YardManagementServiceImpl.class);
	 
	@Override
	public ResponseDto addYardManagement(YardManagementDto yardManagementDto) {
		return yardManagementRepository.addYardManagement(yardManagementDto);
	}

	@Override
	public ResponseDto addYardManagement(YardManagementRequestDto yardManagementRequestDto) {
		ResponseDto responseDto = new ResponseDto();
		for (YardManagementDto yardManagementDto : yardManagementRequestDto.getYardManagementDto()) {
			if(yardManagementDto.getId()!=null)
			{
				LOGGER.error("update yardmanagement "+yardManagementDto.toString());
				responseDto=yardManagementRepository.updateYardManagement(yardManagementDto);
			}else
			{
				LOGGER.error("add yardmanagement "+yardManagementDto.toString());
				responseDto = yardManagementRepository.addYardManagement(yardManagementDto);
			}	
		}
		return responseDto;
	}


	@Override
	public List<YardManagementDto> getYardManagementDetail(YardManagementFilterDto filterDto) {
		List<YardManagementDto> modified = new ArrayList<>();
		List<YardManagementDto> list = yardManagementRepository.getYardManagementDetail(filterDto);
		if ((filterDto.getFromDate() != null && !(filterDto.getFromDate().equals("")))
				&& (filterDto.getToDate() != null) && !(filterDto.getToDate().equals(""))) {
				Date from = ServiceUtil.convertStringToDateForYard(filterDto.getFromDate());
	        	Date to = ServiceUtil.convertStringToDateForYard(filterDto.getToDate());
	        	for(YardManagementDto obj : list) {
	        		if(obj.getPlannedShipDate() != null && !(obj.getPlannedShipDate().equalsIgnoreCase("0")) 
	        				&& !(obj.getPlannedShipDate().equalsIgnoreCase("0"))) {
//	        			if ((filterDto.getFromDate()).compareTo(obj.getPlannedShipDate()) <= 0
//								&& (filterDto.getToDate()).compareTo(obj.getPlannedShipDate()) >= 0)
//	        			{
//	        				modified.add(obj);
//	        			}
		        		 if (from.compareTo(ServiceUtil.convert(obj.getPlannedShipDate())) <= 0 && 
		        				 to.compareTo(ServiceUtil.convert(obj.getPlannedShipDate())) >= 0) {
		        			 modified.add(obj);
		         		} 
	        		}
	        	}

		} else {
			return list;
		}

		return modified;
	}

	@Override
	public List<YardManagementDto> getAllYardManagements() {
		return yardManagementRepository.getAllYardManagements();
	}

	@Override
	public List<YardManagementKpiDto> getYardManagementKpi(YardManagementFilterDto filter) {
		List<YardManagementKpiDto> kpiList = new ArrayList<>();
		List<YardStatusDto> yardStatusDtos = yardStatusRepository.getAllYardStatus();
		Map<String, List<YardStatusDto>> statusList = yardStatusDtos.stream()
				.collect(Collectors.groupingBy(YardStatusDto::getGroupOrKpi));
		
	//	LOGGER.error("status list :"+statusList);
		
		for (Map.Entry<String, List<YardStatusDto>> entry : statusList.entrySet()) {
			
			//LOGGER.error("entry : "+entry.getValue().toString());
			YardManagementKpiDto dto = new YardManagementKpiDto();

			List<String> listStatusId = entry.getValue().stream().map(YardStatusDto::getStatusId)
					.collect(Collectors.toList());
		//	LOGGER.error("List status id :"+listStatusId);
			filter.setStatusList(listStatusId);
			
		//	LOGGER.error("filter :"+filter.toString());
		
			dto = yardManagementRepository.getYardManagementKpi(filter);
			dto.setGroupOrKpi(entry.getKey());
			
		//	LOGGER.error("dto :"+dto.toString());
			
			
			kpiList.add(dto);
		}
		return kpiList;
	}
	
	
	public List<SecurityGuardKpiDto> getSecurityGaurdKpi(String loggedInUserId) {
		List<SecurityGuardKpiDto> kpiList = new ArrayList<>();
		SecurityGuardKpiDto kpi = null;
		int count = 0;
		List<YardStatusDto> statusDto = yardStatusRepository.getAllYardStatus();
		Map<String, List<YardStatusDto>> statusList = statusDto.stream()
				.collect(Collectors.groupingBy(YardStatusDto::getGroupOrKpi));
		for (Map.Entry<String, List<YardStatusDto>> entry : statusList.entrySet()) {
			kpi = new SecurityGuardKpiDto();
			List<String> listStatusId = entry.getValue().stream().map(YardStatusDto::getStatusId)
					.collect(Collectors.toList());
			count = yardManagementRepository.getSecurityGaurdKpi(loggedInUserId, listStatusId);
			kpi.setCount(count);
			kpi.setKpi(entry.getKey());
			kpiList.add(kpi);
		}

		return kpiList;
	}

	@Override
	public ResponseDto addSecurityYardManagement(YardManagementRequestDto yardManagementRequestDto) {
		ResponseDto responseDto = new ResponseDto();

		for (YardManagementDto yardManagementDto : yardManagementRequestDto.getYardManagementDto()) {
			responseDto = yardManagementRepository.addYardManagement(yardManagementDto);
		}

		return responseDto;
	}
	
	public ResponseDto downLoadYardManagementAgingData(YardManagementFilterDto filterDto) {
		List<YardManagementDto> list = getYardManagementDetail(filterDto);
		ResponseDto res = yardManagementExcelUtil.excelDownload(list);
		return res;
	}
	
	@Override
	public ResponseDto downloadYardDetails(List<YardManagementDto> yardManagementDto)
	{
		return excelUtil.excelDownload(yardManagementDto);
	}


}
