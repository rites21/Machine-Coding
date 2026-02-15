package com.ritesh.lld.FlipKartAirlineSearchSystem.Entity;

import lombok.Data;

@Data
public class Flight {
    private String airline;

    private String srcCity;
    private String destCity;

    private double price;

    //    Optional features (e.g., hasMeal)
    //(Optional later) departure/arrival time

    public Flight(String airline, String srcCity, String destCity, double price) {
        this.airline = airline;
        this.srcCity = srcCity;
        this.destCity = destCity;
        this.price = price;
    }
}