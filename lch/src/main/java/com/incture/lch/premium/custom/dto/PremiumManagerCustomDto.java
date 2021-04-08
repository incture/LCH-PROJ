package com.incture.lch.premium.custom.dto;

import com.incture.lch.dto.PremiumFreightDto1;
import com.incture.lch.dto.TaskDetailsDto;

public class PremiumManagerCustomDto
{
	private PremiumFreightDto1 premiumFreightDto1;
	private TaskDetailsDto taskDetailsDto;
	
	public TaskDetailsDto getTaskDetailsDto() {
		return taskDetailsDto;
	}
	public void setTaskDetailsDto(TaskDetailsDto taskDetailsDto) {
		this.taskDetailsDto = taskDetailsDto;
	}
	public PremiumFreightDto1 getPremiumFreightDto1() {
		return premiumFreightDto1;
	}
	public void setPremiumFreightDto1(PremiumFreightDto1 premiumFreightDto1) {
		this.premiumFreightDto1 = premiumFreightDto1;
	}
	}
