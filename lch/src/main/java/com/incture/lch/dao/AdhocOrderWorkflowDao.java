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

import com.incture.lch.dto.AdhocOrderWorkflowDto;
import com.incture.lch.entity.AdhocOrderWorkflow;

@Repository("adhocOrderWorkflowDao")
public class AdhocOrderWorkflowDao {

	@Autowired
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	public AdhocOrderWorkflow importAdhocWorkflow(AdhocOrderWorkflowDto dto) {
		AdhocOrderWorkflow ruleDo = new AdhocOrderWorkflow();
		ruleDo.setId(dto.getId());
		ruleDo.setorderId(dto.getOrderId());
		ruleDo.setDefinitionId(dto.getDefinitionId());
		ruleDo.setInstanceId(dto.getInstanceId());
		ruleDo.setPendingWith(dto.getPendingWith());
		ruleDo.setRequestedBy(dto.getRequestedBy());
		ruleDo.setRequestedDate(dto.getRequestedDate());
		ruleDo.setStatus(dto.getStatus());
		ruleDo.setSubject(dto.getSubject());
		ruleDo.setUpdatedBy(dto.getUpdatedBy());
		ruleDo.setUpdatedDate(dto.getUpdatedDate());
		ruleDo.setWorkflowName(dto.getWorkflowName());
		ruleDo.setBusinessKey(dto.getBusinessKey());
		ruleDo.setDescription(dto.getDescription());
		ruleDo.setType(dto.getType());
		ruleDo.setWorkflowInstanceId(dto.getWorkflowInstanceId());
		
		return ruleDo;

	}

	public AdhocOrderWorkflowDto exportAdhocWorkflow(AdhocOrderWorkflow ruledo) {
		AdhocOrderWorkflowDto ruleDto = new AdhocOrderWorkflowDto();
		ruleDto.setId(ruledo.getId());
		ruleDto.setOrderId(ruledo.getorderId());
		ruleDto.setDefinitionId(ruledo.getDefinitionId());
		ruleDto.setInstanceId(ruledo.getInstanceId());
		ruleDto.setPendingWith(ruledo.getPendingWith());
		ruleDto.setRequestedBy(ruledo.getRequestedBy());
		ruleDto.setRequestedDate(ruledo.getRequestedDate());
		ruleDto.setStatus(ruledo.getStatus());
		ruleDto.setSubject(ruledo.getSubject());
		ruleDto.setUpdatedBy(ruledo.getUpdatedBy());
		ruleDto.setUpdatedDate(ruledo.getUpdatedDate());
		ruleDto.setWorkflowName(ruledo.getWorkflowName());
		ruleDto.setBusinessKey(ruledo.getBusinessKey());
		ruleDto.setDescription(ruledo.getDescription());
		ruleDto.setType(ruledo.getType());
		ruleDto.setWorkflowInstanceId(ruledo.getWorkflowInstanceId());
		return ruleDto;

	}

	public void saveWorkFlowDetails(AdhocOrderWorkflowDto workflowDto) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.save(importAdhocWorkflow(workflowDto));
		session.flush();
		session.clear();
		tx.commit();
		session.close();
	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	public List<AdhocOrderWorkflowDto> getAllPremiumWorkflowLog()
	{
		List<AdhocOrderWorkflowDto> adhocOrdersWorkflowDtoList= new ArrayList<AdhocOrderWorkflowDto>();
		List<AdhocOrderWorkflow> adhocOrderWorkflowList = new ArrayList<AdhocOrderWorkflow>();
		Session  session = sessionFactory.openSession();
		Transaction tx= session.beginTransaction();
		Criteria criteria = session.createCriteria(AdhocOrderWorkflow.class);
		criteria.add(Restrictions.eq("type", "Premium"));
		adhocOrderWorkflowList = criteria.list();

		for(AdhocOrderWorkflow a:adhocOrderWorkflowList)
		{
			adhocOrdersWorkflowDtoList.add(exportAdhocWorkflow(a));
		}
	 	
		session.flush();
		session.clear();
		tx.commit();
		session.close();
		return adhocOrdersWorkflowDtoList;
	}
}
