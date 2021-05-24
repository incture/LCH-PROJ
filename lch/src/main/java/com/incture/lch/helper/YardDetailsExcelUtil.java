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
import com.incture.lch.dto.YardManagementDto;


@Component
public class YardDetailsExcelUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(YardDetailsExcelUtil.class);

	public ResponseDto excelDownload(List<YardManagementDto> yardManagementDto) {
		ResponseDto responseDto = new ResponseDto();
		byte[] array = generateExcel(yardManagementDto);
		String str = Base64.getEncoder().encodeToString(array);
		responseDto.setCode("00");
		responseDto.setMessage(str);
		responseDto.setStatus("SUCCESS");
		return responseDto;
	}

	public byte[] generateExcel(List<YardManagementDto> yardManagementDto) {
		byte[] xls = null;
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("Yard Details");

		List<String> headers = new ArrayList<String>();
		headers.add("PRIORITY");
		headers.add("CARRIER");
		headers.add("TRAILER_NUMBER");
		headers.add("SEAL_NO");
		headers.add("LICENCE_PLATE_NO");
		headers.add("STATUS");
		headers.add("SUPPLIER");
		headers.add("DEST_ID");
		headers.add("PLANNED_SHIP_DATE");
		headers.add("UPDATED_DATE");
		headers.add("YARD_LOCATION");
		headers.add("COMMENTS");
		headers.add("PP_KIT");
		headers.add("ARRIVAL");
		headers.add("HANDLING_UNIT");
		headers.add("FU_COUNT");
		headers.add("PRO_NO");
		headers.add("FREIGHT_ORDER");
		

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
		sheet.setColumnWidth(8, 4000);
		sheet.setColumnWidth(9, 4000);
		sheet.setColumnWidth(10, 4000);
		sheet.setColumnWidth(11, 9000);
		sheet.setColumnWidth(12, 4000);
		sheet.setColumnWidth(13, 4000);
		sheet.setColumnWidth(14, 6000);
		sheet.setColumnWidth(15, 10000);
		sheet.setColumnWidth(16, 5000);
		sheet.setColumnWidth(17, 5000);

		rowHeader = createHeader(rowHeader, sheet, headers, unlockedCellStyle);

		sheet = createYardDetails(sheet, yardManagementDto, edit);

		ByteArrayOutputStream baos = null;
		try {
			OutputStream fileOut = new FileOutputStream("c:\\temp\\yardDetails.xlsx");
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

	public HSSFSheet createYardDetails(HSSFSheet sheet, List<YardManagementDto> yardManagementDto, CellStyle edit) {
		for (int i = 0; i < yardManagementDto.size(); i++) {
			Row row = sheet.createRow(i + 1);
			int j = 0;

			Cell priority = row.createCell(j++);
			priority.setCellValue(yardManagementDto.get(i).getPriority());

			Cell carrier = row.createCell(j++);
			carrier.setCellValue(yardManagementDto.get(i).getCarrier());

			Cell trailer = row.createCell(j++);
			trailer.setCellValue(yardManagementDto.get(i).getTrailer());

			Cell sealNo = row.createCell(j++);
			sealNo.setCellValue(yardManagementDto.get(i).getSealNo());

			Cell licencePlateNo = row.createCell(j++);
			licencePlateNo.setCellValue(yardManagementDto.get(i).getLicencePlateNo());

			Cell status = row.createCell(j++);
			status.setCellValue(yardManagementDto.get(i).getStatus());
			Cell supplier = row.createCell(j++);
			supplier.setCellValue(yardManagementDto.get(i).getSupplier());

			Cell destId = row.createCell(j++);
			destId.setCellValue(yardManagementDto.get(i).getDestId());

			Cell plannedShipDate = row.createCell(j++);
			plannedShipDate.setCellValue(yardManagementDto.get(i).getPlannedShipDate());

			Cell updatedDate = row.createCell(j++);
			updatedDate.setCellValue(yardManagementDto.get(i).getUpdatedDate());

			Cell yardLocation = row.createCell(j++);
			yardLocation.setCellValue(yardManagementDto.get(i).getYardLocation());
			Cell comments = row.createCell(j++);
			comments.setCellValue(yardManagementDto.get(i).getComments());

			Cell isPpKit = row.createCell(j++);
			isPpKit.setCellValue(yardManagementDto.get(i).getIsPpKit());

			Cell arrival = row.createCell(j++);
			arrival.setCellValue(yardManagementDto.get(i).getArrival());

			Cell handlingUnit = row.createCell(j++);
			handlingUnit.setCellValue(yardManagementDto.get(i).getHandlingUnit());

			Cell fuCount = row.createCell(j++);
			fuCount.setCellValue(yardManagementDto.get(i).getFuCount());

			Cell proNo = row.createCell(j++);
			proNo.setCellValue(yardManagementDto.get(i).getProNo());

			Cell freightOrderNo = row.createCell(j++);
			freightOrderNo.setCellValue(yardManagementDto.get(i).getFreightOrderNo());

		}
		return sheet;
	}

}
