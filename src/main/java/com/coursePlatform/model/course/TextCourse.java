package com.coursePlatform.model.course;

import jakarta.persistence.*;

/** Конкретный продукт паттерна FACTORY METHOD — Текстовый курс */
@Entity
@DiscriminatorValue("TEXT")
public class TextCourse extends Course {

    @Column(name = "article_count")
    private Integer articleCount;

    @Column(name = "pdf_url")
    private String pdfUrl;

    public TextCourse() {}

    public TextCourse(Long id, String title, String description, String author,
                      DifficultyLevel difficultyLevel, Integer durationHours,
                      Integer articleCount, String pdfUrl) {
        super(id, title, description, author, difficultyLevel, durationHours);
        this.articleCount = articleCount;
        this.pdfUrl = pdfUrl;
    }

    @Override
    public String getCourseType() { return "Текстовый курс"; }

    @Override
    public String getContentDescription() {
        return "Статей: " + articleCount + ", PDF: " + pdfUrl;
    }

    public Integer getArticleCount() { return articleCount; }
    public void setArticleCount(Integer articleCount) { this.articleCount = articleCount; }

    public String getPdfUrl() { return pdfUrl; }
    public void setPdfUrl(String pdfUrl) { this.pdfUrl = pdfUrl; }
}
