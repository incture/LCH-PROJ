package com.incture.lch.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.incture.lch.dto.YardStatusDto;
import com.incture.lch.service.YardStatusService;


@RestController
@CrossOrigin
//@ComponentScan("com.incture.paccar.transport")
@RequestMapping(value = "/yardStatus", produces = "application/json")
public class YardStatusController {
	
	@Autowired
	YardStatusService yardStatusService;

	@RequestMapping(value = "/getAllYardStatus", method = RequestMethod.GET, consumes = { "application/json" })
	@ResponseBody
	public List<YardStatusDto> getYardStatus() {
		return yardStatusService.getYardStatus();
	}
	
	
	
	
	
}
