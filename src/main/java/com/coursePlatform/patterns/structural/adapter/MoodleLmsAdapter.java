package com.coursePlatform.patterns.structural.adapter;

import com.coursePlatform.model.course.Course;
import com.coursePlatform.model.user.User;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * ПАТТЕРН ADAPTER — конкретный адаптер для Moodle.
 * Оборачивает MoodleExternalApi под интерфейс LmsAdapter.
 */
@Component("moodleLmsAdapter")
public class MoodleLmsAdapter implements LmsAdapter {

    private final MoodleExternalApi moodle = new MoodleExternalApi();

    @Override
    public void publishCourse(Course course) {
        String shortName = "CRS-" + course.getId();
        moodle.moodleCreateCourse(shortName, course.getTitle(), course.getDescription());
    }

    @Override
    public void enrollStudent(User student, Course course) {
        String shortName = "CRS-" + course.getId();
        moodle.moodleEnrolUser(student.getEmail(), shortName, "student");
    }

    @Override
    public List<String> fetchExternalCourses() {
        return List.of(moodle.moodleGetCourses());
    }

    @Override
    public String getLmsName() {
        return "Moodle";
    }
}