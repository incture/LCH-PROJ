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

import com.incture.lch.dto.PremiumRoleDto;
import com.incture.lch.entity.PremiumRole;

@Repository
public class PremiumRoleDao 
{

	@Autowired
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<String> getPremiumRole(String userId) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		List<PremiumRoleDto> roleDtos = new ArrayList<PremiumRoleDto>();
		List<PremiumRole> roles = new ArrayList<PremiumRole>();

		List<String> rolesDescription= new ArrayList<String>();
		Criteria criteria = session.createCriteria(PremiumRole.class);
		criteria.add(Restrictions.eq("userId", userId));
		roles = criteria.list();
		for (PremiumRole role : roles) {
			PremiumRoleDto r = new PremiumRoleDto();
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

	public String addPremiumRole(List<PremiumRoleDto> roleDtos) {

		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		for (PremiumRoleDto r : roleDtos) {
			System.out.println("Inside Loop");
			PremiumRole role = new PremiumRole();
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
