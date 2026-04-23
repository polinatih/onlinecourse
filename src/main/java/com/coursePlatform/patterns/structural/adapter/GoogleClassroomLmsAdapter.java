package com.coursePlatform.patterns.structural.adapter;

import com.coursePlatform.model.course.Course;
import com.coursePlatform.model.user.User;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * ПАТТЕРН ADAPTER — конкретный адаптер для Google Classroom.
 * Оборачивает GoogleClassroomApi под интерфейс LmsAdapter.
 */
@Component("googleClassroomLmsAdapter")
public class GoogleClassroomLmsAdapter implements LmsAdapter {

    private final GoogleClassroomApi googleClassroom = new GoogleClassroomApi();

    @Override
    public void publishCourse(Course course) {
        googleClassroom.createClassroom(
                course.getTitle(),
                course.getAuthor(),
                course.getDescription()
        );
    }

    @Override
    public void enrollStudent(User student, Course course) {
        googleClassroom.inviteStudent(course.getTitle(), student.getEmail());
    }

    @Override
    public List<String> fetchExternalCourses() {
        return googleClassroom.listClassrooms("default-teacher");
    }

    @Override
    public String getLmsName() {
        return "Google Classroom";
    }
}