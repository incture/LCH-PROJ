package com.incture.lch.repository.implementation;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import com.incture.lch.adhoc.custom.dto.AdhocWorkflowCustomDto;
import com.incture.lch.adhoc.custom.dto.WorkflowCustomDto;
import com.incture.lch.adhoc.workflow.constant.WorkflowConstants;
import com.incture.lch.adhoc.workflow.dto.WorkflowApprovalTaskDto;
import com.incture.lch.adhoc.workflow.service.WorkFlowServiceLocal;
import com.incture.lch.adhoc.workflow.service.WorkflowInvokerLocal;
import com.incture.lch.dao.AdhocApprovalRuleDao;
import com.incture.lch.dao.AdhocOrderWorkflowDao;
import com.incture.lch.dao.LkCountriesDao;
import com.incture.lch.dao.LkDivisionDao;
import com.incture.lch.dao.LkShipperDetailsDao;
import com.incture.lch.dto.AdhocApprovalRuleDto;
import com.incture.lch.dto.AdhocOrderDto;
import com.incture.lch.dto.AdhocOrderWorkflowDto;
import com.incture.lch.dto.AdhocRequestDto;
import com.incture.lch.dto.LkCountriesDto;
import com.incture.lch.dto.LkDivisionsDto;
import com.incture.lch.dto.LkShipperDetailsDto;
import com.incture.lch.dto.PartNumberDescDto;
import com.incture.lch.dto.ReasonCodeDto;
import com.incture.lch.dto.ResponseDto;
import com.incture.lch.entity.AdhocOrders;
import com.incture.lch.entity.LchRole;
import com.incture.lch.entity.LkCountries;
import com.incture.lch.entity.LkDivisions;
import com.incture.lch.entity.LkShipperDetails;
import com.incture.lch.entity.ReasonCode;
import com.incture.lch.helper.AdhocOrderWorkflowHelper;
import com.incture.lch.repository.AdhocOrdersRepository;
import com.incture.lch.util.GetReferenceData;
import com.incture.lch.util.ServiceUtil;

@Repository
public class AdhocOrdersRepositoryImpl implements AdhocOrdersRepository {

	@Autowired

	// @Qualifier("sessionDb")
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	@Autowired
	GetReferenceData getReferenceData;

	@Autowired
	private LkShipperDetailsDao lkShipperDetailsDao;

	@Autowired
	private AdhocOrderWorkflowDao adhocOrderWorkflowDao;

	@Autowired
	private LkDivisionDao lkDivDao;

	@Autowired
	private LkCountriesDao lkCouDao;

	@Autowired
	private LkShipperDetailsDao lkShipperDao;

	@Autowired
	private WorkFlowServiceLocal wfService;

	@Autowired
	private WorkflowInvokerLocal wfInvokerLocal;

	@Autowired
	private AdhocApprovalRuleDao adhocApprovalRuleDao;

	@Autowired
	private AdhocOrderWorkflowHelper adhocOrderWorkflowHelper;

	private static final Logger LOGGER = LoggerFactory.getLogger(AdhocOrdersRepositoryImpl.class);

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

	public AdhocOrders importAdhocOrdersDto(AdhocOrderDto AdhocOrderDto) {
		AdhocOrders adhocOrders = new AdhocOrders();

		adhocOrders.setFwoNum(AdhocOrderDto.getOrderId());
		adhocOrders.setPartNum(AdhocOrderDto.getPartNum());
		adhocOrders.setUserId(AdhocOrderDto.getUserId());
		adhocOrders.setCreatedBy(AdhocOrderDto.getCreatedBy());
		adhocOrders.setUpdatedBy(AdhocOrderDto.getUpdatedBy());
		if (AdhocOrderDto.getCreatedDate() != null && !(AdhocOrderDto.getCreatedDate().equals(""))) {
			adhocOrders.setCreatedDate(ServiceUtil.convertStringToDate(AdhocOrderDto.getCreatedDate()));
		}
		if (AdhocOrderDto.getUpdatedDate() != null && !(AdhocOrderDto.getUpdatedDate().equals(""))) {
			adhocOrders.setUpdatedDate(ServiceUtil.convertStringToDate(AdhocOrderDto.getUpdatedDate()));
		}
		adhocOrders.setOriginAddress(AdhocOrderDto.getOriginAddress());
		adhocOrders.setDestinationAddress(AdhocOrderDto.getDestinationAddress());
		if (AdhocOrderDto.getShipDate() != null && !(AdhocOrderDto.getShipDate().equals(""))) {
			adhocOrders.setShipDate(ServiceUtil.convertStringToDate(AdhocOrderDto.getShipDate()));
		}
		if (AdhocOrderDto.getExpectedDeliveryDate() != null && !(AdhocOrderDto.getExpectedDeliveryDate().equals(""))) {
			adhocOrders
					.setExpectedDeliveryDate(ServiceUtil.convertStringToDate(AdhocOrderDto.getExpectedDeliveryDate()));
		}
		adhocOrders.setPartDescription(AdhocOrderDto.getPartDescription());
		adhocOrders.setQuantity(AdhocOrderDto.getQuantity());
		// adhocOrders.setQuantity(ServiceUtil.convertStringToInteger(AdhocOrderDto.getQuantity()));

		adhocOrders.setUom(AdhocOrderDto.getUom());
		if (AdhocOrderDto.getIsInternational() != null) {
			// adhocOrders.setIsInternational(ServiceUtil.convertBooleanToString(AdhocOrderDto.getIsInternational()));
			adhocOrders.setIsInternational(AdhocOrderDto.getIsInternational());

		}

		if (AdhocOrderDto.getIsTruck() != null) {
			// adhocOrders.setIsTruck(ServiceUtil.convertBooleanToString(AdhocOrderDto.getIsTruck()));
			adhocOrders.setIsTruck(AdhocOrderDto.getIsTruck());

		}
		adhocOrders.setCountryOrigin(AdhocOrderDto.getCountryOrigin());
		adhocOrders.setValue(AdhocOrderDto.getValue());
		adhocOrders.setCurrency(AdhocOrderDto.getCurrency());
		// adhocOrders.setWeight(ServiceUtil.convertStringToBigDecimal(AdhocOrderDto.getWeight()));
		adhocOrders.setWeight(AdhocOrderDto.getWeight());

		if (AdhocOrderDto.getDimensionB() != null && !(AdhocOrderDto.getDimensionB().equals(""))) {
			// adhocOrders.setDimensionB(ServiceUtil.convertStringToBigDecimal(AdhocOrderDto.getDimensionB()));
			adhocOrders.setDimensionB(AdhocOrderDto.getDimensionB());

		}

		if (AdhocOrderDto.getDimensionH() != null && !(AdhocOrderDto.getDimensionH().equals(""))) {
			// adhocOrders.setDimensionH(ServiceUtil.convertStringToBigDecimal(AdhocOrderDto.getDimensionH()));
			adhocOrders.setDimensionH(AdhocOrderDto.getDimensionH());

		}

		if (AdhocOrderDto.getDimensionL() != null && !(AdhocOrderDto.getDimensionL().equals(""))) {
			// adhocOrders.setDimensionL(ServiceUtil.convertStringToBigDecimal(AdhocOrderDto.getDimensionL()));
			adhocOrders.setDimensionL(AdhocOrderDto.getDimensionL());

		}
		adhocOrders.setHazmatNumber(AdhocOrderDto.getHazmatNumber());
		adhocOrders.setProjectNumber(AdhocOrderDto.getProjectNumber());
		adhocOrders.setReferenceNumber(AdhocOrderDto.getReferenceNumber());
		adhocOrders.setBusinessDivision(AdhocOrderDto.getBusinessDivision());
		if (AdhocOrderDto.getCharge() != null) {
			adhocOrders.setCharge(ServiceUtil.convertBooleanToString(AdhocOrderDto.getCharge()));
		}
		adhocOrders.setVinNumber(AdhocOrderDto.getVinNumber());
		adhocOrders.setShippingInstruction(AdhocOrderDto.getShippingInstruction());
		adhocOrders.setShippingContact(AdhocOrderDto.getShippingContact());
		adhocOrders.setReceivingContact(AdhocOrderDto.getReceivingContact());
		adhocOrders.setPODataNumber(AdhocOrderDto.getPODataNumber());
		adhocOrders.setCustomerOrderNo(AdhocOrderDto.getCustomerOrderNo());
		adhocOrders.setTerms(AdhocOrderDto.getTerms());
		adhocOrders.setPackageType(AdhocOrderDto.getPackageType());
		if (AdhocOrderDto.getCharge() != null) {
			adhocOrders.setPremiumFreight(ServiceUtil.convertBooleanToString(AdhocOrderDto.getPremiumFreight()));
			// adhocOrders.setPremiumFreight(AdhocOrderDto.getPremiumFreight());
		}
		adhocOrders.setReasonCode(AdhocOrderDto.getReasonCode());
		adhocOrders.setGlCode(AdhocOrderDto.getGlcode());
		adhocOrders.setDestinationCity(AdhocOrderDto.getDestinationCity());
		adhocOrders.setDestinationName(AdhocOrderDto.getDestinationName());
		adhocOrders.setDestinationState(AdhocOrderDto.getDestinationState());
		adhocOrders.setDestinationZip(AdhocOrderDto.getDestinationZip());

		adhocOrders.setShipperName(AdhocOrderDto.getShipperName());
		adhocOrders.setOriginCity(AdhocOrderDto.getOriginCity());
		adhocOrders.setOriginState(AdhocOrderDto.getOriginState());
		adhocOrders.setOriginZip(AdhocOrderDto.getOriginZip());

		if (AdhocOrderDto.getIsHazmat() != null) {
			// adhocOrders.setIsHazmat(ServiceUtil.convertBooleanToString(AdhocOrderDto.getIsHazmat()));

			adhocOrders.setIsHazmat(AdhocOrderDto.getIsHazmat());

		}
		adhocOrders.setShipperNameFreeText(AdhocOrderDto.getShipperNameFreeText());
		adhocOrders.setOriginCountry(AdhocOrderDto.getOriginCountry());
		adhocOrders.setDestinationNameFreeText(AdhocOrderDto.getDestinationNameFreeText());
		adhocOrders.setDestinationCountry(AdhocOrderDto.getDestinationCountry());
		adhocOrders.setHazmatUn(AdhocOrderDto.getHazmatUn());
		adhocOrders.setWeightUom(AdhocOrderDto.getWeightUom());
		adhocOrders.setDimensionsUom(AdhocOrderDto.getDimensionsUom());

		adhocOrders.setShipperNameDesc(AdhocOrderDto.getShipperNameDesc());
		adhocOrders.setDestinationNameDesc(AdhocOrderDto.getDestinationNameDesc());
		adhocOrders.setUserName(AdhocOrderDto.getUserName());
		adhocOrders.setUserEmail(AdhocOrderDto.getUserEmail());
		adhocOrders.setPremiumReasonCode(AdhocOrderDto.getPremiumReasonCode());
		adhocOrders.setPlannerEmail(AdhocOrderDto.getPlannerEmail());
		adhocOrders.setAdhocType(AdhocOrderDto.getAdhocType());
		// adhocOrders.setStatus(AdhocOrderDto.getStatus());
		adhocOrders.setStatus("Pending At Planner");
		adhocOrders.setPendingWith(AdhocOrderDto.getPendingWith());
		adhocOrders.setManagerEmail(AdhocOrderDto.getManagerEmail());
		adhocOrders.setIsSaved(AdhocOrderDto.getIsSaved());
		return adhocOrders;
	}

	public ReasonCodeDto exportReasonCode(ReasonCode reasonCode) {
		ReasonCodeDto reasonCodeDto = new ReasonCodeDto();
		reasonCodeDto.setReasonCodeKey(reasonCode.getReasonCodeKey());
		reasonCodeDto.setReasonCodeValue(reasonCode.getReasonCodeValue());
		return reasonCodeDto;
	}

	public ReasonCode importReasonCode(ReasonCodeDto reasonCodeDto) {
		ReasonCode reasonCode = new ReasonCode();
		reasonCode.setReasonCodeKey(reasonCodeDto.getReasonCodeKey());
		reasonCode.setReasonCodeValue(reasonCodeDto.getReasonCodeValue());
		return reasonCode;
	}

	public WorkflowApprovalTaskDto exportAdhocWorkflowDto(AdhocOrders adhocOrders) {
		WorkflowApprovalTaskDto workflowDto = new WorkflowApprovalTaskDto();
		workflowDto.setAdhocOrderId(adhocOrders.getFwoNum());
		workflowDto.setAdhocType(adhocOrders.getAdhocType());
		workflowDto.setAdhocOrderInfo(exportAdhocOrdersDto(adhocOrders));

		// workflowDto.setManager(manager);

		workflowDto.setBusinessDivision(adhocOrders.getBusinessDivision());
		workflowDto.setCharge(ServiceUtil.convertStringToBoolean(adhocOrders.getCharge()));
		workflowDto.setCountryOrigin(adhocOrders.getCountryOrigin());
		workflowDto.setCreatedDate(ServiceUtil.convertDateToString(adhocOrders.getCreatedDate()));
		workflowDto.setCreatedBy(adhocOrders.getCreatedBy());
		workflowDto.setCurrency(adhocOrders.getCurrency());
		workflowDto.setCustomerOrderNo(adhocOrders.getCustomerOrderNo());
		workflowDto.setDestinationAddress(adhocOrders.getDestinationAddress());
		workflowDto.setDestinationCity(adhocOrders.getDestinationCity());
		workflowDto.setDestinationName(adhocOrders.getDestinationName());
		workflowDto.setDestinationNameDesc(adhocOrders.getDestinationNameDesc());
		workflowDto.setDestinationNameFreeText(adhocOrders.getDestinationNameFreeText());
		workflowDto.setDestinationState(adhocOrders.getDestinationState());
		workflowDto.setDestinationZip(adhocOrders.getDestinationZip());
		workflowDto.setDimensionB(adhocOrders.getDimensionB() != null ? adhocOrders.getDimensionB().toString() : null);
		workflowDto.setDimensionH(adhocOrders.getDimensionH() != null ? adhocOrders.getDimensionH().toString() : null);
		workflowDto.setDimensionL(adhocOrders.getDimensionL() != null ? adhocOrders.getDimensionL().toString() : null);
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
		workflowDto.setPremiumFreight(ServiceUtil.convertStringToBoolean(adhocOrders.getPremiumFreight()));
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
		return workflowDto;

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<AdhocOrderDto> getAllAdhocOrders() {
		List<AdhocOrderDto> AdhocOrderDtos = new ArrayList<>();
		List<AdhocOrders> adhocOrders = new ArrayList<>();
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		String queryStr = "select ad from AdhocOrders ad  Order By fwoNum desc";
		Query query = session.createQuery(queryStr);
		// query.setParameter("isOrderSubmitted", true);
		adhocOrders = query.list();
		for (AdhocOrders adOrders : adhocOrders) {
			AdhocOrderDtos.add(exportAdhocOrdersDto(adOrders));
		}
		session.flush();
		session.clear();
		tx.commit();
		session.close();
		return AdhocOrderDtos;
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public List<AdhocOrderDto> getDrafts(AdhocRequestDto adhocRequestDto) {
		List<AdhocOrders> adhocOrders = new ArrayList<>();
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		// StringBuilder queryStr = new StringBuilder();
		// queryStr.append("select ao from AdhocOrders ao where ao.isSaved =
		// true ");

		Criteria crit = session.createCriteria(AdhocOrders.class);
		crit.add(Restrictions.eq("isSaved", true));
		crit.add(Restrictions.like("fwoNum", "TEM%", MatchMode.ANYWHERE));
		// queryStr.append("");

		if (adhocRequestDto.getorderId() != null && !(adhocRequestDto.getorderId().equals(""))) {
			// queryStr.append(" AND ao.fwoNum=:fwoNum");
			crit.add(Restrictions.eq("adhocOrderId", adhocRequestDto.getorderId()));

		}
		if ((adhocRequestDto.getFromDate() != null && !(adhocRequestDto.getFromDate().equals("")))
				&& (adhocRequestDto.getToDate() != null) && !(adhocRequestDto.getToDate().equals(""))) {
			// queryStr.append(" AND ao.createdDate BETWEEN :fromDate AND
			// :toDate");
			crit.add(Restrictions.between("createdDate", adhocRequestDto.getFromDate(), adhocRequestDto.getToDate()));
		}
		if (adhocRequestDto.getCreatedBy() != null && !(adhocRequestDto.getCreatedBy().equals(""))) {
			// queryStr.append(" AND ao.userId=:userId");
			crit.add(Restrictions.eq("userId", adhocRequestDto.getCreatedBy()));
		}
		if (adhocRequestDto.getPartNo() != null && !(adhocRequestDto.getPartNo().equals(""))) {
			// queryStr.append(" AND ao.partNum=:partNum");
			crit.add(Restrictions.eq("partNo", adhocRequestDto.getPartNo()));
		}

		adhocOrders = crit.list();
		// Query query = session.createQuery(queryStr.toString());
		/*
		 * if (adhocRequestDto.getAdhocOrderId() != null &&
		 * !(adhocRequestDto.getAdhocOrderId().equals(""))) {
		 * query.setParameter("fwoNum", adhocRequestDto.getAdhocOrderId()); } if
		 * ((adhocRequestDto.getFromDate() != null &&
		 * !(adhocRequestDto.getFromDate().equals(""))) &&
		 * (adhocRequestDto.getToDate() != null) &&
		 * !(adhocRequestDto.getToDate().equals(""))) { SimpleDateFormat sdf =
		 * new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); try { Date d1 = (Date)
		 * sdf.parse(adhocRequestDto.getFromDate()); Date d2 = (Date)
		 * sdf.parse(adhocRequestDto.getToDate());
		 * query.setParameter("fromDate", d1); query.setParameter("toDate", d2);
		 * } catch (ParseException e) { LOGGER.error("Exception On Date format:"
		 * + e.getMessage()); }
		 * 
		 * } if (adhocRequestDto.getCreatedBy() != null &&
		 * !(adhocRequestDto.getCreatedBy().equals(""))) {
		 * query.setParameter("userId", adhocRequestDto.getCreatedBy()); } if
		 * (adhocRequestDto.getPartNo() != null &&
		 * !(adhocRequestDto.getPartNo().equals(""))) {
		 * query.setParameter("partNum", adhocRequestDto.getPartNo()); }
		 */
		// System.out.println(adhocRequestDto.getCreatedBy());

		List<AdhocOrderDto> list = new ArrayList<>();
		// @SuppressWarnings("unchecked")
		// List<AdhocOrders> objectsList = (List<AdhocOrders>) query.list();
		// System.out.println(objectsList.size());

		for (AdhocOrders ao : adhocOrders) {
			list.add(exportAdhocOrdersDto(ao));
		}

		session.flush();
		session.clear();
		tx.commit();
		session.close();
		return list;
	}

	//////////////////////////////////////////////////////////////////
	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<AdhocOrderDto> getKpi(int days, AdhocRequestDto adhocRequestDto) {
		List<AdhocOrderDto> AdhocOrderDtos = new ArrayList<>();
		List<AdhocOrders> adhocOrders = new ArrayList<>();

		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();

		Timestamp t = new Timestamp(System.currentTimeMillis());
		Timestamp t3;
		Calendar cal = Calendar.getInstance();
		cal.setTime(t);
		cal.add(Calendar.DATE, days);

		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(t);
		cal2.add(Calendar.DATE, 1);// The day after the current Day
		t3 = new Timestamp(cal2.getTime().getTime());

		Timestamp t2 = new Timestamp(cal.getTime().getTime());

		Criteria crit = session.createCriteria(AdhocOrders.class);
		if (days == 1)
			crit.add(Restrictions.between("shipDate", t, t2));
		else
			crit.add(Restrictions.between("shipDate", t3, t2));

		if (adhocRequestDto.getorderId() != null && !(adhocRequestDto.getorderId().equals(""))) {
			crit.add(Restrictions.eq("adhocOrderId", adhocRequestDto.getorderId()));

		}
		if ((adhocRequestDto.getFromDate() != null && !(adhocRequestDto.getFromDate().equals("")))
				&& (adhocRequestDto.getToDate() != null) && !(adhocRequestDto.getToDate().equals(""))) {
			crit.add(Restrictions.between("createdDate", adhocRequestDto.getFromDate(), adhocRequestDto.getToDate()));
		}
		if (adhocRequestDto.getCreatedBy() != null && !(adhocRequestDto.getCreatedBy().equals(""))) {
			crit.add(Restrictions.eq("userId", adhocRequestDto.getCreatedBy()));
		}
		if (adhocRequestDto.getPartNo() != null && !(adhocRequestDto.getPartNo().equals(""))) {
			crit.add(Restrictions.eq("partNo", adhocRequestDto.getPartNo()));
		}
		crit.addOrder(Order.asc("shipDate"));

		adhocOrders = crit.list();

		for (AdhocOrders a : adhocOrders) {
			AdhocOrderDtos.add(exportAdhocOrdersDto(a));
		}

		session.flush();
		session.clear();
		tx.commit();
		session.close();
		return AdhocOrderDtos;

	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	public ResponseDto addAdhocOrders(AdhocOrderDto AdhocOrderDto) {
		ResponseDto responseDto = new ResponseDto();
		AdhocOrders adhocOrders = new AdhocOrders();
		adhocOrders = importAdhocOrdersDto(AdhocOrderDto);
		adhocOrders.setIsSaved(false);
		LkShipperDetailsDto shipDetDto = new LkShipperDetailsDto();
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		Criteria crit = session.createCriteria(LkShipperDetails.class);
		crit.add(Restrictions.eq("shipperName", AdhocOrderDto.getShipperName()));

		Criteria crit2 = session.createCriteria(LkShipperDetails.class);
		crit2.add(Restrictions.eq("shipperName", AdhocOrderDto.getDestinationName()));

		List<LkShipperDetailsDto> listOfShipper = crit.list();
		List<LkShipperDetailsDto> listOfDestinations = crit2.list();
		if (ServiceUtil.isEmpty(listOfShipper)) {
			shipDetDto.setOnetimeLoc(true);
			shipDetDto.setShipperCity(AdhocOrderDto.getOriginCity());
			shipDetDto.setShipperCountry(AdhocOrderDto.getOriginCountry());
			shipDetDto.setShipperState(AdhocOrderDto.getOriginState());
			shipDetDto.setShipperName(AdhocOrderDto.getShipperName());
			shipDetDto.setShipperZip(AdhocOrderDto.getOriginZip());
			shipDetDto.setShipperContact(AdhocOrderDto.getShippingContact());
			shipDetDto.setBpNumber(ServiceUtil.generateRandomDigits(16));
			shipDetDto.setOnetimeLocId(ServiceUtil.getAlphaNumericString(16));
			session.save(lkShipperDetailsDao.importShipperDetails(shipDetDto));
		}
		if (ServiceUtil.isEmpty(listOfDestinations)) {
			LkShipperDetailsDto shipDetDto2 = new LkShipperDetailsDto();
			shipDetDto2.setOnetimeLoc(true);
			shipDetDto2.setShipperCity(AdhocOrderDto.getDestinationCity());
			shipDetDto2.setShipperCountry(AdhocOrderDto.getDestinationCountry());
			shipDetDto2.setShipperState(AdhocOrderDto.getDestinationState());
			shipDetDto2.setShipperName(AdhocOrderDto.getDestinationName());
			shipDetDto2.setShipperZip(AdhocOrderDto.getDestinationZip());
			shipDetDto2.setShipperContact(AdhocOrderDto.getDestinationAddress());
			shipDetDto2.setBpNumber(ServiceUtil.generateRandomDigits(16));
			shipDetDto2.setOnetimeLocId(ServiceUtil.getAlphaNumericString(16));
			session.save(lkShipperDetailsDao.importShipperDetails(shipDetDto2));
		}

		String adhocOrderId = getReferenceData.getNextSeqNumberAdhoc(
				getReferenceData.executeAdhoc("ADH" + AdhocOrderDto.getShipperName().substring(0, 2)), 5,
				sessionFactory);

		if (adhocOrders.getFwoNum() == null || adhocOrders.getFwoNum().equals("")
				|| adhocOrders.getFwoNum().substring(0, 3) == "TEM"
				|| adhocOrders.getFwoNum().substring(0, 3).equals("TEM")) {
			adhocOrders.setFwoNum(adhocOrderId);

		}
		List<AdhocApprovalRuleDto> ruleDtoList  = new ArrayList<AdhocApprovalRuleDto>();

		if(!adhocOrders.getAdhocType().equals("AS IS"))
		{
			ruleDtoList = adhocApprovalRuleDao.getAdhocApprovalsByAdhocTypeAndApprovalType(adhocOrders.getAdhocType());
			adhocOrders.setPendingWith(adhocOrderWorkflowHelper.getManagerDetails(ruleDtoList));
			adhocOrders.setStatus("Pending At Manager");
			
		}
		
		session.saveOrUpdate(adhocOrders);
		responseDto.setMessage("Save success");
		responseDto.setStatus("SUCCESS");
		responseDto.setCode("00");

		LOGGER.info("Starting Workflow");
		WorkflowApprovalTaskDto workflowDto = new WorkflowApprovalTaskDto();
		workflowDto = exportAdhocWorkflowDto(adhocOrders);
		if(!adhocOrders.getAdhocType().equals("AS IS"))
		{
		workflowDto.setManager(adhocOrderWorkflowHelper.getManagerDetails(ruleDtoList));
		if(!adhocOrders.getAdhocType().equals("Inventory"))
		{
		workflowDto.setPlanner(adhocOrderWorkflowHelper.getPlannerDetails(ruleDtoList));
		}
		}
		LOGGER.info("Workflow inputs........" + workflowDto.toString());
		if (!AdhocOrderDto.getPremiumFreight().equals(Boolean.TRUE))
		{
			wfService.triggerWorkflow(workflowDto);
			LOGGER.info("Workflow Started........");
		}
		else
		{
			List<LchRole> roles = new ArrayList<LchRole>();
			Criteria criteria = session.createCriteria(LchRole.class);
			criteria.add(Restrictions.eq("role", "LCH_Planner"));
			roles = criteria.list();
			System.out.println("Size" + roles.size());
			StringBuilder pendingWith = new StringBuilder();
			if (!ServiceUtil.isEmpty(roles)) 
			{
				for (LchRole l : roles) 
				{
					System.out.println(l.getUserId());
					pendingWith.append(l.getUserId());
					pendingWith.append(",");

				}
			}
			
			adhocOrders.setPendingWith(pendingWith.substring(0, pendingWith.length() - 1));
			adhocOrders.setStatus("Pending At Planner");
			session.saveOrUpdate(adhocOrders);
			/*			
			PremiumWorkflowApprovalTaskDto premiumWorkflowDto = new PremiumWorkflowApprovalTaskDto();
			premiumWorkflowDto=exportToPremiumWorkflowDto(workflowDto);
			wfService.triggerPremiumWorkflow(premiumWorkflowDto);
			LOGGER.info("Premium Workflow Started");*/
			

		}
		session.flush();
		session.clear();

		tx.commit();
		session.close();
		return responseDto;
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	public AdhocOrderDto saveAdhocOrders(AdhocOrderDto AdhocOrderDto) {
		// ResponseDto responseDto = new ResponseDto();
		AdhocOrders adhocOrders = new AdhocOrders();
		adhocOrders = importAdhocOrdersDto(AdhocOrderDto);
		adhocOrders.setIsSaved(true);
		/// adhocOrders.setCreatedBy("LCH");
		LkShipperDetailsDto shipDetDto = new LkShipperDetailsDto();
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		Criteria crit = session.createCriteria(LkShipperDetails.class);
		crit.add(Restrictions.eq("shipperName", AdhocOrderDto.getShipperName()));

		Criteria crit2 = session.createCriteria(LkShipperDetails.class);
		crit2.add(Restrictions.eq("shipperName", AdhocOrderDto.getDestinationName()));

		// crit.add(Restrictions.like("shipperName", "'" +
		// AdhocOrderDto.getShipperName() + "'", MatchMode.EXACT));
		List<LkShipperDetailsDto> listOfShipper = crit.list();
		List<LkShipperDetailsDto> listOfDestinations = crit2.list();
		if (ServiceUtil.isEmpty(listOfShipper)) {
			shipDetDto.setOnetimeLoc(true);
			shipDetDto.setShipperCity(AdhocOrderDto.getOriginCity());
			shipDetDto.setShipperCountry(AdhocOrderDto.getOriginCountry());
			shipDetDto.setShipperState(AdhocOrderDto.getOriginState());
			shipDetDto.setShipperName(AdhocOrderDto.getShipperName());
			shipDetDto.setShipperZip(AdhocOrderDto.getOriginZip());
			shipDetDto.setShipperContact(AdhocOrderDto.getShippingContact());
			shipDetDto.setBpNumber(ServiceUtil.generateRandomDigits(16));
			shipDetDto.setOnetimeLocId(ServiceUtil.getAlphaNumericString(16));
			session.save(lkShipperDetailsDao.importShipperDetails(shipDetDto));
		}
		if (ServiceUtil.isEmpty(listOfDestinations)) {
			LkShipperDetailsDto shipDetDto2 = new LkShipperDetailsDto();
			shipDetDto2.setOnetimeLoc(true);
			shipDetDto2.setShipperCity(AdhocOrderDto.getDestinationCity());
			shipDetDto2.setShipperCountry(AdhocOrderDto.getDestinationCountry());
			shipDetDto2.setShipperState(AdhocOrderDto.getDestinationState());
			shipDetDto2.setShipperName(AdhocOrderDto.getDestinationName());
			shipDetDto2.setShipperZip(AdhocOrderDto.getDestinationZip());
			shipDetDto2.setShipperContact(AdhocOrderDto.getDestinationAddress());
			shipDetDto2.setBpNumber(ServiceUtil.generateRandomDigits(16));
			shipDetDto2.setOnetimeLocId(ServiceUtil.getAlphaNumericString(16));
			session.save(lkShipperDetailsDao.importShipperDetails(shipDetDto2));
		}

		if (!ServiceUtil.isEmpty(AdhocOrderDto.getOrderId())) {
			if (AdhocOrderDto.getOrderId().startsWith("TEM")) {
				// adhocOrders.setFwoNum(AdhocOrderDto.getAdhocOrderId().replace("TEM",
				// "ADH"));
				// NO ACTION NEEDED
			}
		} else {

			/*
			 * String adhocOrderId = getReferenceData.getNextSeqNumberAdhoc(
			 * getReferenceData.executeAdhoc("TEM" +
			 * AdhocOrderDto.getShipperName().substring(0, 2)), 5,
			 * sessionFactory);
			 */
			String adhocOrderId = getReferenceData.getNextSeqNumberAdhoc(getReferenceData.executeAdhoc("TEM"), 5,
					sessionFactory);

			if (adhocOrders.getFwoNum() == null || adhocOrders.getFwoNum().equals("")) {
				adhocOrders.setFwoNum(adhocOrderId);
			}
		}

		session.saveOrUpdate(adhocOrders);
		/*
		 * responseDto.setMessage("Save success");
		 * responseDto.setStatus("SUCCESS"); responseDto.setCode("00");
		 */
		session.flush();
		session.clear();
		tx.commit();
		session.close();
		return exportAdhocOrdersDto(adhocOrders);
		// return null;
	}

	@SuppressWarnings("rawtypes")
	public int deleteAdhocOrders(String adhocOrderId, String userId, String partNum) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		String queryStr = "DELETE FROM AdhocOrders ad WHERE ad.fwoNum=:fwoNum and ad.userId=:userId and ad.partNum=:partNum";
		Query query = session.createQuery(queryStr);
		query.setParameter("fwoNum", adhocOrderId);
		query.setParameter("userId", userId);
		query.setParameter("partNum", partNum);

		int result = query.executeUpdate();
		session.flush();
		session.clear();
		tx.commit();
		session.close();
		return result;

	}

	@SuppressWarnings("rawtypes")
	public List<AdhocOrderDto> getAdhocOrders(AdhocRequestDto adhocRequestDto) {
		StringBuilder queryString = new StringBuilder();
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		queryString.append("SELECT ao FROM AdhocOrders ao WHERE ao.fwoNum = ao.fwoNum AND ao.fwoNum like 'ADH%'");

		if (adhocRequestDto.getorderId() != null && !(adhocRequestDto.getorderId().equals(""))) {
			queryString.append(" AND ao.fwoNum=:fwoNum");
		}
		if ((adhocRequestDto.getFromDate() != null && !(adhocRequestDto.getFromDate().equals("")))
				&& (adhocRequestDto.getToDate() != null) && !(adhocRequestDto.getToDate().equals(""))) {
			queryString.append(" AND ao.createdDate BETWEEN :fromDate AND :toDate");
		}
		if (adhocRequestDto.getCreatedBy() != null && !(adhocRequestDto.getCreatedBy().equals(""))) {
			queryString.append(" AND ao.userId=:userId");
		}
		if (adhocRequestDto.getPartNo() != null && !(adhocRequestDto.getPartNo().equals(""))) {
			queryString.append(" AND ao.partNum=:partNum");
		}
		/*
		 * if (adhocRequestDto.getCreatedBy()!= null &&
		 * !(adhocRequestDto.getCreatedBy().equals(""))) {
		 * queryString.append(" AND ao.createdBy=:createdBy"); }
		 */

		queryString.append(" ORDER BY ao.createdDate DESC");
		Query query = session.createQuery(queryString.toString());

		if (adhocRequestDto.getorderId() != null && !(adhocRequestDto.getorderId().equals(""))) {
			query.setParameter("fwoNum", adhocRequestDto.getorderId());
		}
		if ((adhocRequestDto.getFromDate() != null && !(adhocRequestDto.getFromDate().equals("")))
				&& (adhocRequestDto.getToDate() != null) && !(adhocRequestDto.getToDate().equals(""))) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				Date d1 = (Date) sdf.parse(adhocRequestDto.getFromDate());
				Date d2 = (Date) sdf.parse(adhocRequestDto.getToDate());
				query.setParameter("fromDate", d1);
				query.setParameter("toDate", d2);
			} catch (ParseException e) {
				LOGGER.error("Exception On Date format:" + e.getMessage());
			}

		}
		if (adhocRequestDto.getCreatedBy() != null && !(adhocRequestDto.getCreatedBy().equals(""))) {
			query.setParameter("userId", adhocRequestDto.getCreatedBy());
		}
		if (adhocRequestDto.getPartNo() != null && !(adhocRequestDto.getPartNo().equals(""))) {
			query.setParameter("partNum", adhocRequestDto.getPartNo());
		}

		List<AdhocOrderDto> list = new ArrayList<>();
		@SuppressWarnings("unchecked")
		List<AdhocOrders> objectsList = (List<AdhocOrders>) query.list();
		for (AdhocOrders ao : objectsList) {
			list.add(exportAdhocOrdersDto(ao));
		}
		session.flush();
		session.clear();
		tx.commit();
		session.close();
		return list;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Map<String, List<ReasonCodeDto>> getReasonCode() {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		Map<String, List<ReasonCodeDto>> result = new HashMap<>();
		List<ReasonCodeDto> reasonCodesList = new ArrayList<>();
		List<ReasonCode> reasonCodes = new ArrayList<>();

		try {
			String queryStr = "select rc from ReasonCode rc";
			Query query = session.createQuery(queryStr);
			reasonCodes = query.list();
			for (ReasonCode reasonCode : reasonCodes) {
				reasonCodesList.add(exportReasonCode(reasonCode));
			}
			result.put("aReasonCodes", reasonCodesList);
		} catch (Exception e) {
			LOGGER.error("Exception in getReasonCode api" + e);
		} finally {
			session.flush();
			session.clear();
			tx.commit();
			session.close();
		}
		return result;

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<LkDivisionsDto> getAllDivisions() {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		List<LkDivisionsDto> divDtoList = new ArrayList<>();
		List<LkDivisions> divList = new ArrayList<>();

		try {
			String queryStr = "select div from LkDivisions div";
			Query query = session.createQuery(queryStr);
			divList = query.list();
			for (LkDivisions lkDiv : divList) {
				divDtoList.add(lkDivDao.exportDivisions(lkDiv));
			}
		} catch (Exception e) {
			LOGGER.error("Exception in getReasonCode api" + e);
		} finally {
			session.flush();
			session.clear();
			tx.commit();
			session.close();
		}
		return divDtoList;

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<LkCountriesDto> getAllCountries() {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		List<LkCountriesDto> couDtoList = new ArrayList<>();
		List<LkCountries> couList = new ArrayList<>();

		try {
			String queryStr = "select cou from LkCountries cou";
			Query query = session.createQuery(queryStr);
			couList = query.list();
			for (LkCountries lkcou : couList) {
				couDtoList.add(lkCouDao.exportCountries(lkcou));
			}
		} catch (Exception e) {
			LOGGER.error("Exception in getReasonCode api" + e);
		} finally {
			session.flush();
			session.clear();
			tx.commit();
			session.close();
		}
		return couDtoList;

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<LkShipperDetailsDto> getAllShipperDetails() {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		List<LkShipperDetailsDto> shipDtoList = new ArrayList<>();
		List<LkShipperDetails> shipList = new ArrayList<>();

		try {
			String queryStr = "select ship from LkShipperDetails ship";
			Query query = session.createQuery(queryStr);
			shipList = query.list();
			for (LkShipperDetails lkShipper : shipList) {
				shipDtoList.add(lkShipperDao.exportShipperDetails(lkShipper));
			}
		} catch (Exception e) {
			LOGGER.error("Exception in getReasonCode api" + e);
		} finally {
			session.flush();
			session.clear();
			tx.commit();
			session.close();
		}
		return shipDtoList;

	}

	public PartNumberDescDto getByPartNumber(PartNumberDescDto partNumber) {
		RestTemplate callRestApi = new RestTemplate();
		try {
			PartNumberDescDto obj = callRestApi.postForObject(
					"https://jco-lch.cfapps.eu10.hana.ondemand.com/rest/jco/getTableDataByRFCTest", partNumber,
					PartNumberDescDto.class);
			return obj;
		} catch (Exception e) {
			partNumber.setMessage(e.toString());
			return partNumber;
		}

	}

	@Override
	public ResponseDto addReasonCode(ReasonCodeDto reasonCodeDto) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		ResponseDto responseDto = new ResponseDto();
		try {
			session.saveOrUpdate(importReasonCode(reasonCodeDto));
			responseDto.setCode("00");
			responseDto.setMessage("Save Success");
			responseDto.setStatus("SUCCESS");
			return responseDto;

		} catch (Exception e) {
			LOGGER.error("Exception in addReasonCode api" + e);
		} finally {
			session.flush();
			session.clear();
			tx.commit();
			session.close();
		}
		return responseDto;

	}

	@SuppressWarnings("rawtypes")
	public String getReasonCodeDescById(String id) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		String desc = "";
		String queryStr = "select rc from ReasonCode rc where rc.reasonCodeKey=:reasonCodeKey";
		Query query = session.createQuery(queryStr);
		query.setParameter("reasonCodeKey", id);
		@SuppressWarnings("unchecked")
		List<ReasonCode> reasonCodes = query.list();
		for (ReasonCode reasonCode : reasonCodes) {
			desc = reasonCode.getReasonCodeValue();
		}
		session.flush();
		session.clear();
		tx.commit();
		session.close();
		return desc;
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<LkShipperDetailsDto> getShipperDetails(String shipperName) {
		// String queryStr = "select sh from LkShipperDetails sh where
		// sh.shipperName=:shipperName";
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		Criteria crit = session.createCriteria(LkShipperDetails.class);
		crit.add(Restrictions.like("shipperName", "'" + shipperName + "'%", MatchMode.ANYWHERE));
		List<LkShipperDetailsDto> listOfShipper = crit.list();
		session.flush();
		session.clear();
		tx.commit();
		session.close();
		return listOfShipper;

	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	public String updateWorflowDetails(AdhocOrderWorkflowDto workflowDto) {
		System.out.println("Yuhooo" + workflowDto.getOrderId());

		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		List<AdhocOrders> adhocOrder = new ArrayList<AdhocOrders>();
		Criteria criteria = session.createCriteria(AdhocOrders.class);
		criteria.add(Restrictions.eq("fwoNum", workflowDto.getOrderId()));
		adhocOrder = criteria.list();

		System.out.println(adhocOrder.size());
		for (AdhocOrders a : adhocOrder) {

			System.out.println(a.getFwoNum());
			a.setUpdatedBy(workflowDto.getUpdatedBy());
			a.setUpdatedDate(new Date());
			a.setStatus(workflowDto.getStatus());
			a.setPendingWith(workflowDto.getPendingWith());
			session.saveOrUpdate(a);
		}
		session.save(adhocOrderWorkflowDao.importAdhocWorkflow(workflowDto));

		session.flush();
		session.clear();
		tx.commit();
		session.close();

		System.out.println(workflowDto.getOrderId());
		return workflowDto.getOrderId();
	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	public String updateApprovalWorflowDetails(JSONObject obj) throws JSONException {
		AdhocOrderWorkflowDto workflowDto = new AdhocOrderWorkflowDto();
		workflowDto = prepareAdhocApprovalWorkflowDto(obj);
		System.out.println("Yuhooo" + workflowDto.getOrderId());

		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		List<AdhocOrders> adhocOrder = new ArrayList<AdhocOrders>();
		Criteria criteria = session.createCriteria(AdhocOrders.class);
		criteria.add(Restrictions.eq("fwoNum", workflowDto.getOrderId()));
		adhocOrder = criteria.list();

		System.out.println(adhocOrder.size());
		for (AdhocOrders a : adhocOrder) {

			System.out.println(a.getFwoNum());
			a.setUpdatedBy(workflowDto.getUpdatedBy());
			a.setUpdatedDate(new Date());
			a.setStatus(workflowDto.getStatus());
			a.setPendingWith(workflowDto.getPendingWith());
			session.saveOrUpdate(a);
		}
		session.save(adhocOrderWorkflowDao.importAdhocWorkflow(workflowDto));

		session.flush();
		session.clear();
		tx.commit();
		session.close();

		System.out.println(workflowDto.getOrderId());
		return workflowDto.getOrderId();
	}

	public AdhocOrderWorkflowDto prepareAdhocApprovalWorkflowDto(JSONObject data) throws JSONException {
		AdhocOrderWorkflowDto workflowDto = new AdhocOrderWorkflowDto();
		JSONObject obj = data.getJSONObject("workflowInfo");
		workflowDto.setOrderId(data.getString("adhocOrderId"));
		workflowDto.setDescription(obj.getString("description"));
		workflowDto.setInstanceId(obj.getString("id"));
		workflowDto.setPendingWith(null);
		workflowDto.setRequestedBy(obj.getString("createdBy"));
		workflowDto.setRequestedDate(ServiceUtil.convertStringToDate(obj.getString("createdAt")));
		workflowDto.setUpdatedDate(ServiceUtil.convertStringToDate(obj.getString("completedAt")));
		workflowDto.setSubject(obj.getString("subject"));
		workflowDto.setUpdatedBy(obj.getString("processor"));
		workflowDto.setStatus(obj.getString("status"));
		return workflowDto;

	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	public String updateApprovalWorflowDetails(WorkflowCustomDto obj)
			throws JSONException, ClientProtocolException, IOException {
		AdhocOrderWorkflowDto workflowDto = new AdhocOrderWorkflowDto();
		workflowDto = prepareAdhocApprovalWorkflowDto(obj);
		System.out.println("Yuhooo" + workflowDto.getOrderId());

		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		List<AdhocOrders> adhocOrder = new ArrayList<AdhocOrders>();
		Criteria criteria = session.createCriteria(AdhocOrders.class);
		criteria.add(Restrictions.eq("fwoNum", workflowDto.getOrderId()));
		adhocOrder = criteria.list();

		System.out.println(adhocOrder.size());
		for (AdhocOrders a : adhocOrder) {

			System.out.println(a.getFwoNum());
			a.setUpdatedBy(workflowDto.getUpdatedBy());
			a.setUpdatedDate(new Date());
			a.setStatus(workflowDto.getStatus());
			a.setPendingWith(obj.getPlannerDetails());
			session.saveOrUpdate(a);
		}

		session.save(adhocOrderWorkflowDao.importAdhocWorkflow(workflowDto));

		session.flush();
		session.clear();
		tx.commit();
		session.close();

		System.out.println(workflowDto.getOrderId());
		return workflowDto.getOrderId();
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public String updateApprovalWorflowDetails1(WorkflowCustomDto obj)
			throws JSONException, ClientProtocolException, IOException {
		AdhocOrderWorkflowDto workflowDto = new AdhocOrderWorkflowDto();
		workflowDto = prepareAdhocApprovalWorkflowDto(obj);
		System.out.println("Yuhooo" + workflowDto.getOrderId());

		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		List<AdhocOrders> adhocOrder = new ArrayList<AdhocOrders>();
		Criteria criteria = session.createCriteria(AdhocOrders.class);
		criteria.add(Restrictions.eq("fwoNum", workflowDto.getOrderId()));
		adhocOrder = criteria.list();

		System.out.println(adhocOrder.size());
		for (AdhocOrders a : adhocOrder) {

			System.out.println(a.getFwoNum());
			a.setUpdatedBy(workflowDto.getUpdatedBy());
			a.setUpdatedDate(new Date());
			a.setStatus("Pending At Planner");
			a.setPendingWith(obj.getPlannerDetails());
			session.saveOrUpdate(a);
		}

		session.save(adhocOrderWorkflowDao.importAdhocWorkflow(workflowDto));

		session.flush();
		session.clear();
		tx.commit();
		session.close();

		System.out.println(workflowDto.getOrderId());
		return workflowDto.getOrderId();
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	public String updateApprovalWorflowDetailsForType4(AdhocWorkflowCustomDto dto)
			throws JSONException, ClientProtocolException, IOException {
		AdhocOrderWorkflowDto workflowDto = new AdhocOrderWorkflowDto();
		workflowDto.setOrderId(dto.getAdhocOrderId());
		workflowDto.setBusinessKey(dto.getCreatedBy());
		workflowDto.setWorkflowName("Adhoc Workflow");
		workflowDto.setDescription("Adhoc Type IS AS");
		workflowDto.setBusinessKey("NA");
		workflowDto.setStatus(WorkflowConstants.COMPLETED);
		workflowDto.setUpdatedBy(dto.getCreatedBy());
		workflowDto.setSubject("NA");
		workflowDto.setUpdatedDate(new Date());
		workflowDto.setPendingWith(null);
		System.out.println("Yuhooo" + workflowDto.getOrderId());

		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		List<AdhocOrders> adhocOrder = new ArrayList<AdhocOrders>();
		Criteria criteria = session.createCriteria(AdhocOrders.class);
		criteria.add(Restrictions.eq("fwoNum", dto.getAdhocOrderId()));
		adhocOrder = criteria.list();

		System.out.println(adhocOrder.size());
		for (AdhocOrders a : adhocOrder) {

			System.out.println(a.getFwoNum());
			a.setUpdatedBy(dto.getCreatedBy());
			a.setUpdatedDate(new Date());
			a.setStatus(WorkflowConstants.COMPLETED);
			a.setPendingWith(null);
			session.saveOrUpdate(a);
		}

		session.save(adhocOrderWorkflowDao.importAdhocWorkflow(workflowDto));

		session.flush();
		session.clear();
		tx.commit();
		session.close();

		System.out.println(workflowDto.getOrderId());
		return workflowDto.getOrderId();
	}
	


	

	public AdhocOrderWorkflowDto prepareAdhocApprovalWorkflowDto(WorkflowCustomDto data)
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
		workflowDto.setUpdatedDate(new Date());
		workflowDto.setSubject(obj.getString("subject"));
		workflowDto.setUpdatedBy(obj.getString("processor"));
		workflowDto.setStatus(obj.getString("status"));
		return workflowDto;

	}
}
