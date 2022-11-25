package com.SportRentalInventorySystem.BackEnd.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "category")
public class Category implements Serializable {
    private static final long serialVersionUID = 1L;
    
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "category_id", unique = true, nullable = false)
    private long category_id;
    
    @Column(name = "category_No")
    private String category_No;  
 
    @Column(name = "category_name")
    private String category_Name;  
     
    @Column(name = "season")
    private String season;
    
    @Column(name = "category_stamp")
    private LocalDate category_StampDate;
    
//    @OneToMany(mappedBy = "category", fetch = FetchType.EAGER,
//            cascade = CascadeType.ALL)
//    private Set<Product> product;
    
    
    @Column(name = "categroyImage")
    @Lob
    private String category_Image;
   
    public Category() {
        
    }
       
//    getter and setters for category
    
    public Category(long category_id, String category_No, String category_Name, String season, LocalDate category_StampDate,
            String category_Image) {
        super();
        this.category_id = category_id;
        this.category_No = category_No;
        this.category_Name = category_Name;
        this.season = season;
        this.category_StampDate = category_StampDate;
        this.category_Image = category_Image;
    }



    public String getCategory_No() {
        return category_No;
    }

    public long getCategory_id() {
        return category_id;
    }

    public void setCategory_id(long category_id) {
        this.category_id = category_id;
    }

    public void setCategory_No(String category_No) {
        this.category_No = category_No;
    }


    public String getCategory_Name() {
        return category_Name;
    }

    public void setCategory_Name(String category_Name) {
        this.category_Name = category_Name;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public LocalDate getCategory_StampDate() {
        return category_StampDate;
    }

    public void setCategory_StampDate(LocalDate category_StampDate) {
        this.category_StampDate = category_StampDate;
    }

    public String getCategory_Image() {
        return category_Image;
    }

    public void setCategory_Image(String category_Image) {
        this.category_Image = category_Image;
    }
    
    

//    public Set<Product> getProduct() {
//        return product;
//    }
//
//    public void setProduct(Set<Product> product) {
//        this.product = product;
//    }

    @Override
    public String toString() {
        return "Category [category_id=" + category_id + ", category_No=" + category_No + ", category_Name=" + category_Name
                + ", season=" + season + ", category_StampDate=" + category_StampDate + ", category_Image=" + category_Image
                + "]";
    }

    
    
}
