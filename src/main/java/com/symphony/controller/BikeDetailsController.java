package com.symphony.controller;


import static com.symphony.constant.Constant.FAIL;
import static com.symphony.constant.Constant.MESSAGE;
import static com.symphony.constant.Constant.RESULT;
import static com.symphony.constant.Constant.STATUS;
import static com.symphony.constant.Constant.SUCCESS;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Callable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.symphony.model.BikeDetails;
import com.symphony.repository.BikeDetailsRepo;

@CrossOrigin(origins = "*" , allowedHeaders = {"Origin", "X-Requested-With", "Content-Type", "Accept", "Authorization"}, methods = {RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT, RequestMethod.DELETE})
@RestController
@RequestMapping("/api/bike")
public class BikeDetailsController {
	
	@Autowired
	private BikeDetailsRepo bikeDetailsRepo;
	
	@GetMapping("/all-bike-details")
	public Callable<ResponseEntity<?>> getAllBikeDetails() {
		 return () -> {
	            Map<String, Object> map = new HashMap<>();
	            ResponseEntity<Map<String, Object>> entity;
	            List<BikeDetails> allReport = bikeDetailsRepo.findAll();
	            if (!allReport.isEmpty()) {
	                map.put(STATUS, SUCCESS);
	                map.put(RESULT, allReport);
	                entity = new ResponseEntity<>(map, HttpStatus.OK);
	            } else {
	                map.clear();
	                map.put(STATUS, FAIL);
	                map.put(MESSAGE, "The Bike Details not available");
	                entity = new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
	            }
	            return entity;
	        };
	}
	
	@PostMapping("/save-bike-details")
	Callable<ResponseEntity<?>> saveBikeDetails(@RequestBody BikeDetails bikeDetails) {
		return () -> {
            Map<String, Object> map = new HashMap<>();
            ResponseEntity<Map<String, Object>> entity = null;
            try {
            	BikeDetails bikeDetailsRep = bikeDetailsRepo.save(bikeDetails);
                map.put(STATUS, SUCCESS);
                map.put(MESSAGE, "The Bike Details Save successfully");
                map.put(RESULT, bikeDetailsRep);
                entity = new ResponseEntity<>(map, HttpStatus.OK);
            } catch (Exception e) {
                map.clear();
                e.printStackTrace();
                map.put(STATUS, FAIL);
                map.put(MESSAGE, "The Bike Details not Save");
                entity = new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
            }
            return entity;
        };
		
	}
	
	@GetMapping("/filterByBikeId")
    public Callable<ResponseEntity<?>> filterByBikeId(
            @RequestParam  Long bikeId) {
        return () -> {
            Map<String, Object> map = new HashMap<>();
            ResponseEntity<Map<String, Object>> entity;
            //List<CiClientData> allReport = ciClientDataRepo.findByClientId(clientId);
            Optional<BikeDetails> bikeDetails = bikeDetailsRepo.findById(bikeId);
            if (bikeDetails.isPresent()) {
                map.put(STATUS, SUCCESS);
                map.put("bikeList", bikeDetails);
                entity = new ResponseEntity<>(map, HttpStatus.OK);
            } else {
                map.clear();
                map.put(STATUS, FAIL);
                map.put(MESSAGE, " Bike Data not available");
                entity = new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
            }
            return entity;
        };
    }
	
	@DeleteMapping("/delete-bike-details")
	public Callable<ResponseEntity<?>> deleteByBikeDetails(@RequestParam Long id) {
		return () -> {
			Map<String, Object> map = new HashMap<>();
			ResponseEntity<Map<String, Object>> entity;
			Optional<BikeDetails> bikeDetails = bikeDetailsRepo.findById(id);
			if (bikeDetails.isPresent()) {
				bikeDetailsRepo.deleteById(id);
				//auditUtility.stepUpdater(ympSiteCode, createdBy, 4, "Delete");
				map.put(STATUS, SUCCESS);
				map.put(MESSAGE, "record delete Successfully");
				entity = new ResponseEntity(map, HttpStatus.OK);
			} else {
				map.put(STATUS, FAIL);
				map.put(MESSAGE, "Record not found");
				entity = new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
			}
			return entity;
		};
	}

}
