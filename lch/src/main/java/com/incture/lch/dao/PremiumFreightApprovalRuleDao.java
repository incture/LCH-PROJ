package com.incture.lch.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.incture.lch.adhoc.workflow.constant.WorkflowConstants;
import com.incture.lch.dto.AdhocApprovalRuleDto;
import com.incture.lch.dto.PremiumFreightApprovalRuleDTO;
import com.incture.lch.dto.ResponseDto;
import com.incture.lch.entity.AdhocApprovalRule;
import com.incture.lch.entity.PremiumFreightApprovalRule;
import com.incture.lch.util.ServiceUtil;

@Repository
public class PremiumFreightApprovalRuleDao {
	private static final Logger LOGGER = LoggerFactory.getLogger(AdhocApprovalRuleDao.class);

	@Autowired
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	public PremiumFreightApprovalRule importApprovalRule(PremiumFreightApprovalRuleDTO dto) {
		PremiumFreightApprovalRule ruleDo = new PremiumFreightApprovalRule();

		ruleDo.setId(dto.getId());
		ruleDo.setPlant(dto.getPlant());
		ruleDo.setCost_min(dto.getCost_min());
		ruleDo.setCost_max(dto.getCost_max());
		ruleDo.setApprover(dto.getApprover());
		ruleDo.setApproverEmail(dto.getApproverEmail());
		ruleDo.setBackup(dto.getBackup());
		return ruleDo;

	}

	public PremiumFreightApprovalRuleDTO exportApprovalRule(PremiumFreightApprovalRule ruledo) {
		PremiumFreightApprovalRuleDTO ruleDto = new PremiumFreightApprovalRuleDTO();
		ruleDto.setId(ruledo.getId());
		ruleDto.setPlant(ruledo.getPlant());
		ruleDto.setCost_max(ruledo.getCost_max());
		ruleDto.setCost_min(ruledo.getCost_min());
		ruleDto.setApprover(ruledo.getApprover());
		ruleDto.setApproverEmail(ruledo.getApproverEmail());
		ruleDto.setBackup(ruledo.getBackup());
		return ruleDto;

	}

	// @SuppressWarnings("deprecation")
	public ResponseDto saveApproval(List<PremiumFreightApprovalRuleDTO> ruleList) {

		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		ResponseDto responseDto = new ResponseDto();
		for (PremiumFreightApprovalRuleDTO dto : ruleList) {
			try {
				LOGGER.error("Enter into PremiunFreightApprovalRuleDTO saveApproval end here " + dto.getPlant() + " - "
						+ dto.getCost_min() + "- " + dto.getCost_max() + " - " + dto.getApprover());
				session.save(importApprovalRule(dto));
				responseDto.setMessage("Save success");
				responseDto.setStatus("SUCCESS");
				responseDto.setCode("00");

			} catch (Exception e) {
				responseDto.setCode("02");
				responseDto.setMessage("Save or Update Failed due to " + e.getMessage());
				responseDto.setStatus("FAIL");
				throw new RuntimeException("Error while saving data:: " + e.toString());
			}
		}
		session.flush();
		session.clear();
		tx.commit();
		session.close();
		return responseDto;

	}

	public List<PremiumFreightApprovalRuleDTO> getAllPremiumApprovalList() {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		List<PremiumFreightApprovalRuleDTO> appRuleList = new ArrayList<>();
		List<PremiumFreightApprovalRule> appRule = new ArrayList<>();

		try {
			String queryStr = "select rule from PremiumFreightAprrovalRule rule";
			Query query = session.createQuery(queryStr);
			appRule = query.list();
			for (PremiumFreightApprovalRule lkDiv : appRule) {
				appRuleList.add(exportApprovalRule(lkDiv));
			}
		} catch (Exception e) {
			LOGGER.error("Exception in getReasonCode api" + e);
		} finally {
			session.flush();
			session.clear();
			tx.commit();
			session.close();
		}
		return appRuleList;

	}

	public String getApproverByCost(int charge) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();

		List<PremiumFreightApprovalRule> ruledo = new ArrayList<PremiumFreightApprovalRule>();
		Criteria criteria = session.createCriteria(PremiumFreightApprovalRule.class);
		ruledo = criteria.list();
		String managerUserId = null;
		for (PremiumFreightApprovalRule rule : ruledo) {
			int cost_min = rule.getCost_min();
			int cost_max = rule.getCost_max();
			if (charge > cost_min && charge < cost_max) {

				managerUserId = rule.getApprover();

			}
		}
		return managerUserId;

	}
}
