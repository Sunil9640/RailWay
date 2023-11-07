package com.smart.services;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.smart.daolayer.UserDao;
import com.smart.entities.User;

@org.springframework.stereotype.Service
public class Service {
	@Autowired
	UserDao dao;
	
	@Autowired
     BCryptPasswordEncoder passwordEncoder;
	
	public User Savinguser(User user) {
	
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			user.setRole("USER");
			user.setEnable(true);
			user.setImageUrl("flower.jpg");
			User creatinguser = this.dao.save(user);
			return creatinguser;
		
	}
	

	public void SendEmail( String to,String subject, String message) {
		 
		String from="sunilyadav202396@gmail.com";
       
    	String host="smtp.gmail.com";
         
    	Properties properties=System.getProperties();
    	
    	properties.put("mail.smtp.host", host);
    	properties.put("mail.smtp.port","465");
    	properties.put("mail.smtp.ssl.enable","true");
    	properties.put("mail.smtp.auth","true");
    	
    	
    Session session=Session.getInstance(properties, new Authenticator() {
    	    
    		@Override
    		protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
    			return new javax.mail.PasswordAuthentication("sunilyadav202396@gmail.com", "hwyx nnwy xycn pxet");
    		}
		});
    session.setDebug(true);
    
    //setting MIMI message:
    
    MimeMessage m= new MimeMessage(session);
    try {
    	
    	//from 
		m.setFrom(from);
		
		//to receiver:
		
		m.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
		
		//adding subject 
		m.setSubject(subject);
		
		//adding text to message
		m.setText(message);
		
		//sending the mail
		Transport.send(m);
		
		System.out.println("email sent successfully........");
		
		
	} catch (MessagingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    

	}
	
	
}
