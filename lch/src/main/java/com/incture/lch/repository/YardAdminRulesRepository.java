package com.incture.lch.repository;

import java.util.HashMap;
import java.util.List;

import com.incture.lch.dto.ResponseDto;
import com.incture.lch.dto.YardAdminRulesDto;

public interface YardAdminRulesRepository {

		public ResponseDto addYardAdminRules(YardAdminRulesDto yardAdminRulesDto);
		
		public List<YardAdminRulesDto> getYardAdminRules();
		
		public int deleteYardAdminRules();
		
		public ResponseDto addYardAdminRulesUpload(List<YardAdminRulesDto> list);
		
		public List<YardAdminRulesDto> getYardLocationByPendingWith(List<String> pendingWith);
		
		public List<String> getYardLocationsByYardId(String yardId);
		
		public List<String> getAllYardIds();
		
		public List<HashMap<String, Object>> getYardLocationsAndYardId();
		
}
