package com.incture.lch.premium.custom.dto;

import java.util.List;

import com.incture.lch.dto.PremiumRequestDto;
import com.incture.lch.dto.UserDetailsDto;

public class PremiumRequestUserInfoCustomDto 
{
	private List<PremiumRequestDto> premiumRequestDtoList;
	private UserDetailsDto userDetailInfo;
	public List<PremiumRequestDto> getPremiumRequestDtoList() {
		return premiumRequestDtoList;
	}
	public void setPremiumRequestDtoList(List<PremiumRequestDto> premiumRequestDtoList) {
		this.premiumRequestDtoList = premiumRequestDtoList;
	}
	public UserDetailsDto getUserDetailInfo() {
		return userDetailInfo;
	}
	public void setUserDetailInfo(UserDetailsDto userDetailInfo) {
		this.userDetailInfo = userDetailInfo;
	}
	@Override
	public String toString() {
		return "PremiumRequestUserInfoCustomDto [premiumRequestDtoList=" + premiumRequestDtoList + ", userDetailInfo="
				+ userDetailInfo + "]";
	}
	
	
	

}
