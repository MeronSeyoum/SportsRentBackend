package com.SportRentalInventorySystem.BackEnd.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.SportRentalInventorySystem.BackEnd.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
//  Select category by season for land page
  @Query("select DISTINCT  c from Category c where c.season =?1")
  public List<Category> findBySeason(String season);
   
//  @Query("select DISTINCT  c.category_id, c.category_Name, p.description, c. season from Category c INNER JOIN Product p ON c.category_id= p.category where c.season =?1")
//  public List<categoryProjectionInterface> findBySeason(String season);
//  
//    Search category by keyword
    @Query("select c from Category c where c.category_Name LIKE %?1%")
    public List<Category> searchByCatName(String keyWords);
    
}
