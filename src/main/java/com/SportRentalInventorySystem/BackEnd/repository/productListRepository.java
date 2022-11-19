package com.SportRentalInventorySystem.BackEnd.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.SportRentalInventorySystem.BackEnd.model.ProductList;


public interface productListRepository extends JpaRepository<ProductList, Long>  {

}
