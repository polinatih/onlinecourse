package com.coursePlatform.controller;

import com.coursePlatform.model.course.Course;
import com.coursePlatform.model.user.User;
import com.coursePlatform.model.user.UserRole;
import com.coursePlatform.patterns.behavioral.chain.CheckResult;
import com.coursePlatform.patterns.behavioral.chain.EnrollmentChainService;
import com.coursePlatform.patterns.behavioral.command.CommandHistoryService;
import com.coursePlatform.patterns.behavioral.command.EnrollCommand;
import com.coursePlatform.patterns.behavioral.command.EnrollmentService;
import com.coursePlatform.patterns.singleton.UserSessionManager;
import com.coursePlatform.patterns.structural.composite.CurriculumBuilder;
import com.coursePlatform.patterns.structural.composite.LearningProgram;
import com.coursePlatform.patterns.structural.decorator.UserProfile;
import com.coursePlatform.patterns.structural.decorator.UserProfileFactory;
import com.coursePlatform.patterns.structural.facade.EnrollmentFacade;
import com.coursePlatform.patterns.structural.proxy.CourseAccessProxy;
import com.coursePlatform.repository.CourseRepository;
import com.coursePlatform.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.UUID;

@Controller
public class UserController {

    private static final String SESSION_USER_ID = "userId";
    private static final String SESSION_ID_ATTR = "sessionId";

    private final UserRepository         userRepository;
    private final UserProfileFactory     profileFactory;
    private final EnrollmentFacade       enrollmentFacade;
    private final CourseAccessProxy      courseProxy;
    private final CourseRepository       courseRepository;
    private final CurriculumBuilder      curriculumBuilder;
    private final CommandHistoryService  commandHistoryService;
    private final EnrollmentChainService enrollmentChainService;
    private final EnrollmentService      enrollmentService;

    public UserController(UserRepository userRepository,
                          UserProfileFactory profileFactory,
                          EnrollmentFacade enrollmentFacade,
                          CourseAccessProxy courseProxy,
                          CourseRepository courseRepository,
                          CurriculumBuilder curriculumBuilder,
                          CommandHistoryService commandHistoryService,
                          EnrollmentChainService enrollmentChainService,
                          EnrollmentService enrollmentService) {
        this.userRepository         = userRepository;
        this.profileFactory         = profileFactory;
        this.enrollmentFacade       = enrollmentFacade;
        this.courseProxy            = courseProxy;
        this.courseRepository       = courseRepository;
        this.curriculumBuilder      = curriculumBuilder;
        this.commandHistoryService  = commandHistoryService;
        this.enrollmentChainService = enrollmentChainService;
        this.enrollmentService      = enrollmentService;
    }

    // ─────────────────────────────────────────────────────────────
    //  LOGIN
    // ─────────────────────────────────────────────────────────────

    @GetMapping("/login")
    public String showLogin(@RequestParam(required = false) String error, Model model) {
        if (error != null) model.addAttribute("error", "Неверный логин или пароль");
        return "auth/login";
    }

    @PostMapping("/login")
    public String doLogin(@RequestParam String username,
                          @RequestParam String password,
                          HttpSession httpSession,
                          RedirectAttributes ra) {
        return userRepository.findByUsername(username)
                .filter(u -> u.getPassword().equals(password))
                .map(user -> {
                    String sid = UUID.randomUUID().toString();
                    UserSessionManager.getInstance().openSession(sid, user);
                    httpSession.setAttribute(SESSION_USER_ID, user.getId());
                    httpSession.setAttribute(SESSION_ID_ATTR, sid);
                    return "redirect:/profile/" + user.getId();
                })
                .orElseGet(() -> {
                    ra.addAttribute("error", "1");
                    return "redirect:/login";
                });
    }

    // ─────────────────────────────────────────────────────────────
    //  REGISTER
    // ─────────────────────────────────────────────────────────────

    @GetMapping("/register")
    public String showRegister() { return "auth/register"; }

    @PostMapping("/register")
    public String doRegister(@RequestParam String username,
                             @RequestParam String password,
                             @RequestParam String email,
                             @RequestParam String fullName,
                             @RequestParam(defaultValue = "STUDENT") UserRole role,
                             RedirectAttributes ra) {
        if (userRepository.existsByUsername(username)) {
            ra.addFlashAttribute("error", "Пользователь с таким логином уже существует");
            return "redirect:/register";
        }
        User user = new User(null, username, password, email, role, fullName);
        userRepository.save(user);
        ra.addFlashAttribute("success", "Аккаунт создан! Войдите в систему.");
        return "redirect:/login";
    }

    // ─────────────────────────────────────────────────────────────
    //  LOGOUT
    // ─────────────────────────────────────────────────────────────

    @GetMapping("/logout")
    public String logout(HttpSession httpSession) {
        String sid = (String) httpSession.getAttribute(SESSION_ID_ATTR);
        if (sid != null) UserSessionManager.getInstance().closeSession(sid);
        httpSession.invalidate();
        return "redirect:/login";
    }

    // ─────────────────────────────────────────────────────────────
    //  PROFILE  (DECORATOR)
    // ─────────────────────────────────────────────────────────────

    @GetMapping("/profile/{id}")
    public String showProfile(@PathVariable Long id,
                              @RequestParam(required = false) String avatar,
                              @RequestParam(required = false) String theme,
                              HttpSession httpSession,
                              Model model) {
        User user = userRepository.findById(id).orElseThrow();
        String[] badges = buildBadges(user);
        UserProfile profile = profileFactory.buildProfile(
                user, user.getAvatarUrl(), user.getTheme(), badges);
        int activeSessions = UserSessionManager.getInstance().getActiveSessionCount();

        model.addAttribute("user",           user);
        model.addAttribute("profile",        profile);
        model.addAttribute("activeSessions", activeSessions);
        model.addAttribute("currentUserId",  httpSession.getAttribute(SESSION_USER_ID));
        model.addAttribute("badges",         badges);
        model.addAttribute("completeness",   (int)(profile.getProfileCompleteness() * 100));
        model.addAttribute("displayInfo",    profile.getDisplayInfo());
        model.addAttribute("avatarParam",    avatar);
        model.addAttribute("themeParam",     theme);
        return "user/profile";
    }

    private String[] buildBadges(User user) {
        int enrolled = user.getEnrolledCourses().size();
        if (enrolled >= 5) return new String[]{"Опытный студент", "Активный участник", "Первый курс"};
        if (enrolled >= 2) return new String[]{"Активный участник", "Первый курс"};
        if (enrolled >= 1) return new String[]{"Первый курс"};
        return new String[]{};
    }

    // ─────────────────────────────────────────────────────────────
    //  ENROLL  (FACADE + CHAIN OF RESPONSIBILITY + COMMAND)
    // ─────────────────────────────────────────────────────────────

    @PostMapping("/enroll")
    public String enroll(@RequestParam Long userId,
                         @RequestParam Long courseId,
                         Model model) {
        User user = userRepository.findById(userId).orElseThrow();

        // ПАТТЕРН CHAIN OF RESPONSIBILITY
        CheckResult chainResult = enrollmentChainService.check(user);

        // ПАТТЕРН FACADE
        EnrollmentFacade.EnrollmentResult facadeResult = enrollmentFacade.enrollStudent(userId, courseId);

        // ПАТТЕРН COMMAND
        if (facadeResult.isSuccess()) {
            Course course = courseRepository.findById(courseId).orElseThrow();
            EnrollCommand command = new EnrollCommand(user.getUsername(), course.getTitle(), enrollmentService);
            commandHistoryService.execute(userId, command);
        }

        model.addAttribute("result",      facadeResult);
        model.addAttribute("chainResult", chainResult);
        model.addAttribute("userId",      userId);
        model.addAttribute("courseId",    courseId);
        return "user/enroll-result";
    }

    @PostMapping("/unenroll")
    public String unenroll(@RequestParam Long userId,
                           @RequestParam Long courseId) {
        enrollmentFacade.unenrollStudent(userId, courseId);
        return "redirect:/profile/" + userId;
    }

    // ─────────────────────────────────────────────────────────────
    //  MY COURSES  (PROXY + COMMAND)
    // ─────────────────────────────────────────────────────────────

    @GetMapping("/my-courses")
    public String myCourses(HttpSession httpSession, Model model) {
        Long uid = (Long) httpSession.getAttribute(SESSION_USER_ID);
        if (uid == null) return "redirect:/login";

        User user = userRepository.findById(uid)
                .orElseThrow(() -> new IllegalArgumentException("Пользователь не найден"));

        List<Course> courses    = courseProxy.getAllCourses(user);
        List<Course> allCourses = courseRepository.findAll();

        List<String> commandLog = commandHistoryService.getLog(uid);
        boolean hasHistory      = commandHistoryService.hasHistory(uid);

        model.addAttribute("user",       user);
        model.addAttribute("courses",    courses);
        model.addAttribute("allCourses", allCourses);
        model.addAttribute("commandLog", commandLog);
        model.addAttribute("hasHistory", hasHistory);
        return "user/my-courses";
    }

    @PostMapping("/undo")
    public String undoLastCommand(HttpSession httpSession, RedirectAttributes ra) {
        Long uid = (Long) httpSession.getAttribute(SESSION_USER_ID);
        if (uid != null) {
            String msg = commandHistoryService.undoLast(uid);
            ra.addFlashAttribute("undoMessage", msg);
        }
        return "redirect:/my-courses";
    }

    // ─────────────────────────────────────────────────────────────
    //  LEARNING PROGRAM  (COMPOSITE)
    // ─────────────────────────────────────────────────────────────

    @GetMapping("/program")
    public String learningProgram(Model model) {
        List<Course> all = courseRepository.findAll();
        List<Course> beginner = all.stream()
                .filter(c -> c.getDifficultyLevel() != null &&
                        c.getDifficultyLevel().name().equals("BEGINNER"))
                .toList();
        List<Course> advanced = all.stream()
                .filter(c -> c.getDifficultyLevel() != null &&
                        !c.getDifficultyLevel().name().equals("BEGINNER"))
                .toList();

        LearningProgram program = curriculumBuilder.buildProgram(
                "Полная программа обучения",
                "Комплексный курс от начального до продвинутого уровня",
                beginner.isEmpty() ? all.subList(0, Math.min(2, all.size())) : beginner,
                advanced.isEmpty() ? all.subList(Math.min(2, all.size()), all.size()) : advanced
        );

        model.addAttribute("program",    program);
        model.addAttribute("allCourses", all);
        return "user/program";
    }

    // ─────────────────────────────────────────────────────────────
    //  USERS LIST
    // ─────────────────────────────────────────────────────────────

    @GetMapping("/users")
    public String userList(Model model) {
        model.addAttribute("users", userRepository.findAll());
        model.addAttribute("activeSessions", UserSessionManager.getInstance().getActiveSessionCount());
        return "user/list";
    }

    @PostMapping("/profile/{id}/settings")
    public String saveSettings(@PathVariable Long id,
                               @RequestParam(required = false) String avatarUrl,
                               @RequestParam(required = false) String theme) {
        User user = userRepository.findById(id).orElseThrow();
        if (avatarUrl != null) user.setAvatarUrl(avatarUrl);
        if (theme != null)     user.setTheme(theme);
        userRepository.save(user);
        return "redirect:/profile/" + id;
    }
}