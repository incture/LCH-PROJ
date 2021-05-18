package com.incture.lch.service.implementation;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import com.incture.lch.adhoc.custom.dto.AdhocWorkflowCustomDto;
import com.incture.lch.adhoc.custom.dto.WorkflowCustomDto;
import com.incture.lch.adhoc.workflow.service.WorkflowInvoker;
import com.incture.lch.dao.PartNumberDescDao;
import com.incture.lch.dto.AdhocOrderDto;
import com.incture.lch.dto.AdhocOrderWorkflowDto;
import com.incture.lch.dto.AdhocRequestDto;
import com.incture.lch.dto.LkCountriesDto;
import com.incture.lch.dto.LkDivisionsDto;
import com.incture.lch.dto.LkShipperDetailsDto;
import com.incture.lch.dto.PartNumberDescDto;
import com.incture.lch.dto.ReasonCodeDto;
import com.incture.lch.dto.ResponseDto;
import com.incture.lch.repository.AdhocOrdersRepository;
import com.incture.lch.service.AdhocOrdersService;

@Service
@Transactional
public class AdhocOrdersServiceImplementation implements AdhocOrdersService {

	private final Logger logger1 = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private AdhocOrdersRepository adhocOrdersRepository;

	@Autowired
	private WorkflowInvoker wfInvoker;
	
	@Autowired
	private PartNumberDescDao partDao;

	@Override
	public List<AdhocOrderDto> getAllAdhocOrders() {
		return adhocOrdersRepository.getAllAdhocOrders();
	}

	@Override
	public ResponseDto addAdhocOrders(AdhocOrderDto AdhocOrderDto) {
		return adhocOrdersRepository.addAdhocOrders(AdhocOrderDto);
	}

	@Override
	public AdhocOrderDto saveAdhocOrders(AdhocOrderDto AdhocOrderDto) {
		return adhocOrdersRepository.saveAdhocOrders(AdhocOrderDto);
	}

	@Override
	public ResponseDto deleteAdhocOrders(String adhocOrderId, String userId, String partNum) {
		ResponseDto responseDto = new ResponseDto();
		int result = adhocOrdersRepository.deleteAdhocOrders(adhocOrderId, userId, partNum);
		if (result == 1) {
			responseDto.setMessage("delete success");
			responseDto.setStatus("SUCCESS");
			responseDto.setCode("00");
			return responseDto;
		} else {
			responseDto.setMessage("delete failed");
			responseDto.setStatus("FAIL");
			responseDto.setCode("01");
			return responseDto;
		}
	}

	@Override
	public List<AdhocOrderDto> getAdhocOrders(AdhocRequestDto adhocRequestDto) {
		return adhocOrdersRepository.getAdhocOrders(adhocRequestDto);
	}

	@Override
	public Map<String, List<ReasonCodeDto>> getReasonCode() {
		return adhocOrdersRepository.getReasonCode();
	}

	@Override
	public ResponseDto addReasonCode(List<ReasonCodeDto> listReasonCodeDto) {
		ResponseDto responseDto = new ResponseDto();
		for (ReasonCodeDto reasonCodeDto : listReasonCodeDto) {
			responseDto = adhocOrdersRepository.addReasonCode(reasonCodeDto);
		}
		return responseDto;
	}

	@Override
	public String getReasonCodeDescById(String id) {
		return adhocOrdersRepository.getReasonCodeDescById(id);
	}

	@Override
	public List<LkShipperDetailsDto> getShipperDetails(String shipperName) {
		return adhocOrdersRepository.getShipperDetails(shipperName);

	}

	@Override
	public List<LkCountriesDto> getAllCountries() {
		/*
		 * List<LkCountriesDto> dtoList = new ArrayList<LkCountriesDto>(); for
		 * (LkCountries lkCouDo : lkCountryJpaRepo.findAll()) {
		 * dtoList.add(lkCouDao.exportCountries(lkCouDo)); }
		 */

		return adhocOrdersRepository.getAllCountries();
	}

	public List<LkDivisionsDto> getAllDivisions() {

		logger1.error("Enter into Division service inside");
		/*
		 * List<LkDivisionsDto> dtoList = new ArrayList<LkDivisionsDto>();
		 * 
		 * for (LkDivisions lkDivDo : lkDivJpaRepo.findAll()) {
		 * logger1.error("Enter into Division service iterating ...");
		 * dtoList.add(lkDivDao.exportDivisions(lkDivDo)); }
		 * logger1.error("Enter into Division service iterating ends..");
		 */
		return adhocOrdersRepository.getAllDivisions();
	}

	public List<LkShipperDetailsDto> getAllShipperDetails() {
		return adhocOrdersRepository.getAllShipperDetails();
	}

	public PartNumberDescDto getByPartNumber(PartNumberDescDto partNum) {
		String partNumber = partNum.getPartNum();
		return partDao.getDetailsByPartNumber(partNumber);
		//return adhocOrdersRepository.getByPartNumber(partNum);
	}

	/*
	 * public Map<String, Object> getLoggedInUser(Jwt jwt) { try { Map<String,
	 * Object> userDetailsMap = new LinkedHashMap<String, Object>(); if
	 * (!jwt.getClaims().isEmpty()) { userDetailsMap.put("sub",
	 * jwt.getClaims().get("sub")); userDetailsMap.put("userId",
	 * jwt.getClaims().get("user_id")); userDetailsMap.put("user_name",
	 * jwt.getClaims().get("user_name"));
	 * userDetailsMap.put("xs.system.attributes",
	 * jwt.getClaims().get("xs.system.attributes"));
	 * userDetailsMap.put("Given_Name", jwt.getClaims().get("given_name"));
	 * userDetailsMap.put("email", jwt.getClaims().get("email"));
	 * userDetailsMap.put("family_name", jwt.getClaims().get("family_name"));
	 * userDetailsMap.put("scope", jwt.getClaims().get("scope"));
	 * 
	 * }
	 * 
	 * UserDetailsDto loggedInUser = new UserDetailsDto();
	 * loggedInUser.setID(token.getClaims().get("user_id").toString());
	 * loggedInUser.setDisplayName(token.getClaims().get("given_name").
	 * toString());
	 * loggedInUser.setFirstName(token.getClaims().get("given_name").
	 * toString());
	 * loggedInUser.setLastName(token.getClaims().get("family_name").
	 * toString());
	 * loggedInUser.setEmail(token.getClaims().get("email").toString()); return
	 * ResponseEntity.ok().body(new Response<UserDetailsDto>(loggedInUser));
	 * 
	 * return userDetailsMap; } catch (Exception e) {
	 * 
	 * final Response<String> body = new Response<String>(e.getMessage());
	 * body.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase() );
	 * body.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value()); return
	 * ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body ); }
	 * 
	 * return null; }
	 */

	@Override
	public List<AdhocOrderDto> getDrafts(AdhocRequestDto adhocRequestDto) {
		return adhocOrdersRepository.getDrafts(adhocRequestDto);
	}

	@Override
	public List<AdhocOrderDto> getKpi(int days, AdhocRequestDto adhocRequestDto) {
		return adhocOrdersRepository.getKpi(days, adhocRequestDto);
	}

	@Override
	public String updateWorflowDetails(AdhocOrderWorkflowDto workflowDto) {
		return adhocOrdersRepository.updateWorflowDetails(workflowDto);

	}

	@Override
	public String updateApprovalWorflowDetails(JSONObject obj) throws JSONException {
		return adhocOrdersRepository.updateApprovalWorflowDetails(obj);

	}

	public String updateApprovalWorflowDetails(WorkflowCustomDto dto)
			throws JSONException, ClientProtocolException, IOException {
		return adhocOrdersRepository.updateApprovalWorflowDetails(dto);
	}

	public String updateApprovalWorflowDetails1(WorkflowCustomDto obj)
			throws JSONException, ClientProtocolException, IOException {
		return adhocOrdersRepository.updateApprovalWorflowDetails1(obj);
	}

	@Override
	public HttpResponse approveTask(String taskId) throws ClientProtocolException, IOException, JSONException {
		return wfInvoker.approveTask(null, taskId);
	}

	public String updateApprovalWorflowDetailsForType4(AdhocWorkflowCustomDto dto)
			throws JSONException, ClientProtocolException, IOException {
		return adhocOrdersRepository.updateApprovalWorflowDetailsForType4(dto);
	}

}
