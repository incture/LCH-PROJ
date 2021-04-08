package com.incture.lch.dto;

import javax.persistence.Column;

public class PremiumOrderAccountingDetailsDto {
	private String orderId;

	private String debitBy;
	private String debitAmount;

	private String debitCurrency;

	private String quantity;

	private String proNumber;

	private String trailerNumber;

	private String plannerEmail;

	private String debitPostStatus;

	private String debitTo;

	private String glCode;

	private String profitCenter;

	private String carrier;

	private String comment;

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
