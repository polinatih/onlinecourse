package com.coursePlatform.patterns.structural.composite;

/**
 * ПАТТЕРН COMPOSITE — компонент (Component).
 * Единый интерфейс для листа (курс) и контейнеров (раздел, программа).
 */
public interface CurriculumComponent {
    String getName();
    int getTotalDurationHours();
    int getCourseCount();
    void display(int depth);
}