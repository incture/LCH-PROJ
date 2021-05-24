package com.incture.lch.repository.implementation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.incture.lch.dto.ResponseDto;
import com.incture.lch.dto.YardAdminRulesDto;
import com.incture.lch.entity.YardAdminRules;
import com.incture.lch.repository.YardAdminRulesRepository;
import com.incture.lch.service.YardAdminRulesService;
import com.incture.lch.service.YardManagementService;
import com.incture.lch.util.ServiceUtil;

@Repository
public class YardAdminRulesRepositoryImpl implements YardAdminRulesRepository {

	private static final Logger LOGGER = LoggerFactory.getLogger(YardAdminRulesRepositoryImpl.class);

	@Autowired
//	@Qualifier("sessiondb")
	private SessionFactory sessionFactory;

	@Autowired
	YardAdminRulesService yardAdminRulesService;

	@Autowired
	YardManagementService yardManagementService;

	public YardAdminRules importYardAdminRulesDto(YardAdminRulesDto yardAdminRulesDto) {
		YardAdminRules yardAdminRules = new YardAdminRules();
		yardAdminRules.setSupplierId(yardAdminRulesDto.getSupplierId());
		yardAdminRules.setPartNum(yardAdminRulesDto.getPartNum());
		yardAdminRules.setDestination(yardAdminRulesDto.getDestination());
		yardAdminRules.setDestinationId(yardAdminRulesDto.getDestinationId());
		yardAdminRules.setYardLocation(yardAdminRulesDto.getYardLocation());
		yardAdminRules.setYardAdmin(yardAdminRulesDto.getYardAdmin());
		yardAdminRules.setMaterialHandler(yardAdminRulesDto.getMaterialHandler());
		yardAdminRules.setSecurityGuard(yardAdminRulesDto.getSecurityGuard());
		yardAdminRules.setTransportManager(yardAdminRulesDto.getTransportManager());
		yardAdminRules.setInternalUser(yardAdminRulesDto.getInternalUser());
		yardAdminRules.setEx(yardAdminRulesDto.getEx());
		return yardAdminRules;
	}

	public YardAdminRulesDto exportYardAdminRulesDto(YardAdminRules yardAdminRules) {
		YardAdminRulesDto yardAdminRulesDto = new YardAdminRulesDto();
		yardAdminRulesDto.setSupplierId(yardAdminRules.getSupplierId());
		yardAdminRulesDto.setPartNum(yardAdminRules.getPartNum());
		yardAdminRulesDto.setDestination(yardAdminRules.getDestination());
		yardAdminRulesDto.setDestinationId(yardAdminRules.getDestinationId());
		yardAdminRulesDto.setYardLocation(yardAdminRules.getYardLocation());
		yardAdminRulesDto.setYardAdmin(yardAdminRules.getYardAdmin());
		yardAdminRulesDto.setMaterialHandler(yardAdminRules.getMaterialHandler());
		yardAdminRulesDto.setSecurityGuard(yardAdminRules.getSecurityGuard());
		yardAdminRulesDto.setTransportManager(yardAdminRules.getTransportManager());
		yardAdminRulesDto.setInternalUser(yardAdminRules.getInternalUser());
		yardAdminRulesDto.setEx(yardAdminRules.getEx());
		return yardAdminRulesDto;
	}

	@Override
	public ResponseDto addYardAdminRules(YardAdminRulesDto yardAdminRulesDto) {
		ResponseDto responseDto = new ResponseDto();
		sessionFactory.getCurrentSession().saveOrUpdate(importYardAdminRulesDto(yardAdminRulesDto));
		responseDto.setMessage("SUCCESS");
		responseDto.setStatus("00");
		responseDto.setCode("SUCCESS");
		return responseDto;
	}

	@SuppressWarnings("unchecked")
	public List<YardAdminRulesDto> getYardAdminRules() {
		List<YardAdminRules> list = new ArrayList<>();
		List<YardAdminRulesDto> listOfYardAdminRules = new ArrayList<>();
		String hqlString = "select y from YardAdminRules y";
		list = sessionFactory.getCurrentSession().createQuery(hqlString).list();
		for (YardAdminRules yardAdminRules : list) {
			listOfYardAdminRules.add(exportYardAdminRulesDto(yardAdminRules));
		}
		return listOfYardAdminRules;
	}

	public int deleteYardAdminRules() {
		String queryStr = "DELETE FROM YardAdminRules y";
		Query query = sessionFactory.getCurrentSession().createQuery(queryStr);
		int result = query.executeUpdate();
		return result;
	}

	public ResponseDto addYardAdminRulesUpload(List<YardAdminRulesDto> list) {
		ResponseDto responseDto = new ResponseDto();
		for (YardAdminRulesDto dto : list) {
			sessionFactory.getCurrentSession().saveOrUpdate(importYardAdminRulesDto(dto));
		}
		responseDto.setCode("00");
		responseDto.setStatus("Success");
		responseDto.setMessage("Save Success");
		return responseDto;
	}

	@SuppressWarnings({ "unused", "unchecked" })
	@Override
	public List<YardAdminRulesDto> getYardLocationByPendingWith(List<String> users) { // 
		StringBuilder queryString = new StringBuilder();
		List<YardAdminRules> list = new ArrayList<>();
		List<YardAdminRulesDto> dtoList = new ArrayList<>();
		ArrayList<String> user = new ArrayList<>();
		for(int i=0;i<users.size();i++){
			user.add(users.get(i));
			 LOGGER.error(users.get(i).toString());
		 } 
//		
//		String pending = "(";
//		String yardLocation = " ";
//		/*
//		 * Iterator itr = users.iterator(); while (itr.hasNext()) { pending +=
//		 * itr.next(); pending += ","; } pending = pending.substring(0,
//		 * pending.lastIndexOf(",")); pending +=")"; LOGGER.error("pendingWith"
//		 * + pending);
//		 */ 
		try {
			queryString.append("select yr from YardAdminRules yr where yr.yardAdmin IN (:pending) or "
					+ "yr.materialHandler IN (:pending) OR yr.securityGuard IN (:pending) or"
					+ " yr.transportManager IN (:pending) or "
					+ "yr.internalUser IN (:pending) or yr.ex IN (:pending)");
			Query query = sessionFactory.getCurrentSession().createQuery(queryString.toString());
			query.setParameterList("pending", users);

			LOGGER.error("Query : " + query.getQueryString());
			list = query.list();
			LOGGER.error("List : " );

			for (YardAdminRules yardAdminRules : list) {
				dtoList.add(exportYardAdminRulesDto(yardAdminRules));
			}
		} catch (Exception e) {
			LOGGER.error("Exception " + e.getMessage() + "stackTrace - " + e.getStackTrace());
		}
		return dtoList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getYardLocationsByYardId(String yardId) {
		String queryString = "select ymr.yardLocation from YardAdminRules ymr where ymr.yardId= :yardId";
		Query query = sessionFactory.getCurrentSession().createQuery(queryString);
		query.setParameter("yardId", yardId);
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getAllYardIds() {
		String queryString = "select distinct ymr.yardId from YardAdminRules ymr";
		return sessionFactory.getCurrentSession().createQuery(queryString).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<HashMap<String, Object>> getYardLocationsAndYardId() {
		HashMap<String, HashMap<String, Object>> hm1 = new HashMap<>();
		try {
			List<HashMap<String, String>> list = null;
			HashMap<String, Object> hm2 = null;

			String queryString = "select ymr.yardLocation , ymr.yardId  from YardAdminRules ymr";
			Query query = sessionFactory.getCurrentSession().createQuery(queryString);
			List<Object[]> ObjList = query.list();

			if (!ServiceUtil.isEmpty(ObjList)) {
				for (Object[] o : ObjList) {

					if (hm1.containsKey((String) o[1])) {
						hm2 = hm1.get((String) o[1]);
						list = (List<HashMap<String, String>>) hm2.get("aLoc");
						HashMap<String, String> locationMap = new HashMap<>();
						locationMap.put("yardLocation", (String) o[0]);
						list.add(locationMap);
						hm2.put("yardId", (String) o[1]);
						hm2.put("aLoc", list);
						hm1.put((String) o[1], hm2);

					} else {
						HashMap<String, String> locationMap = new HashMap<>();
						locationMap.put("yardLocation", (String) o[0]);
						list = new ArrayList<>();
						list.add(locationMap);
						hm2 = new HashMap<>();
						hm2.put("yardId", (String) o[1]);
						hm2.put("aLoc", list);
						hm1.put((String) o[1], hm2);
					}
				}
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}

		return hm1.values().stream().collect(Collectors.toList());
	}

}
