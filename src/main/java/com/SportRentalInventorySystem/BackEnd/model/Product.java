package com.SportRentalInventorySystem.BackEnd.model;

import java.io.Serializable;


import javax.persistence.*;

@Entity
@Table(name = "product")
public class Product implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private long id;
    
    @Column(name = "type")
    private int type;
    
    @Column(name = "product_name")
    private String product_Name;
    
    @Column(name = "description")
    private String description;

   
    @Column(name = "serial_number")
    private String serial_Number; 
    
    @Column(name = "price")
    private double price; 
    
    @Column(name = "quantity")
    private double quantity; 
    
     @Column(name = "status")
    private String product_status;
    
     @ManyToOne
     @JoinColumn(name="category")
     private Category category;
 
     @Column(name = "productImage")
     @Lob
     private String product_Image;

    public Product() {
         
     }
     
 
    public Product(long id, int type, String product_Name, String description, String serial_Number, double price,
            double quantity, String product_status, Category category, String product_Image) {
        super();
        this.id = id;
        this.type = type;
        this.product_Name = product_Name;
        this.description = description;
        this.serial_Number = serial_Number;
        this.price = price;
        this.quantity = quantity;
        this.product_status = product_status;
        this.category = category;
        this.product_Image = product_Image;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getProduct_Name() {
        return product_Name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public void setProduct_Name(String product_Name) {
        this.product_Name = product_Name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProduct_status() {
        return product_status;
    }

    public void setProduct_status(String product_status) {
        this.product_status = product_status;
    }

    public String getSerial_Number() {
        return serial_Number;
    }

    public void setSerial_Number(String serial_Number) {
        this.serial_Number = serial_Number;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    
    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
    
    public String getProduct_Image() {
        return product_Image;
    }

    public void setProduct_Image(String product_Image) {
        this.product_Image = product_Image;
    }

    @Override
    public String toString() {
        return "Product [id=" + id + ", type=" + type + ", product_Name=" + product_Name + ", description=" + description
                + ", serial_Number=" + serial_Number + ", price=" + price + ", quantity=" + quantity + ", product_status="
                + product_status + ", category=" + category + ", product_Image=" + product_Image + "]";
    }
    
   
   
}