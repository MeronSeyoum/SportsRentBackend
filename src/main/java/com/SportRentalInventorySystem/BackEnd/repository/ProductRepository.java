package com.SportRentalInventorySystem.BackEnd.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.SportRentalInventorySystem.BackEnd.model.Product;
import com.SportRentalInventorySystem.BackEnd.model.ProductProjection;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // search by product name
    @Query("select c from Product c where c.product_Name LIKE %?1%")
    public List<Product> searchByProName(String keyWords);

//  Get all product by category ID p.id, p.product_name, p.description, p.price, c.category_name, c.season , p.product_image
    @Query(nativeQuery = true, value = "SELECT * FROM product p LEFT JOIN category c ON c.category_id = p.category WHERE c.category_id=:category_id")
    public List<ProductProjection> productByCatId(long category_id);

    @Query("select c from Product c where c.product_Name=:product_Name")
   public List<Product> findByProductName(String product_Name);
}
