package com.symphony.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.symphony.model.BikeDetails;

@Repository
public interface BikeDetailsRepo extends JpaRepository<BikeDetails, Long> {
	
	Optional<BikeDetails> findById(Long id);
	
	//List<BikeDetails> findByClientId(Long clientId);

}
