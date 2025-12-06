package org.example;

// Student class - abstract
public abstract class Student implements Gradable {
    private static int counter = 0;
    private int studentId;
    private String name;
    private int age;
    
    public Student(String name, int age) {
        this.studentId = ++counter;
        this.name = name;
        this.age = age;
    }
    
    public int getStudentId() { 
        return studentId; 
    }
    
    public String getName() { 
        return name; 
    }
    
    public int getAge() { 
        return age; 
    }
    
    public abstract String getStudentType();
    public abstract void displayStudentDetails();
}
