package com.incture.lch.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.incture.lch.dto.YardDestinationDetailsDto;
import com.incture.lch.dto.YardRoleDto;
import com.incture.lch.entity.YardDestinationDetails;
import com.incture.lch.entity.YardRole;

@Repository
public class YardDestinationDetailsDao
{


	@Autowired
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<YardDestinationDetailsDto> getYardDestinationDetails() {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		List<YardDestinationDetailsDto> destinationDto= new ArrayList<YardDestinationDetailsDto>();
		List<YardDestinationDetails> destinationDo = new ArrayList<YardDestinationDetails>();
		
		Criteria criteria_des= session.createCriteria(YardDestinationDetails.class);
		destinationDo=criteria_des.list();
		
		for(YardDestinationDetails y: destinationDo)
		{
			YardDestinationDetailsDto dto= new YardDestinationDetailsDto();
			dto.setDestId(y.getDestId());
			dto.setDestinationDesc(y.getDestDesc());
			destinationDto.add(dto);
		}
		session.flush();
		session.clear();
		tx.commit();
		session.close();
		return destinationDto;
		
	}

	public String addDestinationDetails(List<YardDestinationDetailsDto> dtos) {

		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		for (YardDestinationDetailsDto d : dtos) {
			YardDestinationDetails destination = new YardDestinationDetails();
			destination.setDestId(d.getDestId());
			destination.setDestDesc(d.getDestinationDesc());
			session.saveOrUpdate(destination);
		}
		session.flush();
		session.clear();
		tx.commit();
		session.close();
		return "Success";
	}


}
