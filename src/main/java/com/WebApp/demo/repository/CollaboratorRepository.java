package com.WebApp.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.WebApp.demo.model.Collaborator;
import com.WebApp.demo.model.Trip;
import com.WebApp.demo.model.User;

@Repository
public interface CollaboratorRepository extends JpaRepository<Collaborator,Long> {

		List<Collaborator> findByUserId(Long userId);
		
		Collaborator findByTripAndUserAndAccessLevel(Trip trip, User user, String accessLevel);
}
