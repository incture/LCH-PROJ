package com.incture.lch.service.implementation;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.incture.lch.dto.YardManagementDto;
import com.incture.lch.repository.YardSlts4Repository;
import com.incture.lch.service.YardManagementSlts4Service;
@Service
@Transactional()
public class YardManagementSlts4ServiceImpl implements YardManagementSlts4Service
{
	@Autowired
	YardSlts4Repository yardSlts4Repository;
	
	@Override
	public List<YardManagementDto> getYard() 
	{
		return yardSlts4Repository.getYard();
	}
}

