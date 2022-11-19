package com.SportRentalInventorySystem.BackEnd.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
    @Table(name = "productList")
    public class ProductList implements Serializable {
        private static final long serialVersionUID = 1L;

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Basic(optional = false)
        private long id;
        
        @ManyToOne
        @JoinColumn(name="product")
        private Product product ;
        
        @Column(name = "status")
        private int status;
        
        @Column(name = "prod_description")
        private String prod_Descrition;
        
       
        @Column(name = "serial_number")
        private String serial_Number; 
        
        @Column(name = "productList_stampDate")
        private LocalDate productList_stampDate;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public Product getProduct() {
            return product;
        }

        public void setProduct(Product product) {
            this.product = product;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getProd_Descrition() {
            return prod_Descrition;
        }

        public void setProd_Descrition(String prod_Descrition) {
            this.prod_Descrition = prod_Descrition;
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
