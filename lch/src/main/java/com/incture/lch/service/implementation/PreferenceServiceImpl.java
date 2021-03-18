package com.incture.lch.service.implementation;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.incture.lch.dao.PreferenceDao;
import com.incture.lch.dto.PreferenceDto;
import com.incture.lch.service.PreferenceService;
@Service
@Transactional
public class PreferenceServiceImpl implements PreferenceService {

	@Autowired
	private PreferenceDao preferenceDao;
	
	@Override
	public List<PreferenceDto> getPreference(String userId) 
	{
		try {
			return preferenceDao.getPreference(userId);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}

	@Override
	public String setPreference(List<PreferenceDto> preferenceDto, String userId) {
		return preferenceDao.setPreference(preferenceDto, userId);
	}

}
