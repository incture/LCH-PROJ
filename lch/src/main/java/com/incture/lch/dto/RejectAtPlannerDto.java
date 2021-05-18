package com.incture.lch.dto;

import java.util.List;

public class RejectAtPlannerDto {
	private List<String> adhocorderIds;
	
	private String comment;

	public List<String> getAdhocorderIds() {
		return adhocorderIds;
	}

	public void setAdhocorderIds(List<String> adhocorderIds) {
		this.adhocorderIds = adhocorderIds;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
	
	

}
