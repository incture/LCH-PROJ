package com.incture.lch.repository.implementation;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.incture.lch.dto.ResponseDto;
import com.incture.lch.dto.YardBusinessStatusDto;
import com.incture.lch.entity.YardBusinessStatus;
import com.incture.lch.repository.YardBusinessStatusRepository;

@Repository
public class YardBusinessStatusRepositoryImpl implements YardBusinessStatusRepository {

	@Autowired
	//@Qualifier("sessiondb")
	private SessionFactory sessionFactory;

	public YardBusinessStatus importYardBusinessStatus(YardBusinessStatusDto yardBusinessStatusDto)
	{
		YardBusinessStatus yardBusinessStatus = new YardBusinessStatus();
		yardBusinessStatus.setBusinessRole(yardBusinessStatusDto.getBusinessRole());
		yardBusinessStatus.setBusinessStatusDesc(yardBusinessStatusDto.getBusinessStatusDesc());
		yardBusinessStatus.setBusinessStatusId(yardBusinessStatusDto.getBusinessStatusId());
		return yardBusinessStatus;
	}
	
	public YardBusinessStatusDto exportYardBusinessStatus(YardBusinessStatus yardBusinessStatus)
	{
		YardBusinessStatusDto yardBusinessStatusDto = new YardBusinessStatusDto();
		yardBusinessStatusDto.setBusinessRole(yardBusinessStatus.getBusinessRole());
		yardBusinessStatusDto.setBusinessStatusDesc(yardBusinessStatus.getBusinessStatusDesc());
		yardBusinessStatusDto.setBusinessStatusId(yardBusinessStatus.getBusinessStatusId());

		return yardBusinessStatusDto;
	}
	
	@Override
	public ResponseDto addYardBusinessStatus(YardBusinessStatusDto yardBusinessStatusDto) {
		ResponseDto responseDto = new ResponseDto();
		sessionFactory.getCurrentSession().saveOrUpdate(importYardBusinessStatus(yardBusinessStatusDto));
		responseDto.setMessage("SUCCESS");
		responseDto.setStatus("00");
		responseDto.setCode("SUCCESS");
		return responseDto;
	}

	@SuppressWarnings("unchecked")
	public List<YardBusinessStatusDto> getYardBusinessStatus() {
		List<YardBusinessStatus> list = new ArrayList<>();
		List<YardBusinessStatusDto> listStatuses = new ArrayList<>();
		String hqlString = "select y from YardBusinessStatus y";
		list = sessionFactory.getCurrentSession().createQuery(hqlString).list();
		for (YardBusinessStatus yardAdminRules : list) {
			listStatuses.add(exportYardBusinessStatus(yardAdminRules));
		}
		return listStatuses;
	}
	
	@SuppressWarnings("unchecked")
	public List<YardBusinessStatusDto> getYardBusinessStatusByRole(String role) {
		List<YardBusinessStatusDto> listStatuses = new ArrayList<>();
		String hqlString = "select y from YardBusinessStatus y Where businessRole=:businessRole";
		Query query = sessionFactory.getCurrentSession().createQuery(hqlString.toString());
		query.setParameter("businessRole", role);
		List<YardBusinessStatus> objectsList = (List<YardBusinessStatus>) query.list();
		for (YardBusinessStatus yardAdminRules : objectsList) {
			listStatuses.add(exportYardBusinessStatus(yardAdminRules));
		}
		return listStatuses;
	}
}
