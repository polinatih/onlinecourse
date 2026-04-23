package com.coursePlatform.model.test;

import com.coursePlatform.model.user.User;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "test_attempts")
public class TestAttempt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "test_id", nullable = false)
    private Test test;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private int earnedPoints;

    @Column(nullable = false)
    private int totalPoints;

    @Column(nullable = false)
    private int percentage;

    @Column(nullable = false)
    private boolean passed;

    @Column(nullable = false)
    private LocalDateTime attemptedAt = LocalDateTime.now();

    public TestAttempt() {}

    public TestAttempt(Test test, User user, int earnedPoints,
                       int totalPoints, int percentage, boolean passed) {
        this.test         = test;
        this.user         = user;
        this.earnedPoints = earnedPoints;
        this.totalPoints  = totalPoints;
        this.percentage   = percentage;
        this.passed       = passed;
        this.attemptedAt  = LocalDateTime.now();
    }

    public Long getId()                  { return id; }
    public Test getTest()                { return test; }
    public User getUser()                { return user; }
    public int getEarnedPoints()         { return earnedPoints; }
    public int getTotalPoints()          { return totalPoints; }
    public int getPercentage()           { return percentage; }
    public boolean isPassed()            { return passed; }
    public LocalDateTime getAttemptedAt(){ return attemptedAt; }
}