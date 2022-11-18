package com.SportRentalInventorySystem.BackEnd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.SportRentalInventorySystem.BackEnd.model.Product;
import com.SportRentalInventorySystem.BackEnd.repository.CategoryRepository;
import com.SportRentalInventorySystem.BackEnd.repository.ProductRepository;

@CrossOrigin(origins = "https://sportsrentfrontend.herokuapp.com")
@RestController
@RequestMapping("/api/public")
public class PublicController {
    
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

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
        return new ResponseEntity<>(productRepository.findAll(), HttpStatus.OK);
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
    
}
