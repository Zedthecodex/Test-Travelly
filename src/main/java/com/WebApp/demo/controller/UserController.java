package com.WebApp.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.WebApp.demo.model.User;
import com.WebApp.demo.repository.UserRepository;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	UserRepository userRepository;
	
	@PostMapping()
	User newUser(@RequestBody User newUser) {
		return userRepository.save(newUser);
	}
	
    private final HttpSession httpSession;
    
    public UserController(UserRepository userRepository, HttpSession httpSession) {
        this.userRepository = userRepository;
        this.httpSession = httpSession;
    }
	
	@PostMapping("/signin")
	 public ResponseEntity<String> signIn(@RequestBody User userSignInRequest) {
        User user = userRepository.findByUsername(userSignInRequest.getUsername());
        if (user == null || !user.getPassword().equals(userSignInRequest.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
        
        // Store the signed-in user in the session
        httpSession.setAttribute("user", user);
        
        return ResponseEntity.ok("User signed in successfully");
    }
	
   @GetMapping("/signout")
    public ResponseEntity<String> signOut() {
        // Invalidate the current session to sign out the user
        httpSession.invalidate();
        
        return ResponseEntity.ok("User signed out successfully");
    }
	
	@GetMapping()
	List<User> getAllUsers(){
		return userRepository.findAll();
	}
	
    @GetMapping("/{id}")
    User getUserById(@PathVariable Long id) {
        return userRepository.findById(id).get();
    
    }
    
}
