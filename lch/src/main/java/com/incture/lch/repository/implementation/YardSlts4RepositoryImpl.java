package com.incture.lch.repository.implementation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.incture.lch.dto.YardManagementDto;
import com.incture.lch.repository.YardSlts4Repository;
import com.incture.lch.util.ServiceUtil;

@Repository
public class YardSlts4RepositoryImpl implements YardSlts4Repository {

	@Autowired
	//@Qualifier("sessionSlts4")
	private SessionFactory sessionFactory;

	private static final Logger LOGGER = LoggerFactory.getLogger(YardSlts4RepositoryImpl.class);

	@SuppressWarnings("unchecked")
	@Override
	public List<YardManagementDto> getYard() {
		List<YardManagementDto> request = null;
		List<Map<String, Object>> responseList = null;
		StringBuilder queryString = new StringBuilder();
		queryString.append("select distinct P.\"MTR\" AS MTR,P.\"TOR_ID\" AS FREIGHTORDER,");
		queryString.append("P.\"ZREFERNCE_NUM\" AS REFERNUM,P.\"ZPICKUP_NO\" AS PICKUPNUM,");
		queryString.append("P.\"PARTNER_REF_ID\" AS PRONUM,P.\"ZTRAILER_NO\" AS TRAILERNUM,");
		queryString.append("P.\"TSP_SCAC\" AS CARRIERSCAC,B.\"NAME_ORG1\" AS CARRIERNAME,");
		queryString.append("P.\"TSPID\" AS CARRIERID,STS.\"ZEST_DT_ARRIVAL\" AS ETA,");
		queryString.append("STS.\"ZEST_LOCTZ\" AS TIMEZONE,STS.\"ZSTOP_TYPE\" AS STOPTYPE,");
		queryString.append("P.\"QUA_PCS_VAL\" AS QTYONHEADER,P.\"QUA_PCS_UNI\" AS QTYUOMHEADER,");
		queryString.append("P.\"ZTOTAL_HU\" AS HU,P.\"ZTOTAL_HU_UOM\" AS HUUOM,P.\"ZTOTAL_WEIGHT\" AS WEIGHT,");
		queryString.append("P.\"ZTW_UOM\" AS WEIGHTUOM,F.\"LOG_LOCID\" AS SOURCELOCATION,");
		queryString.append("LOCTF.\"DESCR40\" AS SOURCEDESCRIPTION,L.\"LOG_LOCID\" AS ");
		queryString.append("DESTINATIONLOCATION,LOCTL.\"DESCR40\" AS DESTINATIONDESCRIPTION,");
		queryString.append("P.\"EXECUTION\" AS EXECUTION,SL.\"SEAL_NUMBER\" AS SEALNO,");
		queryString.append("(case when L.\"STOP_CAT\" = 'I' then  L.\"PLAN_TRANS_TIME\" else 0 end) as PLAN_TRANS_TIME,");
		queryString.append("count(distinct C.TOR_ID) AS FUCOUNT	from \"SLTS4\".\"/SCMTMS/D_TORROT\" AS P ");
		queryString.append("left outer join \"SLTS4\".\"/SCMTMS/D_TORITE\" AS I ");
		queryString.append("on p.db_key = I.parent_key left outer join \"SLTS4\".\"/SCMTMS/D_TORROT\" AS  c ");
		queryString.append("on i.fu_root_key=C.db_key left join \"SLTS4\".\"BUT000\" AS B on P.TSPID = B.PARTNER ");
		queryString.append("left join \"SLTS4\".\"/SCMTMS/D_TORSTP\" as F on P.db_key = F.PARENT_KEY and F.STOP_SEQ_POS = 'F' ");
		queryString.append("left join \"SLTS4\".\"/SCMTMS/D_TORSTP\" as L on P.db_key = L.PARENT_KEY and L.STOP_SEQ_POS = 'L' ");
		queryString.append("left join \"SLTS4\".\"/SCMTMS/D_TORSTS\" as STS on P.db_key = STS.ROOT_KEY ");
		queryString.append("left join \"SLTS4\".\"/SAPAPO/LOC\" as LOCF on F.LOG_LOCID = LOCF.LOCNO ");
		queryString.append("left join \"SLTS4\".\"/SAPAPO/LOCT\" as LOCTF on LOCF.LOCID = LOCTF.LOCID ");
		queryString.append("left join \"SLTS4\".\"/SAPAPO/LOC\" as LOCL on L.LOG_LOCID = LOCL.LOCNO ");
		queryString.append("left join \"SLTS4\".\"/SAPAPO/LOCT\" as LOCTL on LOCL.LOCID = LOCTL.LOCID ");
		queryString.append("left join \"SLTS4\".\"/SCMTMS/D_TORSL\" as SL on P.db_key = SL.ROOT_KEY ");
		queryString.append("where /*c.tor_cat = 'FU' and*/ p.TOR_CAT = 'TO' and P.\"EXECUTION\" = '03' AND P.\"MTR\" like '%ZFTL%' ");
		queryString.append("group by P.\"MTR\",P.\"TOR_ID\",P.\"ZREFERNCE_NUM\", P.\"ZPICKUP_NO\", ");
		queryString.append(" P.\"PARTNER_REF_ID\",P.\"ZTRAILER_NO\",P.\"TSP_SCAC\", B.\"NAME_ORG1\", P.\"TSPID\",");
		queryString.append("(case when L.\"STOP_CAT\" = 'I' then  L.\"PLAN_TRANS_TIME\" else 0 end),");
		queryString.append("STS.\"ZEST_DT_ARRIVAL\", STS.\"ZEST_LOCTZ\",STS.\"ZSTOP_TYPE\",");
		queryString.append("P.\"QUA_PCS_VAL\",P.\"QUA_PCS_UNI\",	P.\"ZTOTAL_HU\",");
		queryString.append("P.\"ZTOTAL_HU_UOM\",P.\"ZTOTAL_WEIGHT\",P.\"ZTW_UOM\",F.\"LOG_LOCID\",");
		queryString.append("LOCTF.\"DESCR40\",L.\"LOG_LOCID\",LOCTL.\"DESCR40\",P.\"EXECUTION\",SL.\"SEAL_NUMBER\" ");
		try {

			SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(queryString.toString());
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			responseList = (List<Map<String, Object>>) query.list();
			request = getYardDetails(responseList);
//			LOGGER.error("yard responseList size" + responseList.size());
		} catch (Exception e) {
			LOGGER.error("Exception on getYardManagement service on YardSlts4RepositoryImpl " + e.getMessage());
		}

		return request;
	}

	public List<YardManagementDto> getYardDetails(List<Map<String, Object>> responseList) {
		
		List<YardManagementDto> list = new ArrayList<>();
		YardManagementDto dto = null;
		for (Map<String, Object> entry : responseList) {
			dto = new YardManagementDto();
			
			if (entry.get("FREIGHTORDER") != null && !(entry.get("FREIGHTORDER").equals(""))) {
				dto.setFreightOrderNo(ServiceUtil.parseCostValue(String.valueOf(entry.get("FREIGHTORDER"))));
			} else {
				dto.setFreightOrderNo("0");
			}
			dto.setReferenceNo((String) entry.get("REFERNUM"));
			dto.setPickNo((String) entry.get("PICKUPNUM"));
			dto.setProNo((String) entry.get("PRONUM"));
			dto.setTrailer((String) entry.get("TRAILERNUM"));
			dto.setCarrierDesc((String) entry.get("CARRIERID"));
			dto.setCarrierName((String) entry.get("CARRIERNAME"));
			dto.setCarrier((String) entry.get("CARRIERSCAC"));
			dto.setArrival(String.valueOf(entry.get("ETA")));
			dto.setQty(String.valueOf(entry.get("QTYONHEADER")));
			dto.setHandlingUnit(String.valueOf(entry.get("HU")));
			dto.setWeight(String.valueOf (entry.get("WEIGHT")));
			dto.setSupplier((String) entry.get("SOURCELOCATION"));
			dto.setSupplierAddress((String) entry.get("SOURCEDESCRIPTION"));
			dto.setDestId((String) entry.get("DESTINATIONLOCATION"));
			dto.setDestDesc((String) entry.get("DESTINATIONDESCRIPTION"));
			dto.setSealNo((String) entry.get("SEALNO"));
			dto.setFuCount(String.valueOf(entry.get("FUCOUNT")));
			dto.setBol("");
			dto.setCommodities("");
			dto.setCreatedBy("SYSTEM");
			dto.setCreatedDate(ServiceUtil.convertDateToString(new Date()));
			dto.setUpdatedBy("SYSTEM");
			dto.setUpdatedDate(ServiceUtil.convertDateToString(new Date()));
			dto.setId(null);
			dto.setLineSeq("");
			dto.setLocation((String) entry.get("SOURCELOCATION"));
			dto.setPriority("");
			dto.setStatus("NOTARRIVED");
			dto.setComments("");
			if(entry.get("PLAN_TRANS_TIME") != null) {
				dto.setPlannedShipDate(String.valueOf(entry.get("PLAN_TRANS_TIME")));
			}
			
			list.add(dto);
		}
		
		return list;
	}
}
