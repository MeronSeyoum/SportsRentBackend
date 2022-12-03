package com.SportRentalInventorySystem.BackEnd.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.SportRentalInventorySystem.BackEnd.ExceptionHandler.ResourceNotFoundException;
import com.SportRentalInventorySystem.BackEnd.Payload.Request.StatusRequest;
import com.SportRentalInventorySystem.BackEnd.model.Product;
import com.SportRentalInventorySystem.BackEnd.model.User;
import com.SportRentalInventorySystem.BackEnd.model.UserAddress;
import com.SportRentalInventorySystem.BackEnd.repository.UserAddressRepository;
import com.SportRentalInventorySystem.BackEnd.repository.UserRepository;

@CrossOrigin(origins = "*" ) 
@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private UserAddressRepository userAddressRepository;
    
   
    @GetMapping("/user")
    @PreAuthorize("hasRole('ADMIN')")
    public  List<User> getUser(){
        return userRepository.findAll();
    }
    
    
//    Retrieve User profile data
    @GetMapping("/getUserProfileById/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_USER') OR hasRole('ROLE_MODERATOR')")
    public ResponseEntity<User> getProfile(@PathVariable long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not exist with id:" + id));
        return ResponseEntity.ok(user);
    }
    

    
//  Retrieve User Address
  @GetMapping("/getUserAddress/{id}")
  @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_USER') OR hasRole('ROLE_MODERATOR')")
  public ResponseEntity<UserAddress> getUserAddress(@PathVariable long id) {

      UserAddress userAddress = userAddressRepository.findById(id)
              .orElseThrow(() -> new ResourceNotFoundException("User not exist with id:" + id));
      return ResponseEntity.ok(userAddress);
  }
    
    
    
  @PostMapping("/changeStatusById")
  @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_USER') OR hasRole('ROLE_MODERATOR')")
  public ResponseEntity<?> changeStatusById(@Valid @RequestBody StatusRequest status){
      String[] List = status.getStatus().split(";");
      Long id=Long.parseLong(List[0]);
      String Accountstatus = List[1];
      User user = userRepository.findById(id)
              .orElseThrow(() -> new ResourceNotFoundException("User not exist with id:" + id));
      user.setStatus(Accountstatus);
       userRepository.save(user);
      return ResponseEntity.ok(null);
  }
    
  @PutMapping("/updateAddress/{id}")
  @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_USER') OR hasRole('ROLE_MODERATOR')")
   public ResponseEntity<UserAddress> updateUserAddress(@PathVariable long id, @RequestBody UserAddress userAddress) {
       UserAddress updateUserAddress = userAddressRepository.findById(id)
              .orElseThrow(() -> new ResourceNotFoundException("User not exist with id: " + id));
 
     
//  updateUserAddress.setId(id);
      updateUserAddress.setAddress1(userAddress.getAddress1());
      updateUserAddress.setCity(userAddress.getCity());
      updateUserAddress.setProvince(userAddress.getProvince());
      updateUserAddress.setCountry(userAddress.getCountry());
      updateUserAddress.setZipCode(userAddress.getZipCode());
 
      userAddressRepository.save(updateUserAddress);
     
      return ResponseEntity.ok(updateUserAddress);
  }
    
/**
 * inserted row id
 * @param id
 * @return
 */
 
}