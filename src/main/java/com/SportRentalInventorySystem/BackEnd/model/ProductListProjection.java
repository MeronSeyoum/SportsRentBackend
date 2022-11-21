package com.SportRentalInventorySystem.BackEnd.model;

import java.time.LocalDate;

public interface ProductListProjection {

    //ProductList
    int getProduct_List_id();

    String getStatus();

    String getProd_Description();

    String getSerial_Number();
    LocalDate getProductList_stampDate();

//product
    public long getId();
    String getProduct_Name();
    int getType();
    double getQuantity();
    String getDescription();
    String getProduct_status();
    double getPrice();
    String getProduct_Image();

}
