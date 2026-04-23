package com.coursePlatform.patterns.structural.composite;

import java.util.ArrayList;
import java.util.List;

/**
 * ПАТТЕРН COMPOSITE — контейнер (Composite).
 * Раздел программы: содержит курсы или другие разделы.
 * Рекурсивно агрегирует данные дочерних компонентов.
 */
public class CurriculumSection implements CurriculumComponent {

    private final String name;
    private final List<CurriculumComponent> children = new ArrayList<>();

    public CurriculumSection(String name) {
        this.name = name;
    }

    public void add(CurriculumComponent component) {
        children.add(component);
    }

    public void remove(CurriculumComponent component) {
        children.remove(component);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getTotalDurationHours() {
        return children.stream()
                .mapToInt(CurriculumComponent::getTotalDurationHours)
                .sum();
    }

    @Override
    public int getCourseCount() {
        return children.stream()
                .mapToInt(CurriculumComponent::getCourseCount)
                .sum();
    }

    @Override
    public void display(int depth) {
        String indent = "  ".repeat(depth);
        System.out.printf("%s[Раздел] %s (%d курсов, %d ч.)%n",
                indent, name, getCourseCount(), getTotalDurationHours());
        children.forEach(child -> child.display(depth + 1));
    }
}