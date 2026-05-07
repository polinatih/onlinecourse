package com.coursePlatform.patterns.behavioral.chain;

/**
 * Запрос на запись — передаётся по цепочке обработчиков
 */
public class EnrollmentRequest {

    private String studentName;
    private int age;
    private boolean hasPaid;
    private boolean hasCompletedPrerequisites;

    public EnrollmentRequest(String studentName, int age, boolean hasPaid, boolean hasCompletedPrerequisites) {
        this.studentName = studentName;
        this.age = age;
        this.hasPaid = hasPaid;
        this.hasCompletedPrerequisites = hasCompletedPrerequisites;
    }

    public String getStudentName() { return studentName; }
    public int getAge() { return age; }
    public boolean hasPaid() { return hasPaid; }
    public boolean hasCompletedPrerequisites() { return hasCompletedPrerequisites; }
}
