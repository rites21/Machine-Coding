package com.ritesh.lld.FlipKartAirlineSearchSystem.Entity;

public class Main {
    public static void main(String[] args) {
//        RegisterFlightCLI registerFlightCLI = new RegisterFlightCLI();
//        registerFlightCLI.registerFlight( "register flight-> JetAir -> DEL -> BLR -> 500");
        FlightGraph graph = new FlightGraph();

        graph.addFlight(new Flight("JetAir", "DEL", "BLR", 500));
        graph.addFlight(new Flight("JetAir", "BLR", "LON", 1000));
        graph.addFlight(new Flight("Delta", "DEL", "LON", 2000));
        graph.addFlight(new Flight("Delta", "LON", "NYC", 2000));

        System.out.println("Flights from DEL:");
        graph.getFlightsFrom("DEL").forEach(f ->
                System.out.println(
                        f.getSrcCity() + " -> " + f.getDestCity()
                                + " | " + f.getAirline()
                                + " | " + f.getPrice()
                )
        );
    }
}
