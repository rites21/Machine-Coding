package com.ritesh.lld.OrganizationHierarchySystem.Models;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;

@Data
public class EmployeeDirectory {


    private final Map<Integer, Employee> employees = new HashMap<>();

    public void add(Employee employee) {
        employees.put(employee.getId(), employee);
    }

    public Employee get(int empId) {
        return employees.get(empId);
    }

    public void remove(int empId) {
        employees.remove(empId);
    }

    public boolean exists(int empId) {
        return employees.containsKey(empId);
    }
}
