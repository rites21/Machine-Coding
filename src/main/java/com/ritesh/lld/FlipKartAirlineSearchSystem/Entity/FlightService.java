package com.ritesh.lld.FlipKartAirlineSearchSystem.Entity;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

public class FlightService {
    private final FlightGraph flightGraph;

    public FlightService(FlightGraph flightGraph) {
        this.flightGraph = flightGraph;
    }

    public Route getFlightByMinHopes(String src, String dest) {
        Map<String, List<Flight>> adj = flightGraph.getAdjacencyList();
        Queue<ArrayList<Flight>> q = new ArrayDeque<>();
//        q.add(new ArrayList<>(src));
        int nor = 0;
        HashSet<String> vis = new HashSet<>();
        vis.add(src);
        ArrayList<String> hopes = new ArrayList<>();
        while (!q.isEmpty()) {
            List<Flight> path = q.poll();
            String currentCity = path.isEmpty() ? src
                    : path.get(path.size() - 1).getDestCity();

            if (currentCity.equals(dest)) {
                return buildRoute(path);
            }
            for (Flight flight : flightGraph.getFlightsFrom(currentCity)) {
                List<Flight> newPath = new ArrayList<>(path);
                newPath.add(flight);
                q.add((ArrayList<Flight>) newPath);
                vis.add(flight.getDestCity());
            }
        }
        return null;

    }

    public Route findCheapestRoute(String src, String dest) {
        PriorityQueue<RouteNode> pq = new PriorityQueue<>(
                Comparator.comparingDouble(n -> n.cost)
        );

        pq.add(new RouteNode(src, new ArrayList<>(), 0));
        Map<String, Double> bestCost = new HashMap<>();
        bestCost.put(src, 0.0);

        while (!pq.isEmpty()) {
            RouteNode node = pq.poll();

            if (node.city.equals(dest)) {
                return new Route(node.path, node.cost);
            }

            for (Flight flight : flightGraph.getFlightsFrom(node.city)) {

                double newCost = node.cost + flight.getPrice();
                if (bestCost.containsKey(flight.getDestCity()) &&
                        bestCost.get(flight.getDestCity()) <= newCost) {
                    continue;
                }

                bestCost.put(flight.getDestCity(), newCost);
                List<Flight> newPath = new ArrayList<>(node.path);
                newPath.add(flight);
                pq.add(new RouteNode(flight.getDestCity(), newPath, newCost));
            }
        }
        return null;
    }

    private Route buildRoute(List<Flight> flights) {
        double cost = flights.stream().mapToDouble(Flight::getPrice).sum();
        return new Route(flights, cost);
    }

    private static class RouteNode {
        String city;
        List<Flight> path;
        double cost;

        RouteNode(String city, List<Flight> path, double cost) {
            this.city = city;
            this.path = path;
            this.cost = cost;
        }
    }
}
