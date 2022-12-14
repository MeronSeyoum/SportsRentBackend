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
//    get product and category using id
    @Query(nativeQuery = true, value = "SELECT * FROM product p LEFT JOIN category c ON c.category_id = p.category WHERE c.category_id=:category_id")
    public List<ProductProjection> productByCatId(long category_id);
    
    
    
//  get product and category using id
  @Query(nativeQuery = true, value = "SELECT * FROM product p LEFT JOIN category c ON c.category_id = p.category")
  public List<ProductProjection> productByCat();

  
///  get product and category by season
  @Query(nativeQuery = true, value = "SELECT * FROM product p LEFT JOIN category c ON c.category_id = p.category where c.season =?1")
  public List<ProductProjection> findBySeason(String season);

    
//get product data using product name
    @Query("select p from Product p where p.product_Name=:product_Name")
   public List<Product> findByProductName(String product_Name);
    
    
    //get product data with most featured 
       @Query(nativeQuery = true, value ="select p1.* from Product p1 left join Product p2 on p1.category = p2.category and p1.id < p2.id where p2.id is null")
       public List<Product> findByTopSale();
    

   //  Get all product to be search by front end
       @Query(nativeQuery = true, value = "SELECT * FROM product p LEFT JOIN category c ON c.category_id = p.category")
       public List<ProductProjection> ProductSearch();
    
}
