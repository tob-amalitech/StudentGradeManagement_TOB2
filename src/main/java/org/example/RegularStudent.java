package org.example;

// Regular student
public class RegularStudent extends Student {

    public RegularStudent(String name, int age) {
        super(name, age);
    }

    @Override
    public String getStudentType() {
        return "Regular";
    }

    @Override
    public void displayStudentDetails() {
        System.out.println("ID: " + getStudentId() + " | Name: " + getName() + " | Age: " + getAge() + " | Type: " + getStudentType());
    }

    @Override
    public boolean isPassing(double grade) {
        if (grade >= 50) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String getGradeLevel(double grade) {
        if (grade >= 90) {
            return "A";
        } else if (grade >= 80) {
            return "B";
        } else if (grade >= 70) {
            return "C";
        } else if (grade >= 60) {
            return "D";
        } else if (grade >= 50) {
            return "E";
        } else {
            return "F";
        }
    }
}
