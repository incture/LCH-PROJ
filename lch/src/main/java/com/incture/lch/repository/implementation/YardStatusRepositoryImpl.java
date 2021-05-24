package com.incture.lch.repository.implementation;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.incture.lch.dto.YardStatusDto;
import com.incture.lch.entity.YardStatus;
import com.incture.lch.repository.YardStatusRepository;

@Repository
public class YardStatusRepositoryImpl implements YardStatusRepository {

	@Autowired
	//@Qualifier("sessiondb")
	private SessionFactory sessionFactory;

	public YardStatus importYardStatus(YardStatusDto yardDto) {
		YardStatus yardStatus = new YardStatus();
		yardStatus.setStatusId(yardDto.getStatusId());
		yardStatus.setStatusDesc(yardDto.getStatusDesc());
		yardStatus.setGroupOrKpi(yardDto.getGroupOrKpi());
		return yardStatus;
	}

	public YardStatusDto exportYardStatus(YardStatus yardStatus) {
		YardStatusDto yardStatusDto = new YardStatusDto();
		yardStatusDto.setStatusId(yardStatus.getStatusId());
		yardStatusDto.setStatusDesc(yardStatus.getStatusDesc());
		yardStatusDto.setGroupOrKpi(yardStatus.getGroupOrKpi());
		return yardStatusDto;
	}


	@SuppressWarnings("unchecked")
	public List<YardStatusDto> getAllYardStatus() {
		List<YardStatusDto> yardStatusDto = new ArrayList<>();
		List<YardStatus> yardStatus = new ArrayList<>();
		String querry = "select ys from YardStatus ys";
		Query query = sessionFactory.getCurrentSession().createQuery(querry);
		yardStatus = query.list();
		for (YardStatus ym : yardStatus) {
			yardStatusDto.add(exportYardStatus(ym));
		}
		return yardStatusDto;
	}
	
	@SuppressWarnings("unchecked")
	public List<YardStatusDto> getYardStatusBasedonFilter() {
		List<YardStatusDto> yardStatusDto = new ArrayList<>();
		List<YardStatus> yardStatus = new ArrayList<>();
		List<String>  agingKpiGroup = new ArrayList<>();
		agingKpiGroup.add("At Dock");
		agingKpiGroup.add("In Yard");
		StringBuilder str = new StringBuilder("select ys from YardStatus ys ");
		
		str.append(" where ys.groupOrKpi in :groupOrKpi");
		
		
		Query query = sessionFactory.getCurrentSession().createQuery(str.toString());
		
			query.setParameterList("groupOrKpi", agingKpiGroup);
		
		yardStatus = query.list();
		for (YardStatus ym : yardStatus) {
			yardStatusDto.add(exportYardStatus(ym));
		}
		return yardStatusDto;
	}
	
	
	

	
	

}
