package com.incture.lch.repository.implementation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.incture.lch.dto.ResponseDto;
import com.incture.lch.dto.YardManagementDto;
import com.incture.lch.dto.YardManagementFilterDto;
import com.incture.lch.dto.YardManagementHistoryDto;
import com.incture.lch.entity.CarrierDetails;
import com.incture.lch.entity.YardManagementHistory;
import com.incture.lch.repository.YardManagementHistoryRepository;
import com.incture.lch.util.ServiceUtil;

@Repository
public class YardManagementHistoryRepositoryImpl implements YardManagementHistoryRepository {

	@Autowired
	//@Qualifier("sessiondb")
	private SessionFactory sessionFactory;

	private static final Logger LOGGER = LoggerFactory.getLogger(YardManagementHistoryRepositoryImpl.class);

	public YardManagementHistory importYardManagementHistory(YardManagementHistoryDto yardDto) {
		YardManagementHistory yard = new YardManagementHistory();
		yard.setArrival(yardDto.getArrival());
		yard.setBol(yardDto.getBol());
		yard.setCarrier(yardDto.getCarrier());
		yard.setCarrierDesc(yardDto.getCarrierDesc());
		yard.setCarrierName(yardDto.getCarrierName());
		yard.setCommodities(yardDto.getCommodities());
		yard.setCreatedBy(yardDto.getCreatedBy());
		if (yardDto.getCreatedDate() != null && !(yardDto.getCreatedDate().equals(""))) {
			yard.setCreatedDate(ServiceUtil.convertStringToDate(yardDto.getCreatedDate()));
		}
		yard.setFreightOrderNo(yardDto.getFreightOrderNo());
		yard.setHistoryId(yardDto.getHistoryId());
		yard.setLineSeq(yardDto.getLineSeq());
		yard.setLocation(yardDto.getLocation());
		yard.setPriority(yardDto.getPriority());
		yard.setStatus(yardDto.getStatus());
		yard.setSupplier(yardDto.getSupplier());
		yard.setTrailer(yardDto.getTrailer());

		yard.setComments(yardDto.getComments());
		yard.setDestDesc(yardDto.getDestDesc());
		yard.setDestId(yardDto.getDestId());
		yard.setHandlingUnit(yardDto.getHandlingUnit());
		yard.setPendingWith(yardDto.getPendingWith());
		yard.setPickNo(yardDto.getPickNo());
		yard.setPlannedShipDate(yardDto.getPlannedShipDate());
		yard.setProNo(yardDto.getProNo());
		yard.setQty(yardDto.getQty());
		yard.setReferenceNo(yardDto.getReferenceNo());
		yard.setSupplierAddress(yardDto.getSupplierAddress());
		yard.setUpdatedBy(yardDto.getUpdatedBy());
		if (yardDto.getUpdatedDate() != null && !(yardDto.getUpdatedDate().equals(""))) {
			yard.setUpdatedDate(ServiceUtil.convertStringToDate(yardDto.getUpdatedDate()));
		}
		yard.setWeight(yardDto.getWeight());
		yard.setYardId(yardDto.getYardId());
		yard.setYardLocation(yardDto.getYardLocation());
		yard.setSealNo(yardDto.getSealNo());
		yard.setLicencePlateNo(yardDto.getLicencePlateNo());
		yard.setRole(yardDto.getRole());
		if (yardDto.getIsPpKit() != null && !(yardDto.getIsPpKit().equals(""))) {
			yard.setPpKit(ServiceUtil.convertBooleanToString(yardDto.getIsPpKit()));
		}
		yard.setSecurityComments(yardDto.getSecurityComments());
		yard.setSecurityLocation(yardDto.getSecurityLocation());
		yard.setFuCount(yardDto.getFuCount());
		yard.setAdhocType(yardDto.getAdhocType());
		return yard;
	}

	public YardManagementHistoryDto exportYardManagementHistory(YardManagementHistory yard) {
		YardManagementHistoryDto yardDto = new YardManagementHistoryDto();
		yardDto.setArrival(yard.getArrival());
		yardDto.setBol(yard.getBol());
		yardDto.setCarrier(yard.getCarrier());
		yardDto.setCarrierDesc(yard.getCarrierDesc());
		yardDto.setCarrierName(yard.getCarrierName());
		yardDto.setCommodities(yard.getCommodities());
		yardDto.setCreatedBy(yard.getCreatedBy());
		if (yard.getCreatedDate() != null && !(yard.getCreatedDate().equals(""))) {
			yardDto.setCreatedDate(ServiceUtil.convertDateToString(yard.getCreatedDate()));
		}
		yardDto.setFreightOrderNo(yard.getFreightOrderNo());
		yardDto.setHistoryId(yard.getHistoryId());
		yardDto.setLineSeq(yard.getLineSeq());
		yardDto.setLocation(yard.getLocation());
		yardDto.setPriority(yard.getPriority());
		yardDto.setStatus(yard.getStatus());
		yardDto.setSupplier(yard.getSupplier());
		yardDto.setTrailer(yard.getTrailer());

		yardDto.setComments(yard.getComments());
		yardDto.setDestDesc(yard.getDestDesc());
		yardDto.setDestId(yard.getDestId());
		yardDto.setHandlingUnit(yard.getHandlingUnit());
		yardDto.setPendingWith(yard.getPendingWith());
		yardDto.setPickNo(yard.getPickNo());
		yardDto.setPlannedShipDate(yard.getPlannedShipDate());
		yardDto.setProNo(yard.getProNo());
		yardDto.setQty(yard.getQty());
		yardDto.setReferenceNo(yard.getReferenceNo());
		yardDto.setSupplierAddress(yard.getSupplierAddress());
		yardDto.setUpdatedBy(yard.getUpdatedBy());
		if (yard.getUpdatedDate() != null && !(yard.getUpdatedDate().equals(""))) {
			yardDto.setUpdatedDate(ServiceUtil.convertDateToString(yard.getUpdatedDate()));
		}
		yardDto.setWeight(yard.getWeight());
		yardDto.setYardId(yard.getYardId());
		yardDto.setYardLocation(yard.getYardLocation());
		yardDto.setSealNo(yard.getSealNo());
		yardDto.setLicencePlateNo(yard.getLicencePlateNo());
		yardDto.setRole(yard.getRole());
		if (yard.getPpKit() != null && !(yard.getPpKit().equals(""))) {
			yardDto.setIsPpKit(ServiceUtil.convertStringToBoolean(yard.getPpKit()));
		}
		yardDto.setSecurityComments(yard.getSecurityComments());
		yardDto.setSecurityLocation(yard.getSecurityLocation());
		yardDto.setFuCount(yard.getFuCount());
		yardDto.setAdhocType(yard.getAdhocType());
		return yardDto;
	}

	public YardManagementHistoryDto exportYardManagementHistoryFromYard(YardManagementDto yard) {
		YardManagementHistoryDto yardDto = new YardManagementHistoryDto();
		yardDto.setArrival(yard.getArrival());
		yardDto.setBol(yard.getBol());
		yardDto.setCarrier(yard.getCarrier());
		yardDto.setCarrierDesc(yard.getCarrierDesc());
		yardDto.setCarrierName(yard.getCarrierName());
		yardDto.setCommodities(yard.getCommodities());
		yardDto.setCreatedBy(yard.getCreatedBy());
		yardDto.setCreatedDate(yard.getCreatedDate());
		yardDto.setFreightOrderNo(yard.getFreightOrderNo());
		yardDto.setHistoryId(null);
		yardDto.setLineSeq(yard.getLineSeq());
		yardDto.setLocation(yard.getLocation());
		yardDto.setPriority(yard.getPriority());
		yardDto.setStatus(yard.getStatus());
		yardDto.setSupplier(yard.getSupplier());
		yardDto.setTrailer(yard.getTrailer());

		yardDto.setComments(yard.getComments());
		yardDto.setDestDesc(yard.getDestDesc());
		yardDto.setDestId(yard.getDestId());
		yardDto.setHandlingUnit(yard.getHandlingUnit());
		yardDto.setPendingWith(yard.getPendingWith());
		yardDto.setPickNo(yard.getPickNo());
		yardDto.setPlannedShipDate(yard.getPlannedShipDate());
		yardDto.setProNo(yard.getProNo());
		yardDto.setQty(yard.getQty());
		yardDto.setReferenceNo(yard.getReferenceNo());
		yardDto.setSupplierAddress(yard.getSupplierAddress());
		yardDto.setUpdatedBy(yard.getUpdatedBy());
		yardDto.setUpdatedDate(yard.getUpdatedDate());
		yardDto.setWeight(yard.getWeight());
		yardDto.setYardId(yard.getYardId());
		yardDto.setYardLocation(yard.getYardLocation());
		yardDto.setSealNo(yard.getSealNo());
		yardDto.setLicencePlateNo(yard.getLicencePlateNo());
		yardDto.setRole(yard.getRole());
		yardDto.setAdhocType(yard.getAdhocType());
		return yardDto;
	}

	public ResponseDto addYardManagementHistory(YardManagementHistoryDto yardManagementHistoryDto) {
		ResponseDto responseDto = new ResponseDto();
		sessionFactory.getCurrentSession().saveOrUpdate(importYardManagementHistory(yardManagementHistoryDto));
		responseDto.setCode("00");
		responseDto.setMessage("SUCCESS");
		responseDto.setStatus("SUCCESS");
		return responseDto;
	}

	public ResponseDto addYardManagementHistoryFromYard(YardManagementDto dto) {
		ResponseDto responseDto = new ResponseDto();
		sessionFactory.getCurrentSession()
				.saveOrUpdate(importYardManagementHistory(exportYardManagementHistoryFromYard(dto)));
		responseDto.setCode("00");
		responseDto.setMessage("SUCCESS");
		responseDto.setStatus("SUCCESS");
		return responseDto;
	}

	@SuppressWarnings("unchecked")
	public List<YardManagementHistoryDto> getYardManagementHistory(YardManagementFilterDto yardManagementFilterDto) {
		List<YardManagementHistoryDto> yardManagementHistoryDtos = new ArrayList<>();
		List<YardManagementHistory> YardManagementsHistory = new ArrayList<>();
		StringBuilder queryString = new StringBuilder();
		LOGGER.error("filter: " + yardManagementFilterDto.toString());
		try {
			queryString.append("SELECT y FROM YardManagementHistory y WHERE 1 = 1");

			if (yardManagementFilterDto != null && yardManagementFilterDto.getPendingWith() != null) {

				if (yardManagementFilterDto.getPendingWith().size() > 0) {
					queryString.append(" AND");

					String pendingWith = "(";
					List<String> pendingUsers = new ArrayList<String>();
					pendingUsers = yardManagementFilterDto.getPendingWith();
					for (String pendingUser : pendingUsers) {
						pendingWith += "(y.pendingWith LIKE :pendingWith) OR";
					}

					pendingWith = pendingWith.substring(0, pendingWith.lastIndexOf("OR"));
					pendingWith += ")";

					queryString.append(pendingWith);
				}
			}
			if (yardManagementFilterDto.getCarrier() != null && !(yardManagementFilterDto.getCarrier().equals(""))) {
				queryString.append(" AND (y.carrier=:carrier OR y.carrierName=:carrier OR y.carrierDesc=:carrier)");

			}
			if (yardManagementFilterDto.getSupplier() != null && !(yardManagementFilterDto.getSupplier().equals(""))) {
				queryString.append(" AND (y.supplier=:supplier OR y.supplierAddress=:supplier)");
			}
			if (yardManagementFilterDto.getCreatedBy() != null
					&& !(yardManagementFilterDto.getCreatedBy().equals(""))) {
				queryString.append(" AND y.createdBy=:createdBy");
			}
			if (yardManagementFilterDto.getFreightOrderNo() != null
					&& !(yardManagementFilterDto.getFreightOrderNo().equals(""))) {
				queryString.append(" AND y.freightOrderNo=:freightOrderNo");
			}
			
			
			if (yardManagementFilterDto.getStatus() != null && (yardManagementFilterDto.getStatus().size()>0)) {
				queryString.append(" AND y.status in (:status)");
			}
			if (yardManagementFilterDto.getTrailer() != null && !(yardManagementFilterDto.getTrailer().equals(""))) {
				queryString.append(" AND y.trailer=:trailer");
			}

			if (yardManagementFilterDto.getYardId() != null && !(yardManagementFilterDto.getYardId().equals(""))) {
				queryString.append(" AND y.yardId=:yardId");
			}

			if (yardManagementFilterDto.getYardLocation() != null
					&& !(yardManagementFilterDto.getYardLocation().equals(""))) {
				queryString.append(" AND y.yardLocation=:yardLocation");
			}
			if ((yardManagementFilterDto.getFromDate() != null && !(yardManagementFilterDto.getFromDate().equals("")))
					&& (yardManagementFilterDto.getToDate() != null)
					&& !(yardManagementFilterDto.getToDate().equals(""))) {
				queryString.append(" AND y.updatedDate BETWEEN :fromDate AND :toDate");
			}

			//queryString.append(" ORDER BY y.plannedShipDate  DESC");
			Query query = sessionFactory.getCurrentSession().createQuery(queryString.toString());
			if (yardManagementFilterDto != null && yardManagementFilterDto.getPendingWith() != null) {
				if (yardManagementFilterDto.getPendingWith().size() > 0) {
					List<String> pendingUsers = new ArrayList<String>();
					pendingUsers = yardManagementFilterDto.getPendingWith();
					for (String pendingUser : pendingUsers) {
						query.setParameter("pendingWith", "%" + pendingUser + "%");
					}
				}
			}

			if (yardManagementFilterDto.getCarrier() != null && !(yardManagementFilterDto.getCarrier().equals(""))) {
				query.setParameter("carrier", yardManagementFilterDto.getCarrier());
			}
			if (yardManagementFilterDto.getSupplier() != null && !(yardManagementFilterDto.getSupplier().equals(""))) {
				query.setParameter("supplier", yardManagementFilterDto.getSupplier());
			}
			if (yardManagementFilterDto.getCreatedBy() != null
					&& !(yardManagementFilterDto.getCreatedBy().equals(""))) {
				query.setParameter("createdBy", yardManagementFilterDto.getCreatedBy());
			}
			if (yardManagementFilterDto.getFreightOrderNo() != null
					&& !(yardManagementFilterDto.getFreightOrderNo().equals(""))) {
				query.setParameter("freightOrderNo", yardManagementFilterDto.getFreightOrderNo());
			}

			if (yardManagementFilterDto.getTrailer() != null && !(yardManagementFilterDto.getTrailer().equals(""))) {
				query.setParameter("trailer", yardManagementFilterDto.getTrailer());
			}
			if (yardManagementFilterDto.getYardId() != null && !(yardManagementFilterDto.getYardId().equals(""))) {
				query.setParameter("yardId", yardManagementFilterDto.getYardId());
			}

			if (yardManagementFilterDto.getYardLocation() != null
					&& !(yardManagementFilterDto.getYardLocation().equals(""))) {
				query.setParameter("yardLocation", yardManagementFilterDto.getYardLocation());
			}
			if (yardManagementFilterDto.getStatus() != null && (yardManagementFilterDto.getStatus().size()>0)) {
			query.setParameterList("status", yardManagementFilterDto.getStatus());
			}
			
			

			if ((yardManagementFilterDto.getFromDate() != null && !(yardManagementFilterDto.getFromDate().equals("")))
					&& (yardManagementFilterDto.getToDate() != null)
					&& !(yardManagementFilterDto.getToDate().equals(""))) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				try {
					Date d1 = (Date) sdf.parse(yardManagementFilterDto.getFromDate());
					Date d2 = (Date) sdf.parse(yardManagementFilterDto.getToDate());
					query.setParameter("fromDate", d1);
					query.setParameter("toDate", d2);
				} catch (ParseException e) {
					LOGGER.error("Exception On Date format:" + e.getMessage());
				}

			}

			LOGGER.error("filter query : " + queryString.toString());

			LOGGER.error("query " + query.toString());

			YardManagementsHistory = query.list();
			for (YardManagementHistory ym : YardManagementsHistory) {
				yardManagementHistoryDtos.add(exportYardManagementHistory(ym));
			}
		} catch (Exception e) {
			LOGGER.error("Exception " + e.getMessage() + "stackTrace - " + e.getStackTrace());
		}
		return yardManagementHistoryDtos;
	}

	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> inYarddayscount(List<String> foNums) {
		List<Map<String, Object>> responseList = new ArrayList<>();
		/*
		 * List<String> agingKpiGroup = new ArrayList<>();
		 * agingKpiGroup.add("At Dock"); agingKpiGroup.add("In Yard");
		 */
		StringBuilder queryString = new StringBuilder("SELECT FREIGHT_ORDER AS FONUM, case when ");
		queryString.append(
				"(DAYS_BETWEEN(MIN(UPDATED_DATE),MAX(current_date)) > 0 ) then DAYS_BETWEEN(MIN(UPDATED_DATE),MAX(current_date))+1 ");
		queryString.append(
				"when (DAYS_BETWEEN(MIN(UPDATED_DATE),MAX(current_date)) > 0 ) then DAYS_BETWEEN(MIN(UPDATED_DATE),MAX(current_date))+1 else 0 end as INYARDDAYSCOUNT");
		queryString.append(" FROM \"PACCAR_DB\".\"T_YARD_MANAGEMENT_HISTORY\" WHERE FREIGHT_ORDER IN :FONUM ");
		queryString.append(
				"and status in (select status_id from \"PACCAR_DB\".\"T_YARD_STATUS\" where group_or_kpi in ('At Dock','In Yard')) GROUP BY FREIGHT_ORDER ;");
		Query query = sessionFactory.getCurrentSession().createSQLQuery(queryString.toString());
		query.setParameterList("FONUM", foNums);
		// query.setParameterList("group_or_kpi", agingKpiGroup);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		responseList = (List<Map<String, Object>>) query.list();
		return responseList;

	}

	public List<CarrierDetails> sendCarrierEmail(String bpNumber)
	{
		List<CarrierDetails> carrierDetails = new ArrayList<CarrierDetails>();
		StringBuilder queryString = new StringBuilder();

		queryString.append("SELECT c FROM CarrierDetails c WHERE c.bpNumber=:bpNum");
		Query query = sessionFactory.getCurrentSession().createQuery(queryString.toString());
        query.setParameter("bpNum", bpNumber);

        carrierDetails=query.list();
        return carrierDetails;
	}
	
	
}
