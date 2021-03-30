package com.incture.lch.util;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "SEQUENCE_NUMBER_REQUEST_ID")
@NamedQuery(name = "SequenceNumberRequestId.getAll", query = "SELECT seq FROM SequenceNumberRequestId seq")
public class SequenceNumberRequestId
{
	@Id
	@Column(name = "REFERENCE_CODE")
	private String referenceCode;

	@Column(name = "RUNNING_NUMBER")
	private Integer runningNumber;

	

	public SequenceNumberRequestId()
	{
		
	}
	public SequenceNumberRequestId(String referenceCode, Integer runningNumber) 
	{
		this.referenceCode = referenceCode;
		this.runningNumber = runningNumber;
	}

	public String getReferenceCode() {
		return referenceCode;
	}

	public void setReferenceCode(String referenceCode) {
		this.referenceCode = referenceCode;
	}

	public Integer getRunningNumber() {
		return runningNumber;
	}

	public void setRunningNumber(Integer runningNumber) {
		this.runningNumber = runningNumber;
	}


}
