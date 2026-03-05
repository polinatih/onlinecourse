package com.coursePlatform.patterns.factory;

import com.coursePlatform.model.course.*;
import org.springframework.stereotype.Component;


public abstract class CourseCreator {

    public abstract Course createCourse(String title, String description, String author,
                                        DifficultyLevel level, Integer durationHours,
                                        Object... extraParams);

    public Course registerCourse(String title, String description, String author,
                                  DifficultyLevel level, Integer durationHours,
                                  Object... extraParams) {
        Course course = createCourse(title, description, author, level, durationHours, extraParams);
        System.out.printf("[CourseCreator] Зарегистрирован курс '%s': %s%n",
                course.getCourseType(), course.getTitle());
        return course;
    }
}

@Component("videoCourseCreator")
class VideoCourseCreator extends CourseCreator {
    @Override
    public Course createCourse(String title, String description, String author,
                                DifficultyLevel level, Integer durationHours, Object... extraParams) {
        String videoUrl = extraParams.length > 0 && extraParams[0] != null ? (String) extraParams[0] : "";
        Integer minutes  = extraParams.length > 1 && extraParams[1] != null ? (Integer) extraParams[1] : 0;
        return new VideoCourse(null, title, description, author, level, durationHours, videoUrl, minutes);
    }
}

@Component("textCourseCreator")
class TextCourseCreator extends CourseCreator {
    @Override
    public Course createCourse(String title, String description, String author,
                                DifficultyLevel level, Integer durationHours, Object... extraParams) {
        Integer count = extraParams.length > 0 && extraParams[0] != null ? (Integer) extraParams[0] : 0;
        String pdfUrl  = extraParams.length > 1 && extraParams[1] != null ? (String) extraParams[1] : "";
        return new TextCourse(null, title, description, author, level, durationHours, count, pdfUrl);
    }
}

@Component("interactiveCourseCreator")
class InteractiveCourseCreator extends CourseCreator {
    @Override
    public Course createCourse(String title, String description, String author,
                                DifficultyLevel level, Integer durationHours, Object... extraParams) {
        Integer tasks     = extraParams.length > 0 && extraParams[0] != null ? (Integer) extraParams[0] : 0;
        Boolean simulator = extraParams.length > 1 && extraParams[1] != null ? (Boolean) extraParams[1] : false;
        return new InteractiveCourse(null, title, description, author, level, durationHours, tasks, simulator);
    }
}
