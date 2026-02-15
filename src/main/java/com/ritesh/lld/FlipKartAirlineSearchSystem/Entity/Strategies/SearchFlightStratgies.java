package com.ritesh.lld.FlipKartAirlineSearchSystem.Entity.Strategies;

import java.util.List;

import com.ritesh.lld.FlipKartAirlineSearchSystem.Entity.Flight;

public interface SearchFlightStratgies {

    List<Flight> getFlightByRequirement(String src, String dest);
}
