package com.incture.lch.repository.implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
import com.incture.lch.dto.YardManagementKpiDto;
import com.incture.lch.dto.YardStatusDto;
import com.incture.lch.entity.YardManagement;
import com.incture.lch.repository.YardManagementHistoryRepository;
import com.incture.lch.repository.YardManagementRepository;
import com.incture.lch.repository.YardStatusRepository;
import com.incture.lch.service.YardManagementHistoryService;
import com.incture.lch.util.ServiceUtil;

@Repository
public class YardManagementRepositoryImpl implements YardManagementRepository {

	@Autowired
	YardManagementHistoryRepository yardManagementHistoryRepository;

	@Autowired
	YardStatusRepository yardStatusRepository;

	
	@Autowired
	YardManagementHistoryService history;

	@Autowired
	//@Qualifier("sessiondb")
	private SessionFactory sessionFactory;

	private static final Logger LOGGER = LoggerFactory.getLogger(YardManagementRepositoryImpl.class);

	public YardManagement importYardManagement(YardManagementDto yardDto) {
		YardManagement yard = new YardManagement();
		if (yardDto.getId() != null) {
			yard.setId(yardDto.getId());
		}
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
		yard.setId(yardDto.getId());
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
		yard.setYardIn(yardDto.getYardIn());
		yard.setYardOut(yardDto.getYardOut());
		yard.setLatitude(yardDto.getLatitude());
		yard.setLongitude(yardDto.getLongitude());
		yard.setAdhocType(yardDto.getAdhocType());
		return yard;
	}

	public YardManagementDto exportYardManagement(YardManagement yard) {
		YardManagementDto yardDto = new YardManagementDto();
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
		yardDto.setId(yard.getId());
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
		yardDto.setYardId(yard.getYardId());
		yardDto.setYardLocation(yard.getYardLocation());
		yardDto.setYardIn(yard.getYardIn());
		yardDto.setYardOut(yard.getYardOut());
		yardDto.setLatitude(yard.getLatitude());
		yardDto.setLongitude(yard.getLongitude());
		yardDto.setAdhocType(yard.getAdhocType());
		return yardDto;
	}

	public YardManagement updateYardManagementDto(YardManagementDto yardDto, Long id) {
		YardManagement yard = (YardManagement) sessionFactory.getCurrentSession().get(YardManagement.class, id);
		LOGGER.error("get yard management " + yard);
		if (!ServiceUtil.isEmpty(yardDto.getId())) {
			yard.setId(yardDto.getId());
		}
		if (!ServiceUtil.isEmpty(yardDto.getArrival())) {
			yard.setAdhocType(yardDto.getArrival());
		}
		if (!ServiceUtil.isEmpty(yardDto.getBol())) {
			yard.setBol(yardDto.getBol());
		}
		if (!ServiceUtil.isEmpty(yardDto.getCarrier())) {
			yard.setCarrier(yardDto.getCarrier());
		}
		if (!ServiceUtil.isEmpty(yardDto.getCarrierDesc())) {
			yard.setCarrierDesc(yardDto.getCarrierDesc());
		}
		if (!ServiceUtil.isEmpty(yardDto.getCarrierName())) {
			yard.setCarrierName(yardDto.getCarrierName());
		}
		if (!ServiceUtil.isEmpty(yardDto.getCommodities())) {
			yard.setCommodities(yardDto.getCommodities());
		}
		if (!ServiceUtil.isEmpty(yardDto.getCreatedBy())) {
			yard.setCreatedBy(yardDto.getCreatedBy());
		}
		if (yardDto.getCreatedDate() != null && !(yardDto.getCreatedDate().equals(""))) {
			yard.setCreatedDate(ServiceUtil.convertStringToDate(yardDto.getCreatedDate()));
		}
		if (!ServiceUtil.isEmpty(yardDto.getFreightOrderNo())) {
			yard.setFreightOrderNo(yardDto.getFreightOrderNo());
		}
		if (!ServiceUtil.isEmpty(yardDto.getLineSeq())) {
			yard.setLineSeq(yardDto.getLineSeq());
		}
		if (!ServiceUtil.isEmpty(yardDto.getLocation())) {
			yard.setLocation(yardDto.getLocation());
		}
		if (!ServiceUtil.isEmpty(yardDto.getPriority())) {
			yard.setPriority(yardDto.getPriority());
		}
		if (!ServiceUtil.isEmpty(yardDto.getStatus())) {
			yard.setStatus(yardDto.getStatus());
		}
		if (!ServiceUtil.isEmpty(yardDto.getSupplier())) {
			yard.setSupplier(yardDto.getSupplier());
		}
		if (!ServiceUtil.isEmpty(yardDto.getTrailer())) {
			yard.setTrailer(yardDto.getTrailer());
		}
		if (!ServiceUtil.isEmpty(yardDto.getComments())) {
			yard.setComments(yardDto.getComments());
		}
		if (!ServiceUtil.isEmpty(yardDto.getDestDesc())) {
			yard.setDestDesc(yardDto.getDestDesc());
		}
		if (!ServiceUtil.isEmpty(yardDto.getDestDesc())) {
			yard.setDestId(yardDto.getDestId());
		}
		if (!ServiceUtil.isEmpty(yardDto.getHandlingUnit())) {
			yard.setHandlingUnit(yardDto.getHandlingUnit());
		}
		if (!ServiceUtil.isEmpty(yardDto.getPendingWith())) {
			yard.setPendingWith(yardDto.getPendingWith());
		}
		if (!ServiceUtil.isEmpty(yardDto.getPickNo())) {
			yard.setPickNo(yardDto.getPickNo());
		}
		if (!ServiceUtil.isEmpty(ServiceUtil.StringIntoDate(yardDto.getPlannedShipDate()))) {
			yard.setPlannedShipDate((yardDto.getPlannedShipDate()));
		}
		// yard.setPlannedShipDate(yardDto.getPlannedShipDate());
		if (!ServiceUtil.isEmpty(yardDto.getProNo())) {
			yard.setProNo(yardDto.getProNo());
		}
		if (!ServiceUtil.isEmpty(yardDto.getQty())) {
			yard.setQty(yardDto.getQty());
		}
		if (!ServiceUtil.isEmpty(yardDto.getReferenceNo())) {
			yard.setReferenceNo(yardDto.getReferenceNo());
		}
		if (!ServiceUtil.isEmpty(yardDto.getSupplierAddress())) {
			yard.setSupplierAddress(yardDto.getSupplierAddress());
		}
		if (!ServiceUtil.isEmpty(yardDto.getUpdatedBy())) {
			yard.setUpdatedBy(yardDto.getUpdatedBy());
		}
		if (yardDto.getUpdatedDate() != null && !(yardDto.getUpdatedDate().equals(""))) {
			yard.setUpdatedDate(ServiceUtil.convertStringToDate(yardDto.getUpdatedDate()));
		}
		if (!ServiceUtil.isEmpty(yardDto.getWeight())) {
			yard.setWeight(yardDto.getWeight());
		}
		if (!ServiceUtil.isEmpty(yardDto.getYardId())) {
			yard.setYardId(yardDto.getYardId());
		}
		if (!ServiceUtil.isEmpty(yardDto.getYardLocation())) {
			yard.setYardLocation(yardDto.getYardLocation());
		}
		if (!ServiceUtil.isEmpty(yardDto.getSealNo())) {
			yard.setSealNo(yardDto.getSealNo());
		}
		if (!ServiceUtil.isEmpty(yardDto.getLicencePlateNo())) {
			yard.setLicencePlateNo(yardDto.getLicencePlateNo());
		}
		if (!ServiceUtil.isEmpty(yardDto.getRole())) {
			yard.setRole(yardDto.getRole());
		}
		if (yardDto.getIsPpKit() != null && !(yardDto.getIsPpKit().equals(""))) {
			yard.setPpKit(ServiceUtil.convertBooleanToString(yardDto.getIsPpKit()));
		}
		if (!ServiceUtil.isEmpty(yardDto.getSecurityComments())) {
			yard.setSecurityComments(yardDto.getSecurityComments());
		}
		if (!ServiceUtil.isEmpty(yardDto.getSecurityLocation())) {
			yard.setSecurityLocation(yardDto.getSecurityLocation());
		}
		if (!ServiceUtil.isEmpty(yardDto.getFuCount())) {
			yard.setFuCount(yardDto.getFuCount());
		}
		if (!ServiceUtil.isEmpty(yardDto.getYardIn())) {
			yard.setYardIn(yardDto.getYardIn());
		}
		if (!ServiceUtil.isEmpty(yardDto.getYardOut())) {
			yard.setYardOut(yardDto.getYardOut());
		}
		if (!ServiceUtil.isEmpty(yardDto.getLatitude())) {
			yard.setLatitude(yardDto.getLatitude());
		}
		if (!ServiceUtil.isEmpty(yardDto.getLongitude())) {
			yard.setLongitude(yardDto.getLongitude());
		}
		if (!ServiceUtil.isEmpty(yardDto.getAdhocType())) {
			yard.setAdhocType(yardDto.getAdhocType());
		}
		return yard;
	}

	@SuppressWarnings("unused")
	public List<YardManagementDto> getYardManagementDetail(YardManagementFilterDto filterDto) {

		List<YardManagementDto> list = new ArrayList<>();
		List<String> listStatusId = new ArrayList<>();
		List<Map<String, Object>> responseList = null;
		StringBuilder queryString = new StringBuilder();
		Boolean flag = false;

		LOGGER.error("filter: " + filterDto.toString());
		try {
			queryString.append("SELECT y FROM YardManagement y WHERE 1 = 1");

			if (filterDto != null && filterDto.getPendingWith() != null) {

				if (filterDto.getPendingWith().size() > 0) {
					queryString.append(" AND");

					String pendingWith = "(";
					List<String> pendingUsers = new ArrayList<String>();
					pendingUsers = filterDto.getPendingWith();
					for (String pendingUser : pendingUsers) {
						pendingWith += "(y.pendingWith LIKE :pendingWith) OR";
					}

					pendingWith = pendingWith.substring(0, pendingWith.lastIndexOf("OR"));
					pendingWith += ")";

					queryString.append(pendingWith);
				}
			}
			if (filterDto != null && filterDto.getStatus() != null) {
				if (filterDto.getStatus().size() > 0) {
					queryString.append(" AND y.status IN (:status)");
				}
			}

			if (filterDto.getCarrier() != null && !(filterDto.getCarrier().equals(""))) {
				queryString.append(" AND (y.carrier=:carrier OR y.carrierName=:carrier OR y.carrierDesc=:carrier)");

			}
			if (filterDto.getSupplier() != null && !(filterDto.getSupplier().equals(""))) {
				queryString.append(" AND (y.supplier=:supplier OR y.supplierAddress=:supplier)");
			}
			if (filterDto.getCreatedBy() != null && !(filterDto.getCreatedBy().equals(""))) {
				queryString.append(" AND y.createdBy=:createdBy");
			}
			if (filterDto.getFreightOrderNo() != null && !(filterDto.getFreightOrderNo().equals(""))) {
				queryString.append(" AND y.freightOrderNo=:freightOrderNo");
			}

			if (filterDto.getTrailer() != null && !(filterDto.getTrailer().equals(""))) {
				queryString.append(" AND y.trailer=:trailer");
			}

			if (filterDto.getYardId() != null && !(filterDto.getYardId().equals(""))) {
				queryString.append(" AND y.yardId=:yardId");
			}

			if (filterDto.getYardLocation() != null && !(filterDto.getYardLocation().equals(""))) {
				queryString.append(" AND y.yardLocation=:yardLocation");
			}

			queryString.append(" ORDER BY y.plannedShipDate  DESC");
			Query query = sessionFactory.getCurrentSession().createQuery(queryString.toString());
			if (filterDto != null && filterDto.getPendingWith() != null) {
				if (filterDto.getPendingWith().size() > 0) {
					List<String> pendingUsers = new ArrayList<String>();
					pendingUsers = filterDto.getPendingWith();
					for (String pendingUser : pendingUsers) {
						query.setParameter("pendingWith", "%" + pendingUser + "%");
					}
				}
			}
			if (filterDto != null && filterDto.getStatus() != null) {
				if (filterDto.getStatus().size() > 0) {
					query.setParameterList("status", filterDto.getStatus());
				}

			}

			if (filterDto.getCarrier() != null && !(filterDto.getCarrier().equals(""))) {
				query.setParameter("carrier", filterDto.getCarrier());
			}
			if (filterDto.getSupplier() != null && !(filterDto.getSupplier().equals(""))) {
				query.setParameter("supplier", filterDto.getSupplier());
			}
			if (filterDto.getCreatedBy() != null && !(filterDto.getCreatedBy().equals(""))) {
				query.setParameter("createdBy", filterDto.getCreatedBy());
			}
			if (filterDto.getFreightOrderNo() != null && !(filterDto.getFreightOrderNo().equals(""))) {
				query.setParameter("freightOrderNo", filterDto.getFreightOrderNo());
			}

			if (filterDto.getTrailer() != null && !(filterDto.getTrailer().equals(""))) {
				query.setParameter("trailer", filterDto.getTrailer());
			}
			if (filterDto.getYardId() != null && !(filterDto.getYardId().equals(""))) {
				query.setParameter("yardId", filterDto.getYardId());
			}

			if (filterDto.getYardLocation() != null && !(filterDto.getYardLocation().equals(""))) {
				query.setParameter("yardLocation", filterDto.getYardLocation());
			}
			LOGGER.error("filter query : " + queryString.toString());

			LOGGER.error("query " + query.toString());
			@SuppressWarnings("unchecked")
			List<YardManagement> objectsList = (List<YardManagement>) query.list();
			LOGGER.error("total list size " + objectsList.size());
			for (YardManagement ym : objectsList) {
				if (!ServiceUtil.isEmpty(ym.getFreightOrderNo())) {
					listStatusId.add(ym.getFreightOrderNo());
				}

			}

			if (listStatusId.size() > 0) {
				responseList = yardManagementHistoryRepository.inYarddayscount(listStatusId);
			}

			for (YardManagement yard : objectsList) {
				YardManagementDto dto = new YardManagementDto();
				dto = exportYardManagement(yard);
				if (responseList != null) {
					if (responseList.size() > 0) {
						dto.setAgingCount(inYardDaysCount(responseList, yard));
					}
				}
				if (!ServiceUtil.isEmpty(filterDto.getAgingCount())) {

					if (filterDto.getAgingCount() <= dto.getAgingCount()) {
						list.add(dto);
					}
				} else {
					list.add(dto);
				}

			}
		} catch (Exception e) {
			LOGGER.error("Exception " + e.getMessage() + "stackTrace - " + e.getStackTrace());

		}

		return list;

	}

	public int inYardDaysCount(List<Map<String, Object>> res, YardManagement yard) {
		List<YardStatusDto> yardStatusdto = yardStatusRepository.getYardStatusBasedonFilter();
		List<String> listStatusId = yardStatusdto.stream().map(YardStatusDto::getStatusId).collect(Collectors.toList());
		for (Map<String, Object> map : res) {

			if (map.get("FONUM") != null && (map.get("FONUM").toString()).equalsIgnoreCase(yard.getFreightOrderNo())
					&& listStatusId.contains(yard.getStatus())) {
				if (!ServiceUtil.isEmpty(map.get("INYARDDAYSCOUNT"))) {
					return (int) map.get("INYARDDAYSCOUNT");
				}
			}
		}
		return 0;

	}

	public ResponseDto addYardManagement(YardManagementDto yardManagementDto) {
		ResponseDto responseDto = new ResponseDto();
		sessionFactory.getCurrentSession().saveOrUpdate(importYardManagement(yardManagementDto));
		yardManagementHistoryRepository.addYardManagementHistoryFromYard(yardManagementDto);
		responseDto.setCode("00");
		responseDto.setMessage("SUCCESS");
		responseDto.setStatus("SUCCESS");
		return responseDto;
	}

	@Override
	public ResponseDto updateYardManagement(YardManagementDto yardManagementDto) {
		ResponseDto responseDto = new ResponseDto();
		YardManagement yardManagement=updateYardManagementDto(yardManagementDto, yardManagementDto.getId());
		sessionFactory.getCurrentSession().update(yardManagement);
		yardManagementHistoryRepository.addYardManagementHistoryFromYard(exportYardManagement(yardManagement));
		responseDto.setCode("00");
		responseDto.setMessage("SUCCESS");
		responseDto.setStatus("SUCCESS");
		return responseDto;
	}

	@SuppressWarnings("unchecked")
	public List<YardManagementDto> getAllYardManagements() {
		List<YardManagementDto> yardManagementDtos = new ArrayList<>();
		List<YardManagement> YardManagements = new ArrayList<>();
		String querry = "select ym from YardManagement ym";
		Query query = sessionFactory.getCurrentSession().createQuery(querry);
		YardManagements = query.list();
		for (YardManagement ym : YardManagements) {
			yardManagementDtos.add(exportYardManagement(ym));
		}
		return yardManagementDtos;
	}

	@SuppressWarnings({ "unchecked" })
	public YardManagementKpiDto getYardManagementKpi(YardManagementFilterDto dto) {

		YardManagementKpiDto yardManagementKpiDto = new YardManagementKpiDto();
		List<YardManagementDto> yardManagementDtos = new ArrayList<>();
		List<String> listStatusId = new ArrayList<>();
		List<Map<String, Object>> responseList = null;

		StringBuilder queryString = new StringBuilder();
		queryString.append("SELECT ym FROM YardManagement ym where ym.status in (:status) and (");
		if (dto.getPendingWith().size() > 0) {
			List<String> pendingUsers = new ArrayList<String>();
			pendingUsers = dto.getPendingWith();
			queryString.append(" ym.pendingWith like :pendingWith");
			if (pendingUsers.size() > 1) {
				for (int i = 1; i <= pendingUsers.size() - 1; i++) {
					queryString.append(" or ym.pendingWith like :pendingWith");
				}
				queryString.append(" or ym.pendingWith like :pendingWith");
			}
			queryString.append(")");

			// for(String pendingUser : pendingUsers)
			// {
			// queryString.append(" or ym.pendingWith like :pendingWith");
			// }
		}
		try {
			Query query = sessionFactory.getCurrentSession().createQuery(queryString.toString());
			query.setParameterList("status", dto.getStatusList());
			if (dto.getPendingWith().size() > 0) {
				List<String> pendingUsers = new ArrayList<String>();
				pendingUsers = dto.getPendingWith();
				for (String pendingUser : pendingUsers) {
					query.setParameter("pendingWith", "%" + pendingUser + "%");
				}
			}
			LOGGER.error("get yard management kpi : " + queryString.toString());
			LOGGER.error("query " + query.toString());
			List<YardManagement> yardManagements = (List<YardManagement>) query.list();
			for (YardManagement ym : yardManagements) {
				LOGGER.error("line num 384 : yardmanagement " + ym);
				if (!ServiceUtil.isEmpty(ym.getFreightOrderNo())) {
					listStatusId.add(ym.getFreightOrderNo());
				}
				// if(!ServiceUtil.isEmpty(ym.getStatus()))
				// {
				// listStatusId.add(ym.getStatus());
				// }
			}
			if (listStatusId.size() > 0) {
				responseList = yardManagementHistoryRepository.inYarddayscount(listStatusId);
				LOGGER.error("392 response list " + responseList);
			}
			for (YardManagement yard : yardManagements) {
				YardManagementDto yardManagementDto = new YardManagementDto();
				yardManagementDto = exportYardManagement(yard);
				if (responseList != null) {
					if (responseList.size() > 0) {
						yardManagementDto.setAgingCount(inYardDaysCount(responseList, yard));
					}
				}
				yardManagementDtos.add(yardManagementDto);
			}

			yardManagementKpiDto.setYmRecords(yardManagementDtos);
			yardManagementKpiDto.setCount(yardManagements.size());
		} catch (Exception e) {
			LOGGER.error("get yard management kpi exception : " + e.getMessage());

		}

		return yardManagementKpiDto;

		// Query query = sessionFactory.getCurrentSession().createQuery(
		// "SELECT ym FROM YardManagement ym where ym.pendingWith like
		// :pendingWith and ym.status in :status");
		//
		// query.setParameter("pendingWith", "%" + dto.getPendingWith() + "%");

	}

	@SuppressWarnings("unchecked")
	@Override
	public int getSecurityGaurdKpi(String loggedInUserId, List<String> status) {
		StringBuilder str = new StringBuilder(
				"SELECT ym FROM YardManagement ym where ym.pendingWith like :pendingWith");
		if (status.size() > 0 && status != null) {
			str.append(" and ym.status in (:status)");
		}
		Query query = sessionFactory.getCurrentSession().createQuery(str.toString());
		query.setParameter("pendingWith", "%" + loggedInUserId + "%");
		if (status.size() > 0 && status != null) {
			query.setParameterList("status", status);
		}
		List<YardManagement> yardManagements = query.list();
		return yardManagements.size();
	}

	@SuppressWarnings("unchecked")
	public List<YardManagementDto> getYardByStatus(String status) {
		List<YardManagementDto> dtoList = new ArrayList<>();
		List<YardManagement> list = new ArrayList<>();
		String queryString = "select ym from YardManagement ym where ym.status = :status ";
		Query query = sessionFactory.getCurrentSession().createQuery(queryString);
		query.setParameter("status", status);
		list = query.list();
		for (YardManagement yard : list) {
			dtoList.add(exportYardManagement(yard));
		}

		return dtoList;
	}
}
