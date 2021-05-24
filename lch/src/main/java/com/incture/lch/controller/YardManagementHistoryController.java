package com.incture.lch.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.incture.lch.dto.ResponseDto;
import com.incture.lch.dto.YardManagementFilterDto;
import com.incture.lch.dto.YardManagementHistoryDto;
import com.incture.lch.service.YardManagementHistoryService;

@RestController
@CrossOrigin
//@ComponentScan("com.incture.paccar.transport")
@RequestMapping(value = "/yardHistory", produces = "application/json")
public class YardManagementHistoryController {

	@Autowired
	YardManagementHistoryService yardManagementHistoryService;

	@RequestMapping(value = "/getYardManagementHistory", method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public List<YardManagementHistoryDto> getYardManagementKpi(@RequestBody YardManagementFilterDto filter) {

		return yardManagementHistoryService.getYardManagementHistory(filter);
	}
	
	@RequestMapping(value = "/downloadYardHistoryDetails", method = RequestMethod.POST, consumes = { "application/json" })
	@ResponseBody
	public ResponseDto downloadYardHistoryDetails(@RequestBody List<YardManagementHistoryDto> yardManagementHistoryDto) {
		return yardManagementHistoryService.downloadYardHistoryDetails(yardManagementHistoryDto);
	}

}
