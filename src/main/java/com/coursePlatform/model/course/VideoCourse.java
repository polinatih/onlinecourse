package com.coursePlatform.model.course;

import jakarta.persistence.*;

/** Конкретный продукт паттерна FACTORY METHOD — Видеокурс */
@Entity
@DiscriminatorValue("VIDEO")
public class VideoCourse extends Course {

    @Column(name = "video_url")
    private String videoUrl;

    @Column(name = "video_duration_minutes")
    private Integer videoDurationMinutes;

    public VideoCourse() {}

    public VideoCourse(Long id, String title, String description, String author,
                       DifficultyLevel difficultyLevel, Integer durationHours,
                       String videoUrl, Integer videoDurationMinutes) {
        super(id, title, description, author, difficultyLevel, durationHours);
        this.videoUrl = videoUrl;
        this.videoDurationMinutes = videoDurationMinutes;
    }

    @Override
    public String getCourseType() { return "Видеокурс"; }

    @Override
    public String getContentDescription() {
        return "Видео-материалы (" + videoDurationMinutes + " мин.), URL: " + videoUrl;
    }

    public String getVideoUrl() { return videoUrl; }
    public void setVideoUrl(String videoUrl) { this.videoUrl = videoUrl; }

    public Integer getVideoDurationMinutes() { return videoDurationMinutes; }
    public void setVideoDurationMinutes(Integer videoDurationMinutes) { this.videoDurationMinutes = videoDurationMinutes; }
}
