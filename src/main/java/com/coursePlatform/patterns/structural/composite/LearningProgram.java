package com.coursePlatform.patterns.structural.composite;

import java.util.ArrayList;
import java.util.List;

/**
 * ПАТТЕРН COMPOSITE — корневой контейнер.
 * Вся учебная программа: содержит разделы, которые содержат курсы.
 * Клиентский код вызывает display() и getCourseCount() одинаково
 * на любом уровне дерева — не зная, лист это или контейнер.
 */
public class LearningProgram implements CurriculumComponent {

    private final String name;
    private final String description;
    private final List<CurriculumComponent> sections = new ArrayList<>();

    public LearningProgram(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void addSection(CurriculumComponent section) {
        sections.add(section);
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getTotalDurationHours() {
        return sections.stream()
                .mapToInt(CurriculumComponent::getTotalDurationHours)
                .sum();
    }

    @Override
    public int getCourseCount() {
        return sections.stream()
                .mapToInt(CurriculumComponent::getCourseCount)
                .sum();
    }

    @Override
    public void display(int depth) {
        String indent = "  ".repeat(depth);
        System.out.printf("%n%s[Программа] %s%n", indent, name);
        System.out.printf("%s  %s%n", indent, description);
        System.out.printf("%s  Итого: %d курсов | %d часов%n%n",
                indent, getCourseCount(), getTotalDurationHours());
        sections.forEach(section -> section.display(depth + 1));
    }
}