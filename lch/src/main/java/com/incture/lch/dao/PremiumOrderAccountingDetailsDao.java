package com.incture.lch.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.incture.lch.dto.PremiumOrderAccountingDetailsDto;
import com.incture.lch.entity.PremiumOrderAccountingDetails;

@Repository
public class PremiumOrderAccountingDetailsDao 
{

	private static final Logger LOGGER = LoggerFactory.getLogger(PremiumOrderAccountingDetailsDao.class);

	@Autowired
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}
	
	public PremiumOrderAccountingDetails importPremiumOrderAccountingDetails(PremiumOrderAccountingDetailsDto dto)
	{
		PremiumOrderAccountingDetails premiumOrderAccountingDetailsDo = new PremiumOrderAccountingDetails();
		premiumOrderAccountingDetailsDo.setOrderId(dto.getOrderId());
		premiumOrderAccountingDetailsDo.setDebitBy(dto.getDebitBy());
		premiumOrderAccountingDetailsDo.setDebitAmount(dto.getDebitAmount());
		premiumOrderAccountingDetailsDo.setDebitCurrency(dto.getDebitCurrency());
		premiumOrderAccountingDetailsDo.setQuantity(dto.getQuantity());
		premiumOrderAccountingDetailsDo.setProNumber(dto.getProNumber());
		premiumOrderAccountingDetailsDo.setTrailerNumber(dto.getTrailerNumber());
		premiumOrderAccountingDetailsDo.setPlannerEmail(dto.getPlannerEmail());
		premiumOrderAccountingDetailsDo.setDebitPostStatus(dto.getDebitPostStatus());
		premiumOrderAccountingDetailsDo.setDebitTo(dto.getDebitTo());
		premiumOrderAccountingDetailsDo.setGlCode(dto.getGlCode());
		premiumOrderAccountingDetailsDo.setProfitCenter(dto.getProfitCenter());
		premiumOrderAccountingDetailsDo.setCarrier(dto.getCarrier());
		premiumOrderAccountingDetailsDo.setComment(dto.getComment());
		premiumOrderAccountingDetailsDo.setTotalCost(dto.getTotalCost());
		
		return premiumOrderAccountingDetailsDo;

		
		
	}
	

	public PremiumOrderAccountingDetailsDto exportPremiumOrderAccountingDetails(PremiumOrderAccountingDetails dto)
	{
		PremiumOrderAccountingDetailsDto premiumOrderAccountingDetailsDo = new PremiumOrderAccountingDetailsDto();
		premiumOrderAccountingDetailsDo.setOrderId(dto.getOrderId());
		premiumOrderAccountingDetailsDo.setDebitBy(dto.getDebitBy());
		premiumOrderAccountingDetailsDo.setDebitAmount(dto.getDebitAmount());
		premiumOrderAccountingDetailsDo.setDebitCurrency(dto.getDebitCurrency());
		premiumOrderAccountingDetailsDo.setQuantity(dto.getQuantity());
		premiumOrderAccountingDetailsDo.setProNumber(dto.getProNumber());
		premiumOrderAccountingDetailsDo.setTrailerNumber(dto.getTrailerNumber());
		premiumOrderAccountingDetailsDo.setPlannerEmail(dto.getPlannerEmail());
		premiumOrderAccountingDetailsDo.setDebitPostStatus(dto.getDebitPostStatus());
		premiumOrderAccountingDetailsDo.setDebitTo(dto.getDebitTo());
		premiumOrderAccountingDetailsDo.setGlCode(dto.getGlCode());
		premiumOrderAccountingDetailsDo.setProfitCenter(dto.getProfitCenter());
		premiumOrderAccountingDetailsDo.setCarrier(dto.getCarrier());
		premiumOrderAccountingDetailsDo.setComment(dto.getComment());
		premiumOrderAccountingDetailsDo.setTotalCost(dto.getTotalCost());
		
		return premiumOrderAccountingDetailsDo;

				
	}

	public Boolean savePremiumAccountingDetails(List<PremiumOrderAccountingDetailsDto> premAccountingDetailsDto)
	{
		LOGGER.error("Enter into adhocApprovalRuleDao saveApproval");

		Boolean isSaved=false;
		Session session = sessionFactory.openSession();
		Transaction tx= session.beginTransaction();
		
		for(PremiumOrderAccountingDetailsDto pdetails:premAccountingDetailsDto)
		{
			session.save(importPremiumOrderAccountingDetails(pdetails));
			isSaved=true;
		}
		session.flush();
		session.clear();
		tx.commit();
		session.close();
		return isSaved;
	}
	
	

	/*public List<>*/


}
