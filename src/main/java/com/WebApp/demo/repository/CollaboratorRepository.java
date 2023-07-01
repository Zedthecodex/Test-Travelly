package com.WebApp.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.WebApp.demo.model.Collaborator;

@Repository
public interface CollaboratorRepository extends JpaRepository<Collaborator,Long> {

		List<Collaborator> findByUserId(Long userId);
}
