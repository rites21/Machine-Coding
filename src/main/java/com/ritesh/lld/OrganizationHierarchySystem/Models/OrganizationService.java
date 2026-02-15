package com.ritesh.lld.OrganizationHierarchySystem.Models;

import java.util.List;

public class OrganizationService {

    private final EmployeeDirectory employeeDirectory;
    private final HierarchyDirectory hierarchyDirectory;

    public OrganizationService(EmployeeDirectory ed, HierarchyDirectory hd) {
        this.employeeDirectory = ed;
        this.hierarchyDirectory = hd;
    }

    // Add employee
    public void addEmployee(int id, String name, Integer managerId) {
        if (managerId != null && !employeeDirectory.exists(managerId)) {
            throw new IllegalArgumentException("Manager does not exist");
        }

        Employee employee = new Employee(id, name, managerId);
        employeeDirectory.add(employee);
        hierarchyDirectory.addReport(managerId, employee);
    }

    // Get manager of employee
    public Integer getManager(int empId) {
        Employee employee = employeeDirectory.get(empId);
        return employee != null ? employee.getManagerId() : null;
    }

    // Remove employee and reassign subordinates
    public void removeEmployee(int empId) {
        Employee employee = employeeDirectory.get(empId);
        if (employee == null) return;

        Integer managerId = employee.getManagerId();

        // Reassign subordinates
        List<Employee> reports = hierarchyDirectory.getDirectReports(empId);
        for (Employee report : reports) {
            hierarchyDirectory.addReport(managerId, report);
        }

        hierarchyDirectory.removeManager(empId);
        hierarchyDirectory.removeReport(managerId, empId);
        employeeDirectory.remove(empId);
    }
}
