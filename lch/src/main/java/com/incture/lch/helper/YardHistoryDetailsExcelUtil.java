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
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.incture.lch.dto.ResponseDto;
import com.incture.lch.dto.YardManagementHistoryDto;

@Component
public class YardHistoryDetailsExcelUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(YardHistoryDetailsExcelUtil.class);

	public ResponseDto excelDownload(List<YardManagementHistoryDto> yardManagementHistoryDto) {
		ResponseDto responseDto = new ResponseDto();
		byte[] array = generateExcel(yardManagementHistoryDto);
		String str = Base64.getEncoder().encodeToString(array);
		responseDto.setCode("00");
		responseDto.setMessage(str);
		responseDto.setStatus("SUCCESS");
		return responseDto;
	}

	public byte[] generateExcel(List<YardManagementHistoryDto> yardManagementHistoryDto) {
		byte[] xls = null;
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("Yard History Details");

		List<String> headers = new ArrayList<String>();
		headers.add("CARRIER");
		headers.add("TRAILER");
		headers.add("SEAL_NO");
		headers.add("LICENCE_PLATE_NO");
		headers.add("STATUS");
		headers.add("SUPPLIER");
		headers.add("DEST_ID");
		headers.add("PLANNED_SHIP_DATE");

		CellStyle unlockedCellStyle = workbook.createCellStyle();
		Font font = workbook.createFont();
		font.setBold(true);
		font.setColor(IndexedColors.BLACK.getIndex());
		unlockedCellStyle.setFont(font);
		unlockedCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		unlockedCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		CellStyle edit = workbook.createCellStyle();
		edit.setLocked(false);

		CellStyle dateFormat = workbook.createCellStyle();
		CreationHelper createHelper = workbook.getCreationHelper();
		dateFormat.setDataFormat(createHelper.createDataFormat().getFormat("yyyy-MM-dd HH:mm:ss"));

		Row rowHeader = sheet.createRow(0);
		sheet.setColumnWidth(0, 4000);
		sheet.setColumnWidth(1, 9000);
		sheet.setColumnWidth(2, 4000);
		sheet.setColumnWidth(3, 4000);
		sheet.setColumnWidth(4, 6000);
		sheet.setColumnWidth(5, 10000);
		sheet.setColumnWidth(6, 5000);
		sheet.setColumnWidth(7, 5000);

		rowHeader = createHeader(rowHeader, sheet, headers, unlockedCellStyle);

		sheet = createYardDetails(sheet, yardManagementHistoryDto, edit);

		ByteArrayOutputStream baos = null;
		try {
			OutputStream fileOut = new FileOutputStream("c:\\temp\\yardHistoryDetails.xlsx");
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
				ex.printStackTrace();
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

	public HSSFSheet createYardDetails(HSSFSheet sheet, List<YardManagementHistoryDto> yardManagementHistoryDto,
			CellStyle edit) {
		for (int i = 0; i < yardManagementHistoryDto.size(); i++) {
			Row row = sheet.createRow(i + 1);
			int j = 0;
			Cell carrier = row.createCell(j++);
			carrier.setCellValue(yardManagementHistoryDto.get(i).getCarrier());

			Cell trailer = row.createCell(j++);
			trailer.setCellValue(yardManagementHistoryDto.get(i).getTrailer());

			Cell sealNo = row.createCell(j++);
			sealNo.setCellValue(yardManagementHistoryDto.get(i).getSealNo());

			Cell licencePlateNo = row.createCell(j++);
			licencePlateNo.setCellValue(yardManagementHistoryDto.get(i).getLicencePlateNo());

			Cell status = row.createCell(j++);
			status.setCellValue(yardManagementHistoryDto.get(i).getStatus());

			Cell supplier = row.createCell(j++);
			supplier.setCellValue(yardManagementHistoryDto.get(i).getSupplier());

			Cell destId = row.createCell(j++);
			destId.setCellValue(yardManagementHistoryDto.get(i).getDestId());

			Cell plannedShipDate = row.createCell(j++);
			plannedShipDate.setCellValue(yardManagementHistoryDto.get(i).getPlannedShipDate());

		}
		return sheet;
	}
}
