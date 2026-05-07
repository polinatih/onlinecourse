package com.coursePlatform.patterns.behavioral.command;

import java.util.HashSet;
import java.util.Set;

/**
 * Получатель (Receiver) — сервис управления записями
 */
import org.springframework.stereotype.Service;

@Service

public class EnrollmentService {

    private Set<String> enrollments = new HashSet<>();

    public void enroll(String studentName, String courseName) {
        String key = studentName + ":" + courseName;
        enrollments.add(key);
        System.out.println("[EnrollmentService] Студент «" + studentName + "» записан на курс «" + courseName + "»");
    }

    public void unenroll(String studentName, String courseName) {
        String key = studentName + ":" + courseName;
        enrollments.remove(key);
        System.out.println("[EnrollmentService] Студент «" + studentName + "» отписан от курса «" + courseName + "»");
    }

    public boolean isEnrolled(String studentName, String courseName) {
        return enrollments.contains(studentName + ":" + courseName);
    }
}
