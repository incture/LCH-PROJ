package com.incture.lch.repository.implementation;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hpsf.Array;
import org.apache.poi.util.SystemOutLogger;
import org.apache.xmlbeans.impl.xb.xsdschema.RestrictionDocument.Restriction;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.incture.lch.adhoc.custom.dto.AdhocWorkflowCustomDto;
import com.incture.lch.adhoc.workflow.constant.WorkflowConstants;
import com.incture.lch.adhoc.workflow.dto.PremiumWorkflowApprovalTaskDto;
import com.incture.lch.dao.CarrierDetailsDao;
import com.incture.lch.dto.AdhocOrderWorkflowDto;
import com.incture.lch.dto.CarrierDetailsDto;
import com.incture.lch.dto.ChargeDetailsPaginated;
import com.incture.lch.dto.ChargeRequestDto;
import com.incture.lch.dto.PaginationDto;
import com.incture.lch.dto.PremiumFreightOrderDto;
import com.incture.lch.dto.PremiumRequestDto;
import com.incture.lch.dto.ResponseDto;
import com.incture.lch.entity.AdhocOrders;
import com.incture.lch.entity.CarrierDetails;
import com.incture.lch.entity.LchRole;
import com.incture.lch.entity.OrderIdMapping;
import com.incture.lch.entity.PremiumFreightChargeDetails;
import com.incture.lch.exception.CarrierNotExistException;
import com.incture.lch.exception.FilterInvalidEntryException;
import com.incture.lch.exception.PageNumberNotFoundException;
import com.incture.lch.exception.SetCarrierDetailsException;
import com.incture.lch.repository.PremiumFreightOrdersRepository;
import com.incture.lch.util.GetReferenceData;
import com.incture.lch.util.ServiceUtil;

@Repository
public class PremiumFreightOrdersRepositoryImpl implements PremiumFreightOrdersRepository {

	@Autowired
	// @Qualifier("sessionDb")
	private SessionFactory sessionFactory;

	@Autowired
	GetReferenceData getReferenceData;

	@Autowired
	private CarrierDetailsDao carrierDetailsDao;

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
	@SuppressWarnings("unchecked")
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
					// createdBy = aorders.getPlannerEmail();
					PremiumFreightChargeDetails premiumFreightChargeDetails = new PremiumFreightChargeDetails();
					// Updating Status in Master Table
					aorders.setStatus("Pending with Carrier Admin");
					Criteria criteria_role = session.createCriteria(LchRole.class);
					StringBuilder pendingWith = new StringBuilder();
					List<LchRole> role = new ArrayList<LchRole>();

					// Since the order is send to the Carrier Admin , It is
					// pending at id referring to Carrier Admin
					criteria_role.add(Restrictions.eq("role", "LCH_Carrier_Admin"));

					role = criteria_role.list();

					// Creating the Comma Seperated list of all the Carrier
					// Admin
					for (LchRole l : role) {
						pendingWith.append(l.getUserId());
						pendingWith.append(",");
					}
					// Updating in Master Table
					aorders.setPendingWith(pendingWith.substring(0, pendingWith.length() - 1));

					session.saveOrUpdate(aorders);
					// Converting AdhocOrders to Premium Format DTO
					premiumdto = exportPremiumFreightOrders(aorders);
					// Updating the Premium status
					premiumdto.setStatus("Pending with Carrier Admin");
					// adding in premium List
					premiumFreightOrderDtos.add(premiumdto);
					Criteria criteria = session.createCriteria(PremiumFreightChargeDetails.class);
					// Getting the charge details from the same id
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
			OrderIdMapping orderIdMapping = new OrderIdMapping();

			String requestId = getReferenceData
					.getNextSeqNumberRequestId(getReferenceData.executePremiumRequestId("REQ"), 4, sessionFactory);
			System.out.println("Request Id: " + requestId);

			StringBuilder orderids = new StringBuilder();
			for (String adString : adhocOrdersList) {
				orderids.append(adString);
				orderids.append(",");
			}
			System.out.println("OrderIds: " + orderids + "   " + orderids.substring(0, orderids.length() - 1));
			orderIdMapping.setRequestId(requestId);
			orderIdMapping.setOrderIds(orderids.substring(0, orderids.length() - 1));

			orderIdMapping.setCreatedDate(java.time.LocalDate.now());

			session.saveOrUpdate(orderIdMapping);

			// Create a Premium Workflow DTO and populate it here , by calling
			// another export kind of function
			// Trigger the Premium Workflow DTO
			// Triggering is done in the Workflow Trigger Services

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
				c2.add(Restrictions.eq("adhocOrderId", adhocOrderId));

				List<AdhocOrders> adhocOrders = new ArrayList<AdhocOrders>();
				List<PremiumFreightChargeDetails> premiumChargeDetails = new ArrayList<PremiumFreightChargeDetails>();

				adhocOrders = c1.list();
				premiumChargeDetails = c2.list();

				System.out.println(premiumChargeDetails.size());

				for (AdhocOrders a : adhocOrders) {

					System.out.println(a.getFwoNum());
					System.out.println(a.getStatus());

					a.setStatus("Pending With Approver");
					session.saveOrUpdate(a);
					session.saveOrUpdate(a);
					// updating in AdhocTable
					System.out.println(a.getStatus());
					System.out.println("Updating in Adhoc Table ");

				}

				for (PremiumFreightChargeDetails pdetail : premiumChargeDetails) {
					System.out.println(pdetail.getStatus());

					System.out.println(pdetail.getorderId());
					pdetail.setStatus("Pending with Approver");
					session.saveOrUpdate(pdetail);
					session.saveOrUpdate(pdetail);
					System.out.println(pdetail.getStatus());
					System.out.println("Updating in Charge Table");

				}

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

	public String updateTableDetails(PremiumWorkflowApprovalTaskDto premiumWorkflowDto) {
		// from the premium workflow dto fetch role

		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		String current_role = premiumWorkflowDto.getRole();

		AdhocOrders aorder = new AdhocOrders();
		List<AdhocOrders> aorders = new ArrayList<AdhocOrders>();
		PremiumFreightChargeDetails pdetail = new PremiumFreightChargeDetails();
		List<PremiumFreightChargeDetails> pdetails = new ArrayList<PremiumFreightChargeDetails>();

		String orderId = premiumWorkflowDto.getAdhocOrderId();

		Criteria criteria_adhoc = session.createCriteria(AdhocOrders.class);
		criteria_adhoc.add(Restrictions.eq("fwoNum", orderId));
		aorders = criteria_adhoc.list();

		Criteria criteria_premium = session.createCriteria(PremiumFreightChargeDetails.class);
		criteria_premium.add(Restrictions.eq("orderId", orderId));
		pdetails = criteria_premium.list();

		StringBuilder userIdList = new StringBuilder();
		List<LchRole> roles = new ArrayList<LchRole>();

		Criteria criteria_role = session.createCriteria(LchRole.class);
		criteria_role.add(Restrictions.eq("role", current_role));
		roles = criteria_role.list();

		for (LchRole l : roles) {
			userIdList.append(l.getUserId());
			userIdList.append(",");
		}

		String userIds = userIdList.substring(0, userIdList.length() - 1);

		if (current_role.equals("LCH_Planner")) {
			if (premiumWorkflowDto.getCharge() == 0) {
				for (AdhocOrders a : aorders) {
					a.setStatus("Pending at Carrier Admin");
					a.setPendingWith(userIds);
					session.saveOrUpdate(a);
				}
				for (PremiumFreightChargeDetails p : pdetails) {
					p.setStatus("Pending at Carrier Admin");
					// p.setCharge(premiumWorkflowDto.getCharge());
					session.saveOrUpdate(p);

				}
			} else {
				for (AdhocOrders a : aorders) {
					a.setStatus("Pending at Manager");
					a.setPendingWith(userIds);
					session.saveOrUpdate(a);
				}
				for (PremiumFreightChargeDetails p : pdetails) {
					p.setStatus("Pending at Manager");
					session.saveOrUpdate(p);

				}
			}
		}

		if (current_role.equals("LCH_Carrier_Admin")) {
			for (AdhocOrders a : aorders) {
				a.setStatus("Pending at Planner");
				a.setPendingWith(userIds);
				session.saveOrUpdate(a);
			}
			for (PremiumFreightChargeDetails p : pdetails) {
				p.setStatus("Pending at Planner");
				p.setCharge(premiumWorkflowDto.getCharge());
				session.saveOrUpdate(p);

			}

		}

		if (current_role.equals("LCH_Manager")) {
			for (AdhocOrders a : aorders) {
				a.setStatus("Pending at Accountant");
				a.setPendingWith(userIds);
				session.saveOrUpdate(a);
			}
			for (PremiumFreightChargeDetails p : pdetails) {
				p.setStatus("Pending at Accountant");
				session.saveOrUpdate(p);

			}

		}

		if (current_role.equals("LCH_Accountant")) {
			for (AdhocOrders a : aorders) {
				a.setStatus("Completed");
				a.setPendingWith(userIds);
				session.saveOrUpdate(a);
			}
			for (PremiumFreightChargeDetails p : pdetails) {
				p.setStatus("Completed");
				session.saveOrUpdate(p);

			}

		}

		return premiumWorkflowDto.getAdhocOrderId();
	}
}
