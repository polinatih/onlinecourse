package com.coursePlatform.controller;

import com.coursePlatform.model.course.Course;
import com.coursePlatform.model.course.DifficultyLevel;
import com.coursePlatform.model.user.User;
import com.coursePlatform.patterns.behavioral.observer.CourseNotificationService;
import com.coursePlatform.patterns.behavioral.observer.UserNotificationListener;
import com.coursePlatform.patterns.behavioral.strategy.CoursePricingService;
import com.coursePlatform.patterns.behavioral.strategy.PriceResult;
import com.coursePlatform.patterns.behavioral.template.InteractiveLesson;
import com.coursePlatform.patterns.behavioral.template.LessonTemplate;
import com.coursePlatform.patterns.behavioral.template.TextLesson;
import com.coursePlatform.patterns.behavioral.template.VideoLesson;
import com.coursePlatform.repository.UserRepository;
import com.coursePlatform.service.CourseService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/courses")
public class CourseController {

    private final CourseService courseService;
    private final CoursePricingService pricingService;
    private final CourseNotificationService notificationService;
    private final UserRepository userRepository;
    private final VideoLesson videoLesson;
    private final TextLesson textLesson;
    private final InteractiveLesson interactiveLesson;

    public CourseController(CourseService courseService,
                            CoursePricingService pricingService,
                            CourseNotificationService notificationService,
                            UserRepository userRepository,
                            VideoLesson videoLesson,
                            TextLesson textLesson,
                            InteractiveLesson interactiveLesson) {
        this.courseService       = courseService;
        this.pricingService      = pricingService;
        this.notificationService = notificationService;
        this.userRepository      = userRepository;
        this.videoLesson         = videoLesson;
        this.textLesson          = textLesson;
        this.interactiveLesson   = interactiveLesson;
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
    public String courseDetail(@PathVariable Long id,
                               HttpSession httpSession,
                               Model model) {
        Course course = courseService.getCourseById(id)
                .orElseThrow(() -> new IllegalArgumentException("Курс не найден"));

        Long userId = (Long) httpSession.getAttribute("userId");
        User currentUser = userId != null
                ? userRepository.findById(userId).orElse(null)
                : null;

        // ПАТТЕРН STRATEGY: рассчитываем цену со скидкой
        PriceResult price = pricingService.calculatePrice(course, currentUser);

        // ПАТТЕРН OBSERVER: данные подписки
        int subscriberCount = notificationService.getSubscriberCount(id);
        List<String> eventLog = notificationService.getEventLog(id);
        boolean isSubscribed = currentUser != null
                && notificationService.isSubscribed(id, currentUser.getUsername());

        // ПАТТЕРН TEMPLATE METHOD: выбираем шаблон урока по типу курса
        LessonTemplate lessonTemplate = selectTemplate(course.getCourseType());
        List<String> lessonSteps = lessonTemplate.getSteps();

        model.addAttribute("course",          course);
        model.addAttribute("currentUserId",   userId);
        model.addAttribute("price",           price);
        model.addAttribute("subscriberCount", subscriberCount);
        model.addAttribute("eventLog",        eventLog);
        model.addAttribute("isSubscribed",    isSubscribed);
        model.addAttribute("lessonSteps",     lessonSteps);
        model.addAttribute("lessonType",      lessonTemplate.getLessonType());
        model.addAttribute("lessonIcon",      lessonTemplate.getIcon());

        return "courses/detail";
    }

    // ПАТТЕРН OBSERVER: подписаться на курс
    @PostMapping("/{id}/subscribe")
    public String subscribe(@PathVariable Long id, HttpSession httpSession) {
        Long userId = (Long) httpSession.getAttribute("userId");
        if (userId != null) {
            userRepository.findById(userId).ifPresent(user -> {
                UserNotificationListener listener =
                        new UserNotificationListener(user.getUsername());
                notificationService.subscribe(id, listener);
                notificationService.notifySubscribers(id, "NEW_SUBSCRIBER",
                        user.getUsername() + " подписался на курс");
            });
        }
        return "redirect:/courses/" + id;
    }

    // ПАТТЕРН OBSERVER: отписаться от курса
    @PostMapping("/{id}/unsubscribe")
    public String unsubscribe(@PathVariable Long id, HttpSession httpSession) {
        Long userId = (Long) httpSession.getAttribute("userId");
        if (userId != null) {
            userRepository.findById(userId).ifPresent(user ->
                    notificationService.unsubscribe(id, user.getUsername()));
        }
        return "redirect:/courses/" + id;
    }

    @PostMapping("/{id}/delete")
    public String deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return "redirect:/courses";
    }

    private LessonTemplate selectTemplate(String courseType) {
        if (courseType == null) return videoLesson;
        return switch (courseType.toLowerCase()) {
            case "textcourse"        -> textLesson;
            case "interactivecourse" -> interactiveLesson;
            default                  -> videoLesson;
        };
    }
}