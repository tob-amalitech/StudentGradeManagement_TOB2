package org.example;

// Manages students
class StudentManager {
    private Student[] students;
    private int count;

    public StudentManager() {
        students = new Student[50];
        count = 0;

        // Add some default students
        students[count++] = new RegularStudent("Alice", 18);
        students[count++] = new RegularStudent("Bob", 17);
        students[count++] = new RegularStudent("Charlie", 19);
        students[count++] = new HonorsStudent("Diana", 18);
        students[count++] = new HonorsStudent("Edward", 17);
    }

    public void addStudent(Student student) {
        students[count++] = student;
        System.out.println("Student added! ID: " + student.getStudentId());
    }

    public void viewAllStudents() {
        System.out.println("\n--- All Students ---");
        for (int i = 0; i < count; i++) {
            students[i].displayStudentDetails();
        }
    }

    public Student findStudent(int id) {
        for (int i = 0; i < count; i++) {
            if (students[i].getStudentId() == id) {
                return students[i];
            }
        }
        return null;
    }
}
