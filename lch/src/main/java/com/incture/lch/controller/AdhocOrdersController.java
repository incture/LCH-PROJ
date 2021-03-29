package com.incture.lch.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.incture.lch.adhoc.custom.dto.AdhocWorkflowCustomDto;
import com.incture.lch.adhoc.custom.dto.ResponseMessage;
import com.incture.lch.adhoc.custom.dto.WorkflowCustomDto;
import com.incture.lch.dao.AdhocApprovalRuleDao;
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
import com.incture.lch.helper.AdhocExcelHelper;
import com.incture.lch.service.AdhocExcelService;
import com.incture.lch.service.AdhocOrdersService;
import com.incture.lch.util.ServiceUtil;

@RestController
@CrossOrigin
@RequestMapping(value = "/adhocorders", produces = "application/json")
public class AdhocOrdersController {

	private final Logger logger1 = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private AdhocOrdersService adhocOrdersService;

	@Autowired
	private AdhocApprovalRuleDao adhocApprovalRuleDao;

	@Autowired
	private AdhocExcelHelper helper;

	@Autowired
	private AdhocExcelService excelService;


	@RequestMapping(value = "/addAdhocOrders", method = RequestMethod.POST, consumes = { "application/json" })
	@ResponseBody
	public ResponseDto addAdhocOrders(@RequestBody AdhocOrderDto AdhocOrderDto) {
		return adhocOrdersService.addAdhocOrders(AdhocOrderDto);
	}

	@RequestMapping(value = "/saveAdhocOrders", method = RequestMethod.POST, consumes = { "application/json" })
	@ResponseBody
	public AdhocOrderDto saveAdhocOrders(@RequestBody AdhocOrderDto AdhocOrderDto) {
		return adhocOrdersService.saveAdhocOrders(AdhocOrderDto);
	}

	@RequestMapping(value = "/getAllAdhocOrders", method = RequestMethod.POST, consumes = { "application/json" })
	@ResponseBody
	public List<AdhocOrderDto> getAllAdhocOrders() {
		return adhocOrdersService.getAllAdhocOrders();
	}

	@RequestMapping(value = "/getDrafts", method = RequestMethod.POST, consumes = { "application/json" })
	@ResponseBody
	public List<AdhocOrderDto> getDrafts(@RequestBody AdhocRequestDto adhocRequestDto) {
		return adhocOrdersService.getDrafts(adhocRequestDto);
	}

	@RequestMapping(value = "/getKpiBasedResult/{days}", method = RequestMethod.POST, consumes = { "application/json" })
	@ResponseBody
	public List<AdhocOrderDto> getKpiBasedResult(@PathVariable(value = "days") int days,
			@RequestBody AdhocRequestDto adhocRequestDto) {
		return adhocOrdersService.getKpi(days, adhocRequestDto);
	}

	@RequestMapping(value = "/getByPartNumber", method = RequestMethod.POST, consumes = { "application/json" })
	@ResponseBody
	public PartNumberDescDto getByPartNumber(@RequestBody PartNumberDescDto partNum) {
		return adhocOrdersService.getByPartNumber(partNum);
	}

	@RequestMapping(value = "/deleteAdhocOrders", method = RequestMethod.DELETE, consumes = { "application/json" })
	@ResponseBody
	public ResponseDto deleteAdhocOrders(String adhocOrderId, String userId, String partNum) {
		return adhocOrdersService.deleteAdhocOrders(adhocOrderId, userId, partNum);
	}

	@RequestMapping(value = "/getAdhocOrders", method = RequestMethod.POST, consumes = { "application/json" })
	@ResponseBody
	public List<AdhocOrderDto> getAdhocOrders(@RequestBody AdhocRequestDto adhocRequestDto) {
		return adhocOrdersService.getAdhocOrders(adhocRequestDto);
	}

	@RequestMapping(value = "/getReasonCode", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, List<ReasonCodeDto>> getReasonCode() {
		return adhocOrdersService.getReasonCode();
	}

	@RequestMapping(value = "/addReasonCode", method = RequestMethod.POST, consumes = { "application/json" })
	@ResponseBody
	public ResponseDto addReasonCode(@RequestBody List<ReasonCodeDto> listReasonCodeDto) {
		return adhocOrdersService.addReasonCode(listReasonCodeDto);
	}

	@RequestMapping(value = "/getAllCountries", method = RequestMethod.GET, consumes = { "application/json" })
	@ResponseBody
	public List<LkCountriesDto> getAllCountries() {
		return adhocOrdersService.getAllCountries();
	}

	@RequestMapping(value = "/getAllDivisions", method = RequestMethod.GET, consumes = { "application/json" })
	@ResponseBody
	public List<LkDivisionsDto> getAllDivisions() {
		logger1.error("Enter into Division controller");
		return adhocOrdersService.getAllDivisions();
	}

	@RequestMapping(value = "/getReasonCodeById/{id}", method = RequestMethod.GET, consumes = { "application/json" })
	@ResponseBody
	public String getReasonCodeById(@PathVariable(value = "id") String id) {
		return adhocOrdersService.getReasonCodeDescById(id);
	}

	@RequestMapping(value = "/getShipperDetails/{shipperName}", method = RequestMethod.GET, consumes = {
			"application/json" })
	@ResponseBody
	public List<LkShipperDetailsDto> getShipperDetails(@PathVariable(value = "shipperName") String shipperName) {
		return adhocOrdersService.getShipperDetails(shipperName);
	}

	@RequestMapping(value = "/getAllShipperDetails", method = RequestMethod.GET, consumes = { "application/json" })
	@ResponseBody
	public List<LkShipperDetailsDto> getAllShipperDetails() {
		return adhocOrdersService.getAllShipperDetails();
	}

	@RequestMapping(value = "/postMessage", method = RequestMethod.GET)
	public String testPostMessage() {
		return "Your message has been posted to IOP. Reference No. #" + ServiceUtil.generateRandomDigits(16);
	}

	@RequestMapping(value = "/excelUpload", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
		String message = "";
		logger1.error("Enter into AdhocApprovalRule controller uploadFile");
		System.out.println("Enter into AdhocApprovalRule controller uploadFile");
		if (AdhocExcelHelper.hasExcelFormat(file)) {
			try {
				excelService.save(file);

				message = "Uploaded the file successfully: " + file.getOriginalFilename();
				return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
			} catch (Exception e) {
				logger1.error("Could not upload the file: " + e.toString());
				message = "Could not upload the file: " + file.getOriginalFilename() + "!";
				return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
			}
		}

		message = "Please upload an excel file!";
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
	}

	@GetMapping("/getAllApproveRule")
	public List<AdhocApprovalRuleDto> getAllApproveRule() {
		logger1.error("Enter into AdhocApprovalRule controller");
		return excelService.getAllAdhocApprovalList();
	}

	@GetMapping("/downloadApprovalRule")
	public ResponseEntity<Resource> getFile() {
		String filename = "AdhocApproval.xlsx";
		InputStreamResource file = new InputStreamResource(excelService.load());

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
				.contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(file);
	}

	@GetMapping("/downloadSampleTemplate")
	public ResponseEntity<Resource> getSampleFile() {
		String filename = "Sample_Template.xlsx";
		InputStreamResource file = new InputStreamResource(excelService.loadSampleFile());

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
				.contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(file);
	}

	@RequestMapping(value = "/updateWorkflowDetails", method = RequestMethod.POST)
	public String updateWorflowDetails(@RequestBody AdhocOrderWorkflowDto workflowDto) {
		System.out.println(workflowDto.getOrderId());
		return adhocOrdersService.updateWorflowDetails(workflowDto);
	}

	/*
	 * @RequestMapping(value = "/updateApprovalWorkflowDetails", method =
	 * RequestMethod.POST)
	 * 
	 * @ResponseBody public String updateApprovalWorkflowDetails(@RequestBody
	 * JSONObject obj) throws ClientProtocolException, IOException,
	 * JSONException {
	 * logger1.error("ENTERING INTO updateApprovalWorkflowDetails CONTROLLER");
	 * return adhocOrdersService.updateApprovalWorflowDetails(obj); }
	 */

	@RequestMapping(value = "/updateApprovalWorkflowDetails", method = RequestMethod.POST)
	@ResponseBody
	public String updateApprovalWorkflowDetails(@RequestBody WorkflowCustomDto dto)
			throws ClientProtocolException, IOException, JSONException {
		logger1.error("ENTERING INTO updateApprovalWorkflowDetails CONTROLLER");
		return adhocOrdersService.updateApprovalWorflowDetails(dto);
	}

	@RequestMapping(value = "/updateApprovalWorkflowDetailsForType4", method = RequestMethod.POST)
	@ResponseBody
	public String updateApprovalWorkflowDetailsForType4(@RequestBody AdhocWorkflowCustomDto dto)
			throws ClientProtocolException, IOException, JSONException {
		logger1.error("ENTERING INTO updateApprovalWorkflowDetails CONTROLLER");
		return adhocOrdersService.updateApprovalWorflowDetailsForType4(dto);
	}


}
