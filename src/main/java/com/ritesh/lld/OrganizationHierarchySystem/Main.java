package com.ritesh.lld.OrganizationHierarchySystem;

import com.ritesh.lld.OrganizationHierarchySystem.Models.EmployeeDirectory;
import com.ritesh.lld.OrganizationHierarchySystem.Models.HierarchyDirectory;
import com.ritesh.lld.OrganizationHierarchySystem.Models.OrganizationService;

public class Main {
    public static void main(String[] args) {

        OrganizationService service = new OrganizationService(
                new EmployeeDirectory(),
                new HierarchyDirectory()
        );

        service.addEmployee(1, "CEO", null);
        service.addEmployee(2, "Manager", 1);
        service.addEmployee(3, "Dev1", 2);
        service.addEmployee(4, "Dev2", 2);

        System.out.println("Manager of Dev1: " + service.getManager(3));

        service.removeEmployee(2); // Manager leaves
        System.out.println("New manager of Dev1: " + service.getManager(3));
    }
}
