package com.incture.lch.helper;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.incture.lch.dto.ResponseDto;
import com.incture.lch.dto.YardManagementDto;

@Component
public class YardManagementExcelUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(YardManagementExcelUtil.class);

	public ResponseDto excelDownload(List<YardManagementDto> list) {
		ResponseDto responseDto = new ResponseDto();
		byte[] array = generateExcel(list);
		String str = Base64.getEncoder().encodeToString(array);
		responseDto.setCode("00");
		responseDto.setMessage(str);
		responseDto.setStatus("SUCCESS");
		return responseDto;
	}
	
	public byte[] generateExcel(List<YardManagementDto> list) {
		byte[] xls = null;
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("YARD_MANAGEMENT");
		List<String> headers = null;
	
		headers = new ArrayList<String>();
		headers.add("FREIGHT ORDER");
		headers.add("CARRIER");
		headers.add("DEST ID");
		headers.add("PLAN DUE DATE");
		headers.add("TRUCK ENTRY TIME");
		headers.add("ETA");
		headers.add("HU");
		headers.add("YARD ID");
		headers.add("LOCATION");
		headers.add("STATUS");
		headers.add("PRIORITY");
		headers.add("COMMENT");
		headers.add("AGING");
		
		CellStyle unlockedCellStyle = workbook.createCellStyle();
		Font font = workbook.createFont();
		font.setBold(true);
		font.setColor(IndexedColors.BLACK.getIndex());
		unlockedCellStyle.setFont(font);
		unlockedCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		unlockedCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		CellStyle edit = workbook.createCellStyle();
		edit.setLocked(false);

		Row rowHeader = sheet.createRow(0);
		sheet.setColumnWidth(0, 7000);
		sheet.setColumnWidth(1, 5000);
		sheet.setColumnWidth(2, 5000);
		sheet.setColumnWidth(3, 5000);
		sheet.setColumnWidth(4, 5000);
		sheet.setColumnWidth(5, 5000);
		sheet.setColumnWidth(6, 5000);
		sheet.setColumnWidth(7, 8000);
		sheet.setColumnWidth(8, 5000);
		sheet.setColumnWidth(9, 8000);
		sheet.setColumnWidth(10, 5000);
		sheet.setColumnWidth(11, 5000);
		sheet.setColumnWidth(12, 5000);
		
		rowHeader = createHeader(rowHeader, sheet, headers, unlockedCellStyle);

		sheet = createYardManagement(sheet, list, edit);
		
		// Write the output to a file
				ByteArrayOutputStream baos = null;
				try {
					OutputStream fileOut = new FileOutputStream("D:\\YARD_MANAGEMENT.xls");
					workbook.write(fileOut);
					baos = new ByteArrayOutputStream();
					workbook.write(baos);
					xls = baos.toByteArray();
					workbook.close();
				} catch (FileNotFoundException ex) {
					LOGGER.error("File not found exception:" + ex.getMessage());
				} catch (IOException ex) {
					LOGGER.error("IO exception:" + ex.getMessage());
				} finally {
					try {
						if (baos != null) {
							baos.close();
						}
					} catch (IOException ex) {
						LOGGER.error("Exception:" + ex.getMessage());
					}
				}
				return xls;
			}
	public Row createHeader(Row row, HSSFSheet sheet, List<String> headers, CellStyle style) {
		for (int i = 0; i < headers.size(); i++) {
			row.createCell(i).setCellValue(headers.get(i));
			row.getCell(i).setCellStyle(style);
		}
		return row;
	}
	
	public HSSFSheet createYardManagement(HSSFSheet sheet, List<YardManagementDto> list, CellStyle edit) {

		for (int i = 0; i < list.size(); i++) {
			Row row = sheet.createRow(i + 1);

			YardManagementDto dto = list.get(i);
			
			Cell freightOrderNo = row.createCell(0);
			freightOrderNo.setCellValue(dto.getFreightOrderNo());
			freightOrderNo.setCellStyle(edit);
			
			Cell carrier = row.createCell(1);
			carrier.setCellValue(dto.getCarrier());
			carrier.setCellStyle(edit);
			
			Cell destId = row.createCell(2);
			destId.setCellValue(dto.getDestId());
			destId.setCellStyle(edit);
			
			Cell plannedShipDate = row.createCell(3);
			plannedShipDate.setCellValue(dto.getPlannedShipDate());
			plannedShipDate.setCellStyle(edit);
			
			Cell truckEntryTime = row.createCell(4);
			truckEntryTime.setCellValue(dto.getArrival());
			truckEntryTime.setCellStyle(edit);
			
			Cell eta = row.createCell(5);
			eta.setCellValue(dto.getArrival());
			eta.setCellStyle(edit);
			
			Cell handlingUnit = row.createCell(6);
			handlingUnit.setCellValue(dto.getHandlingUnit());
			handlingUnit.setCellStyle(edit);
			
			Cell yardId = row.createCell(7);
			yardId.setCellValue(dto.getYardId());
			yardId.setCellStyle(edit);
			
			Cell location = row.createCell(8);
			location.setCellValue(dto.getLocation());
			location.setCellStyle(edit);
			
			Cell status = row.createCell(9);
			status.setCellValue(dto.getStatus());
			status.setCellStyle(edit);
			
			Cell priority = row.createCell(10);
			priority.setCellValue(dto.getPriority());
			priority.setCellStyle(edit);
			
			Cell comments = row.createCell(11);
			comments.setCellValue(dto.getComments());
			comments.setCellStyle(edit);
			
			Cell aging = row.createCell(12);
			aging.setCellValue(dto.getAgingCount());
			aging.setCellStyle(edit);
	


		}
		return sheet;
	}
}
