package com.SportRentalInventorySystem.BackEnd.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

    @Entity
    @Table(name = "productlist")
    public class ProductList implements Serializable {
        private static final long serialVersionUID = 1L;

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Basic(optional = false)
        @Column(name = "product_List_id")
        private long product_List_id;
        
        @ManyToOne(fetch = FetchType.EAGER, optional = false)
        @JoinColumn(name="product_id", nullable = false)
        private Product product ;
        
        
        @Column(name = "product_status")
        private String product_status;
        
        @Column(name = "prod_description")
        private String prod_Description;
        
       
        @Column(name = "serial_number")
        private String serial_Number; 
        
        @Column(name = "productList_stampDate")
        private LocalDate productList_stampDate;

        
        
        
        
        public ProductList( Product product, String product_status, String prod_Description, String serial_Number) {
            super();
          
            this.product = product;
            this.product_status = product_status;
            this.prod_Description = prod_Description;
            this.serial_Number = serial_Number;
        }

        public ProductList() {}
        

        public long getProduct_List_id() {
            return product_List_id;
        }

        public void setProduct_List_id(long product_List_id) {
            this.product_List_id = product_List_id;
        }

        public Product getProduct() {
            return product;
        }

        public void setProduct(Product product) {
            this.product = product;
        }

        public String getProduct_status() {
            return product_status;
        }

        public void setProduct_status(String product_status) {
            this.product_status = product_status;
        }

        public String getProd_Description() {
            return prod_Description;
        }

        public void setProd_Description(String prod_Description) {
            this.prod_Description = prod_Description;
        }

        public String getSerial_Number() {
            return serial_Number;
        }

        public void setSerial_Number(String serial_Number) {
            this.serial_Number = serial_Number;
        }

        public LocalDate getProductList_stampDate() {
            return productList_stampDate;
        }

        public void setProductList_stampDate(LocalDate productList_stampDate) {
            this.productList_stampDate = productList_stampDate;
        }

}
