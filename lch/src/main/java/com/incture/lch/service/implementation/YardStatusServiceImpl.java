package com.incture.lch.service.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.incture.lch.dto.YardStatusDto;
import com.incture.lch.repository.YardStatusRepository;
import com.incture.lch.service.YardStatusService;

@Service
@Transactional
public class YardStatusServiceImpl implements YardStatusService {

	@Autowired
	YardStatusRepository yardStatusRepository;

	@Override
	public List<YardStatusDto> getYardStatus() {
		return yardStatusRepository.getAllYardStatus();
	}

}
