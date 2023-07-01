package com.WebApp.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.WebApp.demo.model.Post;

public interface PostRepository extends JpaRepository<Post,Long>{

}
