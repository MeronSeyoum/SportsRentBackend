package com.SportRentalInventorySystem.BackEnd.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "reserved")
public class Reservation {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reserve_id")
    private Long reserve_id;

    @ManyToOne
    @JoinColumn(name="user")
    private User user;
  
     @Column(name = "address")
    private String address;
     
    @Column(name = "city")
    private String city;
    
    @Column(name = "province")
    private String province;
    
    @Column(name = "country")
    private String country;
   
    @Column(name = "zip")
    private String zip;
 
    @Column(name = "duration")
    private Integer duration;
    
    @Column(name = "startDate")
    private LocalDate startDate;
    
    @Column(name = "endDate")
    private LocalDate endDate;
    
    @Column(name = "total_price")
    private Double totalPrice;

    @Column(name = "date_Stamp_Date")
    private LocalDate date_Stamp_Date;

    @Column(name = "payment_Option")
    private String payment_Option;

    @Column(name = "reservation_Status")
    private String reservation_Status;
    
    @OneToMany(fetch = FetchType.EAGER)
    private List<ReservedItem> reservedItem;

    public Reservation() {
        this.date_Stamp_Date = LocalDate.now();
        this.reservedItem = new ArrayList<>();
    }

    public Long getReserve_id() {
        return reserve_id;
    }

    public void setReserve_id(Long reserve_id) {
        this.reserve_id = reserve_id;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public LocalDate getDate_Stamp_Date() {
        return date_Stamp_Date;
    }

    public void setDate_Stamp_Date(LocalDate date_Stamp_Date) {
        this.date_Stamp_Date = date_Stamp_Date;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getPayment_Option() {
        return payment_Option;
    }

    public void setPayment_Option(String payment_Option) {
        this.payment_Option = payment_Option;
    }

    public String getReservation_Status() {
        return reservation_Status;
    }

    public void setReservation_Status(String reservation_Status) {
        this.reservation_Status = reservation_Status;
    }

    public List<ReservedItem> getReservedItem() {
        return reservedItem;
    }

    public void setReservedItem(List<ReservedItem> reservedItem) {
        this.reservedItem = reservedItem;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public List<ReservedItem> getReservedItems() {
        return reservedItem;
    }

    public void setReservedItems(List<ReservedItem> reservedItem) {
        this.reservedItem = reservedItem;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Reservation reserve = (Reservation) o;
        return Objects.equals(reserve_id, reserve.reserve_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reserve_id);
    }
}