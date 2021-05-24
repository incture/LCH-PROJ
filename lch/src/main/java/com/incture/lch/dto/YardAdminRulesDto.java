package com.incture.lch.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class YardAdminRulesDto {
	
	private Long id;
	
	private String supplierId;
	
	private String partNum;
	
	private String destination;

	private String destinationId;
	
	//private String yardId;

	private String yardLocation;

	private String yardAdmin;

	private String materialHandler;
	
	private String securityGuard;
	
	private String transportManager;
	
	private String internalUser;
	
	private String ex;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	public String getPartNum() {
		return partNum;
	}

	public void setPartNum(String partNum) {
		this.partNum = partNum;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getDestinationId() {
		return destinationId;
	}

	public void setDestinationId(String destinationId) {
		this.destinationId = destinationId;
	}

	public String getYardLocation() {
		return yardLocation;
	}

	public void setYardLocation(String yardLocation) {
		this.yardLocation = yardLocation;
	}

	public String getYardAdmin() {
		return yardAdmin;
	}

	public void setYardAdmin(String yardAdmin) {
		this.yardAdmin = yardAdmin;
	}

	public String getMaterialHandler() {
		return materialHandler;
	}

	public void setMaterialHandler(String materialHandler) {
		this.materialHandler = materialHandler;
	}

	public String getSecurityGuard() {
		return securityGuard;
	}

	public void setSecurityGuard(String securityGuard) {
		this.securityGuard = securityGuard;
	}

	public String getTransportManager() {
		return transportManager;
	}

	public void setTransportManager(String transportManager) {
		this.transportManager = transportManager;
	}

	public String getInternalUser() {
		return internalUser;
	}

	public void setInternalUser(String internalUser) {
		this.internalUser = internalUser;
	}

	public String getEx() {
		return ex;
	}

	public void setEx(String ex) {
		this.ex = ex;
	}

	
	
}
