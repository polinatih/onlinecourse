package com.coursePlatform.patterns.structural.composite;

import com.coursePlatform.model.course.Course;

/**
 * ПАТТЕРН COMPOSITE — лист (Leaf).
 * Представляет один курс — конечный узел дерева.
 */
public class CourseLeaf implements CurriculumComponent {

    private final Course course;

    public CourseLeaf(Course course) {
        this.course = course;
    }

    @Override
    public String getName() {
        return course.getTitle();
    }

    @Override
    public int getTotalDurationHours() {
        return course.getDurationHours() != null ? course.getDurationHours() : 0;
    }

    @Override
    public int getCourseCount() {
        return 1;
    }

    @Override
    public void display(int depth) {
        String indent = "  ".repeat(depth);
        System.out.printf("%s[Курс] %s [%s, %d ч.]%n",
                indent, course.getTitle(),
                course.getCourseType(),
                getTotalDurationHours());
    }
}