package com.incture.lch.repository;

import java.util.List;

import com.incture.lch.dto.YardStatusDto;

public interface YardStatusRepository {

	public List<YardStatusDto> getAllYardStatus();
	
	public List<YardStatusDto> getYardStatusBasedonFilter() ;

}
