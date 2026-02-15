package com.ritesh.lld.FlipKartAirlineSearchSystem.Entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class RegisterFlightCLI {
    public void registerFlight(String s){
        String[] str = s.split("->");
        for (String string : str) {
            System.out.println(string);
        }
        if(!str[0].toLowerCase().startsWith("register")){
            throw new IllegalArgumentException("invalid input");
        }
        String flight = str[2];//JetAir
        String srcCity = str[3];
        String destCIty = str[4];
        Double price = Double.valueOf(str[4]);

        Flight flight1 = new Flight(flight,srcCity,destCIty,price);
    }

//    i:register flight-> JetAir -> DEL -> BLR -> 500
//    o: JetAir DEL -> BLR flight registered
//
//    i:search flight-> DEL -> NYC
//    o:
}
