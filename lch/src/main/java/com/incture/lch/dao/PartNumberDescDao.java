package com.incture.lch.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.incture.lch.dto.PartNumberDescDto;
import com.incture.lch.entity.PartNumberDesc;

@Repository
public class PartNumberDescDao {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}
	
	public PartNumberDesc importPartDesc(PartNumberDescDto dto) {
		PartNumberDesc partDo = new PartNumberDesc();
		partDo.setPartNum(dto.getPartNum());
		partDo.setPartDesc(dto.getPartDesc());
		partDo.setMessage(dto.getMessage());
		return partDo;

	}
	
	public PartNumberDescDto exportPartDesc(PartNumberDesc partDo) {
		PartNumberDescDto partDto = new PartNumberDescDto();
		partDto.setPartNum(partDo.getPartNum());
		partDto.setPartDesc(partDo.getPartDesc());
		partDto.setMessage(partDo.getMessage());
		return partDto;

	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<PartNumberDescDto> getAllPartDetails() {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		List<PartNumberDescDto> partDtoList = new ArrayList<>();
		List<PartNumberDesc> partList = new ArrayList<>();

		try {
			String queryStr = "select part from PartNumberDesc part";
			Query query = session.createQuery(queryStr);
			partList = query.list();
			for (PartNumberDesc lkDiv : partList) {
				partDtoList.add(exportPartDesc(lkDiv));
			}
		} catch (Exception e) {
			System.err.println("Exception in getReasonCode api" + e);
		} finally {
			session.flush();
			session.clear();
			tx.commit();
			session.close();
		}
		return partDtoList;

	}
	
	public Boolean savePartDetails(List<PartNumberDescDto> partDtoList) {
		Boolean isSaved = false;
		
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		for (PartNumberDescDto dto : partDtoList) {
			try {
				
				session.saveOrUpdate(importPartDesc(dto));
			} catch (Exception e) {
				throw new RuntimeException("Error while saving data:: " + e.toString());
			}
		}
		session.flush();
		session.clear();
		tx.commit();
		session.close();
		return isSaved;

	}

	public PartNumberDescDto getDetailsByPartNumber(String partNumber) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		List<PartNumberDescDto> partdtolist = new ArrayList<>();
		String queryStr = "select part from PartNumberDesc part where part.partNum=:partNum";
		Query query = session.createQuery(queryStr);
		query.setParameter("partNum", partNumber);
		@SuppressWarnings("unchecked")			PartNumberDescDto pdto= new PartNumberDescDto();

		List<PartNumberDesc> listData = query.list();
		for (PartNumberDesc rule : listData) {
			pdto=exportPartDesc(rule);

			partdtolist.add(exportPartDesc(rule));
		}
		session.flush();
		session.clear();
		tx.commit();
		session.close();
		return pdto;
	}
}
