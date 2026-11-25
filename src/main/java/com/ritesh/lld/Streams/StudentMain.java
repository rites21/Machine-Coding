package com.ritesh.lld.Streams;

import java.util.*;
import java.util.stream.Collectors;

public class StudentMain {
    public static void main(String[] args) {

//        Map<String, Double> containing average marks per student.
//        Map<String, String> containing each student's highest-scoring subject.
//        The overall highest-scoring student by average marks.


        List<Student> students = List.of(
                new Student("John", Map.of("Math", 80, "English", 90)),
                new Student("Mike", Map.of("Math", 70, "English", 80))
        );

        Map<String, Double> avgMarks = students.stream().collect(
                Collectors.toMap(
                        Student::name,
                        student -> student.marks().values().stream()
                                .mapToInt(Integer::intValue)
                                .average()
                                .orElse(0.0)));



        Optional<Student> topStudent = students.stream().max(Comparator.comparingDouble(
                student-> student.marks().values().stream()
                        .mapToInt(Integer::intValue)
                        .average()
                        .orElse(0.0)
        ));
        topStudent.ifPresent(System.out::println);
    }
}
