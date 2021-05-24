package com.incture.lch.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.incture.lch.dto.ResponseDto;
import com.incture.lch.dto.UploadExcelRequestDto;
import com.incture.lch.dto.YardAdminRulesDto;
import com.incture.lch.service.YardAdminRulesService;

@RestController
@CrossOrigin
//@ComponentScan("com.incture.paccar.transport")
@RequestMapping(value = "/yardrules", produces = "application/json")
public class YardAdminRulesController {
	
	@Autowired
	YardAdminRulesService yardAdminRulesService;

	@RequestMapping(value = "/addYardAdminRules", method = RequestMethod.POST, consumes = { "application/json" })
	@ResponseBody
	public ResponseDto addYardAdminRules(@RequestBody YardAdminRulesDto requestDto) {
		return yardAdminRulesService.addYardAdminRules(requestDto);
	}

	@RequestMapping(value = "/uploadYardAdminRules", method = RequestMethod.POST, consumes = { "application/json" })
	@ResponseBody
	public ResponseDto uploadYardAdminRules(@RequestBody UploadExcelRequestDto file) {
		ResponseDto responseDto = new ResponseDto();
		responseDto = yardAdminRulesService.uploadYardAdminRules(file.getFile(), file.getType());
		return responseDto;
	}

	@RequestMapping(value = "/downloadYardAdminRules", method = RequestMethod.GET, consumes = { "application/json" })
	@ResponseBody
	public ResponseDto downloadYardAdminRulesData() {
		return yardAdminRulesService.downLoadYardAdminRules();
	}

	@RequestMapping(value = "/getYardAdminRules", method = RequestMethod.POST, consumes = { "application/json" })
	@ResponseBody
	public List<YardAdminRulesDto> getYardAdminRules() {
		return yardAdminRulesService.getYardAdminRules();
	}
	
	@RequestMapping(value = "/getYardLocationsByYardId", method = RequestMethod.GET, consumes = { "application/json" })
	@ResponseBody
	public  List<HashMap<String , String>> getYardLocationsByYardId(@RequestParam(value="yardId") String yardId) {
		return yardAdminRulesService.getYardLocationsByYardId(yardId);
	}
	
	@RequestMapping(value = "/getAllYardIds", method = RequestMethod.GET, consumes = { "application/json" })
	@ResponseBody
	public  List<HashMap<String , String>> getAllYardIds() {
		return yardAdminRulesService.getAllYardIds();
	}
	

	@RequestMapping(value = "/getYardLocationByPendingWith", method = RequestMethod.POST, consumes = {"application/json"})
	@ResponseBody
	public List<YardAdminRulesDto> getYardLocationByPendingWith(@RequestBody List<String> pendingWith) {
		return yardAdminRulesService.getYardLocationByPendingWith(pendingWith);
	}
	
	@RequestMapping(value = "/getYardLocationsAndYardId", method = RequestMethod.GET, consumes = { "application/json" })
	@ResponseBody
	public  List<HashMap<String , Object>> getYardLocationsAndYardId() {
		return yardAdminRulesService.getYardLocationsAndYardId();
	}
}
