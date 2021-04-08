package com.incture.lch.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="T_PREMIUM_ORDER_ACCOUNTING_DETAILS")
public class PremiumOrderAccountingDetails
{
	@Id
	@Column(name="ORDER_ID")
	private String orderId;
	
	@Column(name="DEBIT_BY")
	private String debitBy;
	 
	@Column(name="DEBIT_AMOUNT")
	private String debitAmount;
	
	@Column(name="DEBIT_CURRENCY")
	private String debitCurrency;
	
	@Column(name="QUANTITY")
	private String quantity;
	
	@Column(name="PRO_NUMBER")
	private String proNumber;
	
	@Column(name="TRAILER_NUMBER")
	private String trailerNumber;
	
	@Column(name="PLANNER_EMAIL")
	private String plannerEmail;
	
	@Column(name="DEBIT_POST_STATUS")
	private String debitPostStatus;
	
	@Column(name="DEBIT_TO")
	private String debitTo;
	
	@Column(name="GL_CODE")
	private String glCode;
	
	@Column(name="PROFIT_CENTER")
	private String profitCenter;
	
	@Column(name="CARRIER")
	private String carrier;
	
	@Column(name="COMMENT")
	private String comment;
	
	@Column(name="TOTAL_COST")
	private String totalCost;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getDebitBy() {
		return debitBy;
	}

	public void setDebitBy(String debitBy) {
		this.debitBy = debitBy;
	}

	public String getDebitAmount() {
		return debitAmount;
	}

	public void setDebitAmount(String debitAmount) {
		this.debitAmount = debitAmount;
	}

	public String getDebitCurrency() {
		return debitCurrency;
	}

	public void setDebitCurrency(String debitCurrency) {
		this.debitCurrency = debitCurrency;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getProNumber() {
		return proNumber;
	}

	public void setProNumber(String proNumber) {
		this.proNumber = proNumber;
	}

	public String getTrailerNumber() {
		return trailerNumber;
	}

	public void setTrailerNumber(String trailerNumber) {
		this.trailerNumber = trailerNumber;
	}

	public String getPlannerEmail() {
		return plannerEmail;
	}

	public void setPlannerEmail(String plannerEmail) {
		this.plannerEmail = plannerEmail;
	}

	public String getDebitPostStatus() {
		return debitPostStatus;
	}

	public void setDebitPostStatus(String debitPostStatus) {
		this.debitPostStatus = debitPostStatus;
	}

	public String getDebitTo() {
		return debitTo;
	}

	public void setDebitTo(String debitTo) {
		this.debitTo = debitTo;
	}

	public String getGlCode() {
		return glCode;
	}

	public void setGlCode(String glCode) {
		this.glCode = glCode;
	}

	public String getProfitCenter() {
		return profitCenter;
	}

	public void setProfitCenter(String profitCenter) {
		this.profitCenter = profitCenter;
	}

	public String getCarrier() {
		return carrier;
	}

	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(String totalCost) {
		this.totalCost = totalCost;
	}
	
	
	

}
