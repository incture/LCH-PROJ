package com.incture.lch.adhoc.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.oauth2.jwt.Jwt;

import com.incture.lch.adhoc.dto.AdhocOrderDto;
import com.incture.lch.adhoc.dto.AdhocOrderWorkflowDto;
import com.incture.lch.adhoc.dto.AdhocRequestDto;
import com.incture.lch.adhoc.dto.LkCountriesDto;
import com.incture.lch.adhoc.dto.LkDivisionsDto;
import com.incture.lch.adhoc.dto.LkShipperDetailsDto;
import com.incture.lch.adhoc.dto.PartNumberDescDto;
import com.incture.lch.adhoc.dto.ReasonCodeDto;
import com.incture.lch.adhoc.dto.Response;
import com.incture.lch.adhoc.dto.ResponseDto;

public interface AdhocOrdersService {

	public List<AdhocOrderDto> getAllAdhocOrders();
	
	
	public List<AdhocOrderDto> getKpi(int days,AdhocRequestDto adhocRequestDto);

	
	
	public List<AdhocOrderDto> getDrafts(AdhocRequestDto adhocRequestDto);
	
	
	public ResponseDto addAdhocOrders(AdhocOrderDto adhocOrdersDto);
	

	public AdhocOrderDto saveAdhocOrders(AdhocOrderDto adhocOrdersDto);


	public ResponseDto deleteAdhocOrders(String adhocOrderId, String userId, String partNum);

	public List<AdhocOrderDto> getAdhocOrders(AdhocRequestDto adhocRequestDto);

	public Map<String, List<ReasonCodeDto>> getReasonCode();

	public ResponseDto addReasonCode(List<ReasonCodeDto> listReasonCodeDto);

	public String getReasonCodeDescById(String id);

	public List<LkShipperDetailsDto> getShipperDetails(String shipperName);

	public List<LkCountriesDto> getAllCountries();

	public List<LkDivisionsDto> getAllDivisions();

	public List<LkShipperDetailsDto> getAllShipperDetails();

	// public ResponseEntity<Response<?>> getLoggedInUser(Jwt token);
	//public Map<String, Object> getLoggedInUser(Jwt token);

	public PartNumberDescDto getByPartNumber(PartNumberDescDto partNum);
	
	public String updateWorflowDetails(AdhocOrderWorkflowDto workflowDto);
	
	public String updateApprovalWorflowDetails(JSONObject obj) throws JSONException;


	public HttpResponse approveTask(String taskId) throws ClientProtocolException, IOException, JSONException;
	
	

}