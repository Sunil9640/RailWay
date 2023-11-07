package com.smart.daolayer;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.smart.entities.Contact;
import com.smart.entities.User;

public interface contactDao  extends JpaRepository<Contact, Integer>{
	
	//pagenation
	// page is a sub list of a list object.
	
	@Query("from Contact as c where c.user.id =:customid")
	public Page<Contact> findcontactsbyUser(@Param("customid")int userId,Pageable pageable);
	
	
	

}
