package com.incture.lch.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transaction;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.incture.lch.dto.AdhocRoadmapDto;

public class AdhocRoadmapDao {
	@Autowired
	private SessionFactory sessionFactory;

	public Map<String,List<AdhocRoadmapDto>> getAllStatus(String orderId)
	{
	
       Map<String,List<AdhocRoadmapDto>> statusMap= new HashMap<String,List<AdhocRoadmapDto>>();
       Session  session = sessionFactory.openSession();
	   org.hibernate.Transaction tx= session.beginTransaction();
       
       //for()
       return statusMap;
    		   
        }

}
