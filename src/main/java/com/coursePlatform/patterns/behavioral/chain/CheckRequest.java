package com.coursePlatform.patterns.behavioral.chain;

import java.util.ArrayList;
import java.util.List;

public class CheckRequest {
    private final String username;
    private final int age;
    private final boolean hasPaid;
    private final int completedCourses;

    public CheckRequest(String username, int age, boolean hasPaid, int completedCourses) {
        this.username         = username;
        this.age              = age;
        this.hasPaid          = hasPaid;
        this.completedCourses = completedCourses;
    }

    public String getUsername()        { return username; }
    public int getAge()                { return age; }
    public boolean isHasPaid()         { return hasPaid; }
    public int getCompletedCourses()   { return completedCourses; }
}
