package org.com.controller;

import java.util.List;
import java.util.Optional;
import org.com.dao.CustomerRepository;
import org.com.exception.RecordNotFoundException;
import org.com.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usr")
@CrossOrigin(value="http://localhost:4200",maxAge=80)
public class CustomerController {
	@Autowired
	CustomerRepository dao;

	@GetMapping("/allUser")
	public List<Customer> getAllCustomer() {
		return dao.findAll();
	}
	
	@RequestMapping("/searchUser/{id}")
	@ExceptionHandler(RecordNotFoundException.class)
	public ResponseEntity<?> findProduct1(@PathVariable("id") int uid) {
		Optional<Customer> findById = dao.findById(uid);
		try {
			if (findById.isPresent()) {
				Customer usr = findById.get();
				return new ResponseEntity<Customer>(usr, HttpStatus.OK);
			} else
				throw new RecordNotFoundException("Record not Found Exception!!!");
		} catch (RecordNotFoundException e) {

			return new ResponseEntity<Customer>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/newUser")
	@ExceptionHandler(RecordNotFoundException.class)
	public ResponseEntity<Customer> addUser(@RequestBody Customer usr) {
		Optional<Customer> findById = dao.findById(usr.getId());
		try {
			if (!findById.isPresent()) {
				dao.save(usr);
				return new ResponseEntity<Customer>(usr, HttpStatus.OK);
			} else
				throw new RecordNotFoundException("Record Already Present!!");
		} catch (RecordNotFoundException e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/deleteUser/{id}")
	@ExceptionHandler(RecordNotFoundException.class)
	public String deleteUser(@PathVariable("id") int uid) {
		Optional<Customer> findById = dao.findById(uid);
		try {
			if (findById.isPresent()) {
				dao.deleteById(uid);
				return "User removed!!";
			} else
				throw new RecordNotFoundException("Record Not Found!!");
		} catch (RecordNotFoundException e) {
			return e.getMessage();
		}
	}

	@GetMapping("/getCount")
	public String getCount() {
		return "Total No. of Records are : " + dao.count();
	}

	@PutMapping("/updateUser")
	public String updateUser(@RequestBody Customer usr) {
		Optional<Customer> findById = dao.findById(usr.getId());
		if (findById.isPresent()) {
			dao.save(usr);
			return "User details have been Updated!!";
		}
		return "User not Found!!";
	}

}
