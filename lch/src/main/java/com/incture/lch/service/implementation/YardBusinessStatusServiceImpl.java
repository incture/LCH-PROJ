package com.incture.lch.service.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.incture.lch.dto.ResponseDto;
import com.incture.lch.dto.YardBusinessStatusDto;
import com.incture.lch.repository.YardBusinessStatusRepository;
import com.incture.lch.service.YardBusinessStatusService;

@Service
@Transactional
public class YardBusinessStatusServiceImpl implements YardBusinessStatusService {

	@Autowired
	YardBusinessStatusRepository yardBusinessStatusRepository;
	
	@Override
	public ResponseDto addYardBusinessStatus(YardBusinessStatusDto yardBusinessStatusDto) {
		return yardBusinessStatusRepository.addYardBusinessStatus(yardBusinessStatusDto);
	}

	@Override
	public List<YardBusinessStatusDto> getYardBusinessStatus() {
		return yardBusinessStatusRepository.getYardBusinessStatus();
	}

	@Override
	public List<YardBusinessStatusDto> getYardBusinessStatusByRole(String role) {
		return yardBusinessStatusRepository.getYardBusinessStatusByRole(role);
	}

}
