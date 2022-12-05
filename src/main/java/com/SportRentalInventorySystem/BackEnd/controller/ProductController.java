package com.SportRentalInventorySystem.BackEnd.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.SportRentalInventorySystem.BackEnd.ExceptionHandler.ResourceNotFoundException;
import com.SportRentalInventorySystem.BackEnd.model.Category;
import com.SportRentalInventorySystem.BackEnd.model.Product;
import com.SportRentalInventorySystem.BackEnd.model.ProductList;
import com.SportRentalInventorySystem.BackEnd.repository.CategoryRepository;
import com.SportRentalInventorySystem.BackEnd.repository.ProductListRepository;
import com.SportRentalInventorySystem.BackEnd.repository.ProductRepository;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductListRepository productListRepository;

    Category categoryNo;

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

    /**
     * build get user by id REST API
     * 
     * @param id
     * @return
     */

    @GetMapping("/productById/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Product> getProductById(@PathVariable long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not exist with id:" + id));
        return ResponseEntity.ok(product);
    }

    // @GetMapping("/productById/{id}")
    // @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Product getProductId(long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not exist with id:" + id));
        return product;
    }

    /**
     * build create user REST API
     * 
     * @param id
     * @param productDetails
     * @return
     * @throws RuntimeException
     */

    @PostMapping("/createProduct/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Product> createProduct(@PathVariable long id, @RequestBody Product productDetails)
            throws RuntimeException {

        Product lastInsertedProduct = null;
        long productId = 0;
        String serialNumberCode = "";

        // String pro_Desc= "";
        // Insert Product if category exist;
        Product product = categoryRepository.findById(id).map(category -> {
            productDetails.setCategory(category);
            categoryNo = category;
            // check if a product exist by searching it product name if exist escape saving
            // and send warring message
            List<Product> products = productRepository.findByProductName(productDetails.getProduct_Name());
            if (products.isEmpty()) {
                // save the product data
                return productRepository.save(productDetails);

            }
            return productDetails;

        }).orElseThrow(() -> new RuntimeException("create Product fail "));

        productRepository.flush();

//      insert product list using the as last inserted row primary id

        // current insert object(row) primary id
        productId = product.getId();
        // get the product row as object from table using id
        lastInsertedProduct = getProductId(productId);

        for (int itemNo = 1; itemNo <= lastInsertedProduct.getQuantity(); itemNo++) {

            serialNumberCode = categoryNo.getCategory_No() + "-" + lastInsertedProduct.getType() + "-" + itemNo;

            ProductList productList = new ProductList(lastInsertedProduct, lastInsertedProduct.getProduct_status(),
                    lastInsertedProduct.getDescription(),
                    serialNumberCode);

            productListRepository.save(productList);
        }

        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    /**
     * build update Product REST API
     * 
     * @param id
     * @param productInfo
     * @return
     */
    @PutMapping("/productUpdate/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Product> updateProduct(@PathVariable long id, @RequestBody Product productInfo) {
        Product updateProduct = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product Update fail " + id));

        updateProduct.setDescription(productInfo.getDescription());
//        updateProduct.setCategory(productInfo.getCategory());
        updateProduct.setProduct_Image(productInfo.getProduct_Image());
        updateProduct.setPrice(productInfo.getPrice());
        updateProduct.setProduct_status(productInfo.getProduct_status());
        updateProduct.setProduct_Name(productInfo.getProduct_Name());
        updateProduct.setQuantity(productInfo.getQuantity());
        updateProduct.setType(productInfo.getType());

        productRepository.save(updateProduct);
        return ResponseEntity.ok(updateProduct);
    }

    /**
     * delete Product REST API
     * 
     * @param id
     * @return
     */
    @DeleteMapping("/deleteProduct/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<HttpStatus> deleteProduct(@PathVariable long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not exist with id: " + id));
        productRepository.delete(product);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Product search
     * 
     * @param keyWords
     * @return
     */
    @GetMapping("/searchProduct/{keyWords}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> SearchProdut(@PathVariable String keyWords) {
        return new ResponseEntity<>(productRepository.searchByProName(keyWords), HttpStatus.OK);
    }

    /**
     * Get productList data
     * 
     * @return
     */
    @GetMapping("/productListById/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getAllProductList(@PathVariable long id) {

        return new ResponseEntity<>(productListRepository.findByProductListId(id), HttpStatus.OK);
    }

}
