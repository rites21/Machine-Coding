package com.ritesh.lld.Streams;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Main {

    List<Employee> employees = List.of(new Employee("A", "IT", 50000),
            new Employee("B", "HR", 40000),
            new Employee("C", "IT", 70000),
            new Employee("D", "HR", 60000),
            new Employee("E", "IT", 60000));

//    Find the second-highest distinct salary.
//    Find the highest-paid employee in each department.
//    Group employees by department.
//    Calculate average salary by department.
//    Sort employees by salary descending, then name ascending.

    int secondHighest = employees.stream().map(Employee::salary).distinct().
            sorted(Comparator.reverseOrder()).skip(1).findFirst().orElseThrow();

    Map<String, Optional<Employee>> map = employees.stream().
            collect(Collectors.groupingBy(Employee::department,Collectors.maxBy(Comparator.comparingInt(Employee::salary))));



    Map<String, List<Employee>> map2 = employees.stream()
            .collect(Collectors.groupingBy(Employee::department));

    Map<String, Double> avgSalary = employees.stream().
            collect(Collectors.groupingBy(Employee::department,Collectors.averagingInt(Employee::salary)));

    List<Employee> ascList = employees.stream()
            .sorted(Comparator.comparingInt(Employee::salary))
            .toList();

    List<Employee> descList = employees.stream()
            .sorted(Comparator.comparingInt(Employee::salary).reversed())
            .toList();








}
