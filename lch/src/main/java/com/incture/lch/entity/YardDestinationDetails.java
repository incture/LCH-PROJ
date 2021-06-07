package com.incture.lch.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table (name= "T_YARD_DESTINATION_DETAILS")
public class YardDestinationDetails {
	

	@Id
	@Column(name = "DEST_ID")
	private String destId;
	
	@Column(name = "DEST_DESC")
	private String destDesc;

	public String getDestId() {
		return destId;
	}

	public void setDestId(String destId) {
		this.destId = destId;
	}

	public String getDestDesc() {
		return destDesc;
	}

	public void setDestDesc(String destDesc) {
		this.destDesc = destDesc;
	}
	
	

}
