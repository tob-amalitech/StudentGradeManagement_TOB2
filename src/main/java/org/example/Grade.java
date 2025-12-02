package org.example;

// Grade class
class Grade {
    private static int counter = 0;
    private int gradeId;
    private int studentId;
    private Subject subject;
    private double score;
    private String gradeLevel;
    private boolean passing;

    public Grade(int studentId, Subject subject, double score, String gradeLevel, boolean passing) {
        this.gradeId = ++counter;
        this.studentId = studentId;
        this.subject = subject;
        this.score = score;
        this.gradeLevel = gradeLevel;
        this.passing = passing;
    }

    public int getStudentId() {
        return studentId;
    }

    public Subject getSubject() {
        return subject;
    }

    public double getScore() {
        return score;
    }

    public String getGradeLevel() {
        return gradeLevel;
    }

    public boolean isPassing() {
        return passing;
    }
}
