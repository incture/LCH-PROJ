package com.incture.lch.repository;

import java.util.List;

import com.incture.lch.dto.ResponseDto;
import com.incture.lch.dto.YardBusinessStatusDto;

public interface YardBusinessStatusRepository {

	public ResponseDto addYardBusinessStatus(YardBusinessStatusDto yardBusinessStatusDto);
	
	public List<YardBusinessStatusDto> getYardBusinessStatus();
	
	public List<YardBusinessStatusDto> getYardBusinessStatusByRole(String role);
}
