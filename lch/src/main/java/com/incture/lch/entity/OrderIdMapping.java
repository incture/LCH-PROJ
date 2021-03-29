package com.incture.lch.entity;

import java.sql.Date;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="T_ORDER_ID_MAPPING")
public class OrderIdMapping 
{
	@Id
	@Column(name="REQUEST ID")
	private String requestId;
	
	@Column(name="ORDER ID")
	private String orderIds;
	
	@Column(name="CREATED DATE")
	private LocalDate createdDate;
	
	@Column(name="CREATED BY")
	private String createdBy;

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getOrderIds() {
		return orderIds;
	}

	public void setOrderIds(String orderIds) {
		this.orderIds = orderIds;
	}

	public LocalDate getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDate createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	

}
