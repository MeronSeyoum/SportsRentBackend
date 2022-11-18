package com.SportRentalInventorySystem.BackEnd.model;

public interface ProductProjection {

    long getId();

    String getCategory_Name();

    String getProduct_Name();

    String getDescription();

    double getPrice();

    String getSeason();

    String getProduct_Image();

    double getQuantity();
}
