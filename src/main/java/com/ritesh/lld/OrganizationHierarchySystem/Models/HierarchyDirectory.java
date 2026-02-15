package com.ritesh.lld.OrganizationHierarchySystem.Models;

import java.util.*;

public class HierarchyDirectory {

    private final Map<Integer, List<Employee>> managerToReports = new HashMap<>();

    public void addReport(Integer managerId, Employee employee) {
        if (managerId == null) return;
        managerToReports
                .computeIfAbsent(managerId, k -> new ArrayList<>())
                .add(employee);
    }

    public List<Employee> getDirectReports(int managerId) {
        return managerToReports.getOrDefault(managerId, Collections.emptyList());
    }

    public void removeManager(int managerId) {
        managerToReports.remove(managerId);
    }

    public void removeReport(Integer managerId, int empId) {
        if (managerId == null) return;
        List<Employee> reports = managerToReports.get(managerId);
        if (reports != null) {
            reports.removeIf(e -> e.getId() == empId);
        }
    }
}
