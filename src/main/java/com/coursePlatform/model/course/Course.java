package com.coursePlatform.model.course;

import com.coursePlatform.model.test.Test;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Абстрактный базовый класс курса.
 * Основа для паттерна FACTORY METHOD.
 * SOLID OCP: новые типы курсов добавляются без изменения этого класса.
 */
@Entity
@Table(name = "courses")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "course_type", discriminatorType = DiscriminatorType.STRING)
public abstract class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(length = 2000)
    private String description;

    @Column(nullable = false)
    private String author;

    @Enumerated(EnumType.STRING)
    @Column(name = "difficulty_level")
    private DifficultyLevel difficultyLevel;

    @Column(name = "duration_hours")
    private Integer durationHours;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Test> tests = new ArrayList<>();

    // ─── Constructors ───────────────────────────────────────────
    public Course() {}

    public Course(Long id, String title, String description, String author,
                  DifficultyLevel difficultyLevel, Integer durationHours) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.author = author;
        this.difficultyLevel = difficultyLevel;
        this.durationHours = durationHours;
        this.tests = new ArrayList<>();
    }

    // ─── Abstract methods ───────────────────────────────────────
    public abstract String getCourseType();
    public abstract String getContentDescription();

    // ─── Getters & Setters ──────────────────────────────────────
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public DifficultyLevel getDifficultyLevel() { return difficultyLevel; }
    public void setDifficultyLevel(DifficultyLevel difficultyLevel) { this.difficultyLevel = difficultyLevel; }

    public Integer getDurationHours() { return durationHours; }
    public void setDurationHours(Integer durationHours) { this.durationHours = durationHours; }

    public List<Test> getTests() { return tests; }
    public void setTests(List<Test> tests) { this.tests = tests; }
}
