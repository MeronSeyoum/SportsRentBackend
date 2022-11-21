package com.SportRentalInventorySystem.BackEnd.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.SportRentalInventorySystem.BackEnd.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
   
//    Select category by season for land page
    @Query("select DISTINCT  c from Category c where c.season =?1")
    public List<Category> findBySeason(String season);
    
//    Search category by keyword
    @Query("select c from Category c where c.category_Name LIKE %?1%")
    public List<Category> searchByCatName(String keyWords);
    
}
