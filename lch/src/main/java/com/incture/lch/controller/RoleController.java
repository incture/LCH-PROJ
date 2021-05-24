package com.incture.lch.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.incture.lch.dao.PremiumRoleDao;
import com.incture.lch.dao.RoleDao;
import com.incture.lch.dao.YardRoleDao;
import com.incture.lch.dto.PremiumRoleDto;
import com.incture.lch.dto.RoleDto;
import com.incture.lch.dto.YardRoleDto;

@RestController
@CrossOrigin
@RequestMapping(value = "/lchRole", produces = "application/json")
public class RoleController 
{
	
	@Autowired
	private YardRoleDao yardRoleDao;
	@Autowired
	private RoleDao roledao;
	
	@Autowired
	private PremiumRoleDao premiumRoleDao;

	//get role for Adhoc
	@RequestMapping(value = "/getRole/{userId}", method = RequestMethod.GET, consumes = { "application/json" })
	@ResponseBody
	public  List<String> getRole(@PathVariable String userId)
	{
		return roledao.getRole(userId);
	}
	
	//Add role for Adhoc
	@RequestMapping(value="/addRole", method = RequestMethod.POST, consumes={"application/json"})
	@ResponseBody
	public String addRole(@RequestBody List<RoleDto> roleDtos)
	{
		System.out.println(roleDtos);
		return roledao.addRole(roleDtos);
	}
	
	//Get roles for Premium
	@RequestMapping(value = "/getPremiumRole/{userId}", method = RequestMethod.GET, consumes = { "application/json" })
	@ResponseBody
	public  List<String> getPremiumRole(@PathVariable String userId)
	{
		return premiumRoleDao.getPremiumRole(userId);
	}
	
	//Adding roles for Premium
	@RequestMapping(value="/addPremiumRole", method = RequestMethod.POST, consumes={"application/json"})
	@ResponseBody
	public String addPremiumRole(@RequestBody List<PremiumRoleDto> roleDtos)
	{
		System.out.println(roleDtos);
		return premiumRoleDao.addPremiumRole(roleDtos);
	}


	@RequestMapping(value = "/getYardRole/{userId}", method = RequestMethod.GET, consumes = { "application/json" })
	@ResponseBody
	public  List<String> getYardRole(@PathVariable String userId)
	{
		return yardRoleDao.getYardRole(userId);
	}
	
	@RequestMapping(value="/addYardRole", method = RequestMethod.POST, consumes={"application/json"})
	@ResponseBody
	public String addYardRole(@RequestBody List<YardRoleDto> roleDtos)
	{
		System.out.println(roleDtos);
		return yardRoleDao.addYardRole(roleDtos);
	}
}
