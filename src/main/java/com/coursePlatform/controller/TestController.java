package com.coursePlatform.controller;

import com.coursePlatform.service.CourseService;
import com.coursePlatform.service.TestService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
class HomeController {

    private final CourseService courseService;

    HomeController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("courseCount", courseService.getAllCourses().size());
        return "home";
    }
}

@Controller
@RequestMapping("/tests")
class TestController {

    private final TestService testService;
    private final CourseService courseService;

    TestController(TestService testService, CourseService courseService) {
        this.testService = testService;
        this.courseService = courseService;
    }

    @GetMapping("/create")
    public String showCreateForm(@RequestParam Long courseId, Model model) {
        model.addAttribute("course", courseService.getCourseById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Курс не найден")));
        model.addAttribute("factories", new String[]{
                "beginnerQuestionFactory", "advancedQuestionFactory"
        });
        return "tests/create";
    }

    @PostMapping("/create")
    public String createTest(@RequestParam Long courseId, @RequestParam String title,
                              @RequestParam int timeLimitMinutes, @RequestParam int passingScore,
                              @RequestParam String factoryType) {
        testService.createTest(courseId, title, timeLimitMinutes, passingScore, factoryType);
        return "redirect:/courses/" + courseId;
    }

    @GetMapping("/{id}")
    public String testDetail(@PathVariable Long id, Model model) {
        model.addAttribute("test", testService.getTestById(id)
                .orElseThrow(() -> new IllegalArgumentException("Тест не найден")));
        model.addAttribute("prototypes", testService.getAvailablePrototypes());
        return "tests/detail";
    }

    @PostMapping("/{id}/clone-question")
    public String cloneQuestion(@PathVariable Long id, @RequestParam String prototypeKey) {
        testService.addClonedQuestion(id, prototypeKey);
        return "redirect:/tests/" + id;
    }
}
