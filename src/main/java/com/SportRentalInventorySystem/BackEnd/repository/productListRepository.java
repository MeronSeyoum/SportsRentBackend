package com.SportRentalInventorySystem.BackEnd.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.SportRentalInventorySystem.BackEnd.model.ProductList;

public interface ProductListRepository extends JpaRepository<ProductList, Long>  {
    
  //get product data using product id
    @Query(nativeQuery = true, value ="SELECT * FROM ProductList where product_id=:product_id")
    List<ProductList> findByProductListId(long product_id);
}
