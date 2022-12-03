package com.SportRentalInventorySystem.BackEnd.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.SportRentalInventorySystem.BackEnd.ExceptionHandler.ResourceNotFoundException;
import com.SportRentalInventorySystem.BackEnd.model.Product;
import com.SportRentalInventorySystem.BackEnd.model.ProductList;
import com.SportRentalInventorySystem.BackEnd.model.Reservation;
import com.SportRentalInventorySystem.BackEnd.model.ReservedItem;
import com.SportRentalInventorySystem.BackEnd.model.User;
import com.SportRentalInventorySystem.BackEnd.repository.ReservationRepository;
import com.SportRentalInventorySystem.BackEnd.repository.UserRepository;

@CrossOrigin(origins = "*" ) 
@RestController
@RequestMapping("/api/Reservation")
public class ReservationController {

    
    @Autowired
    private ReservationRepository reservationRepository;
   
    @Autowired
    private UserRepository userRepository;
    
    @PostMapping("/makeReservation/{id}")
    public ResponseEntity<Reservation> makeReservation(@PathVariable long id, @RequestBody Reservation reservationDetails ) {
        
       
        Reservation reserve = userRepository.findById(id).map(user -> {
            reservationDetails.setUser(user);
            return reservationRepository.save(reservationDetails);
        }).orElseThrow(() -> new RuntimeException("create new Reservation fail "));   
        
        return new ResponseEntity<>(reserve, HttpStatus.CREATED);
    }
       
    
    
/**
 *  Retrieve User profile data
 * @param id
 * @return
 */
    @GetMapping("/getPickupInfo/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_USER') OR hasRole('ROLE_MODERATOR')")
    public ResponseEntity<?> getPickupInfo(@PathVariable long id) {
  
        return new ResponseEntity<>( reservationRepository.pickupInfo(id), HttpStatus.OK);
    }

}
