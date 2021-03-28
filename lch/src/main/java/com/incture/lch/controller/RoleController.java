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

import com.incture.lch.dao.RoleDao;
import com.incture.lch.dto.RoleDto;

@RestController
@CrossOrigin
@RequestMapping(value = "/lchRole", produces = "application/json")
public class RoleController 
{
	@Autowired
	private RoleDao roledao;
	@RequestMapping(value = "/getRole/{userId}", method = RequestMethod.GET, consumes = { "application/json" })
	@ResponseBody
	public  List<String> getRole(@PathVariable String userId)
	{
		return roledao.getRole(userId);
	}
	
	@RequestMapping(value="/addRole", method = RequestMethod.POST, consumes={"application/json"})
	@ResponseBody
	public String addRole(@RequestBody List<RoleDto> roleDtos)
	{
		System.out.println(roleDtos);
		return roledao.addRole(roleDtos);
	}
}
