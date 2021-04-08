package com.incture.lch.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.incture.lch.dto.AccountingDetailsDto;
import com.incture.lch.dto.AdhocApprovalRuleDto;
import com.incture.lch.entity.AccountingDetails;
import com.incture.lch.entity.AdhocApprovalRule;

@Repository
public class AccountingDetailsDao 
{


	private static final Logger LOGGER = LoggerFactory.getLogger(AccountingDetailsDao.class);

	@Autowired
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}
	public AccountingDetails importAccountingDetails(AccountingDetailsDto dto)
	{
		AccountingDetails accountingDo= new AccountingDetails();
		accountingDo.setCompanyCode(dto.getCompanyCode());
		accountingDo.setCostCenter(dto.getCostCenter());
		accountingDo.setGlAcc(dto.getGlAcc());
		accountingDo.setProfitCenter(dto.getProfitCenter());
		accountingDo.setS4Debit(dto.getS4Debit());
		accountingDo.setPlantCode(dto.getPlantCode());
		
		return accountingDo;
		
		
	}


	public AccountingDetailsDto exportAccountingDetails(AccountingDetails accountingDo)
	{
		AccountingDetailsDto dto = new AccountingDetailsDto();
		dto.setCompanyCode(accountingDo.getCompanyCode());
		dto.setCostCenter(accountingDo.getCostCenter());
		dto.setGlAcc(accountingDo.getGlAcc());
		dto.setProfitCenter(accountingDo.getProfitCenter());
		dto.setS4Debit(accountingDo.getS4Debit());
		dto.setPlantCode(accountingDo.getPlantCode());		
		return dto;
	}

	public Boolean saveAccountingDetails(List<AccountingDetailsDto> accountingDetailsDto)
	{
		LOGGER.error("Enter into adhocApprovalRuleDao saveApproval");

		Boolean isSaved=false;
		Session session = sessionFactory.openSession();
		Transaction tx= session.beginTransaction();
		
		for(AccountingDetailsDto a:accountingDetailsDto)
		{
			session.save(importAccountingDetails(a));
			isSaved=true;
		}
		session.flush();
		session.clear();
		tx.commit();
		session.close();
		return isSaved;
	}
	
	public List<AccountingDetailsDto> getAllAccountingDetails()
	{
		Session session= sessionFactory.openSession();
		Transaction tx= session.beginTransaction();
		List<AccountingDetailsDto> accountingDetaildto= new ArrayList<AccountingDetailsDto>();
		List<AccountingDetails> accountingDetails = new ArrayList<AccountingDetails>();
		
		try {
			String queryStr = "select details from AccountingDetails details";
			Query query = session.createQuery(queryStr);
			accountingDetails = query.list();
			for (AccountingDetails acdetails : accountingDetails) {
				accountingDetaildto.add(exportAccountingDetails(acdetails));
			}
		} catch (Exception e) {
			LOGGER.error("Exception in getReasonCode api" + e);
		} finally {
			session.flush();
			session.clear();
			tx.commit();
			session.close();
		}
		return accountingDetaildto;

		
		
	}

	


}
