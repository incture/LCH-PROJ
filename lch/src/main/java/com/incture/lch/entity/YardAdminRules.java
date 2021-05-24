package com.incture.lch.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "T_YARD_ADMIN_RULES")
public class YardAdminRules {

	@Id
	@Column(name = "ID", nullable = false)
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Long id;
	
	@Column(name = "SUPPLIER_ID")
	private String supplierId;
	
	@Column(name = "PART_NUM")
	private String partNum;
	
	@Column(name = "DESTINATION")
	private String destination;
	
	@Column(name = "DESTINATION_ID")
	private String destinationId;
	
//	@Column(name = "YARD_ID")
//	private String yardId;
	
	@Column(name = "YARD_LOCATION")
	private String yardLocation;
	
	@Column(name = "YARD_ADMIN")
	private String yardAdmin;
	
	@Column(name = "MATERIAL_HANDLER")
	private String materialHandler;
	
	@Column(name = "SECURITY_GUARD")
	private String securityGuard;
	
	@Column(name = "TRANSPORT_MANAGER")
	private String transportManager;
	
	@Column(name = "INTERNAL_USER")
	private String internalUser;
	
	@Column(name = "EX")
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
