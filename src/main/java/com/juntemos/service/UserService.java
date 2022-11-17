package com.juntemos.service;

import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.juntemos.models.User;
import com.juntemos.repository.UserRepository;


@Service
public class UserService {
	
    @Autowired
    private UserRepository userRepository;
    
	@Autowired
	PasswordEncoder encoder;
	
	@Async
	@Transactional
	public User editUser(User u) throws Exception {

		Optional<User> response = userRepository.findById(u.getId());
		
	    if (response.isPresent()) {
	    	User user = response.get();

	    	if (u.getUsername() != null && !u.getUsername().isEmpty()) {
				user.setUsername(u.getUsername());
			}
            if (u.getName() != null && !u.getName().isEmpty()) {
				user.setName(u.getName());
			}
            if (u.getLastname() != null && !u.getLastname().isEmpty()) {
				user.setLastname(u.getLastname());
			}
            if (u.getName() != null && !u.getName().isEmpty()) {
				user.setName(u.getName());
			}
            if (u.getName() != null && !u.getName().isEmpty()) {
				user.setName(u.getName());
			}
            if (u.getDescription() != null && !u.getDescription().isEmpty()) {
				user.setDescription(u.getDescription());
			}
            if (u.getBirthday() != null) { // Nacimiento
				user.setBirthday(u.getBirthday());
			}
            if (u.getCountry() != null && !u.getCountry().isEmpty()) {
				user.setCountry(u.getCountry());
			}
            if (u.getPhone() != null && !u.getPhone().isEmpty()) {
				user.setPhone(u.getPhone());
			}   
            if (u.getEmail() != null && !u.getEmail().isEmpty() && u.getEmail().contains("@")) {
				user.setEmail(u.getEmail());
			}
            if (u.getPassword() != null && !u.getPassword().isEmpty()) {
				user.setPassword(encoder.encode(u.getPassword()));
			} 
	    	
	        userRepository.save(user);
	        
	        } else {
	        	throw new Exception("Usuario no encontrado.");
	        }
	    
		return userRepository.findById(u.getId()).get();
	}
	

}
