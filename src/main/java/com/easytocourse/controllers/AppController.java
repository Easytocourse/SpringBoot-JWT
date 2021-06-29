package com.easytocourse.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.easytocourse.UserUtility.JwtUtil;
import com.easytocourse.model.AuthRequest;
import com.easytocourse.model.User;
import com.easytocourse.repository.UserRepository;

@RestController
public class AppController {

	@Autowired
	private UserRepository userRepo;

	@PostMapping("/register")
	public String processRegister(@RequestBody User user) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);

		userRepo.save(user);

		return "register_success";
	}

	@GetMapping("/users")
	public List<User> listUsers() {
		return userRepo.findAll();

	}
	
	String name="";
	@ModelAttribute("foo")
	public Void foo() {
		Authentication auth= (Authentication) SecurityContextHolder.getContext().getAuthentication();
		name = auth.getName();

		return null;

	}	
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	JwtUtil jwtutil;
	
	@PostMapping("/ground")
	public String authenticate(@RequestBody AuthRequest authRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );
        } catch (Exception ex) {
            throw new Exception("inavalid username/password");
        }
        return jwtutil.generateToken(authRequest.getUsername());
    }
	

	@GetMapping("/batting")
	public String batting() {
		return "Welcome to Batting-->" + name;
	}

	@GetMapping("/bowling")
	public String bolwing() {
		return "Welcome to bowling-->" + name;
	}

	@GetMapping("/keeping")
	public String wikectkeeping() {
		return "Welcome to wikcetkeeping-->" + name;
	}

	@GetMapping("/umpiring")
	public String umpiring() {
		return "Welcome to umpring--->" + name;
	}
	
	
	
	
	

}