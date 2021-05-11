package com.incture.lch.dto;

public class AccountingDetailsDto {

	private String companyCode;

	private String costCenter;

	private String glAcc;

	private String profitCenter;

	private String s4Debit;
	

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getCostCenter() {
		return costCenter;
	}

	public void setCostCenter(String costCenter) {
		this.costCenter = costCenter;
	}

	public String getGlAcc() {
		return glAcc;
	}

	public void setGlAcc(String glAcc) {
		this.glAcc = glAcc;
	}

	public String getProfitCenter() {
		return profitCenter;
	}

	public void setProfitCenter(String profitCenter) {
		this.profitCenter = profitCenter;
	}

	public String getS4Debit() {
		return s4Debit;
	}

	public void setS4Debit(String s4Debit) {
		this.s4Debit = s4Debit;
	}

	public String getPlantCode() {
		return plantCode;
	}

	public void setPlantCode(String plantCode) {
		this.plantCode = plantCode;
	}

	private String plantCode;

}
