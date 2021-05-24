package com.incture.lch.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SecurityGuardKpiDto {

	private String kpi;
	private int count;
	public String getKpi() {
		return kpi;
	}
	public void setKpi(String kpi) {
		this.kpi = kpi;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
	/*int notArrivedCount;
	int atDockCount;
	int inYardCount;
	int atGateCount;
	int outGateCount;
	
	public int getNotArrivedCount() {
		return notArrivedCount;
	}
	public void setNotArrivedCount(int notArrivedCount) {
		this.notArrivedCount = notArrivedCount;
	}
	public int getAtGateCount() {
		return atGateCount;
	}
	public void setAtGateCount(int atGateCount) {
		this.atGateCount = atGateCount;
	}
	public int getAtDockCount() {
		return atDockCount;
	}
	public void setAtDockCount(int atDockCount) {
		this.atDockCount = atDockCount;
	}
	public int getInYardCount() {
		return inYardCount;
	}
	public void setInYardCount(int inYardCount) {
		this.inYardCount = inYardCount;
	}
	public int getOutGateCount() {
		return outGateCount;
	}
	public void setOutGateCount(int outGateCount) {
		this.outGateCount = outGateCount;
	}
	*/

}
