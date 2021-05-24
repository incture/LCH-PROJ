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

import com.incture.lch.dto.YardRoleDto;
import com.incture.lch.entity.YardRole;
@Repository
public class YardRoleDao {

	@Autowired
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<String> getYardRole(String userId) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		List<YardRoleDto> roleDtos = new ArrayList<YardRoleDto>();
		List<YardRole> roles = new ArrayList<YardRole>();

		List<String> rolesDescription= new ArrayList<String>();
		Criteria criteria = session.createCriteria(YardRole.class);
		criteria.add(Restrictions.eq("userId", userId));
		roles = criteria.list();
		for (YardRole role : roles) {
			YardRoleDto r = new YardRoleDto();
			r.setUserId(role.getUserId());
			r.setUserEmail(role.getUserEmail());
			r.setRole(role.getRole());
			roleDtos.add(r);
			rolesDescription.add(role.getRole());
		}
		session.flush();
		session.clear();
		tx.commit();
		session.close();
		//return roleDtos;
		return rolesDescription;
	}

	public String addYardRole(List<YardRoleDto> roleDtos) {

		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		for (YardRoleDto r : roleDtos) {
			System.out.println("Inside Loop");
			YardRole role = new YardRole();
			System.out.println(r.getUserEmail());
			role.setUserId(r.getUserId());
			role.setUserEmail(r.getUserEmail());
			role.setRole(r.getRole());
			session.saveOrUpdate(role);
		}
		session.flush();
		session.clear();
		tx.commit();
		session.close();
		return "Success";
	}



}
