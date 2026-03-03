package com.coursePlatform.controller;

import com.coursePlatform.model.course.Course;
import com.coursePlatform.model.course.DifficultyLevel;
import com.coursePlatform.service.CourseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public String listCourses(Model model) {
        model.addAttribute("courses", courseService.getAllCourses());
        model.addAttribute("difficultyLevels", DifficultyLevel.values());
        return "courses/list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("difficultyLevels", DifficultyLevel.values());
        return "courses/create";
    }

    @PostMapping("/create")
    public String createCourse(
            @RequestParam String courseType,
            @RequestParam String title,
            @RequestParam(required = false) String description,
            @RequestParam String author,
            @RequestParam DifficultyLevel difficultyLevel,
            @RequestParam Integer durationHours,
            @RequestParam(required = false) String videoUrl,
            @RequestParam(required = false) Integer videoDurationMinutes,
            @RequestParam(required = false) Integer articleCount,
            @RequestParam(required = false) String pdfUrl,
            @RequestParam(required = false) Integer taskCount,
            @RequestParam(required = false) Boolean hasSimulator) {

        Object[] extraParams = switch (courseType) {
            case "videoCourseCreator"       -> new Object[]{videoUrl, videoDurationMinutes};
            case "textCourseCreator"        -> new Object[]{articleCount, pdfUrl};
            case "interactiveCourseCreator" -> new Object[]{taskCount, hasSimulator};
            default -> new Object[]{};
        };

        courseService.createCourse(courseType, title, description, author,
                difficultyLevel, durationHours, extraParams);
        return "redirect:/courses";
    }

    @GetMapping("/{id}")
    public String courseDetail(@PathVariable Long id, Model model) {
        Course course = courseService.getCourseById(id)
                .orElseThrow(() -> new IllegalArgumentException("Курс не найден"));
        model.addAttribute("course", course);
        return "courses/detail";
    }

    @PostMapping("/{id}/delete")
    public String deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return "redirect:/courses";
    }
}
