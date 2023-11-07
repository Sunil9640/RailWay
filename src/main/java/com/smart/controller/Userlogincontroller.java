package com.smart.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;


import com.smart.daolayer.UserDao;
import com.smart.daolayer.contactDao;
import com.smart.entities.Contact;
import com.smart.entities.EmailEntity;
import com.smart.entities.User;
import com.smart.helper.Message;
import com.smart.services.Service;

import lombok.extern.java.Log;

@Controller
@RequestMapping("/user")
public class Userlogincontroller{
	
	 private static final Logger log = Logger.getLogger(Userlogincontroller.class.getName());	
	@Autowired
	private UserDao dao;
	
	@Autowired
	 private Service service;
	
	@Autowired
	private contactDao contactDao;
	
	
	
	
	@ModelAttribute
	public void addcommonData(Model model,Principal principal) {
		
		String name = principal.getName();
		User user = dao.getUserByUserName(name);
		model.addAttribute("username", user);
	
		/* System.out.println("User name"+ user); */
		
	}
	
	// donate us API payment gateway
	
	@GetMapping("/payment")
	public String DonateUs() {
		return "normal/payment";
	}
	
	//postmapping for paymentorder
	
	@PostMapping("/process_order")
	@ResponseBody
	public String paymentOrder() {
		
		log.info("inside the payment");
			return "done";
			
	}
	
	
	//getting email page using get method:
	
	@GetMapping("/email")
	public String Demo() {
		return "normal/mail";
	}
	
	//sending mail using post mapping
	

	@PostMapping("/sendEmail")
	public  String SendEmail (@ModelAttribute EmailEntity emailentity){
		System.out.println("this method hit inside post method");
		
	  this.service.SendEmail(emailentity.getTo(),emailentity.getSubject(),emailentity.getMessage());
		System.out.println(emailentity);
		return "normal/mail";
		
	}
	
	@RequestMapping("/viewcontacts/{page}")
	public String viewcontacts(@PathVariable("page") Integer page,Model model,Principal principal) {
		String currentUser_name = principal.getName();
		
		User user = dao.getUserByUserName(currentUser_name);
	
		log.info("Email ------------------> "+user.getEmail());
		log.error("Email ------------------> "+user.getEmail());
		
		  /* List<Contact> contacts = user.getContacts();
		     model.addAttribute("contacts",contacts);
		     System.out.println(contacts);   */
		   
		   
			Pageable pageable= PageRequest.of(page, 3);
		
		      Page<Contact> findcontactsbyUser = this.contactDao.findcontactsbyUser(user.getId(),pageable);
		      model.addAttribute("contacts", findcontactsbyUser);
				model.addAttribute("currentpage", page);
				 model.addAttribute("totalpages", findcontactsbyUser.getTotalPages());
			  
				 
		           return "normal/viewcontacts";
	}
	

	
	// deleting the single contact handler
	
	@GetMapping("/delete/{cid}")
	public String ContactDelete(@PathVariable("cid") Integer cId,HttpSession session,Model model) {
		
		            //     this.contactDao.deleteById(cId);
		      
				
		 Contact contact= this.contactDao.findById(cId).get();
					 System.out.println(contact);
					  
					    contact.setUser(null);
					    
					 this.contactDao.delete(contact);  
					 
		
		session.setAttribute("message", new Message("contact deleted successfully", "success"));
		
		 return "redirect:/user/viewcontacts/0";
		 
	}
	
	
	// opening updating handler the form .....
	
	@GetMapping("/update/{cid}")
	public String update(@PathVariable("cid") Integer cId,@ModelAttribute Contact contact,Model model) {
		
	
		Contact contact2 = this.contactDao.findById(cId).get();
		System.out.println(contact2.getEmail());
        
		
		
		model.addAttribute("contact", contact2);
		
		return "normal/update";
	}
	
	// post mapping handler for update
	
	@PostMapping("/process-update")
	public String postmethodhandler(@ModelAttribute Contact contact,Principal principal) {
	  String name=	principal.getName();
	   User user = dao.getUserByUserName(name);
	   contact.setUser(user);
		
		contactDao.save(contact);
		return "normal/update";
	}
	 
	
	

	// normal home handler
	@RequestMapping("/page")
	public String Home() {
		return "normal/home";
	}

	@RequestMapping("/dash")
	public  String dashboard(Model model,Principal principal) {
		
		return "normal/dashboard";
	}
	
	// userAdd handler
		@RequestMapping("/addcontact")
		public String addUserContact(Model model) {
			model.addAttribute("contact", new Contact());
			
			return "normal/Addcontact";
			
		}
	
		
		// adding contact handler
		
		@PostMapping("/process-contact")
		public String userprocess_contact(@Valid @ModelAttribute("contact") Contact contact,
				// @RequestParam("image") MultipartFile file,
				BindingResult bindingResult,
				Principal principal,Model model,HttpSession httpSession){
			
			
			if(bindingResult.hasErrors()) {
				System.out.println(bindingResult.toString());
				
				model.addAttribute("contact", new Contact());
				return "normal/Addcontact";
			}
			try {
			String name = principal.getName();
			User userbyemail = this.dao.getUserByUserName(name);
			contact.setUser(userbyemail);
			userbyemail.getContacts().add(contact);
			/*
			if(mulfile.isEmpty()) {
				System.out.println("empty file");
			}else {
			*/
			/*
			 * contact.setImage(mulfile.getOriginalFilename()); File file = new
			 * ClassPathResource("static/image/userfiles").getFile(); Path path =
			 * Paths.get(file.getAbsolutePath()+file.separator+mulfile.getOriginalFilename()
			 * ); Files.copy(mulfile.getInputStream(), path,
			 * StandardCopyOption.REPLACE_EXISTING);
			 * 
			 */
			
	/*   	try {
			String image = contact.getImage();
			 File file2 = new ClassPathResource("static/image/userfiles").getFile();
			 Path path = Paths.get(file2.getAbsolutePath()+file2.separator+file.getOriginalFilename());
			 Files.copy(file.getInputStream(),path ,
					  StandardCopyOption.REPLACE_EXISTING);
			 
			}catch (Exception e) {
				e.printStackTrace();
			}    */
			
			
			this.dao.save(userbyemail);
			System.out.println("user contact saved");
			httpSession.setAttribute("message", new Message("your contact is saved! add new one", "success"));
			
			System.out.println(contact);
			}catch (Exception e) { 
				httpSession.setAttribute("message", new Message("something went wrong!", "danger"));
				e.printStackTrace();
			}
			return "normal/Addcontact";
		}
		
		
}
