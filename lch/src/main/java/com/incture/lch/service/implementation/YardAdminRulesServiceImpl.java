package com.incture.lch.service.implementation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.incture.lch.dto.ResponseDto;
import com.incture.lch.dto.YardAdminRulesDto;
import com.incture.lch.helper.YardAdminRulesExcelUtil;
import com.incture.lch.helper.YardAdminRulesReadExcel;
import com.incture.lch.repository.YardAdminRulesRepository;
import com.incture.lch.service.YardAdminRulesService;

@Service
@Transactional()
public class YardAdminRulesServiceImpl implements YardAdminRulesService {
	
	@Autowired
	YardAdminRulesRepository YardAdminRulesRepository;
	
	@Autowired
	YardAdminRulesExcelUtil util;
	
	@Autowired
	YardAdminRulesReadExcel readExcel;

	@Override
	public ResponseDto addYardAdminRules(YardAdminRulesDto YardAdminRulesDto) {
		return YardAdminRulesRepository.addYardAdminRules(YardAdminRulesDto);
	}

	@Override
	public List<YardAdminRulesDto> getYardAdminRules() {
		return YardAdminRulesRepository.getYardAdminRules();
	}
	
	public ResponseDto downLoadYardAdminRules() {
		List<YardAdminRulesDto> list = new ArrayList<YardAdminRulesDto>();
		list = getYardAdminRules();
		return util.excelDownload(list);
	}
	
	public ResponseDto uploadYardAdminRules(String fileLocation, String ext) {

		ResponseDto responseDto = new ResponseDto();
		List<YardAdminRulesDto> list = readExcel.readExcelDataFormat(fileLocation, ext);
		deleteYardAdminRules();
		responseDto = YardAdminRulesRepository.addYardAdminRulesUpload(list);
		responseDto.setCode("00");
		responseDto.setMessage("Upload Success");
		responseDto.setStatus("SUCCESS");

		return responseDto;
	}

	@Override
	public int deleteYardAdminRules() {
		return YardAdminRulesRepository.deleteYardAdminRules();
	}

	@Override
	public List<HashMap<String , String>> getYardLocationsByYardId(String yardId) {
		List<HashMap<String , String>> locationList = new ArrayList<>();
		YardAdminRulesRepository.getYardLocationsByYardId(yardId).stream().forEach(loc -> {
			HashMap<String,String> hm=new HashMap<>();
			hm.put("yardLocation", loc);
			locationList.add(hm); });
		return locationList;
	}

	@Override
	public List<HashMap<String , String>> getAllYardIds() {
		List<HashMap<String , String>> yardIdList = new ArrayList<>();
		YardAdminRulesRepository.getAllYardIds().stream().forEach(loc -> {
			HashMap<String,String> hm=new HashMap<>();
			hm.put("yardId", loc);
			yardIdList.add(hm); });
		return yardIdList;
	}
	
	@Override
	public List<YardAdminRulesDto> getYardLocationByPendingWith(List<String> pendingWith) {
		if(pendingWith.size()>0)
		{
			return YardAdminRulesRepository.getYardLocationByPendingWith(pendingWith);
		}
		return null;
	}

	@Override
	public List<HashMap<String, Object>> getYardLocationsAndYardId() {
		return YardAdminRulesRepository.getYardLocationsAndYardId();
	}
	
}
