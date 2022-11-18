package com.SportRentalInventorySystem.BackEnd.services;

import java.util.List;

import com.SportRentalInventorySystem.BackEnd.model.Product;

public interface ProductService {

    Product saveProduct(Product product);

    Product updateProduct(Product product);

    void deleteProduct(Long productId);

    Long numberOfProducts();

    List<Product> findAllProducts();
}


