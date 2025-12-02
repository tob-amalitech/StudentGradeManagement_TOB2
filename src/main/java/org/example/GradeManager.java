package org.example;

// Manages grades
class GradeManager {
    private Grade[] grades;
    private int count;

    public GradeManager() {
        grades = new Grade[100];
        count = 0;
    }

    public void recordGrade(Student student, Subject subject, double score) {
        String level = student.getGradeLevel(score);
        boolean pass = student.isPassing(score);

        grades[count++] = new Grade(student.getStudentId(), subject, score, level, pass);

        System.out.println("\nGrade recorded!");
        System.out.println("Student: " + student.getName());
        System.out.println("Subject: " + subject.getSubjectName());
        System.out.println("Score: " + score);
        System.out.println("Grade: " + level);
        if (pass) {
            System.out.println("Status: PASS");
        } else {
            System.out.println("Status: FAIL");
        }
    }

    public void viewGradeReport(Student student) {
        System.out.println("\n--- Grade Report ---");
        student.displayStudentDetails();
        System.out.println("\nGrades:");

        int gradeCount = 0;
        double total = 0;

        for (int i = 0; i < count; i++) {
            if (grades[i].getStudentId() == student.getStudentId()) {
                System.out.println(grades[i].getSubject().getSubjectName() + ": " + grades[i].getScore() + " (" + grades[i].getGradeLevel() + ")");
                total = total + grades[i].getScore();
                gradeCount++;
            }
        }

        if (gradeCount > 0) {
            double average = total / gradeCount;
            System.out.println("\nAverage: " + average);
        } else {
            System.out.println("No grades yet.");
        }
    }
}
