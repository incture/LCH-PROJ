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

import com.incture.lch.dto.RoleDto;
import com.incture.lch.entity.LchRole;

@Repository

public class RoleDao {

	@Autowired
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	public List<String> getRole(String userId) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		List<RoleDto> roleDtos = new ArrayList<RoleDto>();
		List<LchRole> roles = new ArrayList<LchRole>();

		List<String> rolesDescription= new ArrayList<String>();
		Criteria criteria = session.createCriteria(LchRole.class);
		criteria.add(Restrictions.eq("userId", userId));
		roles = criteria.list();
		for (LchRole role : roles) {
			RoleDto r = new RoleDto();
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

	public String addRole(List<RoleDto> roleDtos) {

		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		for (RoleDto r : roleDtos) {
			System.out.println("Inside Loop");
			LchRole role = new LchRole();
			System.out.println(r.getUserId());
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
