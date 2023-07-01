package com.WebApp.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.WebApp.demo.model.Post;
import com.WebApp.demo.model.User;
import com.WebApp.demo.repository.PostRepository;
import com.WebApp.demo.repository.UserRepository;

@RestController
public class postController {
	
	@Autowired
	PostRepository postRepository;
	
	@Autowired
	UserRepository userRepository;
	
	
	@PostMapping("/users/{userId}/posts")
	Post newPost(
			@PathVariable("userId") Long userId,
			@RequestBody Post newPost)
	{
		User user = userRepository.findById(userId).get();
		if(user==null) {
			
		}
		newPost.setUser(user);
		return postRepository.save(newPost);
	}
	
	@GetMapping("/posts")
	List<Post> getPost(){
		return postRepository.findAll();
	}
	
	
}
