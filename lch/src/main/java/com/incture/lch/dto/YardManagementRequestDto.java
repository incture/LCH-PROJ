package com.incture.lch.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class YardManagementRequestDto {

	private List<YardManagementDto> yardManagementDto;

	public List<YardManagementDto> getYardManagementDto() {
		return yardManagementDto;
	}

	public void setYardManagementDto(List<YardManagementDto> yardManagementDto) {
		this.yardManagementDto = yardManagementDto;
	}
	
	
}
