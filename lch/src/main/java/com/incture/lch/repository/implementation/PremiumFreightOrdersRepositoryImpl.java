package com.incture.lch.repository.implementation;

import java.io.IOException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.incture.lch.adhoc.custom.dto.ResponseMessage;
import com.incture.lch.adhoc.workflow.dto.PremiumWorkflowApprovalTaskDto;
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
import com.incture.lch.dto.PremiumFreightDto1;
import com.incture.lch.dto.PremiumFreightOrderDto;
import com.incture.lch.dto.PremiumOrderAccountingDetailsDto;
import com.incture.lch.dto.PremiumRequestDto;
import com.incture.lch.dto.RejectAtPlannerDto;
import com.incture.lch.dto.ResponseDto;
import com.incture.lch.dto.TaskDetailsDto;
import com.incture.lch.entity.AdhocOrderWorkflow;
import com.incture.lch.entity.AdhocOrders;
import com.incture.lch.entity.CarrierDetails;
import com.incture.lch.entity.LchRole;
import com.incture.lch.entity.PremiumFreightChargeDetails;
import com.incture.lch.entity.PremiumOrderAccountingDetails;
import com.incture.lch.entity.PremiumRole;
import com.incture.lch.exception.CarrierNotExistException;
import com.incture.lch.exception.PageNumberNotFoundException;
import com.incture.lch.exception.SetCarrierDetailsException;
import com.incture.lch.premium.custom.dto.PremiumCustomDto;
import com.incture.lch.premium.custom.dto.PremiumRequestUserInfoCustomDto;
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
	private PremiumWorkflowInvokerLocal wfInvokerLocal;

	@Autowired
	private PremiumFreightApprovalRuleDao ruleDao;

	@Autowired
	private PremiumOrderAccountingDetailsDao accoutingDetailsdao;

	@Autowired
	private AdhocOrderWorkflowDao adhocOrderWorkflowDao;
	
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

	@SuppressWarnings({ "unchecked", "deprecation" })
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

	// Paginated List of Data for all screen
	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public PaginationDto getAllPremiumFreightOrders(PremiumRequestDto premiumRequestDto)
			throws ClientProtocolException, IOException, JSONException {

		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();

		String current_userId = premiumRequestDto.getUserId();

		System.err.println("The UserId is " + current_userId);

		PaginationDto paginationDtoCommon = new PaginationDto();

		Criteria criteria_role = session.createCriteria(PremiumRole.class);
		List<PremiumRole> role = new ArrayList<PremiumRole>();

		criteria_role.add(Restrictions.eq("userId", current_userId));

		role = criteria_role.list();

		String current_page_role = "";
		for (PremiumRole l : role) {
			current_page_role = l.getRole();
		}

		// String current_page_role = premiumRequestDto.getUserRole();

		System.err.println("The User Role is  " + current_page_role);

		if (current_page_role.equalsIgnoreCase("LCH_Planner")) {
			PaginationDto1 paginationDtoForPlanner = getPlannerOrders(premiumRequestDto);
			List<PremiumFreightDto1> premiumFreightDto1s = new ArrayList<PremiumFreightDto1>();
			premiumFreightDto1s = paginationDtoForPlanner.getPremiumFreightDto1();

			List<PremiumCustomDto> premiumCustomDtos = new ArrayList<PremiumCustomDto>();
			for (PremiumFreightDto1 p : premiumFreightDto1s) {
				// PremiumCustomDto premiumManagerCustomDto1 = new
				// PremiumCustomDto();
				PremiumCustomDto premiumCustomDto = new PremiumCustomDto();

				premiumCustomDto.setPremiumFreightDto1(p);
				premiumCustomDto.setTaskDetailsDto(null);
				// System.err.println(premiumManagerCustomDto.getPremiumFreightDto1().getOrderId());
				premiumCustomDtos.add(premiumCustomDto);
				// System.err.println("Size :" +
				// premiumManagerCustomDtos.size());

			}
			paginationDtoCommon.setPremiumFreightOrderDetailsList(premiumCustomDtos);
			paginationDtoCommon.setCount(paginationDtoForPlanner.getCount());

		} else if (current_page_role.equalsIgnoreCase("LCH_Carrier_Admin")) {
			ChargeDetailsPaginated paginationDtoForCarrier = getAllCarrierOrders(premiumRequestDto);
			List<PremiumFreightChargeDetails> premiumFreightChargeDetailsList = new ArrayList<PremiumFreightChargeDetails>();
			premiumFreightChargeDetailsList = paginationDtoForCarrier.getPremiumFreightChargeDetails();

			List<PremiumFreightDto1> premiumFreightDto1s = new ArrayList<PremiumFreightDto1>();
			for (PremiumFreightChargeDetails p : premiumFreightChargeDetailsList) {
				PremiumFreightDto1 premiumFreightDto1 = new PremiumFreightDto1();

				premiumFreightDto1.setBpNumber(p.getBpNumber());
				premiumFreightDto1.setCarrierDetails(p.getCarrierDetails());
				premiumFreightDto1.setCarrierMode(p.getCarrierMode());
				premiumFreightDto1.setCarrierRatePerKM(p.getCarrierRatePerKM());
				premiumFreightDto1.setCarrierScac(p.getCarrierScac());
				premiumFreightDto1.setCharge(p.getCharge());
				premiumFreightDto1.setCountryOrigin(null);

				if (p.getCreatedDate() != null && !(p.getCreatedDate().equals(""))) {
					premiumFreightDto1.setCreatedDate(ServiceUtil.convertDateToString(p.getCreatedDate()));
				}
				// Carrier
				premiumFreightDto1.setCreatedBy(null);
				premiumFreightDto1.setCreatedDate(null);
				premiumFreightDto1.setCurrency(null);
				premiumFreightDto1.setDestinationAddress(p.getDestinationAdress());
				premiumFreightDto1.setDestinationCity(p.getDestinationCity());
				premiumFreightDto1.setDestinationCountry(p.getDestinationCountry());
				premiumFreightDto1.setDestinationName(p.getDestinationName());
				premiumFreightDto1.setDestinationState(p.getDestinationState());
				premiumFreightDto1.setDestinationZip(p.getDestinationZip());
				premiumFreightDto1.setOrderId(p.getorderId());
				premiumFreightDto1.setOriginAddress(p.getOriginAddress());
				premiumFreightDto1.setOriginCity(p.getOriginCity());
				premiumFreightDto1.setOriginCountry(p.getOriginCountry());
				premiumFreightDto1.setOriginName(p.getOriginName());
				premiumFreightDto1.setOriginState(p.getOriginState());
				premiumFreightDto1.setOriginZip(p.getOriginZip());
				premiumFreightDto1.setPlannerEmail(p.getPlannerEmail());
				premiumFreightDto1.setReasonCode(p.getReasonCode());
				premiumFreightDto1.setStatus(p.getStatus());

				premiumFreightDto1s.add(premiumFreightDto1);

			}

			List<PremiumCustomDto> premiumCustomDtos = new ArrayList<PremiumCustomDto>();
			for (PremiumFreightDto1 p : premiumFreightDto1s) {
				PremiumCustomDto premiumCustomDto = new PremiumCustomDto();

				premiumCustomDto.setPremiumFreightDto1(p);
				premiumCustomDto.setTaskDetailsDto(null);
				premiumCustomDtos.add(premiumCustomDto);
			}
			paginationDtoCommon.setPremiumFreightOrderDetailsList(premiumCustomDtos);
			paginationDtoCommon.setCount(paginationDtoForCarrier.getCount());
		}

		// Condition for Manager
		else if (current_page_role.equalsIgnoreCase("LCH_Manager")) {
			List<PremiumCustomDto> premiumManagerCustomDtoList = new ArrayList<PremiumCustomDto>();
			// premiumManagerCustomDtoList = getManagerOrders(current_userId);
			premiumManagerCustomDtoList = getManagerOrders(premiumRequestDto);

			paginationDtoCommon.setPremiumFreightOrderDetailsList(premiumManagerCustomDtoList);
			paginationDtoCommon.setCount(premiumManagerCustomDtoList.size());

		}

		session.flush();
		session.clear();
		tx.commit();
		session.close();
		return paginationDtoCommon;
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	public PaginationDto1 getPlannerOrders(PremiumRequestDto premiumRequestDto) {
		List<PremiumFreightDto1> premiumFreightDto1s = new ArrayList<>();
		PaginationDto1 paginationDto1 = new PaginationDto1();
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();

		Criteria criteria_role = session.createCriteria(PremiumRole.class);
		String plannerEmail = "";
		List<PremiumRole> role = new ArrayList<PremiumRole>();
		criteria_role.add(Restrictions.eq("userId", premiumRequestDto.getUserId()));
		role = criteria_role.list();

		for (PremiumRole l : role) {
			plannerEmail = l.getUserEmail();
		}

		System.err.println("The planner Email is " + plannerEmail);
		Criteria criteria = session.createCriteria(AdhocOrders.class);

		criteria.add(Restrictions.eq("premiumFreight", "true"));
		criteria.add(Restrictions.ne("status", "REJECTED"));
		criteria.add(Restrictions.eq("plannerEmail", plannerEmail));
		criteria.add(Restrictions.ne("status", "Pending at Manager"));

		if (premiumRequestDto.getorderId() != null && !(premiumRequestDto.getorderId().equals(""))) {
			criteria.add(Restrictions.eq("orderId", premiumRequestDto.getorderId()));

		}
		if ((premiumRequestDto.getFromDate() != null && !(premiumRequestDto.getFromDate().equals("")))
				&& (premiumRequestDto.getToDate() != null) && !(premiumRequestDto.getToDate().equals(""))) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			try {
				Date d1 = (Date) sdf.parse(premiumRequestDto.getFromDate());
				Date d2 = (Date) sdf.parse(premiumRequestDto.getToDate());
				criteria.add(Restrictions.between("createdDate", d1, d2));
			} catch (Exception e) {
				System.err.println("Error in date format: " + e);
			}

		}

		if (premiumRequestDto.getReasonCode() != null && !(premiumRequestDto.getReasonCode().equals(""))) {
			System.err.println("Entered the Reason Code : " + premiumRequestDto.getReasonCode());
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

	@SuppressWarnings({ "deprecation", "unchecked" })
	public ChargeDetailsPaginated getAllCarrierOrders(PremiumRequestDto premiumRequestDto) {

		List<PremiumFreightChargeDetails> premiumFreightChargeDetails = new ArrayList<PremiumFreightChargeDetails>();
		ChargeDetailsPaginated chargeDetailsPaginated = new ChargeDetailsPaginated();
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();

		Criteria criteria = session.createCriteria(PremiumFreightChargeDetails.class);

		criteria.add(Restrictions.eq("status", "Pending with Carrier Admin"));

		if (premiumRequestDto.getorderId() != null && !(premiumRequestDto.getorderId().equals(""))) {
			criteria.add(Restrictions.eq("orderId", premiumRequestDto.getorderId()));

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

		}

		if (premiumRequestDto.getReasonCode() != null && !(premiumRequestDto.getReasonCode().equals(""))) {
			criteria.add(Restrictions.eq("reasonCode", premiumRequestDto.getReasonCode()));

		}
		if (premiumRequestDto.getStatus() != null && !(premiumRequestDto.getStatus().equals(""))) {
			criteria.add(Restrictions.eq("status", premiumRequestDto.getStatus()));
		}

		if (premiumRequestDto.getOriginName() != null && !(premiumRequestDto.getOriginName().equals(""))) {
			criteria.add(Restrictions.eq("originName", premiumRequestDto.getOriginName()));
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

	/*
	 * @SuppressWarnings("unchecked")
	 * 
	 * @Override public ChargeDetailsPaginated
	 * getAllManagerOrders(PremiumRequestDto premiumRequestDto) {
	 * 
	 * List<PremiumFreightChargeDetails> premiumFreightChargeDetails = new
	 * ArrayList<PremiumFreightChargeDetails>(); ChargeDetailsPaginated
	 * chargeDetailsPaginated = new ChargeDetailsPaginated(); Session session =
	 * sessionFactory.openSession(); Transaction tx =
	 * session.beginTransaction();
	 * 
	 * @SuppressWarnings("deprecation") Criteria criteria =
	 * session.createCriteria(PremiumFreightChargeDetails.class);
	 * 
	 * criteria.add(Restrictions.eq("status", "Pending at Manager"));
	 * 
	 * if (premiumRequestDto.getorderId() != null &&
	 * !(premiumRequestDto.getorderId().equals(""))) {
	 * criteria.add(Restrictions.eq("orderId", premiumRequestDto.getorderId()));
	 * 
	 * } if ((premiumRequestDto.getFromDate() != null &&
	 * !(premiumRequestDto.getFromDate().equals(""))) &&
	 * (premiumRequestDto.getToDate() != null) &&
	 * !(premiumRequestDto.getToDate().equals(""))) { SimpleDateFormat sdf = new
	 * SimpleDateFormat("yyyy-MM-dd"); try { Date d1 = (Date)
	 * sdf.parse(premiumRequestDto.getFromDate()); Date d2 = (Date)
	 * sdf.parse(premiumRequestDto.getToDate());
	 * criteria.add(Restrictions.between("createdDate", d1, d2)); } catch
	 * (Exception e) { System.out.println("Error in date format"); }
	 * 
	 * }
	 * 
	 * if (premiumRequestDto.getReasonCode() != null &&
	 * !(premiumRequestDto.getReasonCode().equals(""))) {
	 * criteria.add(Restrictions.eq("premiumReasonCode",
	 * premiumRequestDto.getReasonCode()));
	 * 
	 * } if (premiumRequestDto.getStatus() != null &&
	 * !(premiumRequestDto.getStatus().equals(""))) {
	 * criteria.add(Restrictions.eq("status", premiumRequestDto.getStatus())); }
	 * 
	 * if (premiumRequestDto.getOriginName() != null &&
	 * !(premiumRequestDto.getOriginName().equals(""))) {
	 * criteria.add(Restrictions.eq("shipperName",
	 * premiumRequestDto.getOriginName())); }
	 * 
	 * if (premiumRequestDto.getDestinationName() != null &&
	 * !(premiumRequestDto.getDestinationName().equals(""))) {
	 * criteria.add(Restrictions.eq("destinationName",
	 * premiumRequestDto.getDestinationName())); }
	 * 
	 * criteria.addOrder(Order.asc("orderId")); int total_entries =
	 * criteria.list().size(); System.out.println("Enter the page number" +
	 * premiumRequestDto.getPageNumber()); int startNum =
	 * (premiumRequestDto.getPageNumber() - 1) * 10;
	 * 
	 * System.out.println("Page Number " + startNum); if
	 * (premiumRequestDto.getNoOfEntry() > total_entries) { throw new
	 * PageNumberNotFoundException(total_entries); }
	 * 
	 * criteria.setFirstResult(startNum); if (premiumRequestDto.getNoOfEntry()
	 * == 0) { criteria.setMaxResults(10); } else {
	 * criteria.setMaxResults(premiumRequestDto.getNoOfEntry()); }
	 * 
	 * premiumFreightChargeDetails = criteria.list();
	 * 
	 * chargeDetailsPaginated.setPremiumFreightChargeDetails(
	 * premiumFreightChargeDetails);
	 * chargeDetailsPaginated.setCount(total_entries);
	 * 
	 * session.flush(); session.clear(); tx.commit(); session.close(); return
	 * chargeDetailsPaginated; }
	 */
	@Override
	@SuppressWarnings({ "unchecked", "deprecation" })
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

	@SuppressWarnings({ "deprecation", "unchecked" })
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
	@SuppressWarnings({ "unchecked", "deprecation", "rawtypes" })
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

					PremiumOrderAccountingDetails accountingDetails = new PremiumOrderAccountingDetails();
					// Getting the data from charge details table for the same
					// id
					criteria.add(Restrictions.eq("orderId", adid));
					// Setting the new object for PremiumCharge Details
					premiumFreightChargeDetails.setorderId(adid);

					// Setting the new Object for Premium Accounting Details
					accountingDetails.setOrderId(adid);

					accountingDetails.setGlCode(aorders.getGlCode());
					System.err.println("Quantity " + aorders.getQuantity());
					accountingDetails.setQuantity(aorders.getQuantity());

					accountingDetails.setPlannerEmail(aorders.getPlannerEmail());
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
						accountingDetails.setCarrier(cdets.getCarrierDetails());

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

					premiumFreightChargeDetails.setCreatedDate(aorders.getCreatedDate());
					premiumFreightChargeDetails.setShipDate(aorders.getShipDate());
					premiumFreightChargeDetails.setExpectedDeliveryDate(aorders.getExpectedDeliveryDate());

					// Logic to insert in the Accounting Table:

					session.saveOrUpdate(premiumFreightChargeDetails);
					session.saveOrUpdate(accountingDetails);

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
			workflowDto.setPendingWithApprover(adhocOrders.getPendingWith());
			workflowDto.setPendingWithAccountant(adhocOrders.getPendingWith());
			

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

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public String setCharge(List<ChargeRequestDto> dto) {
		// Getting List of Charges with orderIds
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		PremiumFreightChargeDetails premiumFreightChargeDetail = new PremiumFreightChargeDetails();
		List<PremiumOrderAccountingDetails> accountingDetailsList = new ArrayList<PremiumOrderAccountingDetails>();
		List<PremiumFreightChargeDetails> premiumFreightChargeDetails = new ArrayList<PremiumFreightChargeDetails>();

		for (ChargeRequestDto c : dto) {
			// Fetching the AdhocOrders List from all the AdhocOrderId received
			Criteria criteria = session.createCriteria(AdhocOrders.class);
			criteria.add(Restrictions.eq("fwoNum", c.getorderId()));
			List<AdhocOrders> adhocOrders = new ArrayList<AdhocOrders>();
			// adhocOrders will have list of all orders
			adhocOrders = criteria.list();
			// For every AdhocOrder Details update the Status.
			for (AdhocOrders a : adhocOrders) {
				a.setStatus("Pending At Planner");
				session.saveOrUpdate(a);

			}

			// Fetching the list of PremiumFreighChargeDetails from the orderId
			Criteria criteria2 = session.createCriteria(PremiumFreightChargeDetails.class);
			criteria2.add(Restrictions.eq("orderId", c.getorderId()));
			premiumFreightChargeDetails = criteria2.list();

			// Condition when no such orderId exist in Charge Table
			// Creating a new one using the adhocOrders details
			if (premiumFreightChargeDetails == null) {
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
				// We are getting the bpnumber and carrierMode from the
				// front-end
				// so we need to fetch the details from that from carrier Table
				List<CarrierDetails> carrierDetails = new ArrayList<CarrierDetails>();
				Criteria criteria3 = session.createCriteria(CarrierDetails.class);
				criteria3.add(Restrictions.eq("bpNumber", c.getBpNumber()));
				criteria3.add(Restrictions.eq("carrierMode", c.getCarrierMode()));
				carrierDetails = criteria3.list();
				//// Since only one carrier is there , setting the carrier
				//// Details in the charge details object
				for (CarrierDetails cdets : carrierDetails) {
					premiumFreightChargeDetail.setBpNumber(cdets.getBpNumber());
					premiumFreightChargeDetail.setCarrierScac(cdets.getCarrierScac());
					premiumFreightChargeDetail.setCarrierDetails(cdets.getCarrierDetails());
					premiumFreightChargeDetail.setCarrierMode(cdets.getCarrierMode());
					// We will update the Accounting
					// AccountinDetails.setCharge

				}
				// setting the chage from the details obtained
				premiumFreightChargeDetail.setCharge(c.getCharge());
				session.saveOrUpdate(premiumFreightChargeDetail);

			}
			// If there is already a record from that orderId
			else {
				// Go thru the list and update the necessary field
				for (PremiumFreightChargeDetails p : premiumFreightChargeDetails) {
					System.out.println("This is where it is alreday present");
					p.setCharge(c.getCharge());
					p.setStatus("Pending At Planner");
					session.saveOrUpdate(p);
				}
			}
			// Setting details in the accounting table

			System.err.println("The Charge is :" + c.getCharge());

			Criteria criteria_accounting = session.createCriteria(PremiumOrderAccountingDetails.class);
			criteria_accounting.add(Restrictions.eq("orderId", c.getorderId()));
			accountingDetailsList = criteria_accounting.list();
			System.err.println("The Size Is :" + accountingDetailsList.size());

			for (PremiumOrderAccountingDetails accnt_dets : accountingDetailsList) {

				System.err.println("The Charge is :" + c.getCharge());
				accnt_dets.setTotalCost(c.getCharge());
				session.saveOrUpdate(accnt_dets);
			}

		}

		session.flush();
		session.clear();
		tx.commit();
		session.close();
		return "Charge Set";

	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	@Override
	public ResponseDto forwardToApprover(PremiumRequestUserInfoCustomDto premiumRequestCustomDtos) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		ResponseDto responseDto = new ResponseDto();

		try {
			for (PremiumRequestDto p : premiumRequestCustomDtos.getPremiumRequestDtoList()) {

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
				// List<String> pendingWith = new ArrayList<String>();
				String pendingWith = "";
				for (PremiumFreightChargeDetails pdetail : premiumChargeDetails) {
					System.out.println(pdetail.getStatus());

					System.out.println(pdetail.getorderId());
					pdetail.setStatus("Pending at Manager");

					charge = pdetail.getCharge();

					// pendingWith.add(ruleDao.getApproverByCost(charge));
					pendingWith = (ruleDao.getApproverByCost(charge));

					session.saveOrUpdate(pdetail);

					System.out.println(pdetail.getStatus());
					System.out.println("Updating in Charge Table");

				}
				// int i = 0;
				for (AdhocOrders a : adhocOrders) {

					System.out.println(a.getFwoNum());
					System.out.println(a.getStatus());

					a.setStatus("Pending at Manager");
					// a.setPendingWith(pendingWith.get(i));
					a.setPendingWith(pendingWith);

					session.saveOrUpdate(a);
					// updating in AdhocTable
					System.out.println(a.getStatus());
					System.out.println("Updating in Adhoc Table ");

				}


				// Creating Workflow DTO
				System.out.println("triggering the workflow");
				PremiumWorkflowApprovalTaskDto premiumWorkflowDto = new PremiumWorkflowApprovalTaskDto();
				premiumWorkflowDto = exportToPremiumWorkflowDto(adhocOrders, premiumChargeDetails);

				// Triggering Premium Workflow
				wfService.triggerPremiumWorkflow(premiumWorkflowDto, premiumRequestCustomDtos.getUserDetailInfo());

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

	@SuppressWarnings({ "deprecation", "unchecked" })
	@Override
	public ResponseDto RejectPremiumOrder(RejectAtPlannerDto dto) {
		ResponseDto responseDto = new ResponseDto();
		
		List<String> adhocOrderIds = dto.getAdhocorderIds();
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
					p.setComment("Rejected By planner : "+dto.getComment());
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
				System.out.println("Yuhooo" + dto.getOrderIdDetails());
		return updateTableBasedoOnRole(dto);

	}

	public String approveTask() {
		return null;
	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	private ResponseMessage updateTableBasedoOnRole(WorkflowPremiumCustomDto dto) throws ClientProtocolException, JSONException, IOException {

		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		String current_role = dto.getRoleDetails();

		List<AdhocOrders> aorders = new ArrayList<AdhocOrders>();
		List<PremiumFreightChargeDetails> pdetails = new ArrayList<PremiumFreightChargeDetails>();

		String orderId = dto.getOrderIdDetails();

		LOGGER.info("OrderId " + orderId);

		Criteria criteria_adhoc = session.createCriteria(AdhocOrders.class);
		criteria_adhoc.add(Restrictions.eq("fwoNum", orderId));
		aorders = criteria_adhoc.list();

		LOGGER.info("Size of the Adhoc " + aorders.size());

		Criteria criteria_premium = session.createCriteria(PremiumFreightChargeDetails.class);
		criteria_premium.add(Restrictions.eq("orderId", orderId));
		pdetails = criteria_premium.list();

		LOGGER.info("Size of the Premium Charge " + pdetails.size());

		StringBuilder userIdList = new StringBuilder();
		List<LchRole> roles = new ArrayList<LchRole>();

		LOGGER.info("role :: " + current_role + "  --  " + dto.getManagerActionType()+ " -- " + dto.getAccountantActionType());
		
		AdhocOrderWorkflowDto workflowDto = new AdhocOrderWorkflowDto();
		
		
		workflowDto = prepareAdhocApprovalWorkflowDto(dto);
		LOGGER.info("Workflow DTO built" + workflowDto.getInstanceId() + "  --  " + workflowDto.getOrderId());


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
					LOGGER.info("SAVING CHARGE DETAILS--- ");

				}
				for (AdhocOrders a : aorders) {
					a.setStatus("Pending at Accountant");
					LOGGER.info("SAVING ADHOC ORDERS DETAILS--- ");

					a.setPendingWith(userIds);
					session.saveOrUpdate(a);
				}
			} else {
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

		if (current_role.equalsIgnoreCase("LCH_Accountant")) {
			LOGGER.info("Entering into Accountant Conditions.");


			String status=dto.getAccountantActionType().equalsIgnoreCase("Approved") ? "COMPLETED" : "REJECTED";
			LOGGER.info("--Status-- : "+status );

			for (AdhocOrders a : aorders) {
				LOGGER.info("Entering into Accountant Conditions.  -----    OrderId Saving : "+a.getFwoNum()  );

				a.setStatus(status);
				a.setPendingWith("");
				session.saveOrUpdate(a);
				LOGGER.info("Exiting into Accountant Conditions.  -----     OrderId saved: "+a.getFwoNum()  );

			}
			for (PremiumFreightChargeDetails p : pdetails) {
				LOGGER.info("Entering into Accountant Conditions.  -----   Premium  OrderId Saving : "+p.getOrderId()  );

				p.setStatus(status);
				//if(dto.getAccoutingDetailsDto().getComment().equal)
				p.setComment("Approved at Accountant ");

				session.saveOrUpdate(p);
				
				LOGGER.info("Exiting into Accountant Conditions.  -----   Premium  OrderId saved : "+p.getOrderId()  );


			}
			if(dto.getAccoutingDetailsDto()!=null)
			{
			PremiumOrderAccountingDetails accountingDetails = new PremiumOrderAccountingDetails();
			accountingDetails = accoutingDetailsdao.importPremiumOrderAccountingDetails(dto.getAccoutingDetailsDto());
			LOGGER.info("Saving Accounting Details , Order ID :" + accountingDetails.getOrderId());

			session.saveOrUpdate(accountingDetails);

			}
		}

		session.save(adhocOrderWorkflowDao.importAdhocWorkflow(workflowDto));

		ResponseMessage response = new ResponseMessage(dto.getOrderIdDetails());
		session.flush();
		session.clear();
		tx.commit();
		session.close();
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
		//workflowDto.setDescription(obj.getString("description"));
		workflowDto.setInstanceId(obj.getString("id"));

		workflowDto.setPendingWith(null);

		workflowDto.setRequestedBy(obj.getString("createdBy"));
		//workflowDto.setRequestedDate(ServiceUtil.convertStringToDate(obj.getString("createdAt")));
		workflowDto.setUpdatedDate(new java.util.Date());
		workflowDto.setSubject(obj.getString("subject"));
		workflowDto.setUpdatedBy(obj.getString("processor"));
		workflowDto.setStatus(obj.getString("status"));
		return workflowDto;

	}

	@SuppressWarnings({ "deprecation", "unchecked" })

	@Override
	public List<PremiumCustomDto> getAllAccountantOrders(PremiumRequestDto premiumRequestDto)
			throws ClientProtocolException, IOException, JSONException {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();

		String userId = premiumRequestDto.getUserId();
		List<PremiumCustomDto> premiumCustomDtos = new ArrayList<PremiumCustomDto>();

		List<AdhocOrderWorkflow> adhocOrderWorkflows = new ArrayList<AdhocOrderWorkflow>();

		// Will get the JSON array of List of Ids

		List<TaskDetailsDto> workflowDetailsList = wfInvokerLocal.getAllWorkflowTaskInstanceId(userId);
		for (TaskDetailsDto t : workflowDetailsList) {

			String workflowInstanceId = t.getWorkflowInstanceId();
			String workflowDefinitionId = t.getWorkflowDefinitionId();

			Criteria criteria = session.createCriteria(AdhocOrderWorkflow.class);
			if (workflowDefinitionId != null && workflowDefinitionId.equals("")) {
				criteria.add(Restrictions.eq("workflowDefinitionId", "premiumorderworkflow"));
			}

			criteria.add(Restrictions.eq("instanceId", workflowInstanceId));
			// criteria.add(Restrictions.ne("orderId", null));

			adhocOrderWorkflows = criteria.list();

			if (adhocOrderWorkflows.size() != 0) {
				String orderId = "";

				for (AdhocOrderWorkflow a : adhocOrderWorkflows) {
					orderId = a.getOrderId();
					/*
					 * if (adhocOrderWorkflows.size() == 1) {
					 * orderId.append(a.getOrderId()); orderId = a.getorderId();
					 * } else { //
					 * orderId.append(adhocOrderWorkflows.get(0).getOrderId());
					 * orderId = adhocOrderWorkflows.get(0).getOrderId();
					 * 
					 * }
					 */
				}

				System.err.println("OrderID" + orderId);

				if (orderId != null && !orderId.equals("")) {
					System.err.println("OrderID after coming inside " + orderId);

					Criteria criteria_adhoc = session.createCriteria(AdhocOrders.class);
					criteria_adhoc.add(Restrictions.eq("fwoNum", orderId));
					criteria_adhoc.add(Restrictions.eq("status", "Pending at Accountant"));
					criteria.addOrder(Order.asc("orderId"));
					List<AdhocOrders> adorders = new ArrayList<AdhocOrders>();
					adorders = criteria_adhoc.list();
					System.err.println("Size of AdhocOrder Data received" + adorders.size());

					PremiumFreightDto1 pdetails = new PremiumFreightDto1();

					if (adorders.size() != 0) {
						for (AdhocOrders a : adorders) {

							pdetails = exportPremiumFreightOrders1(a);

							System.err.println(pdetails.getOrderId());

						}

						PremiumCustomDto premiumcustomDto = new PremiumCustomDto();

						premiumcustomDto.setTaskDetailsDto(t);
						premiumcustomDto.setPremiumFreightDto1(pdetails);
						premiumCustomDtos.add(premiumcustomDto);

					}
				}
			}
		}
		session.flush();
		session.clear();
		tx.commit();
		session.close();
		return premiumCustomDtos;
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public PremiumOrderAccountingDetailsDto getPremiumAccountingDetails(String workflowInstanceId) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		System.err.println(workflowInstanceId);
		List<AdhocOrderWorkflow> adhocOrderWorkflowList = new ArrayList<AdhocOrderWorkflow>();
		Criteria criteria_workflow = session.createCriteria(AdhocOrderWorkflow.class);
		criteria_workflow.add(Restrictions.eq("instanceId", workflowInstanceId));
		adhocOrderWorkflowList = criteria_workflow.list();
		System.err.println("Size of the array that contain the instance Id : " + adhocOrderWorkflowList.size());
		List<String> orderIdList = new ArrayList<String>();
		for (AdhocOrderWorkflow workflowdets : adhocOrderWorkflowList) {
			orderIdList.add(workflowdets.getOrderId());
			System.err.println("OrderId: " + orderIdList);
		}

		List<PremiumOrderAccountingDetails> premiumOrderAccountingDetails = new ArrayList<>();

		PremiumOrderAccountingDetailsDto premiumOrderAccountingDetailsDto = new PremiumOrderAccountingDetailsDto();

		for (String orderId : orderIdList) {
			System.err.println("Order Id : " + orderId);
			Criteria criteria = session.createCriteria(PremiumOrderAccountingDetails.class);
			criteria.add(Restrictions.eq("orderId", orderId));
			premiumOrderAccountingDetails = criteria.list();

			System.err.println("Size of details fetched: " + premiumOrderAccountingDetails.size());
			for (PremiumOrderAccountingDetails accountingdetails : premiumOrderAccountingDetails) {
				premiumOrderAccountingDetailsDto = accoutingDetailsdao
						.exportPremiumOrderAccountingDetails(accountingdetails);
			}
		}
		session.flush();
		session.clear();
		tx.commit();
		session.close();

		return premiumOrderAccountingDetailsDto;

	}

	@Override
	public ResponseDto updatePremiumAccountingDetails(PremiumOrderAccountingDetailsDto accountingDetailsDto) {
		ResponseDto responseDto = new ResponseDto();
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			PremiumOrderAccountingDetails padetails = new PremiumOrderAccountingDetails();
			padetails = accoutingDetailsdao.importPremiumOrderAccountingDetails(accountingDetailsDto);
			session.saveOrUpdate(padetails);
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

	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<PremiumCustomDto> getManagerOrders(PremiumRequestDto premiumRequestDto)
			throws ClientProtocolException, IOException, JSONException {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();

		String userId = premiumRequestDto.getUserId();
		List<PremiumCustomDto> premiumCustomDtos = new ArrayList<PremiumCustomDto>();

		List<AdhocOrderWorkflow> adhocOrderWorkflows = new ArrayList<AdhocOrderWorkflow>();

		// Will get the JSON array of List of Ids

		List<TaskDetailsDto> workflowDetailsList = wfInvokerLocal.getAllWorkflowTaskInstanceId(userId);
		for (TaskDetailsDto t : workflowDetailsList) {

			String workflowInstanceId = t.getWorkflowInstanceId();
			String workflowDefinitionId = t.getWorkflowDefinitionId();

			Criteria criteria = session.createCriteria(AdhocOrderWorkflow.class);
			if (workflowDefinitionId != null && workflowDefinitionId.equals("")) {
				criteria.add(Restrictions.eq("workflowDefinitionId", "premiumorderworkflow"));
			}

			criteria.add(Restrictions.eq("instanceId", workflowInstanceId));
			// criteria.add(Restrictions.ne("orderId", null));

			adhocOrderWorkflows = criteria.list();

			if (adhocOrderWorkflows.size() != 0) {
				String orderId = "";

				for (AdhocOrderWorkflow a : adhocOrderWorkflows) {
					orderId = a.getOrderId();
					/*
					 * if (adhocOrderWorkflows.size() == 1) {
					 * orderId.append(a.getOrderId()); orderId = a.getorderId();
					 * } else { //
					 * orderId.append(adhocOrderWorkflows.get(0).getOrderId());
					 * orderId = adhocOrderWorkflows.get(0).getOrderId();
					 * 
					 * }
					 */
				}

				System.err.println("OrderID" + orderId);

				if (orderId != null && !orderId.equals("")) {
					System.err.println("OrderID after coming inside " + orderId);

					Criteria criteria_adhoc = session.createCriteria(AdhocOrders.class);
					criteria_adhoc.add(Restrictions.eq("fwoNum", orderId));
					criteria_adhoc.add(Restrictions.eq("status", "Pending at Manager"));

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

					}
					if (premiumRequestDto.getReasonCode() != null && !(premiumRequestDto.getReasonCode().equals(""))) {
						criteria.add(Restrictions.eq("reasonCode", premiumRequestDto.getReasonCode()));

					}
					if (premiumRequestDto.getStatus() != null && !(premiumRequestDto.getStatus().equals(""))) {
						criteria.add(Restrictions.eq("status", premiumRequestDto.getStatus()));
					}

					if (premiumRequestDto.getOriginName() != null && !(premiumRequestDto.getOriginName().equals(""))) {
						criteria.add(Restrictions.eq("originName", premiumRequestDto.getOriginName()));
					}

					if (premiumRequestDto.getDestinationName() != null
							&& !(premiumRequestDto.getDestinationName().equals(""))) {
						criteria.add(Restrictions.eq("destinationName", premiumRequestDto.getDestinationName()));
					}

					criteria.addOrder(Order.asc("orderId"));
					List<AdhocOrders> adorders = new ArrayList<AdhocOrders>();
					adorders = criteria_adhoc.list();
					System.err.println("Size of AdhocOrder Data received" + adorders.size());

					PremiumFreightDto1 pdetails = new PremiumFreightDto1();

					if (adorders.size() != 0) {
						for (AdhocOrders a : adorders) {

							pdetails = exportPremiumFreightOrders1(a);

							System.err.println(pdetails.getOrderId());

						}

						PremiumCustomDto premiumcustomDto = new PremiumCustomDto();

						premiumcustomDto.setTaskDetailsDto(t);
						premiumcustomDto.setPremiumFreightDto1(pdetails);
						premiumCustomDtos.add(premiumcustomDto);

					}
				}
			}
		}
		session.flush();
		session.clear();
		tx.commit();
		session.close();
		return premiumCustomDtos;
	}

}
