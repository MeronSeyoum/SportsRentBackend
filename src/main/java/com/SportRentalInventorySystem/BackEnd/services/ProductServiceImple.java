//package com.SportRentalInventorySystem.BackEnd.services;
//
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.util.List;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.SportRentalInventorySystem.BackEnd.model.Product;
//import com.SportRentalInventorySystem.BackEnd.repository.ProductRepository;
//
//@Service
//@Transactional //It is not necessary. You can use it, if you have multiple database operation in a single service method.
//public class ProductServiceImpl implements ProductService {
//
//    @Autowired
//    private ProductRepository productRepository;
//
//    @Override
//    public Product saveProduct(final Product product){
//        productRepository.save(product);
//        return product;
//    }
//
//    @Override
//    public Product updateProduct(final Product product){
//        return productRepository.save(product);
//    }
//
//    @Override
//    public void deleteProduct(final Long productId){
//        productRepository.deleteById(productId);
//    }
//
//    @Override
//    public Long numberOfProducts(){
//        return productRepository.count();
//    }
//
//    @Override
//    public List<Product> findAllProducts(){
//        return productRepository.findAll();
//    }
//}