package com.incture.lch.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="PART_NUMBER_DESC")
public class PartNumberDesc 
{
	@Id
	@Column(name="PART_NUM")
	private String partNum;
	
	@Column(name="PART_DESC")
	private String partDesc;
	
	@Column(name="MESSAGE")
	private String message;

	
	public String getPartNum() {
		return partNum;
	}

	public void setPartNum(String partNum) {
		this.partNum = partNum;
	}

	public String getPartDesc() {
		return partDesc;
	}

	public void setPartDesc(String partDesc) {
		this.partDesc = partDesc;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
