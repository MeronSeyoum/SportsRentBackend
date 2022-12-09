package com.SportRentalInventorySystem.BackEnd.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.SportRentalInventorySystem.BackEnd.ExceptionHandler.ResourceNotFoundException;
import com.SportRentalInventorySystem.BackEnd.model.Category;
import com.SportRentalInventorySystem.BackEnd.repository.CategoryRepository;
import com.SportRentalInventorySystem.BackEnd.utility.FileUploadUtil;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    

    /**
     * Category information managed by admin. manager CRUD operation is done here
     * 
     * @return
     */

    @GetMapping("/categories")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getCategories() {
        return new ResponseEntity<>(categoryRepository.findAll(), HttpStatus.OK);
    }

    /**
     * build get Category by id REST API
     * 
     * @param id
     * @return
     */
    @GetMapping("/categoryById/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Category> getCategory(@PathVariable long id) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not exist with id:" + id));
        return ResponseEntity.ok(category);
    }

    /**
     * build create Category REST API
     * 
     * @param categoryDetails
     * @return
     */
    @PostMapping("/createCategory/{image}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Category> createCategory(@PathVariable MultipartFile image, @RequestBody Category categoryDetails) throws IOException  {

        
        String fileName = StringUtils.cleanPath(image.getOriginalFilename());
        categoryDetails.setCategory_Image(fileName);
         
          Category category = categoryRepository.save(categoryDetails); 
          
 
        String uploadDir = "assets/productImage/image_" + category.getCategory_id();
 
        FileUploadUtil.saveFile(uploadDir, fileName, image);
        
        return new ResponseEntity<>(category, HttpStatus.CREATED);
    }

    /**
     * delete PRoduct REST API
     * 
     * @param id
     * @return
     */
    @DeleteMapping("/deleteCategory/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<HttpStatus> deleteCategory(@PathVariable long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("category not exist with id: " + id));

        categoryRepository.delete(category);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * build update Product REST API
     * 
     * @param id
     * @param categoryInfo
     * @return
     */

    
//    Update is not working
    @PutMapping("/categoryUpdate/{id}")
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

    /**
     * Product search
     * 
     * @param keyWords
     * @return
     */

    @GetMapping("/searchcategory/{keyWords}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> searchCategory(@PathVariable String keyWords) {
        return new ResponseEntity<>(categoryRepository.searchByCatName(keyWords), HttpStatus.OK);
    }
    
}
