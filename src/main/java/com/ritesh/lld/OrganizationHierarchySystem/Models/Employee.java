package com.ritesh.lld.OrganizationHierarchySystem.Models;

import lombok.Data;

@Data
public class Employee {
    private int id;
    private String name;
    private Integer managerId;

    public Employee(int id, String name, Integer managerId) {
        this.id = id;
        this.name = name;
        this.managerId = managerId;
    }
}
