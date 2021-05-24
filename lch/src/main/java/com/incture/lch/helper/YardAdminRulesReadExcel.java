package com.incture.lch.helper;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Component;

import com.incture.lch.dto.YardAdminRulesDto;


@Component
public class YardAdminRulesReadExcel {

	public List<YardAdminRulesDto> readExcelDataFormat(String fileLocation, String ext) {

		byte[] decodedString = Base64.getDecoder().decode(fileLocation);
		InputStream st = new ByteArrayInputStream(decodedString);
		List<YardAdminRulesDto> list = new ArrayList<>();
		DataFormatter dataFormat = new DataFormatter();
		Workbook workbook;
		Sheet sheet;
		try {
			if (ext.equalsIgnoreCase("xlsx")) {
				workbook = WorkbookFactory.create(st);
			} else {
				workbook = new HSSFWorkbook(st);
			}
				sheet = workbook.getSheetAt(0);
				int rowCount = sheet.getPhysicalNumberOfRows();
				Row row0 = sheet.getRow(0);
				int cellCount = row0.getPhysicalNumberOfCells();
				for (int r = 1; r < rowCount; r++) {
					YardAdminRulesDto yardAdminRulesDto = new YardAdminRulesDto();
					Row row = sheet.getRow(r);
					if(row != null) {
						for (int c = 0; c < cellCount; c++) {
							switch (dataFormat.formatCellValue(row0.getCell(c))) {
	
								case "SUPPLIER ID": {
									yardAdminRulesDto.setSupplierId(dataFormat.formatCellValue(row.getCell(c)));
									break;
								}
								case "PART NUM": {
									yardAdminRulesDto.setPartNum(dataFormat.formatCellValue(row.getCell(c)));
									break;
								}
								case "DESTINATION": {
									yardAdminRulesDto.setDestination(dataFormat.formatCellValue(row.getCell(c)));
									break;
								}
								case "DESTINATION ID": {
									yardAdminRulesDto.setDestinationId(dataFormat.formatCellValue(row.getCell(c)));
									break;
								}
								case "YARD LOCATION": {
									yardAdminRulesDto.setYardLocation(dataFormat.formatCellValue(row.getCell(c)));
									break;
								}
								case "YARD ADMIN": {
									yardAdminRulesDto.setYardAdmin(dataFormat.formatCellValue(row.getCell(c)));
									break;
								}
								
								case "MATERIAL HANDLER": {
									yardAdminRulesDto.setMaterialHandler(dataFormat.formatCellValue(row.getCell(c)));
									break;
								}
								
								case "SECURITY GUARD": {
									yardAdminRulesDto.setSecurityGuard(dataFormat.formatCellValue(row.getCell(c)));
									break;
								}
								
								case "TRANSPORT MANAGER":
								{
									yardAdminRulesDto.setTransportManager(dataFormat.formatCellValue(row.getCell(c)));
									break;
								}
								
								case "INTERNAL USER":
								{
									yardAdminRulesDto.setInternalUser(dataFormat.formatCellValue(row.getCell(c)));
									break;
								}
								
								case "Ex":
								{
									yardAdminRulesDto.setEx(dataFormat.formatCellValue(row.getCell(c)));
									break;
								}
								
							}
						}
					}
					list.add(yardAdminRulesDto);
				}
		} catch (IOException | EncryptedDocumentException e) {
			e.printStackTrace();
		}
		return list;
	}

}
