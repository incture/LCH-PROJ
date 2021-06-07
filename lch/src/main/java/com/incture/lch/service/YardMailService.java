package com.incture.lch.service;

import java.util.List;

import javax.mail.MessagingException;

import com.incture.lch.dto.ResponseDto;
import com.incture.lch.dto.YardMailDto;

public interface YardMailService 
{
	ResponseDto sendEmailToCarrier(List<YardMailDto> dtos) throws MessagingException ;

}
