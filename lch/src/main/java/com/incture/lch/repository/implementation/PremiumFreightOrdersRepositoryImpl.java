package com.incture.lch.repository.implementation;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.apache.xmlbeans.impl.xb.xsdschema.RestrictionDocument.Restriction;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.incture.lch.adhoc.custom.dto.ResponseMessage;
import com.incture.lch.adhoc.workflow.dto.PremiumWorkflowApprovalTaskDto;
import com.incture.lch.dao.AccountingDetailsDao;
import com.incture.lch.dao.AdhocOrderWorkflowDao;
import com.incture.lch.dao.CarrierDetailsDao;
import com.incture.lch.dao.PremiumFreightApprovalRuleDao;
import com.incture.lch.dao.PremiumOrderAccountingDetailsDao;
import com.incture.lch.dto.AdhocOrderDto;
import com.incture.lch.dto.AdhocOrderWorkflowDto;
import com.incture.lch.dto.CarrierDetailsDto;
import com.incture.lch.dto.ChargeDetailsPaginated;
import com.incture.lch.dto.ChargeRequestDto;
import com.incture.lch.dto.PaginationDto;
import com.incture.lch.dto.PaginationDto1;
import com.incture.lch.dto.PremiumFreightChargeDetailsDto;
import com.incture.lch.dto.PremiumFreightDto1;
import com.incture.lch.dto.PremiumFreightOrderDto;
import com.incture.lch.dto.PremiumRequestDto;
import com.incture.lch.dto.ResponseDto;
import com.incture.lch.dto.TaskDetailsDto;
import com.incture.lch.entity.AdhocOrderWorkflow;
import com.incture.lch.entity.AdhocOrders;
import com.incture.lch.entity.CarrierDetails;
import com.incture.lch.entity.LchRole;
import com.incture.lch.entity.PremiumFreightChargeDetails;
import com.incture.lch.entity.PremiumOrderAccountingDetails;
import com.incture.lch.exception.CarrierNotExistException;
import com.incture.lch.exception.FilterInvalidEntryException;
import com.incture.lch.exception.PageNumberNotFoundException;
import com.incture.lch.exception.SetCarrierDetailsException;
import com.incture.lch.premium.custom.dto.PremiumManagerCustomDto;
import com.incture.lch.premium.custom.dto.WorkflowPremiumCustomDto;
import com.incture.lch.premium.workflow.service.PremiumWorkflowInvokerLocal;
import com.incture.lch.premium.workflow.service.PremiumWorkflowServiceLocal;
import com.incture.lch.repository.PremiumFreightOrdersRepository;
import com.incture.lch.util.GetReferenceData;
import com.incture.lch.util.ServiceUtil;

@Repository
public class PremiumFreightOrdersRepositoryImpl implements PremiumFreightOrdersRepository {

	@Autowired
	// @Qualifier("sessionDb")
	private SessionFactory sessionFactory;

	@Autowired
	private PremiumWorkflowServiceLocal wfService;

	@Autowired
	GetReferenceData getReferenceData;

	@Autowired
	private CarrierDetailsDao carrierDetailsDao;

	@Autowired
	private AdhocOrderWorkflowDao adhocOrderWorkflowDao;
	@Autowired
	private PremiumWorkflowInvokerLocal wfInvokerLocal;

	@Autowired
	private PremiumFreightApprovalRuleDao ruleDao;

	@Autowired
	private PremiumOrderAccountingDetailsDao accoutingDetailsdao;
	private static final Logger LOGGER = LoggerFactory.getLogger(PremiumFreightOrdersRepositoryImpl.class);

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	@Override
	public PremiumFreightOrderDto exportPremiumFreightOrders(AdhocOrders adhocOrders) {

		PremiumFreightOrderDto premiumFreightOrderDto = new PremiumFreightOrderDto();

		premiumFreightOrderDto.setorderId(adhocOrders.getFwoNum());
		premiumFreightOrderDto.setCreatedBy(adhocOrders.getCreatedBy());

		if (adhocOrders.getCreatedDate() != null && !(adhocOrders.getCreatedDate().equals(""))) {
			premiumFreightOrderDto.setCreatedDate(ServiceUtil.convertDateToString(adhocOrders.getCreatedDate()));
		}

		premiumFreightOrderDto.setCountryOrigin(adhocOrders.getCountryOrigin());
		premiumFreightOrderDto.setCurrency(adhocOrders.getCurrency());

		premiumFreightOrderDto.setDestinationName(adhocOrders.getDestinationName());
		premiumFreightOrderDto.setDestinationAddress(adhocOrders.getDestinationAddress());
		premiumFreightOrderDto.setDestinationCity(adhocOrders.getDestinationCity());
		premiumFreightOrderDto.setDestinationState(adhocOrders.getDestinationState());
		premiumFreightOrderDto.setDestinationCountry(adhocOrders.getDestinationCountry());
		premiumFreightOrderDto.setDestinationZip(adhocOrders.getDestinationZip());

		premiumFreightOrderDto.setOriginName(adhocOrders.getShipperName());
		premiumFreightOrderDto.setOriginAddress(adhocOrders.getShippingContact());
		premiumFreightOrderDto.setOriginCity(adhocOrders.getOriginCity());
		premiumFreightOrderDto.setOriginState(adhocOrders.getOriginState());
		premiumFreightOrderDto.setOriginCountry(adhocOrders.getCountryOrigin());
		premiumFreightOrderDto.setOriginZip(adhocOrders.getOriginZip());

		premiumFreightOrderDto.setReasonCode(adhocOrders.getPremiumReasonCode());
		premiumFreightOrderDto.setPlannerEmail(adhocOrders.getPlannerEmail());
		premiumFreightOrderDto.setStatus(adhocOrders.getStatus());
		// premiumFreightOrderDto.setStatus(status);

		return premiumFreightOrderDto;
	}

	public PremiumFreightDto1 exportPremiumFreightOrders1(AdhocOrders adhocOrders) {

		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		PremiumFreightDto1 premiumFreightOrderDto = new PremiumFreightDto1();

		premiumFreightOrderDto.setOrderId(adhocOrders.getFwoNum());
		premiumFreightOrderDto.setCreatedBy(adhocOrders.getCreatedBy());

		if (adhocOrders.getCreatedDate() != null && !(adhocOrders.getCreatedDate().equals(""))) {
			premiumFreightOrderDto.setCreatedDate(ServiceUtil.convertDateToString(adhocOrders.getCreatedDate()));
		}

		premiumFreightOrderDto.setCountryOrigin(adhocOrders.getCountryOrigin());
		premiumFreightOrderDto.setCurrency(adhocOrders.getCurrency());

		premiumFreightOrderDto.setDestinationName(adhocOrders.getDestinationName());
		premiumFreightOrderDto.setDestinationAddress(adhocOrders.getDestinationAddress());
		premiumFreightOrderDto.setDestinationCity(adhocOrders.getDestinationCity());
		premiumFreightOrderDto.setDestinationState(adhocOrders.getDestinationState());
		premiumFreightOrderDto.setDestinationCountry(adhocOrders.getDestinationCountry());
		premiumFreightOrderDto.setDestinationZip(adhocOrders.getDestinationZip());

		premiumFreightOrderDto.setOriginName(adhocOrders.getShipperName());
		premiumFreightOrderDto.setOriginAddress(adhocOrders.getShippingContact());
		premiumFreightOrderDto.setOriginCity(adhocOrders.getOriginCity());
		premiumFreightOrderDto.setOriginState(adhocOrders.getOriginState());
		premiumFreightOrderDto.setOriginCountry(adhocOrders.getCountryOrigin());
		premiumFreightOrderDto.setOriginZip(adhocOrders.getOriginZip());

		premiumFreightOrderDto.setReasonCode(adhocOrders.getPremiumReasonCode());
		premiumFreightOrderDto.setPlannerEmail(adhocOrders.getPlannerEmail());
		premiumFreightOrderDto.setStatus(adhocOrders.getStatus());
		// premiumFreightOrderDto.setStatus(status);

		Criteria criteria = session.createCriteria(PremiumFreightChargeDetails.class);
		criteria.add(Restrictions.eq("orderId", adhocOrders.getFwoNum()));
		List<PremiumFreightChargeDetails> pdetails = new ArrayList<PremiumFreightChargeDetails>();
		pdetails = criteria.list();

		for (PremiumFreightChargeDetails p : pdetails) {
			premiumFreightOrderDto.setBpNumber(p.getBpNumber());
			premiumFreightOrderDto.setCarrierDetails(p.getCarrierDetails());
			premiumFreightOrderDto.setCarrierScac(p.getCarrierScac());
			premiumFreightOrderDto.setCarrierMode(p.getCarrierMode());
			premiumFreightOrderDto.setCharge(p.getCharge());
			premiumFreightOrderDto.setReasonCode(p.getReasonCode());
		}
		session.flush();
		session.clear();
		tx.commit();
		session.close();

		return premiumFreightOrderDto;
	}

	// List of all the Premium Freight Orders based on the PlannerEmail plus
	// date filters
	@SuppressWarnings({ "unchecked" })
	@Override
	public PaginationDto getAllPremiumFreightOrders(PremiumRequestDto premiumRequestDto) {
		List<PremiumFreightOrderDto> premiumFreightOrderDtos = new ArrayList<>();
		PaginationDto paginationDto = new PaginationDto();
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();

		@SuppressWarnings("deprecation")
		Criteria criteria = session.createCriteria(AdhocOrders.class);

		criteria.add(Restrictions.eq("premiumFreight", "true"));
		criteria.add(Restrictions.ne("status", "REJECTED"));
		criteria.add(Restrictions.ne("status", "Pending with Carrier Admin"));

		String filter_field = null;
		try {
			if (premiumRequestDto.getorderId() != null && !(premiumRequestDto.getorderId().equals(""))) {
				criteria.add(Restrictions.eq("orderId", premiumRequestDto.getorderId()));
				filter_field = "adhocOrderId";

			}
			if ((premiumRequestDto.getFromDate() != null && !(premiumRequestDto.getFromDate().equals("")))
					&& (premiumRequestDto.getToDate() != null) && !(premiumRequestDto.getToDate().equals(""))) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				try {
					Date d1 = (Date) sdf.parse(premiumRequestDto.getFromDate());
					Date d2 = (Date) sdf.parse(premiumRequestDto.getToDate());
					criteria.add(Restrictions.between("createdDate", d1, d2));
				} catch (Exception e) {
					System.out.println("Error in date format");
				}
				filter_field = "date_filter";

			}
			if (premiumRequestDto.getPlannerEmail() != null && !(premiumRequestDto.getPlannerEmail().equals(""))) {

				criteria.add(Restrictions.eq("plannerEmail", premiumRequestDto.getPlannerEmail()));
				filter_field = "planner Email";

			}
			if (premiumRequestDto.getReasonCode() != null && !(premiumRequestDto.getReasonCode().equals(""))) {
				criteria.add(Restrictions.eq("premiumReasonCode", premiumRequestDto.getReasonCode()));
				filter_field = "reason_code";

			}
			if (premiumRequestDto.getStatus() != null && !(premiumRequestDto.getStatus().equals(""))) {
				criteria.add(Restrictions.eq("status", premiumRequestDto.getStatus()));
				filter_field = "status";
			}

			if (premiumRequestDto.getOriginName() != null && !(premiumRequestDto.getOriginName().equals(""))) {
				criteria.add(Restrictions.eq("shipperName", premiumRequestDto.getOriginName()));
				filter_field = "Origin Name";
			}

			if (premiumRequestDto.getDestinationName() != null
					&& !(premiumRequestDto.getDestinationName().equals(""))) {
				criteria.add(Restrictions.eq("DestinationName", premiumRequestDto.getDestinationName()));
				filter_field = "destiantion name";
			}
		} catch (Exception e) {
			// Catch that the filter value is invalid
			throw new FilterInvalidEntryException(filter_field);
		}
		criteria.addOrder(Order.asc("fwoNum"));
		int total_entries = criteria.list().size();
		int startNum = (premiumRequestDto.getPageNumber() - 1) * 10;

		if (premiumRequestDto.getNoOfEntry() > total_entries) {
			throw new PageNumberNotFoundException(total_entries);
		}

		criteria.setFirstResult(startNum);
		if (premiumRequestDto.getNoOfEntry() == 0) {
			criteria.setMaxResults(10);
		} else {
			criteria.setMaxResults(premiumRequestDto.getNoOfEntry());
		}

		List<AdhocOrders> adhocOrders = criteria.list();

		for (AdhocOrders adOrders : adhocOrders) {
			premiumFreightOrderDtos.add(exportPremiumFreightOrders(adOrders));
		}
		paginationDto.setPremiumFreightOrderDtos(premiumFreightOrderDtos);
		paginationDto.setCount(total_entries);

		session.flush();
		session.clear();
		tx.commit();
		session.close();
		return paginationDto;
	}

	@Override
	public PaginationDto1 getAllPremiumFreightOrders1(PremiumRequestDto premiumRequestDto) {
		List<PremiumFreightDto1> premiumFreightDto1s = new ArrayList<>();
		PaginationDto1 paginationDto1 = new PaginationDto1();
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();

		@SuppressWarnings("deprecation")
		Criteria criteria = session.createCriteria(AdhocOrders.class);

		criteria.add(Restrictions.eq("premiumFreight", "true"));
		criteria.add(Restrictions.ne("status", "REJECTED"));
		// criteria.add(Restrictions.ne("status", "Pending with Carrier
		// Admin"));

		String filter_field = null;
		try {
			if (premiumRequestDto.getorderId() != null && !(premiumRequestDto.getorderId().equals(""))) {
				criteria.add(Restrictions.eq("orderId", premiumRequestDto.getorderId()));
				filter_field = "adhocOrderId";

			}
			if ((premiumRequestDto.getFromDate() != null && !(premiumRequestDto.getFromDate().equals("")))
					&& (premiumRequestDto.getToDate() != null) && !(premiumRequestDto.getToDate().equals(""))) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				try {
					Date d1 = (Date) sdf.parse(premiumRequestDto.getFromDate());
					Date d2 = (Date) sdf.parse(premiumRequestDto.getToDate());
					criteria.add(Restrictions.between("createdDate", d1, d2));
				} catch (Exception e) {
					System.out.println("Error in date format");
				}
				filter_field = "date_filter";

			}
			if (premiumRequestDto.getPlannerEmail() != null && !(premiumRequestDto.getPlannerEmail().equals(""))) {

				criteria.add(Restrictions.eq("plannerEmail", premiumRequestDto.getPlannerEmail()));
				filter_field = "planner Email";

			}
			if (premiumRequestDto.getReasonCode() != null && !(premiumRequestDto.getReasonCode().equals(""))) {
				criteria.add(Restrictions.eq("premiumReasonCode", premiumRequestDto.getReasonCode()));
				filter_field = "reason_code";

			}
			if (premiumRequestDto.getStatus() != null && !(premiumRequestDto.getStatus().equals(""))) {
				criteria.add(Restrictions.eq("status", premiumRequestDto.getStatus()));
				filter_field = "status";
			}

			if (premiumRequestDto.getOriginName() != null && !(premiumRequestDto.getOriginName().equals(""))) {
				criteria.add(Restrictions.eq("shipperName", premiumRequestDto.getOriginName()));
				filter_field = "Origin Name";
			}

			if (premiumRequestDto.getDestinationName() != null
					&& !(premiumRequestDto.getDestinationName().equals(""))) {
				criteria.add(Restrictions.eq("DestinationName", premiumRequestDto.getDestinationName()));
				filter_field = "destiantion name";
			}
		} catch (Exception e) {
			// Catch that the filter value is invalid
			throw new FilterInvalidEntryException(filter_field);
		}
		criteria.addOrder(Order.asc("fwoNum"));
		int total_entries = criteria.list().size();
		int startNum = (premiumRequestDto.getPageNumber() - 1) * 10;

		if (premiumRequestDto.getNoOfEntry() > total_entries) {
			throw new PageNumberNotFoundException(total_entries);
		}

		criteria.setFirstResult(startNum);
		if (premiumRequestDto.getNoOfEntry() == 0) {
			criteria.setMaxResults(10);
		} else {
			criteria.setMaxResults(premiumRequestDto.getNoOfEntry());
		}

		List<AdhocOrders> adhocOrders = criteria.list();

		for (AdhocOrders adOrders : adhocOrders) {
			premiumFreightDto1s.add(exportPremiumFreightOrders1(adOrders));
		}
		paginationDto1.setPremiumFreightDto1(premiumFreightDto1s);
		paginationDto1.setCount(total_entries);

		session.flush();
		session.clear();
		tx.commit();
		session.close();
		return paginationDto1;
	}

	@SuppressWarnings("deprecation")
	@Override
	public ChargeDetailsPaginated getAllCarrierOrders(PremiumRequestDto premiumRequestDto) {

		List<PremiumFreightChargeDetails> premiumFreightChargeDetails = new ArrayList<PremiumFreightChargeDetails>();
		ChargeDetailsPaginated chargeDetailsPaginated = new ChargeDetailsPaginated();
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();

		@SuppressWarnings("deprecation")
		Criteria criteria = session.createCriteria(PremiumFreightChargeDetails.class);

		criteria.add(Restrictions.eq("status", "Pending with Carrier Admin"));

		if (premiumRequestDto.getorderId() != null && !(premiumRequestDto.getorderId().equals(""))) {
			criteria.add(Restrictions.eq("orderId", premiumRequestDto.getorderId()));

		}
		if ((premiumRequestDto.getFromDate() != null && !(premiumRequestDto.getFromDate().equals("")))
				&& (premiumRequestDto.getToDate() != null) && !(premiumRequestDto.getToDate().equals(""))) {
			@SuppressWarnings("deprecation")
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			try {
				Date d1 = (Date) sdf.parse(premiumRequestDto.getFromDate());
				Date d2 = (Date) sdf.parse(premiumRequestDto.getToDate());
				criteria.add(Restrictions.between("createdDate", d1, d2));
			} catch (Exception e) {
				System.out.println("Error in date format");
			}

		}
		if (premiumRequestDto.getPlannerEmail() != null && !(premiumRequestDto.getPlannerEmail().equals(""))) {

			criteria.add(Restrictions.eq("plannerEmail", premiumRequestDto.getPlannerEmail()));

		}
		if (premiumRequestDto.getReasonCode() != null && !(premiumRequestDto.getReasonCode().equals(""))) {
			criteria.add(Restrictions.eq("premiumReasonCode", premiumRequestDto.getReasonCode()));

		}
		if (premiumRequestDto.getStatus() != null && !(premiumRequestDto.getStatus().equals(""))) {
			criteria.add(Restrictions.eq("status", premiumRequestDto.getStatus()));
		}

		if (premiumRequestDto.getOriginName() != null && !(premiumRequestDto.getOriginName().equals(""))) {
			criteria.add(Restrictions.eq("shipperName", premiumRequestDto.getOriginName()));
		}

		if (premiumRequestDto.getDestinationName() != null && !(premiumRequestDto.getDestinationName().equals(""))) {
			criteria.add(Restrictions.eq("destinationName", premiumRequestDto.getDestinationName()));
		}

		criteria.addOrder(Order.asc("orderId"));
		int total_entries = criteria.list().size();
		int startNum = (premiumRequestDto.getPageNumber() - 1) * 10;

		if (premiumRequestDto.getNoOfEntry() > total_entries) {
			throw new PageNumberNotFoundException(total_entries);
		}

		criteria.setFirstResult(startNum);
		if (premiumRequestDto.getNoOfEntry() == 0) {
			criteria.setMaxResults(10);
		} else {
			criteria.setMaxResults(premiumRequestDto.getNoOfEntry());
		}

		premiumFreightChargeDetails = criteria.list();

		chargeDetailsPaginated.setPremiumFreightChargeDetails(premiumFreightChargeDetails);
		chargeDetailsPaginated.setCount(total_entries);

		session.flush();
		session.clear();
		tx.commit();
		session.close();
		return chargeDetailsPaginated;
	}

	@Override
	public ChargeDetailsPaginated getAllManagerOrders(PremiumRequestDto premiumRequestDto) {

		List<PremiumFreightChargeDetails> premiumFreightChargeDetails = new ArrayList<PremiumFreightChargeDetails>();
		ChargeDetailsPaginated chargeDetailsPaginated = new ChargeDetailsPaginated();
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();

		@SuppressWarnings("deprecation")
		Criteria criteria = session.createCriteria(PremiumFreightChargeDetails.class);

		criteria.add(Restrictions.eq("status", "Pending at Manager"));

		if (premiumRequestDto.getorderId() != null && !(premiumRequestDto.getorderId().equals(""))) {
			criteria.add(Restrictions.eq("orderId", premiumRequestDto.getorderId()));

		}
		if ((premiumRequestDto.getFromDate() != null && !(premiumRequestDto.getFromDate().equals("")))
				&& (premiumRequestDto.getToDate() != null) && !(premiumRequestDto.getToDate().equals(""))) {
			@SuppressWarnings("deprecation")
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			try {
				Date d1 = (Date) sdf.parse(premiumRequestDto.getFromDate());
				Date d2 = (Date) sdf.parse(premiumRequestDto.getToDate());
				criteria.add(Restrictions.between("createdDate", d1, d2));
			} catch (Exception e) {
				System.out.println("Error in date format");
			}

		}
		if (premiumRequestDto.getPlannerEmail() != null && !(premiumRequestDto.getPlannerEmail().equals(""))) {

			criteria.add(Restrictions.eq("plannerEmail", premiumRequestDto.getPlannerEmail()));

		}
		if (premiumRequestDto.getReasonCode() != null && !(premiumRequestDto.getReasonCode().equals(""))) {
			criteria.add(Restrictions.eq("premiumReasonCode", premiumRequestDto.getReasonCode()));

		}
		if (premiumRequestDto.getStatus() != null && !(premiumRequestDto.getStatus().equals(""))) {
			criteria.add(Restrictions.eq("status", premiumRequestDto.getStatus()));
		}

		if (premiumRequestDto.getOriginName() != null && !(premiumRequestDto.getOriginName().equals(""))) {
			criteria.add(Restrictions.eq("shipperName", premiumRequestDto.getOriginName()));
		}

		if (premiumRequestDto.getDestinationName() != null && !(premiumRequestDto.getDestinationName().equals(""))) {
			criteria.add(Restrictions.eq("destinationName", premiumRequestDto.getDestinationName()));
		}

		criteria.addOrder(Order.asc("orderId"));
		int total_entries = criteria.list().size();
		System.out.println("Enter the page number" + premiumRequestDto.getPageNumber());
		int startNum = (premiumRequestDto.getPageNumber() - 1) * 10;

		System.out.println("Page Number " + startNum);
		if (premiumRequestDto.getNoOfEntry() > total_entries) {
			throw new PageNumberNotFoundException(total_entries);
		}

		criteria.setFirstResult(startNum);
		if (premiumRequestDto.getNoOfEntry() == 0) {
			criteria.setMaxResults(10);
		} else {
			criteria.setMaxResults(premiumRequestDto.getNoOfEntry());
		}

		premiumFreightChargeDetails = criteria.list();

		chargeDetailsPaginated.setPremiumFreightChargeDetails(premiumFreightChargeDetails);
		chargeDetailsPaginated.setCount(total_entries);

		session.flush();
		session.clear();
		tx.commit();
		session.close();
		return chargeDetailsPaginated;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<CarrierDetailsDto> getAllCarrierDetails() {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		List<CarrierDetailsDto> carrierDetailsDtos = new ArrayList<>();
		List<CarrierDetails> carrierDetails = new ArrayList<>();

		try {
			Criteria criteria = session.createCriteria(CarrierDetails.class);
			carrierDetails = criteria.list();
			for (CarrierDetails cdetails : carrierDetails) {
				carrierDetailsDtos.add(carrierDetailsDao.exportCarrierDetails(cdetails));
			}
		} catch (Exception e) {
			// LOGGER.error("Exception in getReasonCode api" + e);
		} finally {
			session.flush();
			session.clear();
			tx.commit();
			session.close();
		}

		return carrierDetailsDtos;
	}

	@Override
	public List<String> getMode(String bpNumber) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		List<String> modeList = new ArrayList<>();
		// List<CarrierDetailsDto> carrierDetailsDtos=new ArrayList<>();
		List<CarrierDetails> carrierDetails = new ArrayList<>();
		try {
			Criteria criteria = session.createCriteria(CarrierDetails.class);
			criteria.add(Restrictions.eq("bpNumber", bpNumber));
			/*
			 * String queryStr =
			 * "select carrier from CarrierDetails carrier WHERE carrier.bpNumber=: bpnumber"
			 * ; Query query = session.createQuery(queryStr);
			 * query.setParameter("bpNumber", bpNumber); carrierDetails =
			 * query.list();
			 */
			carrierDetails = criteria.list();

			if (carrierDetails == null) {
				throw new CarrierNotExistException(bpNumber);
			}
			for (CarrierDetails cdetails : carrierDetails) {
				/*
				 * for(String s : cdetails.getCarrierMode()) {
				 */
				modeList.add(cdetails.getCarrierMode());
				// }
			}
		} catch (Exception e) {
			// LOGGER.error("Exception in getReasonCode api" + e);
		} finally {
			session.flush();
			session.clear();
			tx.commit();
			session.close();
		}

		return modeList;
	}

	// This Function will receive the list of AdhocOrders whose charge we want
	// the CA to calculate
	// On getting the Charge respond the Same DTO Premium one for the CA
	// The status changes to Pending with Carrier admin
	@SuppressWarnings({ "unchecked", "deprecation", "deprecation" })
	@Override
	public List<PremiumFreightOrderDto> setCarrierDetails(List<ChargeRequestDto> chargeRequestDto) {

		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		List<PremiumFreightOrderDto> premiumFreightOrderDtos = new ArrayList<PremiumFreightOrderDto>();
		List<AdhocOrders> adhocOrders = new ArrayList<AdhocOrders>();
		List<String> adhocOrdersList = new ArrayList<String>();

		// String createdBy = null;

		PremiumFreightOrderDto premiumdto = new PremiumFreightOrderDto();
		try {
			for (ChargeRequestDto c : chargeRequestDto) {
				String adid = c.getorderId();
				adhocOrdersList.add(adid);

				String queryStr = "SELECT ao FROM AdhocOrders ao WHERE ao.fwoNum = ao.fwoNum AND ao.fwoNum=:fwoNum";
				Query query = session.createQuery(queryStr);
				query.setParameter("fwoNum", adid);
				adhocOrders = query.list();
				for (AdhocOrders aorders : adhocOrders) {
					PremiumFreightChargeDetails premiumFreightChargeDetails = new PremiumFreightChargeDetails();
					// Updating Status in Master Table
					aorders.setStatus("Pending with Carrier Admin");
					Criteria criteria_role = session.createCriteria(LchRole.class);
					StringBuilder pendingWith = new StringBuilder();
					List<LchRole> role = new ArrayList<LchRole>();

					// Since the order is send to the Carrier Admin , It is
					// pending at id of Carrier Admin Role
					criteria_role.add(Restrictions.eq("role", "LCH_Carrier_Admin"));

					role = criteria_role.list();

					// Creating the Comma Seperated list of all the Carrier
					// Admin
					for (LchRole l : role) {
						pendingWith.append(l.getUserId());
						pendingWith.append(",");
					}
					// Updating in Master Table, adding the pending with field
					aorders.setPendingWith(pendingWith.substring(0, pendingWith.length() - 1));

					session.saveOrUpdate(aorders);
					// Converting AdhocOrders to Premium Format DTO
					premiumdto = exportPremiumFreightOrders(aorders);
					// Updating the Premium status*/

					premiumdto.setStatus("Pending with Carrier Admin");
					// adding in premium List
					premiumFreightOrderDtos.add(premiumdto);
					Criteria criteria = session.createCriteria(PremiumFreightChargeDetails.class);

					// Getting the data from charge details table for the same
					// id
					criteria.add(Restrictions.eq("orderId", adid));
					List<PremiumFreightChargeDetails> premiumFreightChargeDetails2 = new ArrayList<PremiumFreightChargeDetails>();
					premiumFreightChargeDetails2 = criteria.list();

					// Setting the new object for PremiumCharge Details
					premiumFreightChargeDetails.setorderId(adid);

					// Fetching the Carrier Details to set in charge table
					List<CarrierDetails> carrierDetails = new ArrayList<CarrierDetails>();
					Criteria criteria3 = session.createCriteria(CarrierDetails.class);
					criteria3.add(Restrictions.eq("bpNumber", c.getBpNumber()));
					criteria3.add(Restrictions.eq("carrierMode", c.getCarrierMode()));
					carrierDetails = criteria3.list();
					for (CarrierDetails cdets : carrierDetails) {
						premiumFreightChargeDetails.setBpNumber(cdets.getBpNumber());
						premiumFreightChargeDetails.setCarrierDetails(cdets.getCarrierDetails());
						premiumFreightChargeDetails.setCarrierMode(cdets.getCarrierMode());
						premiumFreightChargeDetails.setCarrierScac(cdets.getCarrierScac());

					}
					premiumFreightChargeDetails.setOriginName(aorders.getShipperName());
					premiumFreightChargeDetails.setOriginAddress(aorders.getOriginAddress());
					premiumFreightChargeDetails.setOriginCity(aorders.getOriginCity());
					premiumFreightChargeDetails.setOriginState(aorders.getOriginState());
					premiumFreightChargeDetails.setOriginCountry(aorders.getCountryOrigin());
					premiumFreightChargeDetails.setOriginZip(aorders.getOriginZip());

					premiumFreightChargeDetails.setDestinationName(aorders.getDestinationName());
					premiumFreightChargeDetails.setDestinationAdress(aorders.getDestinationAddress());
					premiumFreightChargeDetails.setDestinationCity(aorders.getDestinationCity());
					premiumFreightChargeDetails.setDestinationState(aorders.getDestinationState());
					premiumFreightChargeDetails.setDestinationCountry(aorders.getDestinationCountry());
					premiumFreightChargeDetails.setDestinationZip(aorders.getDestinationZip());

					premiumFreightChargeDetails.setCharge(0);

					premiumFreightChargeDetails.setReasonCode(aorders.getPremiumReasonCode());
					premiumFreightChargeDetails.setStatus("Pending with Carrier Admin");
					premiumFreightChargeDetails.setPlannerEmail(aorders.getPlannerEmail());

					session.saveOrUpdate(premiumFreightChargeDetails);

				}

			}

		} catch (Exception e) {
			System.out.print(e);
			throw new SetCarrierDetailsException();
		} finally {
			session.flush();
			session.clear();
			tx.commit();
			session.close();
		}

		return premiumFreightOrderDtos;
	}

	public AdhocOrderDto exportAdhocOrdersDto(AdhocOrders adhocOrders) {
		AdhocOrderDto AdhocOrderDto = new AdhocOrderDto();

		// System.out.println("Inside DTO CLass");
		AdhocOrderDto.setOrderId(adhocOrders.getFwoNum());
		AdhocOrderDto.setBusinessDivision(adhocOrders.getBusinessDivision());
		if (adhocOrders.getCharge() != null) {
			AdhocOrderDto.setCharge(ServiceUtil.convertStringToBoolean(adhocOrders.getCharge()));
		}

		AdhocOrderDto.setUserId(adhocOrders.getUserId());
		AdhocOrderDto.setPartNum(adhocOrders.getPartNum());
		AdhocOrderDto.setCreatedBy(adhocOrders.getCreatedBy());
		if (adhocOrders.getCreatedDate() != null && !(adhocOrders.getCreatedDate().equals(""))) {
			AdhocOrderDto.setCreatedDate(ServiceUtil.convertDateToString(adhocOrders.getCreatedDate()));
		}
		AdhocOrderDto.setUpdatedBy(adhocOrders.getUpdatedBy());
		if (adhocOrders.getUpdatedDate() != null && !(adhocOrders.getUpdatedDate().equals(""))) {
			AdhocOrderDto.setUpdatedDate(ServiceUtil.convertDateToString(adhocOrders.getUpdatedDate()));
		}
		AdhocOrderDto.setOriginAddress(adhocOrders.getOriginAddress());
		AdhocOrderDto.setDestinationAddress(adhocOrders.getDestinationAddress());
		if (adhocOrders.getShipDate() != null && !(adhocOrders.getShipDate().equals(""))) {
			AdhocOrderDto.setShipDate(ServiceUtil.convertDateToString(adhocOrders.getShipDate()));
		}
		if (adhocOrders.getExpectedDeliveryDate() != null && !(adhocOrders.getExpectedDeliveryDate().equals(""))) {
			AdhocOrderDto
					.setExpectedDeliveryDate(ServiceUtil.convertDateToString(adhocOrders.getExpectedDeliveryDate()));
		}
		/////
		AdhocOrderDto.setPartDescription(adhocOrders.getPartDescription());
		AdhocOrderDto.setQuantity(adhocOrders.getQuantity());
		// AdhocOrderDto.setQuantity(ServiceUtil.convertIntegerToString(adhocOrders.getQuantity()));

		AdhocOrderDto.setUom(adhocOrders.getUom());
		if (adhocOrders.getIsInternational() != null) {
			// AdhocOrderDto.setIsInternational(ServiceUtil.convertStringToBoolean(adhocOrders.getIsInternational()));
			AdhocOrderDto.setIsInternational(adhocOrders.getIsInternational());

		}
		AdhocOrderDto.setCountryOrigin(adhocOrders.getCountryOrigin());
		AdhocOrderDto.setValue(adhocOrders.getValue());
		AdhocOrderDto.setCurrency(adhocOrders.getCurrency());
		// AdhocOrderDto.setWeight(adhocOrders.getWeight());
		// AdhocOrderDto.setDimensionB(ServiceUtil.convertBigDecimalToString(adhocOrders.getWeight()));
		AdhocOrderDto.setDimensionB(adhocOrders.getWeight());

		if (adhocOrders.getDimensionB() != null) {
			/*
			 * AdhocOrderDto.setDimensionB(ServiceUtil.convertBigDecimalToString
			 * (adhocOrders.getDimensionB()));
			 */ AdhocOrderDto.setDimensionB(adhocOrders.getDimensionB());

		}
		if (adhocOrders.getDimensionH() != null) {
			// AdhocOrderDto.setDimensionH(ServiceUtil.convertBigDecimalToString(adhocOrders.getDimensionH()));
			AdhocOrderDto.setDimensionH(adhocOrders.getDimensionH());

		}
		if (adhocOrders.getDimensionL() != null) {
			// AdhocOrderDto.setDimensionL(ServiceUtil.convertBigDecimalToString(adhocOrders.getDimensionL()));
			AdhocOrderDto.setDimensionL(adhocOrders.getDimensionL());

		}
		AdhocOrderDto.setHazmatNumber(adhocOrders.getHazmatNumber());
		AdhocOrderDto.setProjectNumber(adhocOrders.getProjectNumber());
		AdhocOrderDto.setReferenceNumber(adhocOrders.getReferenceNumber());
		if (adhocOrders.getIsTruck() != null) {
			// AdhocOrderDto.setIsTruck(ServiceUtil.convertStringToBoolean(adhocOrders.getIsTruck()));
			AdhocOrderDto.setIsTruck(adhocOrders.getIsTruck());

		}
		AdhocOrderDto.setVinNumber(adhocOrders.getVinNumber());
		AdhocOrderDto.setShippingInstruction(adhocOrders.getShippingInstruction());
		AdhocOrderDto.setShippingContact(adhocOrders.getShippingContact());
		AdhocOrderDto.setReceivingContact(adhocOrders.getReceivingContact());
		AdhocOrderDto.setPODataNumber(adhocOrders.getPODataNumber());
		AdhocOrderDto.setCustomerOrderNo(adhocOrders.getCustomerOrderNo());
		AdhocOrderDto.setTerms(adhocOrders.getTerms());
		AdhocOrderDto.setPackageType(adhocOrders.getPackageType());
		if (adhocOrders.getPremiumFreight() != null) {
			AdhocOrderDto.setPremiumFreight(ServiceUtil.convertStringToBoolean(adhocOrders.getPremiumFreight()));
			// AdhocOrderDto.setPremiumFreight(adhocOrders.getPremiumFreight());
		}
		AdhocOrderDto.setReasonCode(adhocOrders.getReasonCode());
		AdhocOrderDto.setGlcode(adhocOrders.getGlCode());
		AdhocOrderDto.setDestinationAddress(adhocOrders.getDestinationAddress());
		AdhocOrderDto.setDestinationCity(adhocOrders.getDestinationCity());
		AdhocOrderDto.setDestinationName(adhocOrders.getDestinationName());
		AdhocOrderDto.setDestinationState(adhocOrders.getDestinationState());
		AdhocOrderDto.setDestinationZip(adhocOrders.getDestinationZip());
		AdhocOrderDto.setShipperName(adhocOrders.getShipperName());
		AdhocOrderDto.setOriginCity(adhocOrders.getOriginCity());
		AdhocOrderDto.setOriginState(adhocOrders.getOriginState());
		AdhocOrderDto.setOriginZip(adhocOrders.getOriginZip());

		if (adhocOrders.getIsHazmat() != null) {
			// AdhocOrderDto.setIsHazmat(ServiceUtil.convertStringToBoolean(adhocOrders.getIsHazmat()));
			AdhocOrderDto.setIsHazmat(adhocOrders.getIsHazmat());

		}
		AdhocOrderDto.setShipperNameFreeText(adhocOrders.getShipperNameFreeText());
		AdhocOrderDto.setOriginCountry(adhocOrders.getOriginCountry());
		AdhocOrderDto.setDestinationNameFreeText(adhocOrders.getDestinationNameFreeText());
		AdhocOrderDto.setDestinationCountry(adhocOrders.getDestinationCountry());
		AdhocOrderDto.setHazmatUn(adhocOrders.getHazmatUn());
		AdhocOrderDto.setWeightUom(adhocOrders.getWeightUom());
		AdhocOrderDto.setDimensionsUom(adhocOrders.getDimensionsUom());

		AdhocOrderDto.setShipperNameDesc(adhocOrders.getShipperNameDesc());
		AdhocOrderDto.setDestinationNameDesc(adhocOrders.getDestinationNameDesc());
		AdhocOrderDto.setUserName(adhocOrders.getUserName());
		AdhocOrderDto.setUserEmail(adhocOrders.getUserEmail());
		AdhocOrderDto.setPremiumReasonCode(adhocOrders.getPremiumReasonCode());
		AdhocOrderDto.setPlannerEmail(adhocOrders.getPlannerEmail());
		AdhocOrderDto.setAdhocType(adhocOrders.getAdhocType());
		AdhocOrderDto.setStatus(adhocOrders.getStatus());
		AdhocOrderDto.setPendingWith(adhocOrders.getPendingWith());
		AdhocOrderDto.setManagerEmail(adhocOrders.getManagerEmail());
		AdhocOrderDto.setIsSaved(adhocOrders.getIsSaved());
		// System.out.println("End of DTO CLass");

		return AdhocOrderDto;
	}

	private PremiumWorkflowApprovalTaskDto exportToPremiumWorkflowDto(List<AdhocOrders> adhocOrdersList,
			List<PremiumFreightChargeDetails> premiumFreightChargeDetailsList) {
		PremiumWorkflowApprovalTaskDto workflowDto = new PremiumWorkflowApprovalTaskDto();
		for (AdhocOrders adhocOrders : adhocOrdersList) {
			workflowDto.setAdhocOrderId(adhocOrders.getFwoNum());
			workflowDto.setAdhocType(adhocOrders.getAdhocType());

			// workflowDto.setManager(manager);

			workflowDto.setAdhocOrderInfo(exportAdhocOrdersDto(adhocOrders));

			workflowDto.setBusinessDivision(adhocOrders.getBusinessDivision());
			// workflowDto.setCharge(adhocOrders.getCharge());
			workflowDto.setCountryOfOrigin(adhocOrders.getCountryOrigin());
			workflowDto.setCreatedDate(ServiceUtil.convertDateToString(adhocOrders.getCreatedDate()));
			workflowDto.setCreatedBy(adhocOrders.getCreatedBy());
			workflowDto.setCurrency(adhocOrders.getCurrency());
			workflowDto.setCustomerOrderNo(adhocOrders.getCustomerOrderNo());
			workflowDto.setDestinationAdress(adhocOrders.getDestinationAddress());
			workflowDto.setDestinationCity(adhocOrders.getDestinationCity());
			workflowDto.setDestinationName(adhocOrders.getDestinationName());
			workflowDto.setDestinationNameDesc(adhocOrders.getDestinationNameDesc());
			workflowDto.setDestinationNameFreeText(adhocOrders.getDestinationNameFreeText());
			workflowDto.setDestinationState(adhocOrders.getDestinationState());
			workflowDto.setDestinationZip(adhocOrders.getDestinationZip());
			workflowDto
					.setDimensionB(adhocOrders.getDimensionB() != null ? adhocOrders.getDimensionB().toString() : null);
			workflowDto
					.setDimensionH(adhocOrders.getDimensionH() != null ? adhocOrders.getDimensionH().toString() : null);
			workflowDto
					.setDimensionL(adhocOrders.getDimensionL() != null ? adhocOrders.getDimensionL().toString() : null);
			workflowDto.setDimensionsUom(adhocOrders.getDimensionsUom());
			workflowDto.setExpectedDeliveryDate(ServiceUtil.convertDateToString(adhocOrders.getExpectedDeliveryDate()));
			workflowDto.setGlcode(adhocOrders.getGlCode());
			workflowDto.setHazmatNumber(adhocOrders.getHazmatNumber());
			workflowDto.setIsHazmat(adhocOrders.getIsHazmat());
			workflowDto.setIsInternational(adhocOrders.getIsInternational());
			workflowDto.setIsTruck(adhocOrders.getIsTruck());
			workflowDto.setOriginAddress(adhocOrders.getOriginAddress());
			workflowDto.setOriginCity(adhocOrders.getOriginCity());
			workflowDto.setOriginCountry(adhocOrders.getOriginCountry());
			workflowDto.setOriginState(adhocOrders.getOriginState());
			workflowDto.setOriginZip(adhocOrders.getOriginZip());
			workflowDto.setPackageType(adhocOrders.getPackageType());
			workflowDto.setPartDescription(adhocOrders.getPartDescription());
			workflowDto.setPartNum(adhocOrders.getPartNum());

			// workflowDto.setPlanner(adhocOrd);

			workflowDto.setPlannerEmail(adhocOrders.getPlannerEmail());
			workflowDto.setPODataNumber(adhocOrders.getPODataNumber());
			// workflowDto.setPremiumFreight(ServiceUtil.convertStringToBoolean(adhocOrders.getPremiumFreight()));
			workflowDto.setPremiumReasonCode(adhocOrders.getPremiumReasonCode());
			workflowDto.setPremiumReasonCode(adhocOrders.getPremiumReasonCode());
			workflowDto.setProjectNumber(adhocOrders.getProjectNumber());
			workflowDto.setQuantity(String.valueOf(adhocOrders.getQuantity()));
			workflowDto.setReasonCode(adhocOrders.getReasonCode());
			workflowDto.setReceivingContact(adhocOrders.getReceivingContact());
			workflowDto.setReferenceNumber(adhocOrders.getReferenceNumber());
			workflowDto.setShipDate(ServiceUtil.convertDateToString(adhocOrders.getShipDate()));
			workflowDto.setShipperName(adhocOrders.getShipperName());
			workflowDto.setShipperNameDesc(adhocOrders.getShipperNameDesc());
			workflowDto.setShipperNameFreeText(adhocOrders.getShipperNameFreeText());
			workflowDto.setShippingContact(adhocOrders.getShippingContact());
			workflowDto.setShippingInstruction(adhocOrders.getShippingInstruction());
			workflowDto.setTerms(adhocOrders.getTerms());
			workflowDto.setUom(adhocOrders.getUom());
			workflowDto.setUserEmail(adhocOrders.getUserEmail());

			// workflowDto.setUserGroup(adhocOrders.get);

			workflowDto.setUserId(adhocOrders.getUserId());
			workflowDto.setUserName(adhocOrders.getUserName());
			workflowDto.setValue(adhocOrders.getValue());
			workflowDto.setVinNumber(adhocOrders.getVinNumber());
			workflowDto.setWeight(String.valueOf(adhocOrders.getWeight()));
			workflowDto.setWeightUom(adhocOrders.getWeightUom());
		}
		for (PremiumFreightChargeDetails premiumFreightChargeDetails : premiumFreightChargeDetailsList) {
			workflowDto.setBpNumber(premiumFreightChargeDetails.getBpNumber());
			workflowDto.setCarrierDetails(premiumFreightChargeDetails.getCarrierDetails());
			workflowDto.setCarrierScac(premiumFreightChargeDetails.getCarrierScac());
			workflowDto.setCarrierRatePerKM(premiumFreightChargeDetails.getCarrierRatePerKM());
			workflowDto.setCarrierMode(premiumFreightChargeDetails.getCarrierMode());
			workflowDto.setOrderId(premiumFreightChargeDetails.getorderId());
			workflowDto.setCharge(premiumFreightChargeDetails.getCharge());
			workflowDto.setReasonCode(premiumFreightChargeDetails.getReasonCode());
			workflowDto.setPlannerEmail(premiumFreightChargeDetails.getPlannerEmail());
			workflowDto.setStatus(premiumFreightChargeDetails.getStatus());
		}
		return workflowDto;
	}

	// Charge is set by the carrier admin here. Once the Charge is set it
	// updates the charge table
	// and update the Status as Pending At Planner

	@SuppressWarnings("unchecked")
	@Override
	public String setCharge(List<ChargeRequestDto> dto) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		PremiumFreightChargeDetails premiumFreightChargeDetail = new PremiumFreightChargeDetails();
		List<PremiumFreightChargeDetails> premiumFreightChargeDetails = new ArrayList<PremiumFreightChargeDetails>();
		for (ChargeRequestDto c : dto) {
			Criteria criteria = session.createCriteria(AdhocOrders.class);
			criteria.add(Restrictions.eq("fwoNum", c.getorderId()));
			List<AdhocOrders> adhocOrders = new ArrayList<AdhocOrders>();

			adhocOrders = criteria.list();
			for (AdhocOrders a : adhocOrders) {
				a.setStatus("Pending At Planner");
				session.saveOrUpdate(a);

			}

			Criteria criteria2 = session.createCriteria(PremiumFreightChargeDetails.class);
			criteria2.add(Restrictions.eq("orderId", c.getorderId()));
			premiumFreightChargeDetails = criteria2.list();
			if (premiumFreightChargeDetails == null) {
				// If the details aren't there
				for (AdhocOrders a : adhocOrders) {

					premiumFreightChargeDetail.setorderId(a.getFwoNum());

					System.out.println(premiumFreightChargeDetail.getorderId());
					premiumFreightChargeDetail.setOriginName(a.getShipperName());
					premiumFreightChargeDetail.setOriginAddress(a.getOriginAddress());
					premiumFreightChargeDetail.setOriginCity(a.getOriginCity());
					premiumFreightChargeDetail.setOriginState(a.getOriginState());
					premiumFreightChargeDetail.setOriginCountry(a.getOriginCountry());
					premiumFreightChargeDetail.setOriginZip(a.getOriginZip());

					premiumFreightChargeDetail.setDestinationAdress(a.getDestinationAddress());
					premiumFreightChargeDetail.setDestinationCity(a.getDestinationCity());
					premiumFreightChargeDetail.setDestinationState(a.getDestinationState());
					premiumFreightChargeDetail.setDestinationCountry(a.getDestinationCountry());
					premiumFreightChargeDetail.setDestinationZip(a.getDestinationZip());

					premiumFreightChargeDetail.setReasonCode(a.getPremiumReasonCode());
					premiumFreightChargeDetail.setPlannerEmail(a.getPlannerEmail());
					// premiumFreightChargeDetail.setStatus(a.getStatus());
					premiumFreightChargeDetail.setStatus("Pending At Planner");

				}

				List<CarrierDetails> carrierDetails = new ArrayList<CarrierDetails>();
				Criteria criteria3 = session.createCriteria(CarrierDetails.class);
				criteria3.add(Restrictions.eq("bpNumber", c.getBpNumber()));
				criteria3.add(Restrictions.eq("carrierMode", c.getCarrierMode()));
				carrierDetails = criteria3.list();
				for (CarrierDetails cdets : carrierDetails) {
					premiumFreightChargeDetail.setBpNumber(cdets.getBpNumber());
					premiumFreightChargeDetail.setCarrierScac(cdets.getCarrierScac());
					premiumFreightChargeDetail.setCarrierDetails(cdets.getCarrierDetails());
					premiumFreightChargeDetail.setCarrierMode(cdets.getCarrierMode());
					session.saveOrUpdate(premiumFreightChargeDetail);
					;

				}
				premiumFreightChargeDetail.setCharge(c.getCharge());

			} else {
				for (PremiumFreightChargeDetails p : premiumFreightChargeDetails) {
					System.out.println("This is where it is alreday present");
					p.setCharge(c.getCharge());
					p.setStatus("Pending At Planner");
					session.saveOrUpdate(p);
				}
			}
		}

		session.flush();
		session.clear();
		tx.commit();
		session.close();
		return "Charge Set";
		// Same as above
		// Pending at planner

	}

	@Override
	public ResponseDto forwardToApprover(List<PremiumRequestDto> premiumRequestDto) {
		PremiumFreightChargeDetails chargeDetails = new PremiumFreightChargeDetails();
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		ResponseDto responseDto = new ResponseDto();

		try {
			for (PremiumRequestDto p : premiumRequestDto) {
				String adhocOrderId = p.getorderId();
				Criteria c1 = session.createCriteria(AdhocOrders.class);
				Criteria c2 = session.createCriteria(PremiumFreightChargeDetails.class);

				c1.add(Restrictions.eq("fwoNum", adhocOrderId));
				c2.add(Restrictions.eq("orderId", adhocOrderId));

				List<AdhocOrders> adhocOrders = new ArrayList<AdhocOrders>();
				List<PremiumFreightChargeDetails> premiumChargeDetails = new ArrayList<PremiumFreightChargeDetails>();

				adhocOrders = c1.list();
				premiumChargeDetails = c2.list();

				System.out.println(premiumChargeDetails.size());

				int charge;
				List<String> pendingWith = new ArrayList<String>();
				for (PremiumFreightChargeDetails pdetail : premiumChargeDetails) {
					System.out.println(pdetail.getStatus());

					System.out.println(pdetail.getorderId());
					pdetail.setStatus("Pending at Manager");
					charge = pdetail.getCharge();

					pendingWith.add(ruleDao.getApproverByCost(charge));

					session.saveOrUpdate(pdetail);
					session.saveOrUpdate(pdetail);

					System.out.println(pdetail.getStatus());
					System.out.println("Updating in Charge Table");

				}
				int i = 0;
				for (AdhocOrders a : adhocOrders) {

					System.out.println(a.getFwoNum());
					System.out.println(a.getStatus());

					a.setStatus("Pending at Manager");
					a.setPendingWith(pendingWith.get(i));
					session.saveOrUpdate(a);
					session.saveOrUpdate(a);
					// updating in AdhocTable
					System.out.println(a.getStatus());
					System.out.println("Updating in Adhoc Table ");
					i++;

				}

				// Creating Workflow DTO
				PremiumWorkflowApprovalTaskDto premiumWorkflowDto = new PremiumWorkflowApprovalTaskDto();
				premiumWorkflowDto = exportToPremiumWorkflowDto(adhocOrders, premiumChargeDetails);

				// Triggering Premium Workflow
				wfService.triggerPremiumWorkflow(premiumWorkflowDto);

			}
			responseDto.setMessage("Save success");
			responseDto.setStatus("SUCCESS");
			responseDto.setCode("00");
		} catch (Exception e) {
			responseDto.setCode("02");
			responseDto.setMessage("Save or Update Failed due to " + e.getMessage());
			responseDto.setStatus("FAIL");
		} finally {
			session.flush();
			session.clear();
			tx.commit();
			session.close();
		}
		return responseDto;
	}

	@Override
	public ResponseDto RejectPremiumOrder(List<String> adhocOrderIds) {
		ResponseDto responseDto = new ResponseDto();
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {

			for (String s : adhocOrderIds) {
				System.out.println(s);
				// AdhocOrders ad = new AdhocOrders();

				List<AdhocOrders> adlist = new ArrayList<AdhocOrders>();
				Criteria criteria = session.createCriteria(AdhocOrders.class);
				criteria.add(Restrictions.eq("fwoNum", s));
				adlist = criteria.list();
				System.out.println(adlist.size() + "  " + criteria.list().size());
				for (AdhocOrders a : adlist) {
					System.out.println("ADHOCSTATUS" + a.getStatus());
					a.setStatus("REJECTED");
					session.saveOrUpdate(a);
					System.out.println("ADHOCSTATUS" + a.getStatus());

				}

				List<PremiumFreightChargeDetails> plist = new ArrayList<PremiumFreightChargeDetails>();
				Criteria criteria2 = session.createCriteria(PremiumFreightChargeDetails.class);
				criteria2.add(Restrictions.eq("orderId", s));
				plist = criteria2.list();
				for (PremiumFreightChargeDetails p : plist) {

					System.out.println("ADHOCSTATUS" + p.getStatus());
					p.setStatus("REJECTED");
					session.saveOrUpdate(p);
					System.out.println("ADHOCSTATUS" + p.getStatus());

				}
			}
			responseDto.setMessage("delete success");
			responseDto.setStatus("SUCCESS");
			responseDto.setCode("00");
			// return responseDto;
			/*
			 * String queryStr =
			 * "UPDATE AdhocOrders ad set ad.status='REJECTED' where ad.fwoNum in(:fwoNum)"
			 * ; Query query = session.createQuery(queryStr);
			 * 
			 * query.setParameterList("fwoNum", adhocOrderIds); int result =
			 * query.executeUpdate();
			 * 
			 * String qstr =
			 * " UPDATE PremiumFreightChargeDetails p set p.Status='REJECTED' where p.orderId in(:adhocOrderId)"
			 * ; Query q2 = session.createQuery(qstr);
			 * q2.setParameterList("adhocOrderId", adhocOrderIds); int result2 =
			 * query.executeUpdate();
			 */
			/*
			 * if (result == 1) { responseDto.setMessage("delete success");
			 * responseDto.setStatus("SUCCESS"); responseDto.setCode("00");
			 * return responseDto; } else {
			 * responseDto.setMessage("delete failed: Query not Executed");
			 * responseDto.setStatus("FAIL"); responseDto.setCode("01"); return
			 * responseDto; }
			 */
		} catch (Exception e) {
			responseDto.setCode("02");
			responseDto.setMessage("Save or Update Failed due to " + e.getMessage());
			responseDto.setStatus("FAIL");

		} finally {
			session.flush();
			session.clear();
			tx.commit();
			session.close();

		}
		return responseDto;
	}

	@Override
	public ResponseDto addCarrier(CarrierDetailsDto carrierdto) {
		ResponseDto responseDto = new ResponseDto();
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			CarrierDetails cdetails = new CarrierDetails();
			System.out.println(carrierdto.getBpNumber());
			cdetails = carrierDetailsDao.importCarrierDetails(carrierdto);
			System.out.println(cdetails.getBpNumber());
			session.saveOrUpdate(cdetails);
			responseDto.setMessage("Save success");
			responseDto.setStatus("SUCCESS");
			responseDto.setCode("00");
		} catch (Exception e) {
			responseDto.setCode("02");
			responseDto.setMessage("Save or Update Failed due to " + e.getMessage());
			responseDto.setStatus("FAIL");
		} finally {
			session.flush();
			session.clear();
			tx.commit();
			session.close();
		}
		return responseDto;
	}

	// workflow API

	@Override
	public ResponseMessage updateTableDetails(WorkflowPremiumCustomDto dto)
			throws ClientProtocolException, JSONException, IOException {
		// AdhocOrderWorkflowDto----> for workflow log table
		AdhocOrderWorkflowDto workflowDto = new AdhocOrderWorkflowDto();
		workflowDto = prepareAdhocApprovalWorkflowDto(dto);
		System.out.println("Yuhooo" + workflowDto.getOrderId());
		return updateTableBasedoOnRole(dto);
		
	}

	public String approveTask() {
		return null;
	}

	private ResponseMessage updateTableBasedoOnRole(WorkflowPremiumCustomDto dto) {

		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		String current_role = dto.getRoleDetails();

		AdhocOrders aorder = new AdhocOrders();
		List<AdhocOrders> aorders = new ArrayList<AdhocOrders>();
		PremiumFreightChargeDetails pdetail = new PremiumFreightChargeDetails();
		List<PremiumFreightChargeDetails> pdetails = new ArrayList<PremiumFreightChargeDetails>();

		String orderId = dto.getOrderIdDetails();

		Criteria criteria_adhoc = session.createCriteria(AdhocOrders.class);
		criteria_adhoc.add(Restrictions.eq("fwoNum", orderId));
		aorders = criteria_adhoc.list();

		Criteria criteria_premium = session.createCriteria(PremiumFreightChargeDetails.class);
		criteria_premium.add(Restrictions.eq("orderId", orderId));
		pdetails = criteria_premium.list();

		StringBuilder userIdList = new StringBuilder();
		List<LchRole> roles = new ArrayList<LchRole>();

		

		if (current_role.equalsIgnoreCase("LCH_Manager")) {
			if (dto.getManagerActionType().equalsIgnoreCase("Approved")) {
				Criteria criteria_role = session.createCriteria(LchRole.class);
				criteria_role.add(Restrictions.eq("role", "LCH_Accountant"));
				roles = criteria_role.list();

				for (LchRole l : roles) {
					userIdList.append(l.getUserId());
					userIdList.append(",");
				}

				String userIds = userIdList.substring(0, userIdList.length() - 1);

				for (PremiumFreightChargeDetails p : pdetails) {
					p.setStatus("Pending at Accountant");

					session.saveOrUpdate(p);

				}
				for (AdhocOrders a : aorders) {
					a.setStatus("Pending at Accountant");

					a.setPendingWith(userIds);
					session.saveOrUpdate(a);
				}
			}
			else{
				for (PremiumFreightChargeDetails p : pdetails) {
					p.setStatus("Rejected");

					session.saveOrUpdate(p);

				}
				for (AdhocOrders a : aorders) {
					a.setStatus("Rejected");

					a.setPendingWith(null);
					session.saveOrUpdate(a);
				}
			}

		}

		if (current_role.equalsIgnoreCase("LCH_Accountant"))
		{
			
			for (AdhocOrders a : aorders) {
				a.setStatus(dto.getAccountantActionType().equalsIgnoreCase("Approved")?"COMPLETED":"REJECTED");
				a.setPendingWith(null);
				session.saveOrUpdate(a);
			}
			for (PremiumFreightChargeDetails p : pdetails) {
				p.setStatus(dto.getAccountantActionType().equalsIgnoreCase("Approved")?"COMPLETED":"REJECTED");
				session.saveOrUpdate(p);

			}
			PremiumOrderAccountingDetails accountingDetails = new PremiumOrderAccountingDetails();
			accountingDetails= accoutingDetailsdao.importPremiumOrderAccountingDetails(dto.getAccoutingDetailsDto());
			session.saveOrUpdate(accountingDetails);
			

		}

		ResponseMessage response = new ResponseMessage(dto.getOrderIdDetails());
		return response;
	}

	public AdhocOrderWorkflowDto prepareAdhocApprovalWorkflowDto(WorkflowPremiumCustomDto data)
			throws JSONException, ClientProtocolException, IOException {
		AdhocOrderWorkflowDto workflowDto = new AdhocOrderWorkflowDto();
		System.err.println("[prepareAdhocApprovalWorkflowDto WorkflowCustomDto] : " + data.toString() + "---"
				+ data.getTaskIdDetails());

		workflowDto.setOrderId(data.getOrderIdDetails());
		JSONObject obj = wfInvokerLocal.getWorkflowApprovalTaskInstanceId(data.getTaskIdDetails());
		System.err.println("[prepareAdhocApprovalWorkflowDto objectData] : " + obj.toString());
		LOGGER.info("objectData:: " + obj.toString());
		LOGGER.info("objectData Array:: " + obj.toString());
		workflowDto.setDescription(obj.getString("description"));
		workflowDto.setInstanceId(obj.getString("id"));

		workflowDto.setPendingWith(null);

		workflowDto.setRequestedBy(obj.getString("createdBy"));
		workflowDto.setRequestedDate(ServiceUtil.convertStringToDate(obj.getString("createdAt")));
		workflowDto.setUpdatedDate(new java.util.Date());
		workflowDto.setSubject(obj.getString("subject"));
		workflowDto.setUpdatedBy(obj.getString("processor"));
		workflowDto.setStatus(obj.getString("status"));
		return workflowDto;

	}

	// Will get all the manager Table details

	@Override
	public List<PremiumManagerCustomDto> getManagerOrders(String userId)
			throws ClientProtocolException, IOException, JSONException {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();

		List<PremiumManagerCustomDto> premiumManagerCustomDtos = new ArrayList<PremiumManagerCustomDto>();
		List<AdhocOrderWorkflow> adhocOrderWorkflows = new ArrayList<AdhocOrderWorkflow>();

		// Will get the JSON array of List of Ids

		List<TaskDetailsDto> workflowDetailsList = wfInvokerLocal.getAllWorkflowTaskInstanceId(userId);
		for (TaskDetailsDto t : workflowDetailsList) {

			String instanceId = t.getTaskId();
			String workflowInstanceId = t.getWorkflowInstanceId();
			String workflowDefinitionId = t.getWorkflowDefinitionId();
			PremiumManagerCustomDto premiumcustomDto = new PremiumManagerCustomDto();

			premiumcustomDto.setTaskDetailsDto(t);

			StringBuilder orderId = new StringBuilder();
			Criteria criteria = session.createCriteria(AdhocOrderWorkflow.class);
			if (workflowDefinitionId != null && workflowDefinitionId.equals("")) {
				criteria.add(Restrictions.eq("workflowDefinitionId", "premiumWorkflow"));
			}
			if (criteria.list() == null) {
				return null;
			}
			criteria.add(Restrictions.eq("instanceId", instanceId));
			// task Id.
			if (workflowInstanceId != null && !(workflowInstanceId.equals(""))) {
				criteria.add(Restrictions.eq("workflowInstanceId", workflowInstanceId));

			}

			adhocOrderWorkflows = criteria.list();
			for (AdhocOrderWorkflow a : adhocOrderWorkflows) {
				if (adhocOrderWorkflows.size() == 1) {
					orderId.append(a.getOrderId());
				} else {
					orderId.append(adhocOrderWorkflows.get(0).getOrderId());

				}
			}

			Criteria criteria_adhoc = session.createCriteria(AdhocOrders.class);
			criteria_adhoc.add(Restrictions.eq("fwoNum", orderId));
			List<AdhocOrders> adorders = new ArrayList<AdhocOrders>();
			adorders = criteria.list();
			PremiumFreightDto1 pdetails = new PremiumFreightDto1();
			for (AdhocOrders a : adorders) {
				pdetails = exportPremiumFreightOrders1(a);
			}
			premiumcustomDto.setPremiumFreightDto1(pdetails);
			premiumManagerCustomDtos.add(premiumcustomDto);
		}
		return premiumManagerCustomDtos;
	}

}
