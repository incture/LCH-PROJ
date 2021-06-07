package com.incture.lch.dto;

import java.util.List;

import com.incture.lch.entity.AdhocOrderWorkflow;

public class AdhocRoadmapDto 
{
	private String orderId;
	private List<AdhocOrderWorkflow> orderStatusLists;
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public List<AdhocOrderWorkflow> getOrderStatusLists() {
		return orderStatusLists;
	}
	public void setOrderStatusLists(List<AdhocOrderWorkflow> orderStatusLists) {
		this.orderStatusLists = orderStatusLists;
	}
	
	
	
}
