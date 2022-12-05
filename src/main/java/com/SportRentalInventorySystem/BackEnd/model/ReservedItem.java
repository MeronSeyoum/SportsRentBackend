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
        private long rItem_id;
        
        @Column(name = "amount")
        private long amount;

        @Column(name = "quantity")
        private long quantity;

        @ManyToOne(fetch = FetchType.EAGER, optional = false)
        @JoinColumn(name="product_id", nullable = false)
        private Product product ;
      
       
        @ManyToOne(fetch = FetchType.EAGER, optional = false)
        @JoinColumn(name="reserve_id", nullable = false)
        private Reservation reserve ;
       
        
        public ReservedItem() {}

        public ReservedItem(long amount, long quantity, Product product, Reservation reserve) {
            super();
            this.amount = amount;
            this.quantity = quantity;
            this.product = product;
            this.reserve = reserve;
        }
        public long getRItem_id() {
            return rItem_id;
        }

        public void setRItem_id(long rItem_id) {
            this.rItem_id = rItem_id;
        }

        public long getAmount() {
            return amount;
        }

        public void setAmount(long amount) {
            this.amount = amount;
        }

        public long getQuantity() {
            return quantity;
        }

        public void setQuantity(long quantity) {
            this.quantity = quantity;
        }

        public Product getProduct() {
            return product;
        }

        public void setProduct(Product product) {
            this.product = product;
        }

       

        public Reservation getReserve() {
            return reserve;
        }

        public void setReserve(Reservation reserve) {
            this.reserve= reserve;
        }

//        @Override
//        public boolean equals(Object o) {
//            if (this == o) return true;
//            if (o == null || getClass() != o.getClass()) return false;
//            ReservedItem reservedItem = (ReservedItem) o;
//            return Objects.equals(rItem_id, reservedItem.rItem_id);
//        }

        @Override
        public int hashCode() {
            return Objects.hash(rItem_id);
        }
    
    
}
