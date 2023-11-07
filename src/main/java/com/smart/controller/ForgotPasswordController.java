package com.smart.controller;

import java.security.Principal;
import java.util.Random;

import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.smart.services.Service;

@Controller
public class ForgotPasswordController {
	
	@Autowired
	private Service service;
	
	private static final Logger log = Logger.getLogger(ForgotPasswordController.class);
	
	@RequestMapping("/forgot")
	public String openEmailForm() {
		return "forgotEmail";
	}
	
	@PostMapping("/sendOtp")
	public String EmailOtp(@Param("email") String email,HttpSession session) {
	
		
		System.out.println(email);
		
		// generating OTP
		Random random =new Random();
		int otp = random.nextInt(999999);
		String otp1= String.valueOf(otp);
		System.out.println(otp);
	String	subject="validating OTP for User contact manager";
	
	session.setAttribute("myotp", otp1);
	session.setAttribute("Useremail",email );
		
	  this.service.SendEmail(email, subject, otp1);
		
		return "verify_otp";
	}
	
	//verifying opt:
	
	@PostMapping("/verifyOpt")
	public String verifyOtp(@Param ("otp") String otp,HttpSession session){
		 String Myopt=(String   )session.getAttribute("myotp");
		 String Email=(String)session.getAttribute("Useremail");
		
		 if(otp==Myopt) {
			 System.out.print("If block");
			 log.info("opt inside if block ------------------> "+ otp);
			 log.error(Email);
			 return "Password_changeForm";
			 
			 
		 }else {
			 session.setAttribute("message", Email);
			 System.out.print("else block");
			 log.info("opt inside else  block ------------------> "+ otp);
			 return "verify_otp";
			 
		 }
		 
	}

}
