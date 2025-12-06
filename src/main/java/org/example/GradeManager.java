package org.example;

import java.io.*;
import java.util.Arrays;

// Manages grades
public class GradeManager {
    private Grade[] grades;
    private int count;
    
    public GradeManager() {
        grades = new Grade[100];
        count = 0;
    }
    
    public void recordGrade(Student student, Subject subject, double score) {
        String level = student.getGradeLevel(score);
        boolean pass = student.isPassing(score);
        double gpa = student.calculateGPA(score); // NEW: Calculate GPA
        
        if (count < grades.length) {
            grades[count++] = new Grade(student.getStudentId(), subject, score, level, pass, gpa);
            
            System.out.println("\nGrade recorded!");
            System.out.println("Student: " + student.getName());
            System.out.println("Subject: " + subject.getSubjectName());
            System.out.println("Score: " + score);
            System.out.println("Grade: " + level);
            System.out.println("GPA: " + String.format("%.2f", gpa)); // NEW: Show GPA
            System.out.println("Status: " + (pass ? "PASS" : "FAIL"));
        } else {
            System.out.println("Grade storage is full!");
        }
    }
    
    public void viewGradeReport(Student student) {
        System.out.println("\n--- Grade Report ---");
        student.displayStudentDetails();
        System.out.println("\nGrades:");
        
        int gradeCount = 0;
        double total = 0;
        double totalGPA = 0; // NEW: Track total GPA
        
        for (int i = 0; i < count; i++) {
            if (grades[i].getStudentId() == student.getStudentId()) {
                System.out.println(grades[i].getSubject().getSubjectName() + ": " + 
                                 grades[i].getScore() + " (" + grades[i].getGradeLevel() + ") - GPA: " + 
                                 String.format("%.2f", grades[i].getGpa()));
                total += grades[i].getScore();
                totalGPA += grades[i].getGpa(); // NEW: Add to GPA total
                gradeCount++;
            }
        }
        
        if (gradeCount > 0) {
            double average = total / gradeCount;
            double avgGPA = totalGPA / gradeCount; // NEW: Calculate average GPA
            System.out.println("\nAverage Score: " + String.format("%.2f", average));
            System.out.println("Average GPA: " + String.format("%.2f", avgGPA)); // NEW: Display GPA
        } else {
            System.out.println("No grades yet.");
        }
    }
    
    // NEW: Export grade report to text file
    public void exportGradeReport(Student student) {
        String filename = "GradeReport_" + student.getStudentId() + "_" + student.getName().replace(" ", "_") + ".txt";
        
        try {
            FileWriter writer = new FileWriter(filename);
            
            writer.write("========================================\n");
            writer.write("       GRADE REPORT\n");
            writer.write("========================================\n\n");
            writer.write("Student ID: " + student.getStudentId() + "\n");
            writer.write("Name: " + student.getName() + "\n");
            writer.write("Age: " + student.getAge() + "\n");
            writer.write("Type: " + student.getStudentType() + "\n");
            writer.write("\n========================================\n");
            writer.write("       GRADES\n");
            writer.write("========================================\n\n");
            
            int gradeCount = 0;
            double total = 0;
            double totalGPA = 0;
            
            for (int i = 0; i < count; i++) {
                if (grades[i].getStudentId() == student.getStudentId()) {
                    writer.write(String.format("%-20s: %6.2f (%2s) - GPA: %.2f\n",
                               grades[i].getSubject().getSubjectName(),
                               grades[i].getScore(),
                               grades[i].getGradeLevel(),
                               grades[i].getGpa()));
                    total += grades[i].getScore();
                    totalGPA += grades[i].getGpa();
                    gradeCount++;
                }
            }
            
            if (gradeCount > 0) {
                double average = total / gradeCount;
                double avgGPA = totalGPA / gradeCount;
                writer.write("\n========================================\n");
                writer.write("       SUMMARY\n");
                writer.write("========================================\n\n");
                writer.write("Total Subjects: " + gradeCount + "\n");
                writer.write("Average Score: " + String.format("%.2f", average) + "\n");
                writer.write("Average GPA: " + String.format("%.2f", avgGPA) + "\n");
            } else {
                writer.write("\nNo grades recorded yet.\n");
            }
            
            writer.write("\n========================================\n");
            writer.write("End of Report\n");
            writer.write("========================================\n");
            
            writer.close();
            System.out.println("\n✓ Grade report exported successfully to: " + filename);
            
        } catch (IOException e) {
            System.out.println("Error exporting grade report: " + e.getMessage());
        }
    }
    
    // NEW: Bulk import grades from CSV file
    public void bulkImportGrades(StudentManager studentManager) {
        System.out.print("\nEnter CSV filename (e.g., grades.csv): ");
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        String filename = scanner.nextLine();
        
        int successCount = 0;
        int failCount = 0;
        
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            int lineNumber = 0;
            
            System.out.println("\nImporting grades from: " + filename);
            System.out.println("Expected format: StudentID,SubjectName,SubjectCode,SubjectType,Score");
            System.out.println("Example: 1,Math,MATH101,Core,85.5\n");
            
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                
                // Skip empty lines
                if (line.trim().isEmpty()) {
                    continue;
                }
                
                // Skip header line if it exists
                if (lineNumber == 1 && line.toLowerCase().contains("student")) {
                    continue;
                }
                
                String[] parts = line.split(",");
                
                if (parts.length != 5) {
                    System.out.println("Line " + lineNumber + " skipped - invalid format: " + line);
                    failCount++;
                    continue;
                }
                
                try {
                    int studentId = Integer.parseInt(parts[0].trim());
                    String subjectName = parts[1].trim();
                    String subjectCode = parts[2].trim();
                    String subjectType = parts[3].trim();
                    double score = Double.parseDouble(parts[4].trim());
                    
                    // Find student
                    Student student = studentManager.findStudent(studentId);
                    if (student == null) {
                        System.out.println("Line " + lineNumber + " skipped - student ID " + studentId + " not found");
                        failCount++;
                        continue;
                    }
                    
                    // Create subject
                    Subject subject;
                    if (subjectType.equalsIgnoreCase("Core")) {
                        subject = new CoreSubject(subjectName, subjectCode);
                    } else if (subjectType.equalsIgnoreCase("Elective")) {
                        subject = new ElectiveSubject(subjectName, subjectCode);
                    } else {
                        System.out.println("Line " + lineNumber + " skipped - invalid subject type: " + subjectType);
                        failCount++;
                        continue;
                    }
                    
                    // Validate score
                    if (score < 0 || score > 100) {
                        System.out.println("Line " + lineNumber + " skipped - invalid score: " + score);
                        failCount++;
                        continue;
                    }
                    
                    // Record grade
                    recordGrade(student, subject, score);
                    successCount++;
                    
                } catch (NumberFormatException e) {
                    System.out.println("Line " + lineNumber + " skipped - invalid number format: " + line);
                    failCount++;
                }
            }
            
            reader.close();
            
            System.out.println("\n========================================");
            System.out.println("Import Complete!");
            System.out.println("Successfully imported: " + successCount + " grades");
            System.out.println("Failed: " + failCount + " records");
            System.out.println("========================================");
            
        } catch (FileNotFoundException e) {
            System.out.println("Error: File not found - " + filename);
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
    
    // NEW: Calculate and display grade statistics
    public void viewGradeStatistics(StudentManager studentManager) {
        if (count == 0) {
            System.out.println("\nNo grades recorded yet!");
            return;
        }
        
        System.out.println("\n========================================");
        System.out.println("       GRADE STATISTICS");
        System.out.println("========================================\n");
        
        // Collect all scores
        double[] allScores = new double[count];
        for (int i = 0; i < count; i++) {
            allScores[i] = grades[i].getScore();
        }
        
        // Sort scores for median calculation
        Arrays.sort(allScores);
        
        // Calculate statistics
        double sum = 0;
        double highest = allScores[count - 1];
        double lowest = allScores[0];
        
        for (int i = 0; i < count; i++) {
            sum += allScores[i];
        }
        double mean = sum / count;
        
        // Calculate median
        double median;
        if (count % 2 == 0) {
            median = (allScores[count/2 - 1] + allScores[count/2]) / 2.0;
        } else {
            median = allScores[count/2];
        }
        
        // Calculate standard deviation
        double sumSquaredDiff = 0;
        for (int i = 0; i < count; i++) {
            double diff = allScores[i] - mean;
            sumSquaredDiff += diff * diff;
        }
        double stdDev = Math.sqrt(sumSquaredDiff / count);
        
        // Count passing/failing grades
        int passing = 0;
        int failing = 0;
        for (int i = 0; i < count; i++) {
            if (grades[i].isPassing()) {
                passing++;
            } else {
                failing++;
            }
        }
        
        // Display statistics
        System.out.println("Total Grades Recorded: " + count);
        System.out.println("Total Students: " + studentManager.getStudentCount());
        System.out.println("\nScore Statistics:");
        System.out.println("  Highest Score: " + String.format("%.2f", highest));
        System.out.println("  Lowest Score: " + String.format("%.2f", lowest));
        System.out.println("  Mean (Average): " + String.format("%.2f", mean));
        System.out.println("  Median: " + String.format("%.2f", median));
        System.out.println("  Standard Deviation: " + String.format("%.2f", stdDev));
        System.out.println("\nPass/Fail Statistics:");
        System.out.println("  Passing: " + passing + " (" + String.format("%.1f", (passing * 100.0 / count)) + "%)");
        System.out.println("  Failing: " + failing + " (" + String.format("%.1f", (failing * 100.0 / count)) + "%)");
        
        // Grade distribution
        System.out.println("\nGrade Distribution:");
        int[] gradeDistribution = new int[6]; // A, B, C, D, E, F
        for (int i = 0; i < count; i++) {
            String grade = grades[i].getGradeLevel();
            if (grade.startsWith("A")) gradeDistribution[0]++;
            else if (grade.startsWith("B")) gradeDistribution[1]++;
            else if (grade.startsWith("C")) gradeDistribution[2]++;
            else if (grade.startsWith("D")) gradeDistribution[3]++;
            else if (grade.startsWith("E")) gradeDistribution[4]++;
            else if (grade.startsWith("F")) gradeDistribution[5]++;
        }
        
        System.out.println("  A: " + gradeDistribution[0] + " (" + String.format("%.1f", (gradeDistribution[0] * 100.0 / count)) + "%)");
        System.out.println("  B: " + gradeDistribution[1] + " (" + String.format("%.1f", (gradeDistribution[1] * 100.0 / count)) + "%)");
        System.out.println("  C: " + gradeDistribution[2] + " (" + String.format("%.1f", (gradeDistribution[2] * 100.0 / count)) + "%)");
        System.out.println("  D: " + gradeDistribution[3] + " (" + String.format("%.1f", (gradeDistribution[3] * 100.0 / count)) + "%)");
        System.out.println("  E: " + gradeDistribution[4] + " (" + String.format("%.1f", (gradeDistribution[4] * 100.0 / count)) + "%)");
        System.out.println("  F: " + gradeDistribution[5] + " (" + String.format("%.1f", (gradeDistribution[5] * 100.0 / count)) + "%)");
        
        System.out.println("\n========================================");
    }
}
