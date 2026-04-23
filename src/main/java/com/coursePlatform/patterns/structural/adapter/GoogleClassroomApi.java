package com.coursePlatform.patterns.structural.adapter;

import java.util.List;

/**
 * ПАТТЕРН ADAPTER — адаптируемый класс (Adaptee).
 * Имитирует внешний API Google Classroom с несовместимым интерфейсом.
 */
public class GoogleClassroomApi {

    public void createClassroom(String name, String ownerId, String description) {
        System.out.printf("[Google Classroom] Класс создан: %s (владелец: %s)%n", name, ownerId);
    }

    public void inviteStudent(String classroomId, String studentEmail) {
        System.out.printf("[Google Classroom] Приглашение отправлено: %s → classroom:%s%n",
                studentEmail, classroomId);
    }

    public List<String> listClassrooms(String teacherId) {
        return List.of("GC: Python для начинающих", "GC: Machine Learning");
    }
}