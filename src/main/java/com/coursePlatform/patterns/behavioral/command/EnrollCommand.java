package com.coursePlatform.patterns.behavioral.command;

public class EnrollCommand implements CourseCommand {

    private String studentName;
    private String courseName;
    private EnrollmentService enrollmentService;

    public EnrollCommand(String studentName, String courseName, EnrollmentService enrollmentService) {
        this.studentName       = studentName;
        this.courseName        = courseName;
        this.enrollmentService = enrollmentService;
    }

    @Override
    public void execute() {
        enrollmentService.enroll(studentName, courseName);
    }

    @Override
    public void undo() {
        enrollmentService.unenroll(studentName, courseName);
    }

    @Override
    public String getDescription() {
        return "Enroll: " + studentName + " -> " + courseName;
    }
}
