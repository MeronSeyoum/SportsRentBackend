package com.SportRentalInventorySystem.BackEnd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.SportRentalInventorySystem.BackEnd.ExceptionHandler.ResourceNotFoundException;
import com.SportRentalInventorySystem.BackEnd.model.User;
import com.SportRentalInventorySystem.BackEnd.model.UserAddress;
import com.SportRentalInventorySystem.BackEnd.repository.CategoryRepository;
import com.SportRentalInventorySystem.BackEnd.repository.ProductRepository;
import com.SportRentalInventorySystem.BackEnd.repository.UserRepository;

@CrossOrigin(origins = "*" ) 
@RestController
@RequestMapping("/api/public")
public class PublicController {
    
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private UserRepository userRepository;

    // access to all public content

    // retrieve all category for summer
    @GetMapping("/summerCategory")
    public ResponseEntity<?> getSummerCategory() {
        return new ResponseEntity<>(categoryRepository.findBySeason("summer"), HttpStatus.OK);
    }

    // retrieve all category for Winter
    @GetMapping("/winterCategory")
    public ResponseEntity<?> getWinterCategory() {
        return new ResponseEntity<>(categoryRepository.findBySeason("winter"), HttpStatus.OK);
    }

    // Retrieve Product information and send to client
    @GetMapping("/TopSaleProduct")
    public ResponseEntity<?> getAllProduct() {
        return new ResponseEntity<>(productRepository.findByTopSale(), HttpStatus.OK);
    }
 
    @GetMapping("/category")
    public ResponseEntity<?> getAllCategorys(){
        return new ResponseEntity<>(categoryRepository.findAll(), HttpStatus.OK);
    }
    

//  Get product by Category ID
  @GetMapping("/getProductByCategoryID/{id}")
  public ResponseEntity<?> getProductByCat(@PathVariable long id) {
      return new ResponseEntity<>(productRepository.productByCatId(id), HttpStatus.OK);
  }
  
  
//Get product by Category ID
@GetMapping("/getProductByCategory")
public ResponseEntity<?> getProductByCategory() {
    return new ResponseEntity<>(productRepository.productByCat(), HttpStatus.OK);
}
    
  // Retrieve Product information and send to client
  @GetMapping("/searchProduct")
  public ResponseEntity<?> ProductSearch() {
      return new ResponseEntity<>(productRepository.ProductSearch(), HttpStatus.OK);
  }
    
  
  @PutMapping("/update/{id}")
 
  public ResponseEntity<User> updateUser(@PathVariable long id, @RequestBody User userDetails, UserAddress userAddress) {
      User updateUser = userRepository.findById(id)
              .orElseThrow(() -> new ResourceNotFoundException("User not exist with id: " + id));
      updateUser.setFirstName(userDetails.getFirstName());
      updateUser.setLastName(userDetails.getLastName());
      updateUser.setPhoneNumber(userDetails.getPhoneNumber());
     
      
      System.out.print(updateUser);

   // Set child reference(userAddress) in parent entity(user)
//      updateUser.setUserAddress(userAddress);
//
//      // Set parent reference(user) in child entity(userAddress)
//      userAddress.setUser(updateUser);

      // Save Parent Reference (which will save the child as well)
      
      userRepository.save(updateUser);

      return ResponseEntity.ok(updateUser);
  }
  
}
