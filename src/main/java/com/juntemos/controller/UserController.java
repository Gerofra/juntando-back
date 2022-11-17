package com.juntemos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.juntemos.models.User;
import com.juntemos.repository.UserRepository;
import com.juntemos.service.UserService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	UserService userService;
	
	@Async
	@GetMapping("/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> getUser(@PathVariable("id") String id) {			
		return ResponseEntity.ok(userRepository.findById(Long.parseLong(id)));
	}
	
	@PutMapping("/update/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<String> editUser(@RequestBody User user) throws Exception {
		
		try {
			userService.editUser(user);
			return new ResponseEntity<>("Ha sido modificado con éxito.", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("No se han podido realizar los cambios.", HttpStatus.BAD_REQUEST);
		}	
	}
	
}
