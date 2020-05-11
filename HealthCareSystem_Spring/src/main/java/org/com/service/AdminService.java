package org.com.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.com.dao.AdminRepository;
import org.com.exception.RecordNotFoundException;
import org.com.model.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;


@Service
public class AdminService {
	
	@Autowired
	AdminRepository dao;
	
	//Add ADMIN
	public ResponseEntity<Admin> saveAdmin(Admin admin) {
		Optional<Admin> findById=dao.findById(admin.getId());
		try{
			if(!findById.isPresent()) {
				dao.save(admin);
				return new ResponseEntity<Admin>(admin, HttpStatus.OK);
			}
			else
				throw new RecordNotFoundException("Record already present...");
		}
		catch(RecordNotFoundException e){
			return new ResponseEntity<Admin>(admin, HttpStatus.NOT_FOUND);
		}
	}

		// SHOW ALL ADMINS
		public List<Admin> showAllAdmin() {
			return dao.findAll();
		}
		
		//SEARCH ADMIN
		@ExceptionHandler(RecordNotFoundException.class)
		public ResponseEntity<?> findAdmin(@PathVariable("id") int uid) {
			Optional<Admin> findById = dao.findById(uid);
			try {
				if (findById.isPresent()) {
					Admin admin = findById.get();
					return new ResponseEntity<Admin>(admin, HttpStatus.OK);
				} else
					throw new RecordNotFoundException("Record not found!!");
			} catch (RecordNotFoundException e) {

				return new ResponseEntity<Admin>(HttpStatus.NOT_FOUND);
			}
		}
		
		//DELETE ADMIN
		public String removeAdmin(@PathVariable("id") int uid) {
			Optional<Admin> findById = dao.findById(uid);
			if (findById.isPresent()) {
				dao.deleteById(uid);
				return "Admin " + dao.findById(uid) + " Removed!!!";
			}
			return "Admin Not Found!!!";
		}

		
		//COUNT ADMINS
		public String getTotalCount() {
			return "Total no of records are: " + dao.count();
		}

		
		//UPDATE ADMIN
		public String updateAdmin(@RequestBody Admin admin) {
			Optional<Admin> findById = dao.findById(admin.getId());
			if (findById.isPresent()) {
				dao.save(admin);
				return "Admin Detail Updated!!!";
			}
			return "Admin Not Found!!!";
		}
}
