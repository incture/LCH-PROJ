package com.incture.lch.service;

import java.util.HashMap;
import java.util.List;

import com.incture.lch.dto.ResponseDto;
import com.incture.lch.dto.YardAdminRulesDto;

public interface YardAdminRulesService {

	public ResponseDto addYardAdminRules(YardAdminRulesDto YardAdminRulesDto);
	
	public List<YardAdminRulesDto> getYardAdminRules();
	
	public ResponseDto uploadYardAdminRules(String fileLocation, String ext);
	
	public ResponseDto downLoadYardAdminRules();
	
	public int deleteYardAdminRules();
	
	public List<HashMap<String , String>> getYardLocationsByYardId(String yardId);
	
	public List<HashMap<String , String>> getAllYardIds();
	
	public List<YardAdminRulesDto> getYardLocationByPendingWith(List<String> pendingWith);

	public List<HashMap<String, Object>> getYardLocationsAndYardId();
	
}
