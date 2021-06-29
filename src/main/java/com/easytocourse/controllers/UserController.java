package com.easytocourse.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.easytocourse.model.User;
import com.easytocourse.repository.RoleRepository;
import com.easytocourse.repository.UserRepository;

@RestController
public class UserController {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private RoleRepository roleRepo;

	@GetMapping("/Users")
	public List<User> getAllusers() {
		return userRepo.findAll();
	}

	@GetMapping("/Users/{id}")
	public User getUserByID(@PathVariable int id) throws Exception {
		Optional<User> optUser = userRepo.findById(id);
		if (optUser.isPresent()) {
			return optUser.get();
		} else {
			throw new Exception("User not found with id " + id);
		}
	}

	@PostMapping("/Users")
	public User createUser(@RequestBody User User) {

		User.setPassword(new BCryptPasswordEncoder().encode(User.getPassword()));
		return userRepo.save(User);
	}

	@PutMapping("/Users/{id}")
	public User updateUser(@PathVariable int id, @RequestBody User UserUpdated) throws Exception {
		return userRepo.findById(id).map(user -> {
			user.setFirstName(UserUpdated.getFirstName());
			user.setLastName(UserUpdated.getLastName());
			user.setPassword(new BCryptPasswordEncoder().encode(UserUpdated.getPassword()));
			user.setRoles(UserUpdated.getRoles());
			return userRepo.save(user);
		}).orElseThrow(() -> new Exception("Student not found with id " + id));

	}

	@DeleteMapping("/Users/{id}")
	public String deleteUser(@PathVariable int id) throws Exception {
		return userRepo.findById(id).map(User -> {
			userRepo.delete(User);
			return "Delete Successfully!";
		}).orElseThrow(() -> new Exception("User not found with id " + id));
	}

}
