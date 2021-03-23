package com.incture.lch.helper;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.incture.lch.adhoc.workflow.constant.WorkflowConstants;
import com.incture.lch.dao.AdhocOrderWorkflowDao;
import com.incture.lch.dto.AdhocApprovalRuleDto;
import com.incture.lch.dto.AdhocOrderWorkflowDto;
import com.incture.lch.entity.AdhocOrders;
import com.incture.lch.util.ServiceUtil;

@Repository
public class AdhocOrderWorkflowHelper {

	@Autowired
	private AdhocOrderWorkflowDao workflowDao;

	@Autowired
	// @Qualifier("sessionDb")
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	public String updateWorflowDetails(AdhocOrderWorkflowDto workflowDto) {
		System.out.println("Yuhooo" + workflowDto.getorderId());

		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		List<AdhocOrders> adhocOrder = new ArrayList<AdhocOrders>();
		Criteria criteria = session.createCriteria(AdhocOrders.class);
		criteria.add(Restrictions.eq("fwoNum", workflowDto.getorderId()));
		adhocOrder = criteria.list();

		System.out.println(adhocOrder.size());
		for (AdhocOrders a : adhocOrder) {

			System.out.println(a.getFwoNum());
			a.setUpdatedBy(workflowDto.getUpdatedBy());
			a.setUpdatedDate((workflowDto.getUpdatedDate()));
			a.setStatus(workflowDto.getStatus());
			a.setPendingWith(workflowDto.getPendingWith());
			session.saveOrUpdate(a);
		}

		session.save(workflowDao.importAdhocWorkflow(workflowDto));

		session.flush();
		session.clear();
		tx.commit();
		session.close();

		System.out.println(workflowDto.getorderId());
		return workflowDto.getorderId();
	}

	public String getManagerDetails(List<AdhocApprovalRuleDto> ruleDtoList)
	{
		StringBuilder managerBuilder = new StringBuilder();
		if (!ServiceUtil.isEmpty(ruleDtoList)) {
			for (AdhocApprovalRuleDto dto : ruleDtoList) {
				if (dto.getApproverType().equals(WorkflowConstants.MANAGER)) {
					managerBuilder.append(dto.getUserId());
					managerBuilder.append(",");
				}
				
			}
		}
		String manager = managerBuilder.substring(0, managerBuilder.length() - 2).toString();
		return manager;
	}
	
	public String getPlannerDetails(List<AdhocApprovalRuleDto> ruleDtoList)
	
	{
		StringBuilder plannerBuilder = new StringBuilder();
		if (!ServiceUtil.isEmpty(ruleDtoList)) {
			for (AdhocApprovalRuleDto dto : ruleDtoList) {
				
				if (dto.getApproverType().equals(WorkflowConstants.PLANNER)) {
					plannerBuilder.append(dto.getUserId());
					plannerBuilder.append(",");
				}
			}
		}
		String planner = plannerBuilder.substring(0, plannerBuilder.length() - 2).toString();
		return planner;
	}
}
