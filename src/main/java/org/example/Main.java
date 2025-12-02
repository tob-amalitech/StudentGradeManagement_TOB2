package org.example;

import java.util.Scanner;

// Main program
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StudentManager studentManager = new StudentManager();
        GradeManager gradeManager = new GradeManager();

        System.out.println("Student Grade Management System");

        while (true) {
            System.out.println("\n=== Menu ===");
            System.out.println("1. View Students");
            System.out.println("2. Add Student");
            System.out.println("3. Record Grade");
            System.out.println("4. View Grade Report");
            System.out.println("5. Exit");
            System.out.print("Choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                studentManager.viewAllStudents();

            } else if (choice == 2) {
                System.out.println("\n1. Regular Student");
                System.out.println("2. Honors Student");
                System.out.print("Type: ");
                int type = scanner.nextInt();
                scanner.nextLine();

                System.out.print("Name: ");
                String name = scanner.nextLine();
                System.out.print("Age: ");
                int age = scanner.nextInt();
                scanner.nextLine();

                if (type == 1) {
                    studentManager.addStudent(new RegularStudent(name, age));
                } else {
                    studentManager.addStudent(new HonorsStudent(name, age));
                }

            } else if (choice == 3) {
                System.out.print("Student ID: ");
                int id = scanner.nextInt();
                scanner.nextLine();

                Student student = studentManager.findStudent(id);
                if (student == null) {
                    System.out.println("Student not found!");
                    continue;
                }

                System.out.println("\n1. Core Subject");
                System.out.println("2. Elective Subject");
                System.out.print("Type: ");
                int type = scanner.nextInt();
                scanner.nextLine();

                Subject subject = null;
                if (type == 1) {
                    System.out.println("1. Math  2. English  3. Science");
                    System.out.print("Choice: ");
                    int s = scanner.nextInt();
                    scanner.nextLine();
                    if (s == 1) {
                        subject = new CoreSubject("Math", "MATH101");
                    } else if (s == 2) {
                        subject = new CoreSubject("English", "ENG101");
                    } else {
                        subject = new CoreSubject("Science", "SCI101");
                    }
                } else {
                    System.out.println("1. Music  2. Art  3. PE");
                    System.out.print("Choice: ");
                    int s = scanner.nextInt();
                    scanner.nextLine();
                    if (s == 1) {
                        subject = new ElectiveSubject("Music", "MUS101");
                    } else if (s == 2) {
                        subject = new ElectiveSubject("Art", "ART101");
                    } else {
                        subject = new ElectiveSubject("PE", "PE101");
                    }
                }

                System.out.print("Score (0-100): ");
                double score = scanner.nextDouble();
                scanner.nextLine();

                gradeManager.recordGrade(student, subject, score);

            } else if (choice == 4) {
                System.out.print("Student ID: ");
                int id = scanner.nextInt();
                scanner.nextLine();

                Student student = studentManager.findStudent(id);
                if (student == null) {
                    System.out.println("Student not found!");
                    continue;
                }

                gradeManager.viewGradeReport(student);

            } else if (choice == 5) {
                System.out.println("Goodbye!");
                break;
            } else {
                System.out.println("Invalid choice!");
            }
        }

        scanner.close();
    }
}