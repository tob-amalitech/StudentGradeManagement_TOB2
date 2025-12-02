package org.example;

// Honors student
class HonorsStudent extends Student {

    public HonorsStudent(String name, int age) {
        super(name, age);
    }

    @Override
    public String getStudentType() {
        return "Honors";
    }

    @Override
    public void displayStudentDetails() {
        System.out.println("ID: " + getStudentId() + " | Name: " + getName() + " | Age: " + getAge() + " | Type: " + getStudentType());
    }

    @Override
    public boolean isPassing(double grade) {
        if (grade >= 60) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String getGradeLevel(double grade) {
        if (grade >= 90) {
            return "A+";
        } else if (grade >= 80) {
            return "A";
        } else if (grade >= 70) {
            return "B";
        } else if (grade >= 60) {
            return "C";
        } else {
            return "F";
        }
    }
}
