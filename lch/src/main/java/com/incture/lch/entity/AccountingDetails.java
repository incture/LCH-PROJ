package com.incture.lch.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name= "T_ACCOUNTING_DETAILS")
public class AccountingDetails 
{
	@Id
	@Column(name="ID")
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy = "uuid")
	private String id;
	
	@Column(name="COMPANY_CODE")
	private String companyCode;
	
	@Column(name="COST_CENTER")
	private String costCenter;
	
	@Column(name="GL_ACC")
	private String glAcc;
	
	@Column(name="PROFIT_CENTER")
	private String profitCenter;
	
	@Column(name="S4_DEBIT")
	private String s4Debit;
	
	@Column(name="PLANT_CODE")
	private String plantCode;

	public String getId() {
		return id;
	}

	public void setOrderId(String id) {
		this.id = id;
	}

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
	
	
}
