package com.incture.lch.controller;

import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.incture.lch.dto.ResponseDto;
import com.incture.lch.dto.YardMailDto;
import com.incture.lch.service.YardMailService;

@RestController
@CrossOrigin
@RequestMapping(value = "/mail", produces = "application/json")

public class YardMailController {
	
	@Autowired
	private YardMailService service;
	
	@RequestMapping(value = "/sendMailToCarrier", method = RequestMethod.POST, consumes = { "application/json" })
	@ResponseBody
	public ResponseDto sendMail(@RequestBody List<YardMailDto> dtos) throws MessagingException {
		return service.sendEmailToCarrier(dtos);
	}

}
