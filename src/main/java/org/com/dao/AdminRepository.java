package org.com.dao;

import java.util.List;

import org.com.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("adminRepository")
public interface AdminRepository extends JpaRepository<Admin, Integer>{
			
}
