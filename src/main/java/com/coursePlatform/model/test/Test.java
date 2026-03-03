package com.coursePlatform.model.test;

import com.coursePlatform.model.course.Course;
import com.coursePlatform.model.question.Question;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Сущность теста. Создаётся через паттерн BUILDER.
 */
@Entity
@Table(name = "tests")
public class Test {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(name = "time_limit_minutes")
    private Integer timeLimitMinutes;

    @Column(name = "passing_score")
    private Integer passingScore;

    @Column(name = "max_attempts")
    private Integer maxAttempts = 3;

    @Column(name = "is_randomized")
    private Boolean isRandomized = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
        name = "test_questions",
        joinColumns = @JoinColumn(name = "test_id"),
        inverseJoinColumns = @JoinColumn(name = "question_id")
    )
    private List<Question> questions = new ArrayList<>();

    // ─── Constructors ───────────────────────────────────────────
    public Test() {}

    // ─── Getters & Setters ──────────────────────────────────────
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public Integer getTimeLimitMinutes() { return timeLimitMinutes; }
    public void setTimeLimitMinutes(Integer timeLimitMinutes) { this.timeLimitMinutes = timeLimitMinutes; }

    public Integer getPassingScore() { return passingScore; }
    public void setPassingScore(Integer passingScore) { this.passingScore = passingScore; }

    public Integer getMaxAttempts() { return maxAttempts; }
    public void setMaxAttempts(Integer maxAttempts) { this.maxAttempts = maxAttempts; }

    public Boolean getIsRandomized() { return isRandomized; }
    public void setIsRandomized(Boolean isRandomized) { this.isRandomized = isRandomized; }

    public Course getCourse() { return course; }
    public void setCourse(Course course) { this.course = course; }

    public List<Question> getQuestions() { return questions; }
    public void setQuestions(List<Question> questions) { this.questions = questions; }
}
