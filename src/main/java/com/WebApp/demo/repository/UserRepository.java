package com.WebApp.demo.repository;



import com.WebApp.demo.model.User;

import org.springframework.data.jpa.repository.JpaRepository;



public interface UserRepository extends JpaRepository<User,Long>{

	User findByUsername(String username);
}
