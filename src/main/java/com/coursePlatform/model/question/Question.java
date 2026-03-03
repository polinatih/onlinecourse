package com.coursePlatform.model.question;

import jakarta.persistence.*;

/**
 * Сущность вопроса.
 * Реализует Cloneable для паттерна PROTOTYPE.
 */
@Entity
@Table(name = "questions")
public class Question implements Cloneable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 1000)
    private String text;

    @Enumerated(EnumType.STRING)
    @Column(name = "question_type", nullable = false)
    private QuestionType questionType;

    @Column(name = "answer_options", length = 2000)
    private String answerOptions;

    @Column(name = "correct_answer", length = 500)
    private String correctAnswer;

    @Column(nullable = false)
    private Integer points = 1;

    @Column(name = "difficulty_score")
    private Integer difficultyScore = 1;

    // ─── Constructors ───────────────────────────────────────────
    public Question() {}

    public Question(String text, QuestionType questionType, String answerOptions,
                    String correctAnswer, Integer points, Integer difficultyScore) {
        this.text = text;
        this.questionType = questionType;
        this.answerOptions = answerOptions;
        this.correctAnswer = correctAnswer;
        this.points = points;
        this.difficultyScore = difficultyScore;
    }

    /**
     * ПАТТЕРН PROTOTYPE — создаёт копию вопроса.
     * id = null чтобы JPA создал новую запись в БД.
     */
    @Override
    public Question clone() {
        try {
            Question cloned = (Question) super.clone();
            cloned.id = null;
            return cloned;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Ошибка клонирования вопроса", e);
        }
    }

    // ─── Getters & Setters ──────────────────────────────────────
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    public QuestionType getQuestionType() { return questionType; }
    public void setQuestionType(QuestionType questionType) { this.questionType = questionType; }

    public String getAnswerOptions() { return answerOptions; }
    public void setAnswerOptions(String answerOptions) { this.answerOptions = answerOptions; }

    public String getCorrectAnswer() { return correctAnswer; }
    public void setCorrectAnswer(String correctAnswer) { this.correctAnswer = correctAnswer; }

    public Integer getPoints() { return points; }
    public void setPoints(Integer points) { this.points = points; }

    public Integer getDifficultyScore() { return difficultyScore; }
    public void setDifficultyScore(Integer difficultyScore) { this.difficultyScore = difficultyScore; }
}
