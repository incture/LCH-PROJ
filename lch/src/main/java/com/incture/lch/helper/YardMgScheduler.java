package com.incture.lch.helper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.incture.lch.dto.YardAdminRulesDto;
import com.incture.lch.dto.YardManagementDto;
import com.incture.lch.service.YardAdminRulesService;
import com.incture.lch.service.YardManagementHistoryService;
import com.incture.lch.service.YardManagementService;
import com.incture.lch.service.YardManagementSlts4Service;
import com.incture.lch.util.ServiceUtil;

@Configuration
@EnableScheduling
public class YardMgScheduler {

	private static final Logger LOGGER = LoggerFactory.getLogger(YardMgScheduler.class);

	// @Autowired
	// YardMgOdataUtil yardUtil;

	@Autowired
	YardManagementSlts4Service yardManagementSlts4Service;

	@Autowired
	YardManagementService service;

	@Autowired
	YardManagementHistoryService history;

	@Autowired
	YardAdminRulesService yardAdminRulesService;

	@Autowired
	ServiceUtil util;
	
	@SuppressWarnings("static-access")
	@Scheduled(cron = "0 0/15 * * * ?")
	public void cronJobForPremiumFo() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		LOGGER.error("schedling---START for Yard details " + dtf.format(now));
		
		List<YardManagementDto> odataRecords = yardManagementSlts4Service.getYard();
		
		
		if(odataRecords!=null && odataRecords.size()>0) {
			LOGGER.error("schedling---yard odata count  " + odataRecords.size() + ":odata value" +odataRecords.get(0).getFreightOrderNo()+ "... " +odataRecords.get(0).getPlannedShipDate());
			
		}
		List<YardManagementDto> existingYMRecords = service.getAllYardManagements();
		List<YardManagementDto> newYMRecords = new ArrayList<>();
		List<YardAdminRulesDto> yardRuleRecords = yardAdminRulesService.getYardAdminRules();
		YardAdminRulesDto defaultAdminRuleDto = new YardAdminRulesDto();
		String pendingWith ;

		for (YardManagementDto ymOdata : odataRecords) {

			boolean defaultEntry = true;
	
			boolean flag = checkExistingRecord(existingYMRecords, ymOdata);

			if (!flag) {
				for(YardAdminRulesDto yardAdminRulesDto : yardRuleRecords) {
					if(yardAdminRulesDto.getDestinationId().equalsIgnoreCase(ymOdata.getDestId())) {
						defaultEntry = false;
						pendingWith = yardAdminRulesDto.getYardAdmin() + "," + yardAdminRulesDto.getMaterialHandler() + "," + yardAdminRulesDto.getSecurityGuard() + "," +
										yardAdminRulesDto.getTransportManager() + "," + yardAdminRulesDto.getInternalUser() + "," + yardAdminRulesDto.getEx();
						LOGGER.error("PendingWith" + pendingWith);
						List<String> myList = new ArrayList<String>(Arrays.asList(pendingWith.split(",")));
						System.out.println(myList);  
						ymOdata.setDestId(yardAdminRulesDto.getDestinationId());
						ymOdata.setYardLocation(yardAdminRulesDto.getYardLocation());
						ymOdata.setPendingWith(pendingWith);
						}
				}
					if (defaultEntry) {
						pendingWith = defaultAdminRuleDto.getYardAdmin() + "," + defaultAdminRuleDto.getMaterialHandler()+ "," +defaultAdminRuleDto.getSecurityGuard()+ ","
										+defaultAdminRuleDto.getTransportManager() +"," +defaultAdminRuleDto.getInternalUser()+ "," +defaultAdminRuleDto.getEx();
						List<String> myList = new ArrayList<String>(Arrays.asList(pendingWith.split(",")));
						LOGGER.error("PendingWith in default entry" + pendingWith);
						System.out.println(myList); 
						ymOdata.setDestId(defaultAdminRuleDto.getDestinationId());
						ymOdata.setYardLocation(defaultAdminRuleDto.getYardLocation());
						ymOdata.setPendingWith(pendingWith);
					}
					newYMRecords.add(ymOdata);
				}
			
		}
//				for (YardAdminRulesDto yardAdminRulesDto : yardRuleRecords) {
//					if (yardAdminRulesDto.getSupplierId().equalsIgnoreCase(ymOdata.getSupplier())
//							&& yardAdminRulesDto.getDestination().equalsIgnoreCase(ymOdata.getDestId())) {
//						defaultEntry = false;
//						pendingWith = yardAdminRulesDto.getYardAdmin() + "," + yardAdminRulesDto.getSecurityGuard();
//						ymOdata.setDestId(yardAdminRulesDto.getDestinationId());
//						ymOdata.setYardLocation(yardAdminRulesDto.getYardLocation());
//						ymOdata.setPendingWith(pendingWith);
//					} else if (yardAdminRulesDto.getSupplierId().equalsIgnoreCase("*")
//							&& yardAdminRulesDto.getDestination().equalsIgnoreCase(ymOdata.getDestId())) {
//						defaultEntry = false;
//						pendingWith = yardAdminRulesDto.getYardAdmin() + "," + yardAdminRulesDto.getSecurityGuard();
//						ymOdata.setDestId(yardAdminRulesDto.getDestinationId());
//						ymOdata.setYardLocation(yardAdminRulesDto.getYardLocation());
//						ymOdata.setPendingWith(pendingWith);
//					} else if (yardAdminRulesDto.getSupplierId().equalsIgnoreCase(ymOdata.getSupplier())
//							&& yardAdminRulesDto.getDestination().equalsIgnoreCase("*")) {
//						defaultEntry = false;
//						pendingWith = yardAdminRulesDto.getYardAdmin() + "," + yardAdminRulesDto.getSecurityGuard();
//						ymOdata.setDestId(yardAdminRulesDto.getDestinationId());
//						ymOdata.setYardLocation(yardAdminRulesDto.getYardLocation());
//						ymOdata.setPendingWith(pendingWith);
//					} else if (yardAdminRulesDto.getSupplierId().equalsIgnoreCase("*")
//							&& yardAdminRulesDto.getDestination().equalsIgnoreCase("*")) {
//						defaultAdminRuleDto = yardAdminRulesDto;
//					}
//
//				}

//				if (defaultEntry) {
//					pendingWith = defaultAdminRuleDto.getYardAdmin() + "," + defaultAdminRuleDto.getMaterialHandler()+ "," +defaultAdminRuleDto.getSecurityGuard()+ ","
//									+defaultAdminRuleDto.getTransportManager() +"," +defaultAdminRuleDto.getInternalUser()+ "," +defaultAdminRuleDto.getEx();
//					List<String> myList = new ArrayList<String>(Arrays.asList(pendingWith.split(",")));
//
//					System.out.println(myList); 
//					ymOdata.setDestId(defaultAdminRuleDto.getDestinationId());
//					ymOdata.setYardLocation(defaultAdminRuleDto.getYardLocation());
//					ymOdata.setPendingWith(pendingWith);
//				}
//				if (defaultEntry) {
//					pendingWith = defaultAdminRuleDto.getYardAdmin() + "," + defaultAdminRuleDto.getSecurityGuard();
//					ymOdata.setDestId(defaultAdminRuleDto.getDestinationId());
//					ymOdata.setYardLocation(defaultAdminRuleDto.getYardLocation());
//					ymOdata.setPendingWith(pendingWith);
//				}
//				newYMRecords.add(ymOdata);
//
//			}
//		}

		LOGGER.error("yard new records count " + newYMRecords.size());

		for (YardManagementDto dto : newYMRecords) {
			service.addYardManagement(dto);
			history.addYardManagementHistoryFromYard(dto);
		}
		now = LocalDateTime.now();
		LOGGER.error("schedling---END for Yard details " + dtf.format(now));

	}

	@SuppressWarnings("static-access")
	public boolean checkExistingRecord(List<YardManagementDto> existingYMRecords, YardManagementDto ymOdata) {
		boolean flag = false;
		String freight = util.removeLeadingZeros(ymOdata.getFreightOrderNo());
		for(YardManagementDto ymExistingRecord : existingYMRecords) {
			if (freight.equalsIgnoreCase(ymExistingRecord.getFreightOrderNo())) {
				flag = true;
				break;
			}
		}
		return flag;
	}
}