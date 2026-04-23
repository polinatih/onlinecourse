package com.coursePlatform.patterns.structural.composite;

import com.coursePlatform.model.course.Course;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * ПАТТЕРН COMPOSITE — клиентский код.
 * Spring-компонент для построения учебных программ.
 * Работает с CurriculumComponent единообразно на любом уровне дерева.
 */
@Component
public class CurriculumBuilder {

    /**
     * Строит учебную программу из двух групп курсов.
     * Демонстрирует прозрачность Composite: display() вызывается
     * одним методом на всём дереве.
     */
    public LearningProgram buildProgram(String programName,
                                        String description,
                                        List<Course> beginnerCourses,
                                        List<Course> advancedCourses) {
        LearningProgram program = new LearningProgram(programName, description);

        CurriculumSection beginnerSection = new CurriculumSection("Для начинающих");
        beginnerCourses.stream()
                .map(CourseLeaf::new)
                .forEach(beginnerSection::add);

        CurriculumSection advancedSection = new CurriculumSection("Продвинутый уровень");
        advancedCourses.stream()
                .map(CourseLeaf::new)
                .forEach(advancedSection::add);

        program.addSection(beginnerSection);
        program.addSection(advancedSection);

        program.display(0);

        System.out.printf("[CurriculumComposite] Программа '%s': %d курсов, %d ч.%n",
                programName, program.getCourseCount(), program.getTotalDurationHours());

        return program;
    }
}