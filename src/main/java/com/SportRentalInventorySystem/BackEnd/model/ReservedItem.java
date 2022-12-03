package com.SportRentalInventorySystem.BackEnd.model;

import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "reserved_item")
public class ReservedItem {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "rItem_id")
        private Long rItem_id;
        
        @Column(name = "amount")
        private Long amount;

        @Column(name = "quantity")
        private Long quantity;

        @ManyToOne(fetch = FetchType.EAGER, optional = false)
        @JoinColumn(name="product_id", nullable = false)
        private Product product ;
      
//       
//        @OneToMany(mappedBy = "reserve", fetch = FetchType.EAGER,
//                cascade = CascadeType.ALL)
//        private Set<Reservation> reservedItem;

        public ReservedItem(Long amount, Long quantity, Product product) {
            super();
            this.amount = amount;
            this.quantity = quantity;
            this.product = product;
        }

        public Long getRItem_id() {
            return rItem_id;
        }

        public void setRItem_id(Long rItem_id) {
            this.rItem_id = rItem_id;
        }

        public Long getAmount() {
            return amount;
        }

        public void setAmount(Long amount) {
            this.amount = amount;
        }

        public Long getQuantity() {
            return quantity;
        }

        public void setQuantity(Long quantity) {
            this.quantity = quantity;
        }

        public Product getProduct() {
            return product;
        }

        public void setProduct(Product product) {
            this.product = product;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ReservedItem reservedItem = (ReservedItem) o;
            return Objects.equals(rItem_id, reservedItem.rItem_id);
        }

        @Override
        public int hashCode() {
            return Objects.hash(rItem_id);
        }
    
    
}
