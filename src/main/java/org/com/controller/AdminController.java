package org.com.controller;

import java.util.Optional;

import javax.persistence.GeneratedValue;

import org.com.dao.AdminRepository;
import org.com.model.Admin;
import org.com.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	AdminRepository dao;
	
	private AdminService adminService;
	
	@PostMapping("/addAdmin")
	public Admin addAdmin(@RequestBody Admin admin) {
		Optional<Admin> findById= dao.findById(admin.getId());
		if(!findById.isPresent()) {
			dao.save(admin);
			return admin;
		} else 
			return null;
	}
}
