package com.smart.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.smart.entities.User;
import com.smart.services.Service;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;


@Controller
public class Homecontroller {
	
	@Autowired
	Service service;

	@GetMapping("/home")
	public String home(Model model) {
		model.addAttribute("title", "Contact");
		return "home";
	}

	@GetMapping("/about")
	public String about() {

		return "about";
	}

	@GetMapping("/signup")
	public String signup(Model model) {
		model.addAttribute("user", new User());

		return "signup";
	}

	@PostMapping("/doregister")
	public String register(@Valid @ModelAttribute("user") User user,BindingResult bindresult,
			@RequestParam(value = "checkme", defaultValue = "false") boolean checkme,HttpSession session,Model model) {
      
		
		try {
			

			if (!checkme) {
				System.out.println("you have not checked the terms and conditions");
				throw new Exception("you have not checked the terms and conditions");
			}
			
			if(bindresult.hasErrors()) {
				System.out.println(bindresult.toString());
				model.addAttribute("user", user);
				return "signup";
			}else {

			User result = this.service.Savinguser(user);
			model.addAttribute("user", new User());
			session.setAttribute("message", new com.smart.helper.Message("Succussfully register", "alert-success"));
			
			 return "signup";
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("user", user);
			session.setAttribute("message", new com.smart.helper.Message("something went wrong!!" + e.getMessage(), "alert-danger"));
			
			 return "signup";
		}	
	}
	
    @GetMapping("/loginform")
	public String Login() {
		return "login";
	}
	}

