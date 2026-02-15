package com.ritesh.lld.FlipKartAirlineSearchSystem.Entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
//@NoArgsConstructor
public class FlightGraph {

    // Adjacency list: SourceCity -> List of outgoing flights
    private final Map<String, List<Flight>> adjacencyList = new HashMap<>();

    // Register a flight (add edge)
    public void addFlight(Flight flight) {
        adjacencyList
                .computeIfAbsent(flight.getSrcCity(), k -> new ArrayList<>())
                .add(flight);

        // Ensure destination city exists in graph (optional but good)
        adjacencyList
                .computeIfAbsent(flight.getDestCity(), k -> new ArrayList<>());
    }

    // Get all outgoing flights from a city
    public List<Flight> getFlightsFrom(String city) {
        return adjacencyList.getOrDefault(city, Collections.emptyList());
    }

    // Optional: check if city exists
    public boolean containsCity(String city) {
        return adjacencyList.containsKey(city);
    }
}


