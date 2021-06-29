package com.easytocourse.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.easytocourse.model.Role;
import com.easytocourse.model.User;
import com.easytocourse.repository.RoleRepository;
import com.easytocourse.repository.UserRepository;

@RestController
public class RolesController {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private RoleRepository roleRepo;

	@GetMapping("/users/{userId}/roles")
	public List<Role> getContactByuserId(@PathVariable int userId) throws Exception {

		if (!userRepo.existsById(userId)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Foo Not Found");
		}

		return roleRepo.findByUserId(userId);
	}

	@PostMapping("/users/{userId}/roles")
	public User addAssignment(@PathVariable int userId, @RequestBody Role role) throws Exception {
		return userRepo.findById(userId).map(user -> {
			user.addRole(role);
			return userRepo.save(user);
		}).orElseThrow(() -> new Exception("user not found!"));
	}

	@PutMapping("/users/{userId}/roles/{roleid}")
	public Role updateAssignment(@PathVariable int userId, @PathVariable int roleid, @RequestBody Role updatedrole)
			throws Exception {

		if (!userRepo.existsById(userId)) {
			throw new Exception("user not found!");
		}

		return roleRepo.findById(roleid).map(role -> {
			role.setName(updatedrole.getName());

			return roleRepo.save(role);
		}).orElseThrow(() -> new Exception("Assignment not found!"));
	}

	@DeleteMapping("/users/{userId}/roles/{roleid}")
	public String deleteAssignment(@PathVariable int userId, @PathVariable int roleid) throws Exception {

		if (!userRepo.existsById(userId)) {
			throw new Exception("user not found!");
		}

		return roleRepo.findById(roleid).map(role -> {
			roleRepo.delete(role);
			return "Deleted Successfully!";
		}).orElseThrow(() -> new Exception("Contact not found!"));
	}
}
