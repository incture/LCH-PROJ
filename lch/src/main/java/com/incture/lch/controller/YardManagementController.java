package com.incture.lch.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.incture.lch.dto.ResponseDto;
import com.incture.lch.dto.SecurityGuardKpiDto;
import com.incture.lch.dto.YardManagementDto;
import com.incture.lch.dto.YardManagementFilterDto;
import com.incture.lch.dto.YardManagementKpiDto;
import com.incture.lch.dto.YardManagementRequestDto;
import com.incture.lch.service.YardManagementService;
import com.incture.lch.service.YardManagementSlts4Service;

@RestController
@CrossOrigin
//@ComponentScan("com.incture.paccar.transport")
@RequestMapping(value = "/yard", produces = "application/json")
public class YardManagementController {

	@Autowired
	YardManagementService yardManagementService;
	
	@Autowired
	YardManagementSlts4Service yardManagementSlts4Service;
	
	@RequestMapping(value = "/addYardManagement", method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public ResponseDto addYardManagement(@RequestBody YardManagementRequestDto yardManagementDto) {
		return yardManagementService.addYardManagement(yardManagementDto);
	}
	
	@RequestMapping(value = "/addSecurityYardManagement", method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public ResponseDto addSecurityYardManagement(@RequestBody YardManagementRequestDto yardManagementRequestDto) {
		return yardManagementService.addSecurityYardManagement(yardManagementRequestDto);
	}
	
	@RequestMapping(value = "/getYardManagementDetail", method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public List<YardManagementDto> getYardManagementDetail(@RequestBody YardManagementFilterDto dto) {
		return yardManagementService.getYardManagementDetail(dto);
	}
	
	
	@RequestMapping(value = "/getYardManagementKpi", method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public List<YardManagementKpiDto> getYardManagementKpi(@RequestBody YardManagementFilterDto filter) {
		
		return yardManagementService.getYardManagementKpi(filter);
	}
	
	@RequestMapping(value = "/getSecurityGaurdKpi/{loggedInUserId}", method = RequestMethod.GET)
	@ResponseBody
	public List<SecurityGuardKpiDto> getSecurityGaurdKpi(@PathVariable(value="loggedInUserId") String loggedInUserId) {
		return yardManagementService.getSecurityGaurdKpi(loggedInUserId);
	}
	
	@RequestMapping(value = "/YardSlts4", method = RequestMethod.GET, consumes = "application/json")
	@ResponseBody
	public List<YardManagementDto> getYard() {
		return yardManagementSlts4Service.getYard();
	}
	
	@RequestMapping(value = "/downLoadYardAgingData", method = RequestMethod.POST, consumes = { "application/json" })
	@ResponseBody
	public ResponseDto downLoadYardAgingData(@RequestBody YardManagementFilterDto dto) {
		return yardManagementService.downLoadYardManagementAgingData(dto);
	}

	@RequestMapping(value = "/downloadYardDetails", method = RequestMethod.POST, consumes = { "application/json" })
	@ResponseBody
	public ResponseDto downloadYardDetails(@RequestBody List<YardManagementDto> yardManagementDto) {
		return yardManagementService.downloadYardDetails(yardManagementDto);
	}
}
