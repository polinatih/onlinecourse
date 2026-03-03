package com.coursePlatform.model.course;

import jakarta.persistence.*;

/** Конкретный продукт паттерна FACTORY METHOD — Интерактивный курс */
@Entity
@DiscriminatorValue("INTERACTIVE")
public class InteractiveCourse extends Course {

    @Column(name = "task_count")
    private Integer taskCount;

    @Column(name = "has_simulator")
    private Boolean hasSimulator;

    public InteractiveCourse() {}

    public InteractiveCourse(Long id, String title, String description, String author,
                              DifficultyLevel difficultyLevel, Integer durationHours,
                              Integer taskCount, Boolean hasSimulator) {
        super(id, title, description, author, difficultyLevel, durationHours);
        this.taskCount = taskCount;
        this.hasSimulator = hasSimulator;
    }

    @Override
    public String getCourseType() { return "Интерактивный курс"; }

    @Override
    public String getContentDescription() {
        return "Заданий: " + taskCount + (Boolean.TRUE.equals(hasSimulator) ? " + симулятор" : "");
    }

    public Integer getTaskCount() { return taskCount; }
    public void setTaskCount(Integer taskCount) { this.taskCount = taskCount; }

    public Boolean getHasSimulator() { return hasSimulator; }
    public void setHasSimulator(Boolean hasSimulator) { this.hasSimulator = hasSimulator; }
}
