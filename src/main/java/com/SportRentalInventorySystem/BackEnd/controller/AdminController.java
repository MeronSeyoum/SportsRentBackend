package com.SportRentalInventorySystem.BackEnd.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.SportRentalInventorySystem.BackEnd.ExceptionHandler.ResourceNotFoundException;
import com.SportRentalInventorySystem.BackEnd.model.Category;
import com.SportRentalInventorySystem.BackEnd.model.Product;
import com.SportRentalInventorySystem.BackEnd.model.User;
import com.SportRentalInventorySystem.BackEnd.repository.CategoryRepository;
import com.SportRentalInventorySystem.BackEnd.repository.ProductRepository;
import com.SportRentalInventorySystem.BackEnd.repository.UserRepository;
import com.SportRentalInventorySystem.BackEnd.utility.FileUploadUtil;


@CrossOrigin("https://sportsrentfrontend.herokuapp.com")
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    /**
     * User account information managed by admin. manager CRUD operation is done
     * here
     * 
     * @param user
     * @return
     */

    // build create user REST API
    @PostMapping("/createUser")
    @PreAuthorize("hasRole('ADMIN')")
    public User createUser(@RequestBody User user) {
        return userRepository.save(user);
    }

// build update user REST API
    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<User> updateUser(@PathVariable long id, @RequestBody User userDetails) {
        User updateUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not exist with id: " + id));
        updateUser.setFirstName(userDetails.getFirstName());
        updateUser.setLastName(userDetails.getLastName());
        updateUser.setEmail(userDetails.getEmail());
        updateUser.setPhoneNumber(userDetails.getPhoneNumber());
        updateUser.setUsername(userDetails.getUsername());
        // updateUser.setPassword(userDetails.getPassword());

        userRepository.save(updateUser);

        return ResponseEntity.ok(updateUser);
    }

    // Retrieve User information and send to client
    @GetMapping("/user")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getAllUsers() {
        return new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK);
    }

    // build get user by id REST API
    @GetMapping("/userById/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> getUser(@PathVariable long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not exist with id:" + id));
        return ResponseEntity.ok(user);
    }

    // delete user REST API
    @DeleteMapping("/deleteUser/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not exist with id: " + id));

        userRepository.delete(user);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

//  Product search 
//  @GetMapping("/searchUser/{keyWords}")
//  @PreAuthorize("hasRole('ROLE_ADMIN')")
//  public ResponseEntity<?> SearchUser(@PathVariable String keyWords) {
//      return new ResponseEntity<>(userRepository.searchByUserName(keyWords), HttpStatus.OK);
//  }
//    
    
    /**
     * Product information managed by admin. manager CRUD operation is done here
     * 
     * @param Product
     * @return
     */

    // Retrieve Product information and send to client
    @GetMapping("/product")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getAllProducts() {
        return new ResponseEntity<>(productRepository.findAll(), HttpStatus.OK);
    }

    // build get user by id REST API
    @GetMapping("/productById/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Product> getProduct(@PathVariable long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not exist with id:" + id));
        return ResponseEntity.ok(product);
    }

    // build create user REST API
    @PostMapping("/createProduct/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Product> createProduct(@PathVariable long id, @RequestBody Product productDetails) {

        Product product = categoryRepository.findById(id).map(category -> {
            productDetails.setCategory(category);
            return productRepository.save(productDetails);
        }).orElseThrow(() -> new RuntimeException("create Product fail "));

        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    
    // delete Product REST API
    @DeleteMapping("/deleteProduct/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<HttpStatus> deleteProduct(@PathVariable long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not exist with id: " + id));
        productRepository.delete(product);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

 // build update Product REST API
    @PutMapping("/productUpdate/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Product> updateProduct(@PathVariable long id, @RequestBody Product productInfo) {
        Product updateProduct = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product Update fail " + id));

        updateProduct.setDescription(productInfo.getDescription());
        updateProduct.setCategory(productInfo.getCategory());
        updateProduct.setProduct_Image(productInfo.getProduct_Image());
        updateProduct.setPrice(productInfo.getPrice());
        updateProduct.setProduct_status(productInfo.getProduct_status());
        updateProduct.setProduct_Name(productInfo.getProduct_Name());
        updateProduct.setQuantity(productInfo.getQuantity());
        updateProduct.setSerial_Number(productInfo.getSerial_Number());
        updateProduct.setType(productInfo.getType());

        productRepository.save(updateProduct);
        return ResponseEntity.ok(updateProduct);
    }


    
//    Product search 
    @GetMapping("/searchProduct/{keyWords}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> SearchProdut(@PathVariable String keyWords) {
        return new ResponseEntity<>(productRepository.searchByProName(keyWords), HttpStatus.OK);
    }

    /**
     * Category information managed by admin. manager CRUD operation is done here
     * 
     * @param Product
     * @return
     */

    // Retrieve Category information and send to client
    @GetMapping("/categories")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getCategories() {
        return new ResponseEntity<>(categoryRepository.findAll(), HttpStatus.OK);
    }

    // build get Category by id REST API
    @GetMapping("/categoryById/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Category> getCategory(@PathVariable long id) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not exist with id:" + id));
        return ResponseEntity.ok(category);
    }

    // build create Category REST API
    @PostMapping("/createCategory")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Category> createCategory(@RequestBody Category categoryDetails) {
     
        Category category = categoryRepository.save(categoryDetails);
        
        return new ResponseEntity<>(category, HttpStatus.CREATED);
    }

    // delete PRoduct REST API
    @DeleteMapping("/deleteCategory/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<HttpStatus> deleteCategory(@PathVariable long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("category not exist with id: " + id));

        categoryRepository.delete(category);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // build update Product REST API
    @PutMapping("/categoryUpdate")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Category> updateCategory(@PathVariable long id, @RequestBody Category categoryInfo) {
        Category updateCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product Update fail " + id));

        updateCategory.setCategory_No(categoryInfo.getCategory_No());
        updateCategory.setCategory_Image(categoryInfo.getCategory_Image());
        updateCategory.setCategory_Name(categoryInfo.getCategory_Name());
        updateCategory.setSeason(categoryInfo.getSeason());
        
        categoryRepository.save(updateCategory);
        return ResponseEntity.ok(updateCategory);
    }

    
//  Product search 
  @GetMapping("/searchcategory/{keyWords}")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ResponseEntity<?> searchCategory(@PathVariable String keyWords) {
      return new ResponseEntity<>(categoryRepository.searchByCatName(keyWords), HttpStatus.OK);
  }

    
}