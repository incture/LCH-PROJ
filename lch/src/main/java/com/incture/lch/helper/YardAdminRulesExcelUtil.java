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
import com.incture.lch.dto.YardAdminRulesDto;


@Component
public class YardAdminRulesExcelUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(YardAdminRulesExcelUtil.class);
	
	public ResponseDto excelDownload(List<YardAdminRulesDto> list) {
		ResponseDto responseDto = new ResponseDto();
		byte[] array = generateExcel(list);
		String str = Base64.getEncoder().encodeToString(array);
		responseDto.setCode("00");
		responseDto.setMessage(str);
		responseDto.setStatus("SUCCESS");
		return responseDto;
	}

	public byte[] generateExcel(List<YardAdminRulesDto> list) {
		byte[] xls = null;
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("YARD_ADMIN_RULES");
		List<String> headers = null;

		headers = new ArrayList<String>();
		headers.add("SUPPLIER ID");
		headers.add("PART NUM");
		headers.add("DESTINATION");
		headers.add("DESTINATION ID");
		headers.add("YARD LOCATION");
		headers.add("YARD ADMIN");
		headers.add("MATERIAL HANDLER");
		headers.add("SECURITY GUARD");
		headers.add("TRANSPORT MANAGER");
		headers.add("INTERNAL USER");
		headers.add("EX");

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
		sheet.setColumnWidth(0, 4000);
		sheet.setColumnWidth(1, 8000);
		sheet.setColumnWidth(2, 4000);
		sheet.setColumnWidth(3, 4000);
		sheet.setColumnWidth(4, 5000);
		sheet.setColumnWidth(5, 6000);
		sheet.setColumnWidth(6, 5000);
		sheet.setColumnWidth(7, 5000);
		sheet.setColumnWidth(8, 5000);
		sheet.setColumnWidth(9, 5000);
		sheet.setColumnWidth(10, 5000);
		

		rowHeader = createHeader(rowHeader, sheet, headers, unlockedCellStyle);

		sheet = createYardAdminRules(sheet, list, edit);
		
		// Write the output to a file
		ByteArrayOutputStream baos = null;
		try {
			OutputStream fileOut = new FileOutputStream("D:\\YARD_ADMIN_RULES.xls");
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

	public HSSFSheet createYardAdminRules(HSSFSheet sheet, List<YardAdminRulesDto> list, CellStyle edit) {

		for (int i = 0; i < list.size(); i++) {
			Row row = sheet.createRow(i + 1);

			YardAdminRulesDto dto = list.get(i);

			Cell supplierId = row.createCell(0);
			supplierId.setCellValue(dto.getSupplierId());
			supplierId.setCellStyle(edit);

			Cell partNum = row.createCell(1);
			partNum.setCellValue(dto.getPartNum());
			partNum.setCellStyle(edit);

			Cell destination = row.createCell(2);
			destination.setCellValue(dto.getDestination());
			destination.setCellStyle(edit);
			
			Cell destinationId = row.createCell(3);
			destinationId.setCellValue(dto.getDestinationId());
			destinationId.setCellStyle(edit);

//			Cell destinationId = row.createCell(3);
//			destionationId.setCell
//			destinationId.setCellValue(dto.getDestinationId());
//			destinationId.setCellStyle(edit);
			
			Cell yardLocation = row.createCell(4);
			yardLocation.setCellValue(dto.getYardLocation());
			yardLocation.setCellStyle(edit);
			
			Cell yardAdmin = row.createCell(5);
			yardAdmin.setCellValue(dto.getYardAdmin());
			yardAdmin.setCellStyle(edit);
			
			Cell materialHandler = row.createCell(6);
			materialHandler.setCellValue(dto.getMaterialHandler());
			materialHandler.setCellStyle(edit);
			
			Cell securityGuard = row.createCell(7);
			securityGuard.setCellValue(dto.getSecurityGuard());
			securityGuard.setCellStyle(edit);
			
			Cell transportManager = row.createCell(8);
			transportManager.setCellValue(dto.getTransportManager());
			transportManager.setCellStyle(edit);
			
			Cell internalUser = row.createCell(9);
			internalUser.setCellValue(dto.getInternalUser());
			internalUser.setCellStyle(edit);
			
			Cell exr = row.createCell(10);
			exr.setCellValue(dto.getEx());
			exr.setCellStyle(edit);

		}
		return sheet;
	}

}
