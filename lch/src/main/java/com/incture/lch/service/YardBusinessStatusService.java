package com.incture.lch.service;

import java.util.List;

import com.incture.lch.dto.ResponseDto;
import com.incture.lch.dto.YardBusinessStatusDto;

public interface YardBusinessStatusService {

	public ResponseDto addYardBusinessStatus(YardBusinessStatusDto yardBusinessStatusDto);
	
	public List<YardBusinessStatusDto> getYardBusinessStatus();

	public List<YardBusinessStatusDto> getYardBusinessStatusByRole(String role);
}
