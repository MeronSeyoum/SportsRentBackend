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
import com.SportRentalInventorySystem.BackEnd.repository.ProductRepository;
import com.SportRentalInventorySystem.BackEnd.repository.ReservationRepository;
import com.SportRentalInventorySystem.BackEnd.repository.ReservedItemRepository;
import com.SportRentalInventorySystem.BackEnd.repository.UserRepository;

@CrossOrigin(origins = "*" ) 
@RestController
@RequestMapping("/api/Reservation")
public class ReservationController {

    
    @Autowired
    private ReservationRepository reservationRepository;
    
    
    @Autowired
    private ReservedItemRepository reserveItemRepository;
   
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private ProductRepository productRepository;
    
    @PostMapping("/makeReservation/{id}")
    public ResponseEntity<Reservation> makeReservation(@PathVariable long id, @RequestBody Reservation reservationDetails ) {
   
        
        Reservation reserve = userRepository.findById(id).map(user -> {
            reservationDetails.setUser(user);
          
            return reservationRepository.save(reservationDetails);
        }).orElseThrow(() -> new RuntimeException("create new Reservation fail "));   
        
        

        
        return new ResponseEntity<>(reserve, HttpStatus.CREATED);
    }
      
    
    @PostMapping("/addCart/{id}")
    public ResponseEntity<?> addCart(@PathVariable long id, @RequestBody List<ReservedItem> reserveItem ) {
        reservationRepository.flush();
        
      
//      get last entered reservation id
      Product lastInsertedProduct = null;
      long reserveId = 0, productId=0;
      

//    insert product list using the as last inserted row primary id
          
      
      Reservation reserve = reservationRepository.findLastRecord();
     
      
    
        
      
     for(int i = 0; i < reserveItem.size(); i++) {
    System.out.print(reserveItem); 
    // get the reserve row as object from table using id
      lastInsertedProduct = getProductId(productId);
      
//    ReservedItem itemReserve = new ReservedItem(reserveItem.get(0), reserveItem.get(1), lastInsertedProduct ,reserve);

//         reserveItemRepository.save(itemReserve);  
      }
      
      return new ResponseEntity<>(reserveItem, HttpStatus.CREATED);
         
    }
    
    public Product getProductId(long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not exist with id:" + id));
        return product;
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
