package org.example;

public interface Gradable {
    boolean isPassing(double grade);
    String getGradeLevel(double grade);
    double calculateGPA(double grade); // NEW: GPA calculation
}
