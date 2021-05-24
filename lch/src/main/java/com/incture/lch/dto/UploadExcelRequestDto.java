package com.incture.lch.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UploadExcelRequestDto {

	private String file;

	private String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

}
